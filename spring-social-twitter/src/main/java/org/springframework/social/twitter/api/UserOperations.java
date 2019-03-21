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
import java.util.Map;

import org.springframework.social.ApiException;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.twitter.api.impl.AccountSettingsData;


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
	 * Supports either user or application authorization.
	 * @param screenName the screen name for the user whose details are to be retrieved.
	 * @return a {@link TwitterProfile} object representing the user's profile.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	TwitterProfile getUserProfile(String screenName);

	/**
	 * Retrieves a specific user's Twitter profile details.
	 * Supports either user or application authorization.
	 * @param userId the user ID for the user whose details are to be retrieved.
	 * @return a {@link TwitterProfile} object representing the user's profile.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	TwitterProfile getUserProfile(long userId);
	
	/**
	 * Retrieves a list of Twitter profiles for the given list of user IDs.
	 * Supports either user or application authorization.
	 * @param userIds The user IDs to retrieve profiles for
	 * @return a list of Twitter profiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<TwitterProfile> getUsers(long... userIds);

	/**
	 * Retrieves a list of Twitter profiles for the given list of screen names.
	 * Supports either user or application authorization.
	 * @param screenNames The screen names to retrieve profiles for
	 * @return a list of Twitter profiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<TwitterProfile> getUsers(String... screenNames);
	
	/**
	 * Searches for up to 20 users that match a given query.
	 * @param query The search query
	 * @return a list of Twitter profiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<TwitterProfile> searchForUsers(String query);

	/**
	 * Searches for users that match a given query.
	 * @param query The search query
	 * @param page the page of search results to return
	 * @param pageSize the number of {@link TwitterProfile}s per page. Maximum of 20 per page.
	 * @return a list of Twitter profiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<TwitterProfile> searchForUsers(String query, int page, int pageSize);

	/**
	 * Retrieves a list of categories from which suggested users to follow may be found.
	 * Supports either user or application authorization.
	 * @return a list of categories of suggested users
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<SuggestionCategory> getSuggestionCategories();

	/**
	 * Retrieves a list of suggestions of users to follow for a given category.
	 * Supports either user or application authorization.
	 * @param slug the category's slug
	 * @return a list of Twitter profiles
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<TwitterProfile> getSuggestions(String slug);

	/**
	 * Retrieves the rate limit statuses for each of the resource families passed as arguments
	 * Supports either user or application authorization.
	 * @param resources the list of resource families to inquire about
	 * @return a Map of resource families to rate limit statuses
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	Map<ResourceFamily, List<RateLimitStatus>> getRateLimitStatus(ResourceFamily... resources);

	/**
	 * Retrives the authenticating user's account settings.
	 * @return the authenticating user's account settings.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	AccountSettings getAccountSettings();

	/**
	 * Updates the authenticating user's account settings.
	 * @param accountSettingsData An AccountSettingsData with the settings to be changed.
	 * @return The updated account settings.
	 */
	AccountSettings updateAccountSettings(AccountSettingsData accountSettingsData);
}
