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
import org.springframework.social.twitter.api.advertising.GlobalOptOut;
import org.springframework.social.twitter.api.advertising.GlobalOptOutForm;
import org.springframework.social.twitter.api.advertising.TailoredAudience;
import org.springframework.social.twitter.api.advertising.TailoredAudienceChange;
import org.springframework.social.twitter.api.advertising.TailoredAudienceChangeForm;
import org.springframework.social.twitter.api.advertising.TailoredAudienceForm;
import org.springframework.social.twitter.api.advertising.TailoredAudienceOperations;
import org.springframework.social.twitter.api.advertising.TailoredAudienceQuery;
import org.springframework.social.twitter.api.impl.AbstractTwitterOperations;
import org.springframework.social.twitter.api.impl.DataListHolder;
import org.springframework.social.twitter.api.impl.DataSingleHolder;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForHttpEntity;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForUri;
import org.springframework.social.twitter.api.impl.TwitterApiUriResourceForAdvertising;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link TailoredAudience}, providing a binding to
 * Twitter's direct message-oriented REST resources.
 * 
 * @author Hudson Mendes
 */
public class TailoredAudienceTemplate extends AbstractTwitterOperations implements TailoredAudienceOperations {
    private final RestTemplate restTemplate;

    public TailoredAudienceTemplate(RestTemplate restTemplate, boolean isUserAuthorized, boolean isAppAuthorized) {
        super(isUserAuthorized, isAppAuthorized);
        this.restTemplate = restTemplate;
    }

    @Override
    public TailoredAudience getTailoredAudience(String accountId, String id) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TAILORED_AUDIENCE)
                        .withArgument("account_id", accountId)
                        .withArgument("tailored_audience_id", id)
                        .withArgument("with_deleted", true)
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataSingleHolder<TailoredAudience>>() {}
                ).getBody().getData();
    }

    @Override
    public DataListHolder<TailoredAudience> getTailoredAudiences(String accountId, TailoredAudienceQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TAILORED_AUDIENCES)
                        .withArgument("account_id", accountId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<TailoredAudience>>() {}
                ).getBody();
    }

    @Override
    public TailoredAudience createTailoredAudience(String accountId, TailoredAudienceForm input) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TAILORED_AUDIENCES)
                        .withArgument("account_id", accountId)
                        .build(),
                HttpMethod.POST,
                new TwitterApiBuilderForHttpEntity<>(input.toRequestBody()).build(),
                new ParameterizedTypeReference<DataSingleHolder<TailoredAudience>>() {}
                ).getBody().getData();
    }

    @Override
    public void deleteTailoredAudience(String accountId, String id) {
        requireUserAuthorization();
        restTemplate.delete(new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.TAILORED_AUDIENCE)
                .withArgument("account_id", accountId)
                .withArgument("tailored_audience_id", id)
                .build());
    }

    @Override
    public TailoredAudienceChange createTailoredAudienceChange(String accountId, TailoredAudienceChangeForm input) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TAILORED_AUDIENCE_CHANGES)
                        .withArgument("account_id", accountId)
                        .build(),
                HttpMethod.POST,
                new TwitterApiBuilderForHttpEntity<>(input.toRequestBody()).build(),
                new ParameterizedTypeReference<DataSingleHolder<TailoredAudienceChange>>() {}
                ).getBody().getData();
    }

    @Override
    public TailoredAudienceChange getTailoredAudienceChange(String accountId, String id) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.TAILORED_AUDIENCE_CHANGE)
                        .withArgument("account_id", accountId)
                        .withArgument("tailored_audience_change_id", id)
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataSingleHolder<TailoredAudienceChange>>() {}
                ).getBody().getData();
    }

    @Override
    public GlobalOptOut createGlobalOptOut(String accountId, GlobalOptOutForm input) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.GLOBAL_OPT_OUT)
                        .withArgument("account_id", accountId)
                        .build(),
                HttpMethod.PUT,
                new TwitterApiBuilderForHttpEntity<>(input.toRequestBody()).build(),
                new ParameterizedTypeReference<DataSingleHolder<GlobalOptOut>>() {}
                ).getBody().getData();
    }

}
