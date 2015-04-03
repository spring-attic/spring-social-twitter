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

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.social.twitter.api.advertising.StatisticsOfAccountQuery;
import org.springframework.social.twitter.api.advertising.StatisticsOfCampaignQuery;
import org.springframework.social.twitter.api.advertising.StatisticsOfFundingInstrumentQuery;
import org.springframework.social.twitter.api.advertising.StatisticsOfLineItemQuery;
import org.springframework.social.twitter.api.advertising.StatisticsOfPromotedAccountQuery;
import org.springframework.social.twitter.api.advertising.StatisticsOfPromotedTweetQuery;
import org.springframework.social.twitter.api.advertising.StatisticsOperations;
import org.springframework.social.twitter.api.advertising.StatisticsSnapshot;
import org.springframework.social.twitter.api.impl.AbstractTwitterOperations;
import org.springframework.social.twitter.api.impl.DataStatsHolder;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForUri;
import org.springframework.social.twitter.api.impl.TwitterApiUriResourceForAdvertising;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link StatisticsOperations}, providing a binding to
 * Twitter's direct message-oriented REST resources.
 * 
 * @author Hudson Mendes
 */
public class StatisticsTemplate extends AbstractTwitterOperations implements StatisticsOperations {
    private final RestTemplate restTemplate;

    public StatisticsTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
    }

    @Override
    public List<StatisticsSnapshot> byAccounts(String accountId, StatisticsOfAccountQuery query) {
        requireUserAuthorization();
        URI uri = new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.STATS_ACCOUNT)
                .withArgument("account_id", accountId)
                .withArgument(query.toQueryParameters())
                .build();

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                DataStatsHolder.class).getBody().getList();
    }

    @Override
    public List<StatisticsSnapshot> byCampaigns(String accountId, StatisticsOfCampaignQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.STATS_CAMPAIGNS)
                        .withArgument("account_id", accountId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                DataStatsHolder.class).getBody().getList();
    }

    @Override
    public List<StatisticsSnapshot> byCampaign(String accountId, String campaignId, StatisticsOfCampaignQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.STATS_CAMPAIGN)
                        .withArgument("account_id", accountId)
                        .withArgument("campaign_id", campaignId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                DataStatsHolder.class).getBody().getList();
    }

    @Override
    public List<StatisticsSnapshot> byFundingInstruments(String accountId, StatisticsOfFundingInstrumentQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.STATS_FUNDING_INSTRUMENTS)
                        .withArgument("account_id", accountId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                DataStatsHolder.class).getBody().getList();
    }

    @Override
    public List<StatisticsSnapshot> byFundingInstrument(String accountId, String fundingInstrumentId, StatisticsOfFundingInstrumentQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.STATS_FUNDING_INSTRUMENT)
                        .withArgument("account_id", accountId)
                        .withArgument("funding_instrument_id", fundingInstrumentId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                DataStatsHolder.class).getBody().getList();
    }

    @Override
    public List<StatisticsSnapshot> byLineItems(String accountId, StatisticsOfLineItemQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.STATS_LINE_ITEMS)
                        .withArgument("account_id", accountId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                DataStatsHolder.class).getBody().getList();
    }

    @Override
    public List<StatisticsSnapshot> byLineItem(String accountId, String lineItemId, StatisticsOfLineItemQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.STATS_LINE_ITEM)
                        .withArgument("account_id", accountId)
                        .withArgument("line_item_id", lineItemId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                DataStatsHolder.class).getBody().getList();
    }

    @Override
    public List<StatisticsSnapshot> byPromotedAccounts(String accountId, StatisticsOfPromotedAccountQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.STATS_PROMOTED_ACCOUNTS)
                        .withArgument("account_id", accountId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                DataStatsHolder.class).getBody().getList();
    }

    @Override
    public List<StatisticsSnapshot> byPromotedAccount(String accountId, String promotedAccountId, StatisticsOfPromotedAccountQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.STATS_PROMOTED_ACCOUNT)
                        .withArgument("account_id", accountId)
                        .withArgument("promoted_account_id", promotedAccountId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                DataStatsHolder.class).getBody().getList();
    }

    @Override
    public List<StatisticsSnapshot> byPromotedTweets(String accountId, StatisticsOfPromotedTweetQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.STATS_PROMOTED_TWEETS)
                        .withArgument("account_id", accountId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                DataStatsHolder.class).getBody().getList();
    }

    @Override
    public List<StatisticsSnapshot> byPromotedTweet(String accountId, String promotedTweetId, StatisticsOfPromotedTweetQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
                new TwitterApiBuilderForUri()
                        .withResource(TwitterApiUriResourceForAdvertising.STATS_PROMOTED_TWEET)
                        .withArgument("account_id", accountId)
                        .withArgument("promoted_tweet_id", promotedTweetId)
                        .withArgument(query.toQueryParameters())
                        .build(),
                HttpMethod.GET,
                null,
                DataStatsHolder.class).getBody().getList();
    }
}
