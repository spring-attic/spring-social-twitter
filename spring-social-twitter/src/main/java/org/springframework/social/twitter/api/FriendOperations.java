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
import org.springframework.social.NotAuthorizedException;


/**
 * Interface defining the operations for working with a user's friends and followers.
 * @author Craig Walls
 */
public interface FriendOperations {

	/**
	 * Retrieves a list of users that the authenticated user follows.
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<TwitterProfile> getFriends();

	/**
	 * Retrieves a list of users that the given user follows.
	 * @param userId The user's Twitter ID
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<TwitterProfile> getFriends(long userId);

	/**
	 * Retrieves a list of users that the given user follows.
	 * @param screenName The user's Twitter screen name
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<TwitterProfile> getFriends(String screenName);

	/**
	 * Retrieves a list of IDs for the Twitter users that the authenticated user follows.
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Long> getFriendIds();

	/**
	 * Retrieves a list of IDs for the Twitter users that the given user follows.
	 * @param userId the user's Twitter ID
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Long> getFriendIds(long userId);

	/**
	 * Retrieves a list of IDs for the Twitter users that the given user follows.
	 * @param screenName the user's Twitter screen name
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Long> getFriendIds(String screenName);

	/**
	 * Retrieves a list of users that the authenticated user is being followed by
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<TwitterProfile> getFollowers();

	/**
	 * Retrieves a list of users that the given user is being followed by
	 * @param userId The user's Twitter ID
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<TwitterProfile> getFollowers(long userId);
	
	/**
	 * Retrieves a list of users that the given user is being followed by
	 * @param screenName The user's Twitter screen name
	 * @return a list of TwitterProfiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<TwitterProfile> getFollowers(String screenName);
	
	/**
	 * Retrieves a list of IDs for the Twitter users that follow the authenticated user.
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Long> getFollowerIds();

	/**
	 * Retrieves a list of IDs for the Twitter users that follow the given user.
	 * @param userId the user's Twitter ID
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Long> getFollowerIds(long userId);

	/**
	 * Retrieves a list of IDs for the Twitter users that follow the given user.
	 * @param screenName the user's Twitter screen name
	 * @return a list of user IDs
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	List<Long> getFollowerIds(String screenName);

	/**
	 * Allows the authenticated user to follow (create a friendship) with another user.
	 * @param userId The Twitter ID of the user to follow
	 * @return the name of the followed user if successful
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	String follow(long userId);
	
	/**
	 * Allows the authenticated user to follow (create a friendship) with another user.
	 * @param screenName The screen name of the user to follow
	 * @return the name of the followed user if successful
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	String follow(String screenName);

	/**
	 * Allows the authenticated use to unfollow (destroy a friendship) with another user
	 * @param userId the Twitter ID of the user to unfollow 
	 * @return the name of the unfolloed user if successful 
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	String unfollow(long userId);
	
	/**
	 * Allows the authenticated use to unfollow (destroy a friendship) with another user
	 * @param screenName the screen name of the user to unfollow 
	 * @return the name of the unfolloed user if successful 
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	String unfollow(String screenName);
	
	/**
	 * Enable mobile device notifications from Twitter for the specified user.
	 * @param userId the Twitter ID of the user to receive notifications for. 
	 * @return the TwitterProfile for the user
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile enableNotifications(long userId);

	/**
	 * Enable mobile device notifications from Twitter for the specified user.
	 * @param screenName the Twitter screen name of the user to receive notifications for. 
	 * @return the TwitterProfile for the user
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile enableNotifications(String screenName);

	/**
	 * Disable mobile device notifications from Twitter for the specified user.
	 * @param userId the Twitter ID of the user to stop notifications for. 
	 * @return the TwitterProfile for the user
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile disableNotifications(long userId);

	/**
	 * Disable mobile device notifications from Twitter for the specified user.
	 * @param screenName the Twitter screen name of the user to stop notifications for. 
	 * @return the TwitterProfile for the user
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile disableNotifications(String screenName);

	/**
	 * Checks for a friendship between two users. Returns true if userA follows userB.
	 * @param userA the screen name of userA
	 * @param userB the screen name of userB
	 * @throws ApiException if there is an error while communicating with Twitter.
	 */
	boolean friendshipExists(String userA, String userB);

	/**
	 * Returns an array of numeric IDs for every user who has a pending request to follow the authenticating user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Long> getIncomingFriendships();

	/**
	 * Returns an array of numeric IDs for every protected user for whom the authenticating user has a pending follow request.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws NotAuthorizedException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Long> getOutgoingFriendships();
}
