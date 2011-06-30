/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api;

import java.util.List;

import org.springframework.social.ApiException;
import org.springframework.social.MissingAuthorizationException;


/**
 * Interface defining the operations for sending and retrieving tweets. 
 * @author Craig Walls
 */
public interface TimelineOperations {

	/**
	 * Retrieves the 20 most recently posted tweets from the public timeline.
	 * The public timeline is the timeline containing tweets from all Twitter
	 * users. As this is the public timeline, authentication is not required to
	 * use this method.
	 * <p>
	 * Note that Twitter caches public timeline results for 60 seconds. Calling
	 * this method more frequently than that will count against rate limits and
	 * will not return any new results.
	 * </p>
	 * 
	 * @return a collection of {@link Tweet}s in the public timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Tweet> getPublicTimeline();

	/**
	 * Retrieves the 20 most recently posted tweets, including retweets, from
	 * the authenticating user's home timeline. The home timeline includes
	 * tweets from the user's timeline and the timeline of anyone that they
	 * follow.
	 * 
	 * @return a collection of {@link Tweet}s in the authenticating user's home timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getHomeTimeline();

	/**
	 * Retrieves the 20 most recent tweets posted by the authenticating user.
	 * @return a collection of {@link Tweet}s that have been posted by the authenticating user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getUserTimeline();

	/**
	 * Retrieves the 20 most recent tweets posted by the given user.
	 * @param screenName The screen name of the user whose timeline is being requested.
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Tweet> getUserTimeline(String screenName);

	/**
	 * Retrieves the 20 most recent tweets posted by the given user.
	 * @param userId The user ID of the user whose timeline is being requested.
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Tweet> getUserTimeline(long userId);

	/**
	 * Retrieve the 20 most recent tweets that mention the authenticated user.
	 * @return a collection of {@link Tweet} objects that mention the authenticated user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getMentions();

	/**
	 * Retrieve the 20 most recent retweets posted by the authenticated user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getRetweetedByMe();

	/**
	 * Retrieve the 20 most recent retweets posted by users the authenticating user follow.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getRetweetedToMe();

	/**
	 * Retrieve the 20 most recent tweets of the authenticated user that have been retweeted by others.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getRetweetsOfMe();

	/**
	 * Returns a single tweet.
	 * @param tweetId the tweet's ID
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	Tweet getStatus(long tweetId);

	/**
	 * Updates the user's status.
	 * @param status The status message
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws DuplicateTweetException if the status message duplicates a previously posted status.
	 * @throws MessageTooLongException if the length of the status message exceeds Twitter's 140 character limit.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	void updateStatus(String status);

	/**
	 * Updates the user's status, including additional metadata concerning the status.
	 * @param status The status message
	 * @param details Metadata pertaining to the status
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws DuplicateTweetException if the status message duplicates a previously posted status.
	 * @throws MessageTooLongException if the length of the status message exceeds Twitter's 140 character limit.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	void updateStatus(String status, StatusDetails details);

	/**
	 * Removes a status entry.
	 * @param tweetId the tweet's ID
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	void deleteStatus(long tweetId);

	/**
	 * Posts a retweet of an existing tweet.
	 * @param tweetId The ID of the tweet to be retweeted
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	void retweet(long tweetId);

	/**
	 * Retrieves up to 100 retweets of a specific tweet.
	 * @param tweetId the tweet's ID
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Tweet> getRetweets(long tweetId);

	/**
	 * Retrieves the profiles of up to 100 users how have retweeted a specific tweet.
	 * @param id the tweet's ID
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<TwitterProfile> getRetweetedBy(long id);

	/**
	 * Retrieves the IDs of up to 100 users who have retweeted a specific tweet.
	 * @param id the tweet's ID.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Long> getRetweetedByIds(long id);

	/**
	 * Retrieves the 20 most recent tweets favorited by the given user.
	 * 
	 * @return a collection of {@link Tweet}s from the specified user's favorite timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getFavorites();

	/**
	 * Adds a tweet to the user's collection of favorite tweets.
	 * @param id the tweet's ID
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	void addToFavorites(long id);

	/**
	 * Removes a tweet from the user's collection of favorite tweets.
	 * @param id the tweet's ID
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	void removeFromFavorites(long id);

}
