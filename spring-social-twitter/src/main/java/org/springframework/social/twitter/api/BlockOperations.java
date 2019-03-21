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
 * Interface defining the operations for blocking and unblocking users
 * @author Craig Walls
 */
public interface BlockOperations {

	/**
	 * Blocks a user. If a friendship exists with the user, it will be destroyed.
	 * @param userId the ID of the user to block.
	 * @return The {@link TwitterProfile} of the blocked user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile block(long userId);
	
	/**
	 * Blocks a user. If a friendship exists with the user, it will be destroyed.
	 * @param screenName the screen name of the user to block.
	 * @return The {@link TwitterProfile} of the blocked user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile block(String screenName);
	
	/**
	 * Unblocks a user.
	 * @param userId the ID of the user to unblock.
	 * @return The {@link TwitterProfile} of the unblocked user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile unblock(long userId);
	
	/**
	 * Unblocks a user.
	 * @param screenName the screen name of the user to unblock.
	 * @return The {@link TwitterProfile} of the unblocked user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile unblock(String screenName);
	
	/**
	 * Retrieves a list of users that the authenticating user has blocked.
	 * @return a list of {@link TwitterProfile}s for the users that are blocked.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<TwitterProfile> getBlockedUsers();
	
	/**
	 * Retrieves a list of users that the authenticating user has blocked.
	 * @param cursor the cursor to retrieve results from. -1 will retrieve the first cursored page of results.
	 * @return a list of {@link TwitterProfile}s for the users that are blocked.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<TwitterProfile> getBlockedUsersInCursor(long cursor);
	
	/**
	 * Retrieves a list of user IDs for the users that the authenticating user has blocked.
	 * @return a list of user IDs for the users that are blocked.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<Long> getBlockedUserIds();
	
	/**
	 * Retrieves a list of user IDs for the users that the authenticating user has blocked.
	 * @param cursor the cursor to retrieve results from. -1 will retrieve the first cursored page of results.
	 * @return a list of user IDs for the users that are blocked.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	CursoredList<Long> getBlockedUserIdsInCursor(long cursor);

}
