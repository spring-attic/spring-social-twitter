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
import org.springframework.util.MultiValueMap;


/**
 * Interface defining the operations for retrieving information about Twitter users.
 * @author Craig Walls
 */
public interface UserOperations {

	/**
	 * Retrieves the authenticated user's Twitter ID.
	 * @return the user's ID at Twitter
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	long getProfileId();
	
	/**
	 * Retrieves the authenticated user's Twitter screen name
	 * @return the user's screen name
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	String getScreenName();

	/**
	 * Retrieves the authenticated user's Twitter profile details.
	 * @return a {@link TwitterProfile} object representing the user's profile.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile getUserProfile();

	/**
	 * Retrieves a specific user's Twitter profile details.
	 * Note that this method does not require authentication.
	 * @param screenName the screen name for the user whose details are to be retrieved.
	 * @return a {@link TwitterProfile} object representing the user's profile.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile getUserProfile(String screenName);

	/**
	 * Retrieves a specific user's Twitter profile details.
	 * Note that this method does not require authentication.
	 * @param userId the user ID for the user whose details are to be retrieved.
	 * @return a {@link TwitterProfile} object representing the user's profile.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	TwitterProfile getUserProfile(long userId);
	
	/**
	 * Retrieves a list of Twitter profiles for the given list of user IDs.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<TwitterProfile> getUsers(long... userIds);

	/**
	 * Retrieves a list of Twitter profiles for the given list of screen names.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<TwitterProfile> getUsers(String... screenNames);
	
	/**
	 * Searches for up to 20 users that match a given query.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<TwitterProfile> searchForUsers(String query);

	/**
	 * Searches for users that match a given query.
	 * @param page the page of search results to return
	 * @param pageSize the number of {@link TwitterProfile}s per page. Maximum of 20 per page.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<TwitterProfile> searchForUsers(String query, int page, int pageSize);

	/**
	 * Retrieves a list of categories from which suggested users to follow may be found.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<SuggestionCategory> getSuggestionCategories();

	/**
	 * Retrieves a list of suggestions of users to follow for a given category.
	 * @param slug the category's slug
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<TwitterProfile> getSuggestions(String slug);

	/**
	 * Retrieves the rate limit statuses for each of the resource families passed as arguments
	 * @param resources the list of resource families to inquire about
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials. 
	 */
	MultiValueMap<String,RateLimitStatus> getRateLimitStatus(String... resources);
}
