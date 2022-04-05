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
import org.springframework.social.MissingAuthorizationException;


/**
 * Interface defining the operations for working with a user's lists.
 * @author Craig Walls
 */
public interface ListOperations {
	
	/**
	 * Retrieves user lists for the authenticated user.
	 * @return a list of {@link UserList}s for the authenticated user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<UserList> getLists();

	/**
	 * Retrieves user lists for a given user.
	 * Supports either user or application authorization.
	 * @param userId the ID of the Twitter user.
	 * @return a list of {@link UserList}s for the specified user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<UserList> getLists(long userId);

	/**
	 * Retrieves user lists for a given user.
	 * Supports either user or application authorization.
	 * @param screenName the screen name of the Twitter user.
	 * @return a list of {@link UserList}s for the specified user.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<UserList> getLists(String screenName);

	/**
	 * Retrieves a specific user list.
	 * @param listId the ID of the list to retrieve.
	 * @return the requested {@link UserList}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	UserList getList(long listId);

	/**
	 * Retrieves a specific user list.
	 * Supports either user or application authorization.
	 * @param screenName the screen name of the list owner.
	 * @param listSlug the lists's slug
	 * @return the requested {@link UserList}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	UserList getList(String screenName, String listSlug);

	/**
	 * Retrieves the timeline tweets for the given user list.
	 * Supports either user or application authorization.
	 * @param listId the ID of the list to retrieve.
	 * @return a list of {@link Tweet} objects for the items in the user list timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getListStatuses(long listId);

	/**
	 * Retrieves the timeline tweets for the given user list.
	 * Supports either user or application authorization.
	 * @param listId the ID of the list to retrieve.
	 * @param pageSize The number of {@link Tweet}s per page.
	 * @return a list of {@link Tweet} objects for the items in the user list timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getListStatuses(long listId, int pageSize);

	/**
	 * Retrieves the timeline tweets for the given user list.
	 * Supports either user or application authorization.
	 * @param listId the ID of the list to retrieve.
	 * @param pageSize The number of {@link Tweet}s per page.
	 * @param sinceId The minimum {@link Tweet} ID to return in the results
	 * @param maxId The maximum {@link Tweet} ID to return in the results
	 * @return a list of {@link Tweet} objects for the items in the user list timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getListStatuses(long listId, int pageSize, long sinceId, long maxId);

	/**
	 * Retrieves the timeline tweets for the given user list.
	 * Supports either user or application authorization.
	 * @param screenName the screen name of the Twitter user.
	 * @param listSlug the list's slug.
	 * @return a list of {@link Tweet} objects for the items in the user list timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getListStatuses(String screenName, String listSlug);

	/**
	 * Retrieves the timeline tweets for the given user list.
	 * Supports either user or application authorization.
	 * @param screenName the screen name of the Twitter user.
	 * @param listSlug the list's slug.
	 * @param pageSize The number of {@link Tweet}s per page.
	 * @return a list of {@link Tweet} objects for the items in the user list timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getListStatuses(String screenName, String listSlug, int pageSize);

	/**
	 * Retrieves the timeline tweets for the given user list.
	 * Supports either user or application authorization.
	 * @param screenName the screen name of the Twitter user.
	 * @param listSlug the list's slug.
	 * @param pageSize The number of {@link Tweet}s per page.
	 * @param sinceId The minimum {@link Tweet} ID to return in the results
	 * @param maxId The maximum {@link Tweet} ID to return in the results
	 * @return a list of {@link Tweet} objects for the items in the user list timeline.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	List<Tweet> getListStatuses(String screenName, String listSlug, int pageSize, long sinceId, long maxId);

	/**
	 * Create a new user list
	 * @param name the name of the list.
	 * @param description the list description.
	 * @param isPublic if true, the list will be public; if false the list will be private.
	 * @return the newly created {@link UserList}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	UserList createList(String name, String description, boolean isPublic);

	/**
	 * Updates an existing user list
	 * @param listId the ID of the list
	 * @param name the new name of the list.
	 * @param description the new list description.
	 * @param isPublic if true, the list will be public; if false the list will be private.
	 * @return the newly created {@link UserList}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	UserList updateList(long listId, String name, String description, boolean isPublic);

	/**
	 * Removes a user list.
	 * @param listId the ID of the list to be removed.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	void deleteList(long listId);
	
	/**
	 * Retrieves a list of Twitter profiles whose users are members of the list.
	 * Supports either user or application authorization.
	 * @param listId the ID of the list.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 * @return a list of {@link TwitterProfile}
	 */
	CursoredList<TwitterProfile> getListMembers(long listId);

	/**
	 * Retrieves a list of Twitter profiles whose users are members of the list.
	 * Supports either user or application authorization.
	 * @param listId the ID of the list.
	 * @param cursor the cursor to use when fetching the list members.
	 * @param pageSize The number of {@link TwitterProfile}s per page. The default is 20, with a maximum of 5000 per page.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 * @return a list of {@link TwitterProfile}
	 */
	CursoredList<TwitterProfile> getListMembersInCursor(long listId, long cursor, int pageSize);

	/**
	 * Retrieves a list of Twitter profiles whose users are members of the list.
	 * Supports either user or application authorization.
	 * @param screenName the screen name of the list owner.
	 * @param listSlug the slug of the list.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 * @return a list of {@link TwitterProfile}
	 */
	CursoredList<TwitterProfile> getListMembers(String screenName, String listSlug);

	/**
	 * Retrieves a list of Twitter profiles whose users are members of the list.
	 * Supports either user or application authorization.
	 * @param screenName the screen name of the list owner.
	 * @param listSlug the slug of the list.
	 * @param cursor the cursor to use when fetching the list members.
	 * @param pageSize The number of {@link TwitterProfile}s per page. The default is 20, with a maximum of 5000 per page.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 * @return a list of {@link TwitterProfile}
	 */
	CursoredList<TwitterProfile> getListMembersInCursor(String screenName, String listSlug, long cursor, int pageSize);

	/**
	 * Adds one or more new members to a user list.
	 * @param listId the ID of the list.
	 * @param newMemberIds one or more profile IDs of the Twitter profiles to add to the list.
	 * @return the {@link UserList}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	UserList addToList(long listId, long... newMemberIds);

	/**
	 * Adds one or more new members to a user list.
	 * @param listId the ID of the list.
	 * @param newMemberScreenNames one or more profile IDs of the Twitter profiles to add to the list.
	 * @return the {@link UserList}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	UserList addToList(long listId, String... newMemberScreenNames);

	/**
	 * Removes a member from a user list.
	 * @param listId the ID of the list.
	 * @param memberId the ID of the member to be removed.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	void removeFromList(long listId, long memberId);

	/**
	 * Removes a member from a user list.
	 * @param listId the ID of the list.
	 * @param memberScreenName the ID of the member to be removed.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	void removeFromList(long listId, String memberScreenName);

	/**
	 * Subscribes the authenticating user to a list.
	 * @param listId the ID of the list.
	 * @return the {@link UserList}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	UserList subscribe(long listId);

	/**
	 * Subscribes the authenticating user to a list.
	 * @param screenName the screen name of the list owner.
	 * @param listSlug the slug of the list.
	 * @return the {@link UserList}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	UserList subscribe(String screenName, String listSlug);

	/**
	 * Unsubscribes the authenticating user from a list.
	 * @param listId the ID of the list.
	 * @return the {@link UserList}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	UserList unsubscribe(long listId);

	/**
	 * Unsubscribes the authenticating user from a list.
	 * @param screenName the screen name of the list owner.
	 * @param listSlug the slug of the list.
	 * @return the {@link UserList}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	UserList unsubscribe(String screenName, String listSlug);

	/**
	 * Retrieves the subscribers to a list.
	 * Supports either user or application authorization.
	 * @param listId the ID of the list.
	 * @return a list of {@link TwitterProfile}s for the list's subscribers.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<TwitterProfile> getListSubscribers(long listId);

	/**
	 * Retrieves the subscribers to a list.
	 * Supports either user or application authorization.
	 * @param screenName the screen name of the list owner.
	 * @param listSlug the slug of the list.
	 * @return a list of {@link TwitterProfile}s for the list's subscribers.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<TwitterProfile> getListSubscribers(String screenName, String listSlug);

	/**
	 * Retrieves the subscribers to a list.
	 * Supports either user or application authorization.
	 * @param listId the ID of the list.
	 * @param cursor the cursor to use when fetching the list subscribers.
	 * @param pageSize The number of {@link TwitterProfile}s per page. The default is 20, with a maximum of 5000 per page.
	 * @return a list of {@link TwitterProfile}s for the list's subscribers.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<TwitterProfile> getListSubscribersInCursor(long listId, long cursor, int pageSize);

	/**
	 * Retrieves the subscribers to a list.
	 * Supports either user or application authorization.
	 * @param screenName the screen name of the list owner.
	 * @param listSlug the slug of the list.
	 * @param cursor the cursor to use when fetching the list subscribers.
	 * @param pageSize The number of {@link TwitterProfile}s per page. The default is 20, with a maximum of 5000 per page.
	 * @return a list of {@link TwitterProfile}s for the list's subscribers.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<TwitterProfile> getListSubscribersInCursor(String screenName, String listSlug, long cursor, int pageSize);

	/**
	 * Retrieves the lists that a given user is a member of.
	 * Supports either user or application authorization.
	 * @param userId the user ID
	 * @return a list of {@link UserList}s that the user is a member of.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getMemberships(long userId);

	/**
	 * Retrieves the lists that a given user is a member of.
	 * Supports either user or application authorization.
	 * @param screenName the user's screen name
	 * @return a list of {@link UserList}s that the user is a member of.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getMemberships(String screenName);

	/**
	 * Retrieves the lists that a given user is a member of.
	 * Supports either user or application authorization.
	 * @param userId the user ID
	 * @param cursor the cursor to use when fetching the list memberships.
	 * @param pageSize The number of {@link UserList}s per page. The default is 20, with a maximum of 1000 per page.
	 * @return a list of {@link UserList}s that the user is a member of.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getMembershipsInCursor(long userId, long cursor, int pageSize);

	/**
	 * Retrieves the lists that a given user is a member of.
	 * Supports either user or application authorization.
	 * @param screenName the user's screen name
	 * @param cursor the cursor to use when fetching the list memberships.
	 * @param pageSize The number of {@link UserList}s per page. The default is 20, with a maximum of 1000 per page.
	 * @return a list of {@link UserList}s that the user is a member of.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getMembershipsInCursor(String screenName, long cursor, int pageSize);

	/**
	 * Retrieves the lists that a given user is owned by.
	 * Supports either user or application authorization.
	 * @param userId the user ID
	 * @return a list of {@link UserList}s that the user is owned by.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getOwnerships(long userId);

	/**
	 * Retrieves the lists that a given user is owned by.
	 * Supports either user or application authorization.
	 * @param screenName the user's screen name
	 * @return a list of {@link UserList}s that the user is owned by.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getOwnerships(String screenName);

	/**
	 * Retrieves the lists that a given user is owned by.
	 * Supports either user or application authorization.
	 * @param userId the user ID
	 * @param cursor the cursor to use when fetching the list memberships.
	 * @param pageSize The number of {@link UserList}s per page. The default is 20, with a maximum of 1000 per page.
	 * @return a list of {@link UserList}s that the user is owned by.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getOwnershipsInCursor(long userId, long cursor, int pageSize);

	/**
	 * Retrieves the lists that a given user is owned by.
	 * Supports either user or application authorization.
	 * @param screenName the user's screen name
	 * @param cursor the cursor to use when fetching the list memberships.
	 * @param pageSize The number of {@link UserList}s per page. The default is 20, with a maximum of 1000 per page.
	 * @return a list of {@link UserList}s that the user is owned by.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getOwnershipsInCursor(String screenName, long cursor, int pageSize);

	/**
	 * Retrieves the lists that a given user is subscribed to.
	 * Supports either user or application authorization.
	 * @param userId the user ID
	 * @return a list of {@link UserList}s that the user is subscribed to.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getSubscriptions(long userId);

	/**
	 * Retrieves the lists that a given user is subscribed to.
	 * Supports either user or application authorization.
	 * @param screenName the user's screen name
	 * @return a list of {@link UserList}s that the user is subscribed to.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getSubscriptions(String screenName);

	/**
	 * Retrieves the lists that a given user is subscribed to.
	 * Supports either user or application authorization.
	 * @param userId the user ID
	 * @param cursor the cursor to use when fetching the list subscriptions.
	 * @param pageSize The number of {@link UserList}s per page. The default is 20, with a maximum of 1000 per page.
	 * @return a list of {@link UserList}s that the user is subscribed to.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getSubscriptionsInCursor(long userId, long cursor, int pageSize);

	/**
	 * Retrieves the lists that a given user is subscribed to.
	 * Supports either user or application authorization.
	 * @param screenName the user's screen name
	 * @param cursor the cursor to use when fetching the list subscriptions.
	 * @param pageSize The number of {@link UserList}s per page. The default is 20, with a maximum of 1000 per page.
	 * @return a list of {@link UserList}s that the user is subscribed to.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	CursoredList<UserList> getSubscriptionsInCursor(String screenName, long cursor, int pageSize);

	/**
	 * Checks to see if a given user is a member of a given list.
	 * Supports either user or application authorization.
	 * @param listId the list ID
	 * @param memberId the user ID to check for membership
	 * @return true if the user is a member of the list
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	boolean isMember(long listId, long memberId);
	
	/**
	 * Checks to see if a given user is a member of a given list.
	 * Supports either user or application authorization.
	 * @param screenName the screen name of the list's owner
	 * @param listSlug the list's slug
	 * @param memberScreenName the screenName to check for membership
	 * @return true if the user is a member of the list
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	boolean isMember(String screenName, String listSlug, String memberScreenName);

	/**
	 * Checks to see if a given user subscribes to a given list.
	 * Supports either user or application authorization.
	 * @param listId the list ID
	 * @param subscriberId the user ID to check for subscribership
	 * @return true if the user is a member of the list
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials or an application access token.
	 */
	boolean isSubscriber(long listId, long subscriberId);
	
	/**
	 * Checks to see if a given user subscribes to a given list.
	 * Supports either user or application authorization.
	 * @param screenName the screen name of the list's owner
	 * @param listSlug the list's slug
	 * @param subscriberScreenName the screenName to check for subscribership
	 * @return true if the user is a member of the list
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	boolean isSubscriber(String screenName, String listSlug, String subscriberScreenName);

}
