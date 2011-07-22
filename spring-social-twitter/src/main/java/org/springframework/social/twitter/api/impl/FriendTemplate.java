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

import java.util.ArrayList;
import java.util.List;
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

	public FriendTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.restTemplate = restTemplate;
	}

	public List<TwitterProfile> getFriends() {
		return getProfiles(getFriendIds());
	}

	public List<TwitterProfile> getFriends(long userId) {
		return getProfiles(getFriendIds(userId));
	}

	public List<TwitterProfile> getFriends(String screenName) {
		return getProfiles(getFriendIds(screenName));
	}
	
	public CursoredList<Long> getFriendIds() {
		return getFriendIdsWithCursor(-1);
	}
	
	public CursoredList<Long> getFriendIdsWithCursor(long cursor) {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("friends/ids.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFriendIds(long userId) {
		return getFriendIdsWithCursor(userId, -1);
	}
	
	public CursoredList<Long> getFriendIdsWithCursor(long userId, long cursor) {
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("user_id", String.valueOf(userId));
		return restTemplate.getForObject(buildUri("friends/ids.json", parameters), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFriendIds(String screenName) {
		return getFriendIdsWithCursor(screenName, -1);
	}
	
	public CursoredList<Long> getFriendIdsWithCursor(String screenName, long cursor) {
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("screen_name", screenName);
		return restTemplate.getForObject(buildUri("friends/ids.json", parameters), CursoredLongList.class).getList();
	}

	public List<TwitterProfile> getFollowers() {
		return getProfiles(getFollowerIds());
	}

	public List<TwitterProfile> getFollowers(long userId) {
		return getProfiles(getFollowerIds(userId));
	}

	public List<TwitterProfile> getFollowers(String screenName) {
		return getProfiles(getFollowerIds(screenName));
	}

	public CursoredList<Long> getFollowerIds() {
		return getFollowerIdsWithCursor(-1);
	}
	
	public CursoredList<Long> getFollowerIdsWithCursor(long cursor) {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("followers/ids.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFollowerIds(long userId) {
		return getFollowerIdsWithCursor(userId, -1);
	}
	
	public CursoredList<Long> getFollowerIdsWithCursor(long userId, long cursor) {
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("user_id", String.valueOf(userId));
		return restTemplate.getForObject(buildUri("followers/ids.json", parameters), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFollowerIds(String screenName) {
		return getFollowerIdsWithCursor(screenName, -1);
	}
	
	public CursoredList<Long> getFollowerIdsWithCursor(String screenName, long cursor) {
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("screen_name", screenName);
		return restTemplate.getForObject(buildUri("followers/ids.json", parameters), CursoredLongList.class).getList();
	}

	public String follow(long userId) {
		requireAuthorization();
		return (String) restTemplate.postForObject(buildUri("friendships/create.json", "user_id", String.valueOf(userId)), EMPTY_DATA, Map.class).get("screen_name");
	}

	public String follow(String screenName) {
		requireAuthorization();
		return (String) restTemplate.postForObject(buildUri("friendships/create.json", "screen_name", screenName), EMPTY_DATA, Map.class).get("screen_name");
	}
	
	public String unfollow(long userId) {
		requireAuthorization();
		return (String) restTemplate.postForObject(buildUri("friendships/destroy.json", "user_id", String.valueOf(userId)), EMPTY_DATA, Map.class).get("screen_name");
	}

	public String unfollow(String screenName) {
		requireAuthorization();
		return (String) restTemplate.postForObject(buildUri("friendships/destroy.json", "screen_name", screenName), EMPTY_DATA, Map.class).get("screen_name");
	}
	
	public TwitterProfile enableNotifications(long userId) {
		requireAuthorization();
		return restTemplate.postForObject(buildUri("notifications/follow.json", "user_id", String.valueOf(userId)), EMPTY_DATA, TwitterProfile.class);
	}
	
	public TwitterProfile enableNotifications(String screenName) {
		requireAuthorization();
		return restTemplate.postForObject(buildUri("notifications/follow.json", "screen_name", screenName), EMPTY_DATA, TwitterProfile.class);
	}

	public TwitterProfile disableNotifications(long userId) {
		requireAuthorization();
		return restTemplate.postForObject(buildUri("notifications/leave.json", "user_id", String.valueOf(userId)), EMPTY_DATA, TwitterProfile.class);
	}
	
	public TwitterProfile disableNotifications(String screenName) {
		requireAuthorization();
		return restTemplate.postForObject(buildUri("notifications/leave.json", "screen_name", screenName), EMPTY_DATA, TwitterProfile.class);
	}
	
	// doesn't require authentication
	public boolean friendshipExists(String userA, String userB) {
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("user_a", userA);
		params.set("user_b", userB);
		return restTemplate.getForObject(buildUri("friendships/exists.json", params), boolean.class);
	}

	public List<Long> getIncomingFriendships() {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("friendships/incoming.json"), LongIdsList.class).getList();
	}

	public List<Long> getOutgoingFriendships() {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("friendships/outgoing.json"), LongIdsList.class).getList();
	}

	private List<TwitterProfile> getProfiles(List<Long> userIds) {
		List<List<Long>> chunks = chunkList(userIds, 100);
		List<TwitterProfile> users = new ArrayList<TwitterProfile>(userIds.size());
		for (List<Long> userIdChunk : chunks) {
			String joinedIds = ArrayUtils.join(userIdChunk.toArray());
			users.addAll(restTemplate.getForObject(buildUri("users/lookup.json", "user_id", joinedIds), TwitterProfileList.class));
		}
		return users;
	}
	
	private List<List<Long>> chunkList(List<Long> list, int chunkSize) {
		List<List<Long>> chunkedList = new ArrayList<List<Long>>();
		int start = 0;
		while (start < list.size()) {
			int end = Math.min(chunkSize + start, list.size());
			chunkedList.add(list.subList(start, end));
			start = end;
		}
		return chunkedList;
	}
	
	private static final MultiValueMap<String, Object> EMPTY_DATA = new LinkedMultiValueMap<String, Object>();
	
}
