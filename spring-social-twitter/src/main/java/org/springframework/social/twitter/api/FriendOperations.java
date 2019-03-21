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

import org.springframework.social.ApiException;
import org.springframework.social.MissingAuthorizationException;


/**
 * Interface defining the operations for working with a user's friends and followers.
 * @author Craig Walls
 */
public interface FriendOperations {

	/**
	 * Retrieves a list of up to 5000 users that the authenticated user follows.
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the friend IDs and one call for every 100 friends).
	 * If all you need is the friend IDs, consider calling getFriendIds() instead.
	 * Or if you need only a subset of the user's friends, call UserOperations.getUsers() passing in the list of friend IDs you need. 
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<TwitterProfile> getFriends();

	/**
	 * Retrieves a list of up to 5000 users that the authenticated user follows.
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the friend IDs and one call for every 100 friends).
	 * If all you need is the friend IDs, consider calling getFriendIds() instead.
	 * Or if you need only a subset of the user's friends, call UserOperations.getUsers() passing in the list of friend IDs you need.
	 * @param cursor the cursor used to fetch the friend IDs 
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<TwitterProfile> getFriendsInCursor(long cursor);

	/**
	 * Retrieves a list of up to 5000 users that the given user follows.
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the friend IDs and one call for every 100 friends).
	 * If all you need is the friend IDs, consider calling getFriendIds() instead.
	 * Or if you need only a subset of the user's friends, call UserOperations.getUsers() passing in the list of friend IDs you need. 
	 * Supports either application or user authorization.
	 * @param userId The user's Twitter ID
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<TwitterProfile> getFriends(long userId);

	/**
	 * Retrieves a list of up to 5000 users that the given user follows.
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the friend IDs and one call for every 100 friends).
	 * If all you need is the friend IDs, consider calling getFriendIds() instead.
	 * Or if you need only a subset of the user's friends, call UserOperations.getUsers() passing in the list of friend IDs you need. 
	 * Supports either application or user authorization.
	 * @param userId The user's Twitter ID
	 * @param cursor the cursor used to fetch the friend IDs 
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<TwitterProfile> getFriendsInCursor(long userId, long cursor);

	/**
	 * Retrieves a list of up to 5000 users that the given user follows.
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the friend IDs and one call for every 100 friends).
	 * If all you need is the friend IDs, consider calling getFriendIds() instead.
	 * Or if you need only a subset of the user's friends, call UserOperations.getUsers() passing in the list of friend IDs you need. 
	 * @param screenName The user's Twitter screen name
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<TwitterProfile> getFriends(String screenName);

	/**
	 * Retrieves a list of up to 5000 users that the given user follows.
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the friend IDs and one call for every 100 friends).
	 * If all you need is the friend IDs, consider calling getFriendIds() instead.
	 * Or if you need only a subset of the user's friends, call UserOperations.getUsers() passing in the list of friend IDs you need. 
	 * Supports either application or user authorization.
	 * @param screenName The user's Twitter screen name
	 * @param cursor the cursor used to fetch the friend IDs 
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<TwitterProfile> getFriendsInCursor(String screenName, long cursor);

	/**
	 * Retrieves a list of up to 5000 IDs for the Twitter users that the authenticated user follows.
	 * Call getFriendIdsForCursor() with a cursor value to get the next/previous page of entries.
	 * @return a cursored list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<Long> getFriendIds();

	/**
	 * Retrieves a list of up to 5000 IDs for the Twitter users that the authenticated user follows.
	 * @param cursor The cursor value to fetch a specific page of entries. Use -1 for the first page of entries.
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<Long> getFriendIdsInCursor(long cursor);

	/**
	 * Retrieves a list of up to 5000 IDs for the Twitter users that the given user follows.
	 * Supports either application or user authorization.
	 * @param userId the user's Twitter ID
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<Long> getFriendIds(long userId);

	/**
	 * Retrieves a list of up to 5000 IDs for the Twitter users that the given user follows.
	 * Supports either application or user authorization.
	 * @param userId the user's Twitter ID
	 * @param cursor the cursor value to fetch a specific page of entries. Use -1 for the first page of entries.
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<Long> getFriendIdsInCursor(long userId, long cursor);

	/**
	 * Retrieves a list of up to 5000 IDs for the Twitter users that the given user follows.
	 * Supports either application or user authorization.
	 * @param screenName the user's Twitter screen name
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<Long> getFriendIds(String screenName);

	/**
	 * Retrieves a list of up to 5000 IDs for the Twitter users that the given user follows.
	 * Supports either application or user authorization.
	 * @param screenName the user's Twitter screen name
	 * @param cursor the cursor value to fetch a specific page of entries. Use -1 for the first page of entries.
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<Long> getFriendIdsInCursor(String screenName, long cursor);

	/**
	 * Retrieves a list of up to 5000 users that the authenticated user is being followed by
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the follower IDs and one call for every 100 followers).
	 * If all you need is the follower IDs, consider calling getFollowerIds() instead.
	 * Or if you need only a subset of the user's followers, call UserOperations.getUsers() passing in the list of follower IDs you need. 
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<TwitterProfile> getFollowers();

	/**
	 * Retrieves a list of up to 5000 users that the authenticated user is being followed by
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the follower IDs and one call for every 100 followers).
	 * If all you need is the follower IDs, consider calling getFollowerIds() instead.
	 * Or if you need only a subset of the user's followers, call UserOperations.getUsers() passing in the list of follower IDs you need. 
	 * @param cursor the cursor used to fetch the follower IDs 
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<TwitterProfile> getFollowersInCursor(long cursor);

	/**
	 * Retrieves a list of up to 5000 users that the given user is being followed by
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the follower IDs and one call for every 100 followers).
	 * If all you need is the follower IDs, consider calling getFollowerIds() instead.
	 * Or if you need only a subset of the user's followers, call UserOperations.getUsers() passing in the list of follower IDs you need. 
	 * Supports either application or user authorization.
	 * @param userId The user's Twitter ID
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<TwitterProfile> getFollowers(long userId);

	/**
	 * Retrieves a list of up to 5000 users that the given user is being followed by
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the follower IDs and one call for every 100 followers).
	 * If all you need is the follower IDs, consider calling getFollowerIds() instead.
	 * Or if you need only a subset of the user's followers, call UserOperations.getUsers() passing in the list of follower IDs you need. 
	 * Supports either application or user authorization.
	 * @param userId The user's Twitter ID
	 * @param cursor the cursor used to fetch the follower IDs 
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<TwitterProfile> getFollowersInCursor(long userId, long cursor);

	/**
	 * Retrieves a list of up to 5000 users that the given user is being followed by
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the follower IDs and one call for every 100 followers).
	 * If all you need is the follower IDs, consider calling getFollowerIds() instead.
	 * Or if you need only a subset of the user's followers, call UserOperations.getUsers() passing in the list of follower IDs you need. 
	 * Supports either application or user authorization.
	 * @param screenName The user's Twitter screen name
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<TwitterProfile> getFollowers(String screenName);

	/**
	 * Retrieves a list of up to 5000 users that the given user is being followed by
	 * Note that this method make multiple calls to Twitter's REST API (one call to get a list of the follower IDs and one call for every 100 followers).
	 * If all you need is the follower IDs, consider calling getFollowerIds() instead.
	 * Or if you need only a subset of the user's followers, call UserOperations.getUsers() passing in the list of follower IDs you need. 
	 * Supports either application or user authorization.
	 * @param screenName The user's Twitter screen name
	 * @param cursor the cursor used to fetch the follower IDs 
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<TwitterProfile> getFollowersInCursor(String screenName, long cursor);

	/**
	 * Retrieves a list of up to 5000 IDs for the Twitter users that follow the authenticated user.
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<Long> getFollowerIds();

	/**
	 * Retrieves a list of up to 5000 IDs for the Twitter users that follow the authenticated user.
	 * @param cursor the cursor value to fetch a specific page of entries. Use -1 for the first page of entries.
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<Long> getFollowerIdsInCursor(long cursor);

	/**
	 * Retrieves a list of up to 5000IDs for the Twitter users that follow the given user.
	 * Supports either application or user authorization.
	 * @param userId the user's Twitter ID
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<Long> getFollowerIds(long userId);

	/**
	 * Retrieves a list of up to 5000IDs for the Twitter users that follow the given user.
	 * Supports either application or user authorization.
	 * @param userId the user's Twitter ID
	 * @param cursor the cursor value to fetch a specific page of entries. Use -1 for the first page of entries.
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<Long> getFollowerIdsInCursor(long userId, long cursor);

	/**
	 * Retrieves a list of up to 5000 IDs for the Twitter users that follow the given user.
	 * Supports either application or user authorization.
	 * @param screenName the user's Twitter screen name
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<Long> getFollowerIds(String screenName);

	/**
	 * Retrieves a list of up to 5000 IDs for the Twitter users that follow the given user.
	 * Supports either application or user authorization.
	 * @param screenName the user's Twitter screen name
	 * @param cursor the cursor value to fetch a specific page of entries. Use -1 for the first page of entries.
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<Long> getFollowerIdsInCursor(String screenName, long cursor);

	/**
	 * Allows the authenticated user to follow (create a friendship) with another user.
	 * @param userId The Twitter ID of the user to follow
	 * @return the name of the followed user if successful
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	String follow(long userId);
	
	/**
	 * Allows the authenticated user to follow (create a friendship) with another user.
	 * @param screenName The screen name of the user to follow
	 * @return the name of the followed user if successful
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	String follow(String screenName);

	/**
	 * Allows the authenticated use to unfollow (destroy a friendship) with another user
	 * @param userId the Twitter ID of the user to unfollow 
	 * @return the name of the unfolloed user if successful 
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	String unfollow(long userId);
	
	/**
	 * Allows the authenticated use to unfollow (destroy a friendship) with another user
	 * @param screenName the screen name of the user to unfollow 
	 * @return the name of the unfolloed user if successful 
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	String unfollow(String screenName);
	
	/**
	 * Enable mobile device notifications from Twitter for the specified user.
	 * @param userId the Twitter ID of the user to receive notifications for. 
	 * @return the TwitterProfile for the user
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile enableNotifications(long userId);

	/**
	 * Enable mobile device notifications from Twitter for the specified user.
	 * @param screenName the Twitter screen name of the user to receive notifications for. 
	 * @return the TwitterProfile for the user
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile enableNotifications(String screenName);

	/**
	 * Disable mobile device notifications from Twitter for the specified user.
	 * @param userId the Twitter ID of the user to stop notifications for. 
	 * @return the TwitterProfile for the user
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile disableNotifications(long userId);

	/**
	 * Disable mobile device notifications from Twitter for the specified user.
	 * @param screenName the Twitter screen name of the user to stop notifications for. 
	 * @return the TwitterProfile for the user
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile disableNotifications(String screenName);

	/**
	 * @return an array of numeric IDs for every user who has a pending request to follow the authenticating user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<Long> getIncomingFriendships();

	/**
	 * @return an array of numeric IDs for every user who has a pending request to follow the authenticating user.
	 * @param cursor the cursor of the page to retrieve.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<Long> getIncomingFriendships(long cursor);

	/**
	 * @return an array of numeric IDs for every protected user for whom the authenticating user has a pending follow request.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<Long> getOutgoingFriendships();

	/**
	 * @return an array of numeric IDs for every protected user for whom the authenticating user has a pending follow request.
	 * @param cursor the cursor of the page to retrieve.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<Long> getOutgoingFriendships(long cursor);

}
