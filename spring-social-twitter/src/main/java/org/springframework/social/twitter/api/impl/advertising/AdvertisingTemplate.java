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
import org.springframework.social.twitter.api.advertising.AdvertisingAccount;
import org.springframework.social.twitter.api.advertising.AdvertisingAccountFeatureQuery;
import org.springframework.social.twitter.api.advertising.AdvertisingAccountPermissions;
import org.springframework.social.twitter.api.advertising.AdvertisingAccountQuery;
import org.springframework.social.twitter.api.advertising.AdvertisingOperations;
import org.springframework.social.twitter.api.advertising.FundingInstrument;
import org.springframework.social.twitter.api.advertising.FundingInstrumentQuery;
import org.springframework.social.twitter.api.impl.AbstractTwitterOperations;
import org.springframework.social.twitter.api.impl.DataListHolder;
import org.springframework.social.twitter.api.impl.DataSingleHolder;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForUri;
import org.springframework.social.twitter.api.impl.TwitterApiUriResourceForAdvertising;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link AdvertisingOperations}, providing a binding to Twitter's direct message-oriented REST resources.
 *
 * @author Hudson Mendes
 */
public class AdvertisingTemplate extends AbstractTwitterOperations implements AdvertisingOperations {
    private final RestTemplate restTemplate;

    public AdvertisingTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
    }

    @Override
    public DataListHolder<AdvertisingAccount> getAccounts(AdvertisingAccountQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.ACCOUNTS)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<AdvertisingAccount>>() {}
                ).getBody();
    }

    @Override
    public AdvertisingAccount getAccount(String id) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.ACCOUNT)
                        .withArgument("account_id", id)
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataSingleHolder<AdvertisingAccount>>() {}
                ).getBody().getData();
    }

    @Override
    public DataListHolder<String> getAccountFeatures(String accountId, AdvertisingAccountFeatureQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.ACCOUNT_FEATURES)
                        .withArgument("account_id", accountId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<String>>() {}
                ).getBody();
    }

    @Override
    public AdvertisingAccountPermissions getAccountPermissions(String accountId) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.ACCOUNT_PERMISSIONS)
                        .withArgument("account_id", accountId)
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataSingleHolder<AdvertisingAccountPermissions>>() {}
                ).getBody().getData();
    }

    @Override
    public DataListHolder<FundingInstrument> getFundingInstruments(String accountId, FundingInstrumentQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.FUNDING_INSTRUMENTS)
                        .withArgument("account_id", accountId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataListHolder<FundingInstrument>>() {}
                ).getBody();
    }

    @Override
    public FundingInstrument getFundingInstrument(String accountId, String fundingInstrumentId) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.FUNDING_INSTRUMENT)
                        .withArgument("account_id", accountId)
                        .withArgument("funding_instrument_id", fundingInstrumentId)
                        .build(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<DataSingleHolder<FundingInstrument>>() {}
                ).getBody().getData();
    }
}
