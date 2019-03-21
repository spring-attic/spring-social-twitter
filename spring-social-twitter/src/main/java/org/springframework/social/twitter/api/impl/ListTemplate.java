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
package org.springframework.social.twitter.api.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.ListOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UserList;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link ListOperations}, providing a binding to Twitter's list-oriented REST resources.
 * @author Craig Walls
 */
class ListTemplate extends AbstractTwitterOperations implements ListOperations {
	
	private final RestTemplate restTemplate;
					
	public ListTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
		super(isAuthorizedForUser, isAuthorizedForApp);
		this.restTemplate = restTemplate;
	}
	
	public List<UserList> getLists() {
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("lists/list.json"), UserSubscriptionList.class);
	}
	
	public List<UserList> getLists(long userId) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(buildUri("lists/list.json", "user_id", String.valueOf(userId)), UserSubscriptionList.class);
	}
	
	public List<UserList> getLists(String screenName) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(buildUri("lists/list.json", "screen_name", String.valueOf(screenName)), UserSubscriptionList.class);
	}

	public UserList getList(long listId) {
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("lists/show.json", "list_id", String.valueOf(listId)), UserList.class);
	}

	public UserList getList(String screenName, String listSlug) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		return restTemplate.getForObject(buildUri("lists/show.json", parameters), UserList.class);
	}

	public List<Tweet> getListStatuses(long listId) {
		return getListStatuses(listId, 20, 0, 0);
	}

	public List<Tweet> getListStatuses(long listId, int pageSize) {
		return getListStatuses(listId, pageSize, 0, 0);
	}

	public List<Tweet> getListStatuses(long listId, int pageSize, long sinceId, long maxId) {
		requireEitherUserOrAppAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(pageSize, sinceId, maxId);
		parameters.set("list_id", String.valueOf(listId));
		parameters.set("include_entities", "true");
		return restTemplate.getForObject(buildUri("lists/statuses.json", parameters), TweetList.class);
	}

	public List<Tweet> getListStatuses(String screenName, String listSlug) {
		return getListStatuses(screenName, listSlug, 20, 0, 0);
	}

	public List<Tweet> getListStatuses(String screenName, String listSlug, int pageSize) {
		return getListStatuses(screenName, listSlug, pageSize, 0, 0);
	}

	public List<Tweet> getListStatuses(String screenName, String listSlug, int pageSize, long sinceId, long maxId) {
		requireEitherUserOrAppAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(pageSize, sinceId, maxId);
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		parameters.set("include_entities", "true");
		return restTemplate.getForObject(buildUri("lists/statuses.json", parameters), TweetList.class);
	}

	public UserList createList(String name, String description, boolean isPublic) {	
		requireUserAuthorization();
		MultiValueMap<String, Object> request = buildListDataMap(name, description, isPublic);
		return restTemplate.postForObject(buildUri("lists/create.json"), request, UserList.class);
	}

	public UserList updateList(long listId, String name, String description, boolean isPublic) {
		requireUserAuthorization();
		MultiValueMap<String, Object> request = buildListDataMap(name, description, isPublic);
		request.set("list_id", String.valueOf(listId));
		return restTemplate.postForObject(buildUri("lists/update.json"), request, UserList.class);
	}

	public void deleteList(long listId) {
		requireUserAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("list_id", String.valueOf(listId));
		restTemplate.postForObject(buildUri("lists/destroy.json"), request, String.class);
	}

	public CursoredList<TwitterProfile> getListMembers(long listId) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(buildUri("lists/members.json", "list_id", String.valueOf(listId)), TwitterProfileUsersList.class).getList();
	}

	public CursoredList<TwitterProfile> getListMembersInCursor(long listId, long cursor) {
		requireEitherUserOrAppAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("list_id", String.valueOf(listId));
		request.set("cursor", String.valueOf(cursor));
		return restTemplate.getForObject(buildUri("lists/members.json", request), TwitterProfileUsersList.class).getList();
	}

	public CursoredList<TwitterProfile> getListMembers(String screenName, String listSlug) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		return restTemplate.getForObject(buildUri("lists/members.json", parameters), TwitterProfileUsersList.class).getList();
	}

	public CursoredList<TwitterProfile> getListMembersInCursor(String screenName, String listSlug, long cursor) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		parameters.set("cursor", String.valueOf(cursor));
		return restTemplate.getForObject(buildUri("lists/members.json", parameters), TwitterProfileUsersList.class).getList();
	}

	public UserList addToList(long listId, long... newMemberIds) {
		requireUserAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("user_id", ArrayUtils.join(newMemberIds));
		request.set("list_id", String.valueOf(listId));
		return restTemplate.postForObject(buildUri("lists/members/create_all.json"), request, UserList.class);
	}

	public UserList addToList(long listId, String... newMemberScreenNames) {
		requireUserAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("screen_name", ArrayUtils.join(newMemberScreenNames));
		request.set("list_id", String.valueOf(listId));
		return restTemplate.postForObject(buildUri("lists/members/create_all.json"), request, UserList.class);
	}

	public void removeFromList(long listId, long memberId) {
		requireUserAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("user_id", String.valueOf(memberId)); 
		request.set("list_id", String.valueOf(listId));
		restTemplate.postForObject(buildUri("lists/members/destroy.json"), request, String.class);
	}

	public void removeFromList(long listId, String memberScreenName) {
		requireUserAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("screen_name", String.valueOf(memberScreenName)); 
		request.set("list_id", String.valueOf(listId));
		restTemplate.postForObject(buildUri("lists/members/destroy.json"), request, String.class);
	}

	public List<TwitterProfile> getListSubscribers(long listId) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(buildUri("lists/subscribers.json", "list_id", String.valueOf(listId)), TwitterProfileUsersList.class).getList();
	}

	public List<TwitterProfile> getListSubscribers(String screenName, String listSlug) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		return restTemplate.getForObject(buildUri("lists/subscribers.json", parameters), TwitterProfileUsersList.class).getList();
	}
	
	public UserList subscribe(long listId) {
		requireUserAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("list_id", String.valueOf(listId));
		return restTemplate.postForObject(buildUri("lists/subscribers/create.json"), request, UserList.class);
	}

	public UserList subscribe(String ownerScreenName, String listSlug) {
		requireUserAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("owner_screen_name", ownerScreenName);
		request.set("slug", listSlug);
		return restTemplate.postForObject(buildUri("lists/subscribers/create.json"), request, UserList.class);
	}

	public UserList unsubscribe(long listId) {
		requireUserAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("list_id", String.valueOf(listId));
		return restTemplate.postForObject(buildUri("lists/subscribers/destroy.json"), request, UserList.class);
	}

	public UserList unsubscribe(String ownerScreenName, String listSlug) {
		requireUserAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("owner_screen_name", ownerScreenName);
		request.set("slug", listSlug);
		return restTemplate.postForObject(buildUri("lists/subscribers/destroy.json"), request, UserList.class);
	}

	public CursoredList<UserList> getMemberships(long userId) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(buildUri("lists/memberships.json", "user_id", String.valueOf(userId)), UserListList.class).getList();
	}

	public CursoredList<UserList> getMemberships(String screenName) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(buildUri("lists/memberships.json", "screen_name", screenName), UserListList.class).getList();
	}

	public CursoredList<UserList> getMembershipsInCursor(long userId, long cursor) {
		requireEitherUserOrAppAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("user_id", String.valueOf(userId));
		request.set("cursor", String.valueOf(cursor));
		return restTemplate.getForObject(buildUri("lists/memberships.json", request), UserListList.class).getList();
	}

	public CursoredList<UserList> getMembershipsInCursor(String screenName, long cursor) {
		requireEitherUserOrAppAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("screen_name", screenName);
		request.set("cursor", String.valueOf(cursor));
		return restTemplate.getForObject(buildUri("lists/memberships.json", request), UserListList.class).getList();
	}

	public CursoredList<UserList> getSubscriptions(long userId) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(buildUri("lists/subscriptions.json", "user_id", String.valueOf(userId)), UserListList.class).getList();
	}

	public CursoredList<UserList> getSubscriptions(String screenName) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(buildUri("lists/subscriptions.json", "screen_name", screenName), UserListList.class).getList();
	}

	public CursoredList<UserList> getSubscriptionsInCursor(long userId, long cursor) {
		requireEitherUserOrAppAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("user_id", String.valueOf(userId));
		request.set("cursor", String.valueOf(cursor));
		return restTemplate.getForObject(buildUri("lists/subscriptions.json", request), UserListList.class).getList();
	}

	public CursoredList<UserList> getSubscriptionsInCursor(String screenName, long cursor) {
		requireEitherUserOrAppAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("screen_name", screenName);
		request.set("cursor", String.valueOf(cursor));
		return restTemplate.getForObject(buildUri("lists/subscriptions.json", request), UserListList.class).getList();
	}

	public boolean isMember(long listId, long memberId) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("list_id", String.valueOf(listId));
		parameters.set("user_id", String.valueOf(memberId));
		return checkListConnection(buildUri("lists/members/show.json", parameters));
	}

	public boolean isMember(String screenName, String listSlug, String memberScreenName) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		parameters.set("screen_name", memberScreenName);
		return checkListConnection(buildUri("lists/members/show.json", parameters));
	}

	public boolean isSubscriber(long listId, long subscriberId) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("list_id", String.valueOf(listId));
		parameters.set("user_id", String.valueOf(subscriberId));
		return checkListConnection(buildUri("lists/subscribers/show.json", parameters));
	}

	public boolean isSubscriber(String screenName, String listSlug, String subscriberScreenName) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		parameters.set("screen_name", subscriberScreenName);
		return checkListConnection(buildUri("lists/subscribers/show.json", parameters));
	}

	// private helpers

	private boolean checkListConnection(URI uri) {
		try {
			restTemplate.getForObject(uri, String.class);
			return true;
		} catch (ResourceNotFoundException e) {
			return false;
		}
	}

	private MultiValueMap<String, Object> buildListDataMap(String name,
			String description, boolean isPublic) {
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("name", name);
		request.set("description", description);
		request.set("mode", isPublic ? "public" : "private");
		return request;
	}

	@SuppressWarnings("serial")
	private static class TweetList extends ArrayList<Tweet> {}
	
	@SuppressWarnings("serial")
	private static class UserSubscriptionList extends ArrayList<UserList> {}

}
