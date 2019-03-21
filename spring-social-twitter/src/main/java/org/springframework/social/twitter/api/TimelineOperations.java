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
package org.springframework.social.twitter.api;

import java.util.List;

import org.springframework.social.ApiException;
import org.springframework.social.DuplicateStatusException;
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
	 * Supports either user or application authorization.
	 * @param screenName The screen name of the user whose timeline is being requested.
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getUserTimeline(String screenName);

	/**
	 * Retrieves tweets posted by the given user. The most recent tweets are listed first.
	 * Supports either user or application authorization.
	 * @param screenName The screen name of the user whose timeline is being requested.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getUserTimeline(String screenName, int pageSize);

	/**
	 * Retrieves tweets posted by the given user. The most recent tweets are listed first.
	 * Supports either user or application authorization.
	 * @param screenName The screen name of the user whose timeline is being requested.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @param sinceId The minimum {@link Tweet} ID to return in the results
	 * @param maxId The maximum {@link Tweet} ID to return in the results
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getUserTimeline(String screenName, int pageSize, long sinceId, long maxId);

	/**
	 * Retrieves the 20 most recent tweets posted by the given user.
	 * Supports either user or application authorization.
	 * @param userId The user ID of the user whose timeline is being requested.
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getUserTimeline(long userId);

	/**
	 * Retrieves tweets posted by the given user. The most recent tweets are listed first.
	 * Supports either user or application authorization.
	 * @param userId The user ID of the user whose timeline is being requested.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getUserTimeline(long userId, int pageSize);

	/**
	 * Retrieves tweets posted by the given user. The most recent tweets are listed first.
	 * Supports either user or application authorization.
	 * @param userId The user ID of the user whose timeline is being requested.
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 200. (Will return at most 200 entries, even if pageSize is greater than 200.)
	 * @param sinceId The minimum {@link Tweet} ID to return in the results
	 * @param maxId The maximum {@link Tweet} ID to return in the results
	 * @return a collection of {@link Tweet}s from the specified user's timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getUserTimeline(long userId, int pageSize, long sinceId, long maxId);

	/**
	 * Retrieve the 20 most recent tweets of the authenticated user that have been retweeted by others.
	 * @return a list of Tweets
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getRetweetsOfMe();
	
	/**
	 * Retrieve tweets of the authenticated user that have been retweeted by others.  The most recent tweets are listed first.
	 * @param page The page to return
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 100. (Will return at most 100 entries, even if pageSize is greater than 100.) 
	 * @return a list of Tweets
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getRetweetsOfMe(int page, int pageSize);
	
	/**
	 * Retrieve tweets of the authenticated user that have been retweeted by others.  The most recent tweets are listed first.
	 * @param page The page to return
	 * @param pageSize The number of {@link Tweet}s per page. Should be less than or equal to 100. (Will return at most 100 entries, even if pageSize is greater than 100.) 
	 * @param sinceId The minimum {@link Tweet} ID to return in the results.
	 * @param maxId The maximum {@link Tweet} ID to return in the results.
	 * @return a list of Tweets
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Tweet> getRetweetsOfMe(int page, int pageSize, long sinceId, long maxId);
	
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
	 * Supports either user or application authorization.
	 * @param tweetId the tweet's ID
	 * @return the Tweet object
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	Tweet getStatus(long tweetId);

	/**
	 * Returns a single tweet as an oEmbed representation.
	 * Supports either user or application authorization.
	 * @param tweetId the tweet's ID
	 * @return the Tweet object
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	OEmbedTweet getStatusOEmbed(String tweetId);

	/**
	 * Returns a single tweet as an oEmbed representation.
	 * Supports either user or application authorization.
	 * @param tweetId the tweet's ID
	 * @param options options for the embedded tweet
	 * @return an OEmbedTweet representing the tweet in oEmbed form
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	OEmbedTweet getStatusOEmbed(String tweetId, OEmbedOptions options);

	/**
	 * Updates the user's status.
	 * @param status The status message
	 * @return the Tweet object
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws DuplicateStatusException if the status message duplicates a previously posted status.
	 * @throws MessageTooLongException if the length of the status message exceeds Twitter's 140 character limit.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	Tweet updateStatus(String status);
	
	/**
	 * Updates the user's status, including any additional metadata about the status carried in TweetData
	 * @param tweetData The data defining the status.
	 * @return the Tweet object
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws DuplicateStatusException if the status message duplicates a previously posted status.
	 * @throws MessageTooLongException if the length of the status message exceeds Twitter's 140 character limit.
	 * @throws OperationNotPermittedException if the photo resource isn't a GIF, JPG, or PNG.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	Tweet updateStatus(TweetData tweetData);
	
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
	 * @return the Tweet object representing the retweet
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	Tweet retweet(long tweetId);

	/**
	 * Retrieves up to 100 retweets of a specific tweet.
	 * Supports either user or application authorization.
	 * @param tweetId the tweet's ID
	 * @return A list of Tweet objects representing the retweets
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getRetweets(long tweetId);

	/**
	 * Retrieves retweets of a specific tweet.
	 * Supports either user or application authorization.
	 * @param tweetId the tweet's ID
	 * @param count The maximum number of retweets to return. Should be less than or equal to 100. (Will return at most 100 entries, even if pageSize is greater than 100.)
	 * @return A list of Tweet objects representing the retweets
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
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
	 * Retrieves the 20 most recent tweets favorited by the specified user.
	 * Supports either user or application authorization.
	 * @param userId The user ID of the user whose favorites are being requested.
	 * @return a collection of {@link Tweet}s from the specified user's favorite timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getFavorites(long userId);

	/**
	 * Retrieves tweets favorited by the specified user.
	 * Supports either user or application authorization.
	 * @param userId The user ID of the user whose favorites are being requested.
	 * @param pageSize The number of entries per page.
	 * @return a collection of {@link Tweet}s from the specified user's favorite timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getFavorites(long userId, int pageSize);

	/**
	 * Retrieves the 20 most recent tweets favorited by the specified user.
	 * Supports either user or application authorization.
	 * @param screenName The screen name of the user whose favorites are being requested.
	 * @return a collection of {@link Tweet}s from the specified user's favorite timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getFavorites(String screenName);

	/**
	 * Retrieves tweets favorited by the specified user.
	 * Supports either user or application authorization.
	 * @param screenName The screen name of the user whose favorites are being requested.
	 * @param pageSize The number of entries per page.
	 * @return a collection of {@link Tweet}s from the specified user's favorite timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getFavorites(String screenName, int pageSize);

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
