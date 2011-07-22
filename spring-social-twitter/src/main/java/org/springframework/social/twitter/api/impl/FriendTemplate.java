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

	public CursoredList<TwitterProfile> getFriends() {
		return getFriendsInCursor(-1);
	}

	public CursoredList<TwitterProfile> getFriendsInCursor(long cursor) {
		CursoredList<Long> friendIds = getFriendIdsInCursor(cursor);
		return getCursoredProfileList(friendIds, friendIds.getPreviousCursor(), friendIds.getNextCursor());
	}

	public CursoredList<TwitterProfile> getFriends(long userId) {
		return getFriendsInCursor(userId, -1);
	}

	public CursoredList<TwitterProfile> getFriendsInCursor(long userId, long cursor) {
		CursoredList<Long> friendIds = getFriendIdsInCursor(userId, cursor);
		return getCursoredProfileList(friendIds, friendIds.getPreviousCursor(), friendIds.getNextCursor());
	}

	public CursoredList<TwitterProfile> getFriends(String screenName) {
		return getFriendsInCursor(screenName, -1);
	}
	
	public CursoredList<TwitterProfile> getFriendsInCursor(String screenName, long cursor) {
		CursoredList<Long> friendIds = getFriendIdsInCursor(screenName, cursor);
		return getCursoredProfileList(friendIds, friendIds.getPreviousCursor(), friendIds.getNextCursor());
	}
	
	public CursoredList<Long> getFriendIds() {
		return getFriendIdsInCursor(-1);
	}
	
	public CursoredList<Long> getFriendIdsInCursor(long cursor) {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("friends/ids.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFriendIds(long userId) {
		return getFriendIdsInCursor(userId, -1);
	}
	
	public CursoredList<Long> getFriendIdsInCursor(long userId, long cursor) {
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("user_id", String.valueOf(userId));
		return restTemplate.getForObject(buildUri("friends/ids.json", parameters), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFriendIds(String screenName) {
		return getFriendIdsInCursor(screenName, -1);
	}
	
	public CursoredList<Long> getFriendIdsInCursor(String screenName, long cursor) {
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("screen_name", screenName);
		return restTemplate.getForObject(buildUri("friends/ids.json", parameters), CursoredLongList.class).getList();
	}

	public CursoredList<TwitterProfile> getFollowers() {
		return getFollowersInCursor(-1);
	}
	
	public CursoredList<TwitterProfile> getFollowersInCursor(long cursor) {
		CursoredList<Long> followerIds = getFollowerIdsInCursor(cursor);
		return getCursoredProfileList(followerIds, followerIds.getPreviousCursor(), followerIds.getNextCursor());
	}

	public CursoredList<TwitterProfile> getFollowers(long userId) {
		return getFollowersInCursor(userId, -1);
	}
	
	public CursoredList<TwitterProfile> getFollowersInCursor(long userId, long cursor) {
		CursoredList<Long> followerIds = getFollowerIdsInCursor(userId, cursor);
		return getCursoredProfileList(followerIds, followerIds.getPreviousCursor(), followerIds.getNextCursor());
	}

	public CursoredList<TwitterProfile> getFollowers(String screenName) {
		return getFollowersInCursor(screenName, -1);
	}
	
	public CursoredList<TwitterProfile> getFollowersInCursor(String screenName, long cursor) {
		CursoredList<Long> followerIds = getFollowerIdsInCursor(screenName, cursor);
		return getCursoredProfileList(followerIds, followerIds.getPreviousCursor(), followerIds.getNextCursor());
	}

	public CursoredList<Long> getFollowerIds() {
		return getFollowerIdsInCursor(-1);
	}
	
	public CursoredList<Long> getFollowerIdsInCursor(long cursor) {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("followers/ids.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFollowerIds(long userId) {
		return getFollowerIdsInCursor(userId, -1);
	}
	
	public CursoredList<Long> getFollowerIdsInCursor(long userId, long cursor) {
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		parameters.set("user_id", String.valueOf(userId));
		return restTemplate.getForObject(buildUri("followers/ids.json", parameters), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getFollowerIds(String screenName) {
		return getFollowerIdsInCursor(screenName, -1);
	}
	
	public CursoredList<Long> getFollowerIdsInCursor(String screenName, long cursor) {
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

	public CursoredList<Long> getIncomingFriendships() {
		return getIncomingFriendships(-1);
	}
	
	public CursoredList<Long> getIncomingFriendships(long cursor) {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("friendships/incoming.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
	}

	public CursoredList<Long> getOutgoingFriendships() {
		return getOutgoingFriendships(-1);
	}
	
	public CursoredList<Long> getOutgoingFriendships(long cursor) {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("friendships/outgoing.json", "cursor", String.valueOf(cursor)), CursoredLongList.class).getList();
	}

	private CursoredList<TwitterProfile> getCursoredProfileList(List<Long> userIds, long previousCursor, long nextCursor) {
		// TODO: Would be good to figure out how to retrieve profiles in a tighter-than-cursor granularity.
		List<List<Long>> chunks = chunkList(userIds, 100);
		CursoredList<TwitterProfile> users = new CursoredList<TwitterProfile>(userIds.size(), previousCursor, nextCursor);
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
