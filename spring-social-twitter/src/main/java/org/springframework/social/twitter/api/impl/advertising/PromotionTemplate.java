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
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.advertising.PromotableUser;
import org.springframework.social.twitter.api.advertising.PromotableUserQuery;
import org.springframework.social.twitter.api.advertising.PromotedTweetReference;
import org.springframework.social.twitter.api.advertising.PromotedTweetReferenceForm;
import org.springframework.social.twitter.api.advertising.PromotedTweetReferenceQuery;
import org.springframework.social.twitter.api.advertising.PromotedUserReference;
import org.springframework.social.twitter.api.advertising.PromotedUserReferenceForm;
import org.springframework.social.twitter.api.advertising.PromotedUserReferenceQuery;
import org.springframework.social.twitter.api.advertising.PromotionOperations;
import org.springframework.social.twitter.api.advertising.SponsoredTweetForm;
import org.springframework.social.twitter.api.advertising.SponsoredTweetQuery;
import org.springframework.social.twitter.api.impl.AbstractTwitterOperations;
import org.springframework.social.twitter.api.impl.DataListHolder;
import org.springframework.social.twitter.api.impl.DataSingleHolder;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForHttpEntity;
import org.springframework.social.twitter.api.impl.TwitterApiBuilderForUri;
import org.springframework.social.twitter.api.impl.TwitterApiUriResourceForAdvertising;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author Hudson Mendes
 */
public class PromotionTemplate extends AbstractTwitterOperations implements PromotionOperations {
    private final RestTemplate restTemplate;

    public PromotionTemplate(RestTemplate restTemplate, boolean isUserAuthorized, boolean isAppAuthorized) {
        super(isUserAuthorized, isAppAuthorized);
        this.restTemplate = restTemplate;
    }

    @Override
    public DataListHolder<PromotableUser> getPromotableUsers(
            String accountId,
            PromotableUserQuery query) {

        requireUserAuthorization();
        return restTemplate.exchange(
            new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.PROMOTABLE_USERS)
                .withArgument("account_id", accountId)
                .withArgument(query.toQueryParameters())
                .build(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<DataListHolder<PromotableUser>>() {}).getBody();
    }

    @Override
    public DataListHolder<Tweet> getSponsoredTweets(
            String accountId,
            SponsoredTweetQuery query) {

        requireUserAuthorization();
        return restTemplate.exchange(
            new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.SPONSORED_TWEETS)
                .withArgument("account_id", accountId)
                .withArgument("scoped_to", "none")
                .withArgument(query.toQueryParameters())
                .build(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<DataListHolder<Tweet>>() {}).getBody();
    }

    @Override
    public Tweet createSponsoredTweet(
            String accountId,
            SponsoredTweetForm input) {

        requireUserAuthorization();
        return restTemplate.exchange(
            new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.SPONSORED_TWEETS_CREATE)
                .withArgument("account_id", accountId)
                .build(),
            HttpMethod.POST,
            new TwitterApiBuilderForHttpEntity<>(input.toRequestBody()).build(),
            new ParameterizedTypeReference<DataSingleHolder<Tweet>>() {}).getBody().getData();
    }

    @Override
    public DataListHolder<PromotedTweetReference> getPromotedTweetReferences(
            String accountId,
            String lineItemId,
            PromotedTweetReferenceQuery query) {

        requireUserAuthorization();

        TwitterApiBuilderForUri uriBuilder = new TwitterApiBuilderForUri()
            .withResource(TwitterApiUriResourceForAdvertising.PROMOTED_TWEET_REFERENCES)
            .withArgument("account_id", accountId)
            .withArgument(query.toQueryParameters());

        if (!StringUtils.isEmpty(lineItemId))
            uriBuilder = uriBuilder.withArgument("line_item_id", lineItemId);

        return restTemplate.exchange(
            uriBuilder.build(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<DataListHolder<PromotedTweetReference>>() {}).getBody();
    }

    @Override
    public DataListHolder<PromotedTweetReference> getPromotedTweetReferences(
            String accountId,
            PromotedTweetReferenceQuery query) {

        return getPromotedTweetReferences(
            accountId,
            null,
            query);
    }

    @Override
    public DataListHolder<PromotedTweetReference> createPromotedTweetReference(
            String accountId,
            PromotedTweetReferenceForm input) {

        requireUserAuthorization();
        return restTemplate.exchange(
            new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.PROMOTED_TWEET_REFERENCES)
                .withArgument("account_id", accountId)
                .build(),
            HttpMethod.POST,
            new TwitterApiBuilderForHttpEntity<>(input.toRequestBody()).build(),
            new ParameterizedTypeReference<DataListHolder<PromotedTweetReference>>() {}).getBody();
    }

    @Override
    public void deletePromotedTweetReference(String accountId, String promotedTweetId) {
        requireUserAuthorization();
        restTemplate.delete(
            new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.PROMOTED_TWEET_REFERENCE)
                .withArgument("account_id", accountId)
                .withArgument("promoted_tweet_id", promotedTweetId)
                .build());
    }

    @Override
    public DataListHolder<PromotedUserReference> getPromotedUserReferences(String accountId, String lineItemId, PromotedUserReferenceQuery query) {
        requireUserAuthorization();
        return restTemplate.exchange(
            new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.PROMOTED_USER_REFERENCES)
                .withArgument("account_id", accountId)
                .withArgument("line_item_id", lineItemId)
                .withArgument(query.toQueryParameters())
                .build(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<DataListHolder<PromotedUserReference>>() {}).getBody();
    }

    @Override
    public PromotedUserReference createPromotedUserReferences(String accountId, PromotedUserReferenceForm input) {
        requireUserAuthorization();
        return restTemplate.exchange(
            new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.PROMOTED_USER_REFERENCES)
                .withArgument("account_id", accountId)
                .build(),
            HttpMethod.POST,
            new TwitterApiBuilderForHttpEntity<>(input.toRequestBody()).build(),
            new ParameterizedTypeReference<DataSingleHolder<PromotedUserReference>>() {}).getBody().getData();
    }

    @Override
    public void deletePromotedUserReference(String accountId, String promotedUserId) {
        requireUserAuthorization();
        restTemplate.delete(
            new TwitterApiBuilderForUri()
                .withResource(TwitterApiUriResourceForAdvertising.PROMOTED_USER_REFERENCE)
                .withArgument("account_id", accountId)
                .withArgument("promoted_account_id", promotedUserId)
                .build());
    }
}
