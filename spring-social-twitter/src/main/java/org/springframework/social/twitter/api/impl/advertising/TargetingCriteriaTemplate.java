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
package org.springframework.social.twitter.api.impl.advertising;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaForm;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaOperations;
import org.springframework.social.twitter.api.advertising.TargetingCriterion;
import org.springframework.social.twitter.api.advertising.TargetingCriterionForm;
import org.springframework.social.twitter.api.advertising.TargetingCriterionQuery;
import org.springframework.social.twitter.api.impl.AbstractTwitterOperations;
import org.springframework.social.twitter.api.impl.DataListHolder;
import org.springframework.social.twitter.api.impl.DataSingleHolder;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForHttpEntity;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForUri;
import org.springframework.social.twitter.api.impl.TwitterApiUriResourceForAdvertising;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link TargetingCriteriaOperations}, providing a binding to Twitter's direct message-oriented REST resources.
 *
 * @author Hudson Mendes
 */
public class TargetingCriteriaTemplate extends AbstractTwitterOperations implements TargetingCriteriaOperations {
    private final RestTemplate restTemplate;

    public TargetingCriteriaTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
    }

    @Override
    public TargetingCriterion getTargetingCriterion(String accountId, String id) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETING_CRITERIA)
                        .withArgument("account_id", accountId)
                        .withArgument("targeting_criteria_id", id)
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataSingleHolder<TargetingCriterion>>() {}
                ).getBody().getData();
    }

    @Override
    public DataListHolder<TargetingCriterion> getTargetingCriterions(String accountId, TargetingCriterionQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETING_CRITERIAS)
                        .withArgument("account_id", accountId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TargetingCriterion>>() {}
                ).getBody();
    }

    @Override
    public TargetingCriterion createTargetingCriterion(String accountId, TargetingCriterionForm data) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETING_CRITERIAS)
                        .withArgument("account_id", accountId)
                        .build(),
                HttpMethod.POST,
                new TwitterApiBuilderForHttpEntity<>(data.toRequestBody()).build(),
                new ParameterizedTypeReference<DataSingleHolder<TargetingCriterion>>() {}
                ).getBody().getData();
    }

    @Override
    public DataListHolder<TargetingCriterion> setTargetingCriteria(String accountId, TargetingCriteriaForm data) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TARGETING_CRITERIAS)
                        .withArgument("account_id", accountId)
                        .build(),
                HttpMethod.PUT,
                new TwitterApiBuilderForHttpEntity<>(data.toRequestBody()).build(),
                new ParameterizedTypeReference<DataListHolder<TargetingCriterion>>() {}
                ).getBody();
    }

    @Override
    public void deleteTargetingCriterion(String accountId, String id) {
        requireUserAuthorization();
        restTemplate.delete(new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.TARGETING_CRITERIA)
                .withArgument("account_id", accountId)
                .withArgument("targeting_criteria_id", id)
                .build());
    }
}
