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

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.social.ApiException;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.OperationNotPermittedException;


/**
 * Interface defining the operations for sending and retrieving tweets. 
 * @author Craig Walls
 */
public interface TimelineOperations {

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
	 * Retrieves tweets, including retweets, from the authenticating user's home timeline. 
	 * The home timeline includes tweets from the user's timeline and the timeline of anyone that they follow.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @return a collection of {@link Tweet}s in the authenticating user's home timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getHomeTimeline(int pageSize);

	/**
	 * Retrieves tweets, including retweets, from the authenticating user's home timeline. 
	 * The home timeline includes tweets from the user's timeline and the timeline of anyone that they follow.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @param sinceId The minimum {@link Tweet} ID to return in the results
	 * @param maxId The maximum {@link Tweet} ID to return in the results
	 * @return a collection of {@link Tweet}s in the authenticating user's home timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getHomeTimeline(int pageSize, long sinceId, long maxId);

	/**
	 * Retrieves the 20 most recent tweets posted by the authenticating user.
	 * @return a collection of {@link Tweet}s that have been posted by the authenticating user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getUserTimeline();

	/**
	 * Retrieves tweets posted by the authenticating user. The most recent tweets are listed first.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @return a collection of {@link Tweet}s that have been posted by the authenticating user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getUserTimeline(int pageSize);

	/**
	 * Retrieves tweets posted by the authenticating user. The most recent tweets are listed first.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @param sinceId The minimum {@link Tweet} ID to return in the results
	 * @param maxId The maximum {@link Tweet} ID to return in the results
	 * @return a collection of {@link Tweet}s that have been posted by the authenticating user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getUserTimeline(int pageSize, long sinceId, long maxId);

	/**
	 * Retrieves the 20 most recent tweets posted by the given user.
	 * @param screenName The screen name of the user whose timeline is being requested.
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Tweet> getUserTimeline(String screenName);

	/**
	 * Retrieves tweets posted by the given user. The most recent tweets are listed first.
	 * @param screenName The screen name of the user whose timeline is being requested.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Tweet> getUserTimeline(String screenName, int pageSize);

	/**
	 * Retrieves tweets posted by the given user. The most recent tweets are listed first.
	 * @param screenName The screen name of the user whose timeline is being requested.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @param sinceId The minimum {@link Tweet} ID to return in the results
	 * @param maxId The maximum {@link Tweet} ID to return in the results
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Tweet> getUserTimeline(String screenName, int pageSize, long sinceId, long maxId);

	/**
	 * Retrieves the 20 most recent tweets posted by the given user.
	 * @param userId The user ID of the user whose timeline is being requested.
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Tweet> getUserTimeline(long userId);

	/**
	 * Retrieves tweets posted by the given user. The most recent tweets are listed first.
	 * @param userId The user ID of the user whose timeline is being requested.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Tweet> getUserTimeline(long userId, int pageSize);

	/**
	 * Retrieves tweets posted by the given user. The most recent tweets are listed first.
	 * @param userId The user ID of the user whose timeline is being requested.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @param sinceId The minimum {@link Tweet} ID to return in the results
	 * @param maxId The maximum {@link Tweet} ID to return in the results
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Tweet> getUserTimeline(long userId, int pageSize, long sinceId, long maxId);

	/**
	 * Retrieve the 20 most recent tweets that mention the authenticated user.
	 * @return a collection of {@link Tweet} objects that mention the authenticated user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getMentions();

	/**
	 * Retrieve tweets that mention the authenticated user. The most recent tweets are listed first.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @return a collection of {@link Tweet} objects that mention the authenticated user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getMentions(int pageSize);

	/**
	 * Retrieve tweets that mention the authenticated user. The most recent tweets are listed first.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @param sinceId The minimum {@link Tweet} ID to return in the results
	 * @param maxId The maximum {@link Tweet} ID to return in the results
	 * @return a collection of {@link Tweet} objects that mention the authenticated user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getMentions(int pageSize, long sinceId, long maxId);

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
	Tweet updateStatus(String status);

	/**
	 * Updates the user's status along with a picture.
	 * @param status The status message
	 * @param photo A {@link Resource} for the photo data. The given Resource must implement the getFilename() method (such as {@link FileSystemResource} or {@link ClassPathResource}) and must contain GIF, JPG, or PNG data.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws DuplicateTweetException if the status message duplicates a previously posted status.
	 * @throws MessageTooLongException if the length of the status message exceeds Twitter's 140 character limit.
	 * @throws OperationNotPermittedException if the photo resource isn't a GIF, JPG, or PNG.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	Tweet updateStatus(String status, Resource photo);

	/**
	 * Updates the user's status, including additional metadata concerning the status.
	 * @param status The status message
	 * @param details Metadata pertaining to the status
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws DuplicateTweetException if the status message duplicates a previously posted status.
	 * @throws MessageTooLongException if the length of the status message exceeds Twitter's 140 character limit.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	Tweet updateStatus(String status, StatusDetails details);

	/**
	 * Updates the user's status, including a picture and additional metadata concerning the status.
	 * @param status The status message
	 * @param photo A {@link Resource} for the photo data. The given Resource must implement the getFilename() method (such as {@link FileSystemResource} or {@link ClassPathResource}) and must contain GIF, JPG, or PNG data.
	 * @param details Metadata pertaining to the status
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws DuplicateTweetException if the status message duplicates a previously posted status.
	 * @throws MessageTooLongException if the length of the status message exceeds Twitter's 140 character limit.
	 * @throws OperationNotPermittedException if the photo resource isn't a GIF, JPG, or PNG.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	Tweet updateStatus(String status, Resource photo, StatusDetails details);

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
	 * Retrieves retweets of a specific tweet.
	 * @param tweetId the tweet's ID
	 * @param count The maximum number of retweets to return. Should be less than or equal to 100. (Will return at most 100 entries, even if pageSize is greater than 100.)
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Tweet> getRetweets(long tweetId, int count);

	/**
	 * Retrieves the 20 most recent tweets favorited by the authenticated user.
	 * @return a collection of {@link Tweet}s from the specified user's favorite timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getFavorites();

	/**
	 * Retrieves tweets favorited by the authenticated user.
	 * @param pageSize The number of entries per page.
	 * @return a collection of {@link Tweet}s from the specified user's favorite timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getFavorites(int pageSize);

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
