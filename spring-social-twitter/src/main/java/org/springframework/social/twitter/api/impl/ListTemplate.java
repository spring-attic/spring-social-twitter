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
					
	public ListTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.restTemplate = restTemplate;
	}

	public CursoredList<UserList> getLists() {
		return getListsInCursor(-1);
	}

	public CursoredList<UserList> getListsInCursor(long cursor) {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("lists.json", "cursor", String.valueOf(cursor)), UserListList.class).getList();
	}

	public CursoredList<UserList> getLists(long userId) {
		return getListsInCursor(userId, -1);
	}
	
	public CursoredList<UserList> getListsInCursor(long userId, long cursor) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("user_id", String.valueOf(userId));
		parameters.set("cursor", String.valueOf(cursor));
		return restTemplate.getForObject(buildUri("lists.json", parameters), UserListList.class).getList();
	}

	public CursoredList<UserList> getLists(String screenName) {
		return getListsInCursor(screenName, -1);
	}
	
	public CursoredList<UserList> getListsInCursor(String screenName, long cursor) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("screen_name", screenName);
		parameters.set("cursor", String.valueOf(cursor));
		return restTemplate.getForObject(buildUri("lists.json", parameters), UserListList.class).getList();
	}

	public UserList getList(long listId) {
		return restTemplate.getForObject(buildUri("lists/show.json", "list_id", String.valueOf(listId)), UserList.class);
	}

	public UserList getList(String screenName, String listSlug) {
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		return restTemplate.getForObject(buildUri("lists/show.json", parameters), UserList.class);
	}

	public List<Tweet> getListStatuses(long listId) {
		return getListStatuses(listId, 1, 20, 0, 0);
	}

	public List<Tweet> getListStatuses(long listId, int page, int pageSize) {
		return getListStatuses(listId, page, pageSize, 0, 0);
	}

	public List<Tweet> getListStatuses(long listId, int page, int pageSize, long sinceId, long maxId) {
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithPerPage(page, pageSize, sinceId, maxId);
		parameters.set("list_id", String.valueOf(listId));
		return restTemplate.getForObject(buildUri("lists/statuses.json", parameters), TweetList.class);
	}

	public List<Tweet> getListStatuses(String screenName, String listSlug) {
		return getListStatuses(screenName, listSlug, 1, 20, 0, 0);
	}

	public List<Tweet> getListStatuses(String screenName, String listSlug, int page, int pageSize) {
		return getListStatuses(screenName, listSlug, page, pageSize, 0, 0);
	}

	public List<Tweet> getListStatuses(String screenName, String listSlug, int page, int pageSize, long sinceId, long maxId) {
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithPerPage(page, pageSize, sinceId, maxId);
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		return restTemplate.getForObject(buildUri("lists/statuses.json", parameters), TweetList.class);
	}

	public UserList createList(String name, String description, boolean isPublic) {	
		requireAuthorization();
		MultiValueMap<String, Object> request = buildListDataMap(name, description, isPublic);
		return restTemplate.postForObject(buildUri("lists/create.json"), request, UserList.class);
	}

	public UserList updateList(long listId, String name, String description, boolean isPublic) {
		requireAuthorization();
		MultiValueMap<String, Object> request = buildListDataMap(name, description, isPublic);
		request.set("list_id", String.valueOf(listId));
		return restTemplate.postForObject(buildUri("lists/update.json"), request, UserList.class);
	}

	public void deleteList(long listId) {
		requireAuthorization();
		restTemplate.delete(buildUri("lists/destroy.json", "list_id", String.valueOf(listId)));
	}

	public List<TwitterProfile> getListMembers(long listId) {
		return restTemplate.getForObject(buildUri("lists/members.json", "list_id", String.valueOf(listId)), TwitterProfileUsersList.class).getList();
	}
	
	public List<TwitterProfile> getListMembers(String screenName, String listSlug) {
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		return restTemplate.getForObject(buildUri("lists/members.json", parameters), TwitterProfileUsersList.class).getList();
	}

	public UserList addToList(long listId, long... newMemberIds) {
		requireAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("user_id", ArrayUtils.join(newMemberIds));
		request.set("list_id", String.valueOf(listId));
		return restTemplate.postForObject(buildUri("lists/members/create_all.json"), request, UserList.class);
	}

	public UserList addToList(long listId, String... newMemberScreenNames) {
		requireAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("screen_name", ArrayUtils.join(newMemberScreenNames));
		request.set("list_id", String.valueOf(listId));
		return restTemplate.postForObject(buildUri("lists/members/create_all.json"), request, UserList.class);
	}

	public void removeFromList(long listId, long memberId) {
		requireAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("user_id", String.valueOf(memberId)); 
		request.set("list_id", String.valueOf(listId));
		restTemplate.postForObject(buildUri("lists/members/destroy.json"), request, String.class);
	}

	public void removeFromList(long listId, String memberScreenName) {
		requireAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("screen_name", String.valueOf(memberScreenName)); 
		request.set("list_id", String.valueOf(listId));
		restTemplate.postForObject(buildUri("lists/members/destroy.json"), request, String.class);
	}

	public List<TwitterProfile> getListSubscribers(long listId) {
		return restTemplate.getForObject(buildUri("lists/subscribers.json", "list_id", String.valueOf(listId)), TwitterProfileUsersList.class).getList();
	}

	public List<TwitterProfile> getListSubscribers(String screenName, String listSlug) {
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		return restTemplate.getForObject(buildUri("lists/subscribers.json", parameters), TwitterProfileUsersList.class).getList();
	}

	
	public UserList subscribe(long listId) {
		requireAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("list_id", String.valueOf(listId));
		return restTemplate.postForObject(buildUri("lists/subscribers/create.json"), request, UserList.class);
	}

	public UserList subscribe(String ownerScreenName, String listSlug) {
		requireAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("owner_screen_name", ownerScreenName);
		request.set("slug", listSlug);
		return restTemplate.postForObject(buildUri("lists/subscribers/create.json"), request, UserList.class);
	}

	public UserList unsubscribe(long listId) {
		requireAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("list_id", String.valueOf(listId));
		return restTemplate.postForObject(buildUri("lists/subscribers/destroy.json"), request, UserList.class);
	}

	public UserList unsubscribe(String ownerScreenName, String listSlug) {
		requireAuthorization();
		MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
		request.set("owner_screen_name", ownerScreenName);
		request.set("slug", listSlug);
		return restTemplate.postForObject(buildUri("lists/subscribers/destroy.json"), request, UserList.class);
	}

	public CursoredList<UserList> getMemberships(long userId) {
		return restTemplate.getForObject(buildUri("lists/memberships.json", "user_id", String.valueOf(userId)), UserListList.class).getList();
	}

	public CursoredList<UserList> getMemberships(String screenName) {
		return restTemplate.getForObject(buildUri("lists/memberships.json", "screen_name", screenName), UserListList.class).getList();
	}

	public CursoredList<UserList> getSubscriptions(long userId) {
		return restTemplate.getForObject(buildUri("lists/subscriptions.json", "user_id", String.valueOf(userId)), UserListList.class).getList();
	}

	public CursoredList<UserList> getSubscriptions(String screenName) {
		return restTemplate.getForObject(buildUri("lists/subscriptions.json", "screen_name", screenName), UserListList.class).getList();
	}

	public boolean isMember(long listId, long memberId) {
		requireAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("list_id", String.valueOf(listId));
		parameters.set("user_id", String.valueOf(memberId));
		return checkListConnection(buildUri("lists/members/show.json", parameters));
	}

	public boolean isMember(String screenName, String listSlug, String memberScreenName) {
		requireAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("owner_screen_name", screenName);
		parameters.set("slug", listSlug);
		parameters.set("screen_name", memberScreenName);
		return checkListConnection(buildUri("lists/members/show.json", parameters));
	}

	public boolean isSubscriber(long listId, long subscriberId) {
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("list_id", String.valueOf(listId));
		parameters.set("user_id", String.valueOf(subscriberId));
		return checkListConnection(buildUri("lists/subscribers/show.json", parameters));
	}

	public boolean isSubscriber(String screenName, String listSlug, String subscriberScreenName) {
		requireAuthorization();
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

}
