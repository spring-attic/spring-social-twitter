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
package org.springframework.social.twitter.api.advertising;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.DataListHolder;

/**
 * Interface defining the operations for promoted tweets (Ads API).
 *
 * @author Hudson Mendes
 */
public interface PromotionOperations {

    /**
     * Retrieves a of {@link PromotableUser}.
     *
     * @param accountId identifies the account for which we are trying to promote users.
     * @param query are the parameters used to query the user
     * @return a cursored list of {@link PromotableUser}
     */
    DataListHolder<PromotableUser> getPromotableUsers(String accountId, PromotableUserQuery query);


    /**
     * Retrieves a sequence of of promoted-only {@link Tweet}
     *
     * @param accountId identifies the account for which we want to retrieve promoted-only tweets.
     * @param query are the parameters for which we want to retrieve tweets.
     * @return a cursored list of {@link Tweet}.
     */
    DataListHolder<Tweet> getSponsoredTweets(String accountId, SponsoredTweetQuery query);

    /**
     * Creates a {@link Tweet} that is a promoted-only tweet.
     *
     * @param accountId identifies the account for which the promoted-only tweet will be created.
     * @param input is the data of the tweet.
     * @return the {@link Tweet} that has been created.
     */
    Tweet createSponsoredTweet(String accountId, SponsoredTweetForm input);

    /**
     * Queries links between {@link Tweet} and {@link LineItem}.
     * Essential to advertise for targetted people.
     *
     * @param accountId identifies the account for which we will query links.
     * @param lineItemId is the line item for which we will query.
     * @param query responsible for aditional parameters of the query, such as pagination.
     * @return a cursored list of {@link PromotedTweetReference}
     */
    DataListHolder<PromotedTweetReference> getPromotedTweetReferences(String accountId, String lineItemId, PromotedTweetReferenceQuery query);

    /**
     * Queries links between {@link Tweet} and {@link LineItem}.
     * Essential to advertise for targetted people.
     *
     * @param accountId identifies the account for which we will query links.
     * @param query responsible for aditional parameters of the query, such as pagination.
     * @return a cursored list of {@link PromotedTweetReference}
     */
    DataListHolder<PromotedTweetReference> getPromotedTweetReferences(String accountId, PromotedTweetReferenceQuery query);

    /**
     * Creates a {@link PromotedTweetReference} that links a {@link LineItem} to a {@link Tweet}.
     *
     * @param accountId identifies the account for which we will create a link.
     * @param input is the information of the link that we will create.
     * @return a list of all {@link PromotedTweetReference} created for all the {@link Tweet} passed.
     */
    DataListHolder<PromotedTweetReference> createPromotedTweetReference(String accountId, PromotedTweetReferenceForm input);

    /**
     * Deletes a {@link PromotedTweetReference} by its reference.
     *
     * @param accountId identifies the account for which we will create a link.
     * @param promotedTweetId identifies the id of the promoted_tweet to be deleted.
     */
    void deletePromotedTweetReference(String accountId, String promotedTweetId);

    /**
     * Retrieves all {@link PromotedUserReference} that links a {@link LineItem} to a {@link PromotableUser} existing in the Twitter Ads system.
     *
     * @param accountId identifies the account for which we will retrieve the links
     * @param lineItemId is the line item for which we will query.
     * @param query responsible for aditional parameters of the query, such as pagination.
     * @return a cursored list of {@link PromotedUserReference}
     */
    DataListHolder<PromotedUserReference> getPromotedUserReferences(String accountId, String lineItemId, PromotedUserReferenceQuery query);

    /**
     *
     * @param accountId identifies the account for which we will create a link.
     * @param input is the information of the link that we will create.
     * @return a {@link PromotedUserReference} created for all {@link PromotableUser} passed.
     */
    PromotedUserReference createPromotedUserReferences(String accountId, PromotedUserReferenceForm input);

    /**
     *
     * @param accountId identifies the account for which we will delete the link.
     * @param promotedUserId the link that shall be deleted.
     */
    void deletePromotedUserReference(String accountId, String promotedUserId);
}
