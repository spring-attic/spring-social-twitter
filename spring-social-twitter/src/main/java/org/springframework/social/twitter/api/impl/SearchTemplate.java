/*
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl;

import static org.springframework.social.twitter.api.impl.SearchParametersUtil.buildQueryParametersFromSearchParameters;

import java.util.List;

import org.springframework.social.twitter.api.SavedSearch;
import org.springframework.social.twitter.api.SearchOperations;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Trends;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link SearchOperations}, providing a binding to Twitter's search and trend-oriented REST resources.
 * 
 * @author Craig Walls
 */
public class SearchTemplate extends AbstractTwitterOperations implements SearchOperations {
    private static final MultiValueMap<String, Object> EMPTY_DATA = new LinkedMultiValueMap<String, Object>();
    private final RestTemplate restTemplate;

    public SearchTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
    }

    @Override
    public SearchResults search(String query) {
        return this.search(new SearchParameters(query));
    }

    @Override
    public SearchResults search(String query, int resultsPerPage) {
        SearchParameters p = new SearchParameters(query).count(resultsPerPage);
        return this.search(p);
    }

    @Override
    public SearchResults search(String query, int resultsPerPage, long sinceId, long maxId) {
        SearchParameters p = new SearchParameters(query).count(resultsPerPage).sinceId(sinceId);
        if (maxId > 0) {
            p.maxId(maxId);
        }
        return this.search(p);
    }

    @Override
    public SearchResults search(SearchParameters searchParameters) {
        requireEitherUserOrAppAuthorization();
        Assert.notNull(searchParameters);
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.SEARCH_TWEETS)
                        .withArgument(buildQueryParametersFromSearchParameters(searchParameters))
                        .build(),
                SearchResults.class);
    }

    @Override
    public List<SavedSearch> getSavedSearches() {
        requireUserAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.SAVED_SEARCHES_LIST)
                        .build(),
                SavedSearchList.class);
    }

    @Override
    public SavedSearch getSavedSearch(long searchId) {
        requireUserAuthorization();
        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.SAVED_SEARCHES_SHOW)
                        .withArgument("search_id", searchId)
                        .build(),
                SavedSearch.class);
    }

    @Override
    public SavedSearch createSavedSearch(String query) {
        requireUserAuthorization();
        return restTemplate.postForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.SAVED_SEARCHES_CREATE)
                        .build(),
                new RestRequestBodyBuilder()
                        .withField("query", query)
                        .build(),
                SavedSearch.class);
    }

    @Override
    public void deleteSavedSearch(long searchId) {
        requireUserAuthorization();
        restTemplate.postForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.SAVED_SEARCHES_DESTROY)
                        .withArgument("search_id", searchId)
                        .build(),
                EMPTY_DATA,
                SavedSearch.class);
    }

    // Trends

    @Override
    public Trends getLocalTrends(long whereOnEarthId) {
        return getLocalTrends(whereOnEarthId, false);
    }

    @Override
    public Trends getLocalTrends(long whereOnEarthId, boolean excludeHashtags) {
        requireEitherUserOrAppAuthorization();

        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.set("id", String.valueOf(whereOnEarthId));
        if (excludeHashtags) {
            parameters.set("exclude", "hashtags");
        }

        return restTemplate.getForObject(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForStandard.TRENDS_PLACE)
                        .withArgument(parameters)
                        .build(),
                LocalTrendsHolder.class
                ).getTrends();
    }

}
