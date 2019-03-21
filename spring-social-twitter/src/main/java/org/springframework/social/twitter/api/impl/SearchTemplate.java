/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl;

import static org.springframework.social.twitter.api.impl.SearchParametersUtil.*;

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
 * @author Craig Walls
 */
class SearchTemplate extends AbstractTwitterOperations implements SearchOperations {

	private final RestTemplate restTemplate;

	public SearchTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
		super(isAuthorizedForUser, isAuthorizedForApp);
		this.restTemplate = restTemplate;
	}

	public SearchResults search(String query) {
		return this.search(new SearchParameters(query));
	}

	public SearchResults search(String query, int resultsPerPage) {
		SearchParameters p = new SearchParameters(query).count(resultsPerPage);
		return this.search(p);
	}

	public SearchResults search(String query, int resultsPerPage, long sinceId, long maxId) {
		SearchParameters p = new SearchParameters(query).count(resultsPerPage).sinceId(sinceId);
		if (maxId > 0) {
			p.maxId(maxId);
		}
		return this.search(p);
	}

	public SearchResults search(SearchParameters searchParameters) {
		requireEitherUserOrAppAuthorization();
		Assert.notNull(searchParameters);
		MultiValueMap<String, String> parameters = buildQueryParametersFromSearchParameters(searchParameters);
		return restTemplate.getForObject(buildUri("search/tweets.json", parameters),SearchResults.class);
	}

	public List<SavedSearch> getSavedSearches() {
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("saved_searches/list.json"), SavedSearchList.class);
	}

	public SavedSearch getSavedSearch(long searchId) {
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("saved_searches/show/" + searchId + ".json"), SavedSearch.class);
	}

	public SavedSearch createSavedSearch(String query) {		
		requireUserAuthorization();
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		data.set("query", query);
		return restTemplate.postForObject(buildUri("saved_searches/create.json"), data, SavedSearch.class);
	}

	public void deleteSavedSearch(long searchId) {
		requireUserAuthorization();
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		restTemplate.postForObject(buildUri("saved_searches/destroy/" + searchId + ".json"), data, SavedSearch.class);
	}
	
	// Trends

	public Trends getLocalTrends(long whereOnEarthId) {
		return getLocalTrends(whereOnEarthId, false);
	}

	public Trends getLocalTrends(long whereOnEarthId, boolean excludeHashtags) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("id",String.valueOf(whereOnEarthId));
		if(excludeHashtags) {
			parameters.set("exclude", "hashtags");
		}
		return restTemplate.getForObject(buildUri("trends/place.json", parameters), LocalTrendsHolder.class).getTrends();
	}

}
