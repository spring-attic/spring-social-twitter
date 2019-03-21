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

import java.util.Map;

import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.FriendOperations;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link FriendTemplate}, providing a binding to Twitter's friends and followers-oriented REST resources.
 * @author Craig Walls
 */
class FriendTemplate extends AbstractTwitterOperations implements FriendOperations {
	
	private final RestTemplate restTemplate;

	public FriendTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
		super(isAuthorizedForUser, isAuthorizedForApp);
		this.restTemplate = restTemplate;
	}

	public CursoredList<TwitterProfile> getFriends() {
		return getFriendsInCursor(-1);
	}

	public CursoredList<TwitterProfile> getFriendsInCursor(long cursor) {
		requireUserAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		return restTemplate.getForObject(buildUri("friends/list.json", parameters), CursoredTwitterProfileUsersList.class).getList();
	}

	public CursoredList<TwitterProfile> getFriends(long userId) {
		return getFriendsInCursor(userId, -1);
	}

	public CursoredList<TwitterProfile> getFriendsInCursor(long userId, long cursor) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("user_id", String.valueOf(userId));
		return restTemplate.getForObject(buildUri("friends/list.json", parameters), CursoredTwitterProfileUsersList.class).getList();
	}

	public CursoredList<TwitterProfile> getFriends(String screenName) {
		return getFriendsInCursor(screenName, -1);
	}
	
	public CursoredList<TwitterProfile> getFriendsInCursor(String screenName, long cursor) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("screen_name", String.valueOf(screenName));
		return restTemplate.getForObject(buildUri("friends/list.json", parameters), CursoredTwitterProfileUsersList.class).getList();
	}
	
	public CursoredList<Long> getFriendIds() {
		return getFriendIdsInCursor(-1);
	}
	
	public CursoredList<Long> getFriendIdsInCursor(long cursor) {
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("friends/ids.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFriendIds(long userId) {
		return getFriendIdsInCursor(userId, -1);
	}
	
	public CursoredList<Long> getFriendIdsInCursor(long userId, long cursor) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("user_id", String.valueOf(userId));
		return restTemplate.getForObject(buildUri("friends/ids.json", parameters), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFriendIds(String screenName) {
		return getFriendIdsInCursor(screenName, -1);
	}
	
	public CursoredList<Long> getFriendIdsInCursor(String screenName, long cursor) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("screen_name", screenName);
		return restTemplate.getForObject(buildUri("friends/ids.json", parameters), CursoredLongList.class).getList();
	}

	public CursoredList<TwitterProfile> getFollowers() {
		return getFollowersInCursor(-1);
	}
	
	public CursoredList<TwitterProfile> getFollowersInCursor(long cursor) {
		requireUserAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		return restTemplate.getForObject(buildUri("followers/list.json", parameters), CursoredTwitterProfileUsersList.class).getList();
	}
	
	public CursoredList<TwitterProfile> getFollowers(long userId) {
		return getFollowersInCursor(userId, -1);
	}
	
	public CursoredList<TwitterProfile> getFollowersInCursor(long userId, long cursor) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("user_id", String.valueOf(userId));
		return restTemplate.getForObject(buildUri("followers/list.json", parameters), CursoredTwitterProfileUsersList.class).getList();
	}

	public CursoredList<TwitterProfile> getFollowers(String screenName) {
		return getFollowersInCursor(screenName, -1);
	}
	
	public CursoredList<TwitterProfile> getFollowersInCursor(String screenName, long cursor) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("screen_name", String.valueOf(screenName));
		return restTemplate.getForObject(buildUri("followers/list.json", parameters), CursoredTwitterProfileUsersList.class).getList();
	}

	public CursoredList<Long> getFollowerIds() {
		return getFollowerIdsInCursor(-1);
	}
	
	public CursoredList<Long> getFollowerIdsInCursor(long cursor) {
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("followers/ids.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFollowerIds(long userId) {
		return getFollowerIdsInCursor(userId, -1);
	}
	
	public CursoredList<Long> getFollowerIdsInCursor(long userId, long cursor) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("user_id", String.valueOf(userId));
		return restTemplate.getForObject(buildUri("followers/ids.json", parameters), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFollowerIds(String screenName) {
		return getFollowerIdsInCursor(screenName, -1);
	}
	
	public CursoredList<Long> getFollowerIdsInCursor(String screenName, long cursor) {
		requireEitherUserOrAppAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("screen_name", screenName);
		return restTemplate.getForObject(buildUri("followers/ids.json", parameters), CursoredLongList.class).getList();
	}

	public String follow(long userId) {
		requireUserAuthorization();
		return (String) restTemplate.postForObject(buildUri("friendships/create.json", "user_id", String.valueOf(userId)), EMPTY_DATA, Map.class).get("screen_name");
	}

	public String follow(String screenName) {
		requireUserAuthorization();
		return (String) restTemplate.postForObject(buildUri("friendships/create.json", "screen_name", screenName), EMPTY_DATA, Map.class).get("screen_name");
	}
	
	public String unfollow(long userId) {
		requireUserAuthorization();
		return (String) restTemplate.postForObject(buildUri("friendships/destroy.json", "user_id", String.valueOf(userId)), EMPTY_DATA, Map.class).get("screen_name");
	}

	public String unfollow(String screenName) {
		requireUserAuthorization();
		return (String) restTemplate.postForObject(buildUri("friendships/destroy.json", "screen_name", screenName), EMPTY_DATA, Map.class).get("screen_name");
	}
	
	public TwitterProfile enableNotifications(long userId) {
		requireUserAuthorization();
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("user_id", String.valueOf(userId));
		params.set("device", "true");
		return restTemplate.postForObject(buildUri("friendships/update.json", params), EMPTY_DATA, TwitterProfile.class);
	}
	
	public TwitterProfile enableNotifications(String screenName) {
		requireUserAuthorization();
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("screen_name", screenName);
		params.set("device", "true");
		return restTemplate.postForObject(buildUri("friendships/update.json", params), EMPTY_DATA, TwitterProfile.class);
	}

	public TwitterProfile disableNotifications(long userId) {
		requireUserAuthorization();
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("user_id", String.valueOf(userId));
		params.set("device", "false");
		return restTemplate.postForObject(buildUri("friendships/update.json", params), EMPTY_DATA, TwitterProfile.class);
	}
	
	public TwitterProfile disableNotifications(String screenName) {
		requireUserAuthorization();
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("screen_name", screenName);
		params.set("device", "false");
		return restTemplate.postForObject(buildUri("friendships/update.json", params), EMPTY_DATA, TwitterProfile.class);
	}

	public CursoredList<Long> getIncomingFriendships() {
		return getIncomingFriendships(-1);
	}
	
	public CursoredList<Long> getIncomingFriendships(long cursor) {
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("friendships/incoming.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getOutgoingFriendships() {
		return getOutgoingFriendships(-1);
	}
	
	public CursoredList<Long> getOutgoingFriendships(long cursor) {
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("friendships/outgoing.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
	}
	
	private static final MultiValueMap<String, Object> EMPTY_DATA = new LinkedMultiValueMap<String, Object>();
	
}
