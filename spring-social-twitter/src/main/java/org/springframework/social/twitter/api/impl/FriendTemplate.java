/*
 * Copyright 2014 the original author or authors.
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
public class FriendTemplate extends AbstractTwitterOperations implements FriendOperations {
	
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
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDS)
					.withArgument("cursor", String.valueOf(cursor))
					.build(),
				CursoredTwitterProfileUsersList.class
			).getList();
	}

	public CursoredList<TwitterProfile> getFriends(long userId) {
		return getFriendsInCursor(userId, -1);
	}

	public CursoredList<TwitterProfile> getFriendsInCursor(long userId, long cursor) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDS)
					.withArgument("cursor", String.valueOf(cursor))
					.withArgument("user_id", String.valueOf(userId))
					.build(),
				CursoredTwitterProfileUsersList.class
			).getList();
	}

	public CursoredList<TwitterProfile> getFriends(String screenName) {
		return getFriendsInCursor(screenName, -1);
	}
	
	public CursoredList<TwitterProfile> getFriendsInCursor(String screenName, long cursor) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDS)
					.withArgument("cursor", String.valueOf(cursor))
					.withArgument("screen_name", String.valueOf(screenName))
					.build(),
				CursoredTwitterProfileUsersList.class
			).getList();
	}
	
	public CursoredList<Long> getFriendIds() {
		return getFriendIdsInCursor(-1);
	}
	
	public CursoredList<Long> getFriendIdsInCursor(long cursor) {
		requireUserAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDS_IDS)
					.withArgument("cursor", String.valueOf(cursor))
					.build(),
				CursoredLongList.class
			).getList();
	}

	public CursoredList<Long> getFriendIds(long userId) {
		return getFriendIdsInCursor(userId, -1);
	}
	
	public CursoredList<Long> getFriendIdsInCursor(long userId, long cursor) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDS_IDS)
					.withArgument("cursor", String.valueOf(cursor))
					.withArgument("user_id", String.valueOf(userId))
					.build(),
				CursoredLongList.class
			).getList();
	}

	public CursoredList<Long> getFriendIds(String screenName) {
		return getFriendIdsInCursor(screenName, -1);
	}
	
	public CursoredList<Long> getFriendIdsInCursor(String screenName, long cursor) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDS_IDS)
					.withArgument("cursor", String.valueOf(cursor))
					.withArgument("screen_name", screenName)
					.build(),
				CursoredLongList.class
			).getList();
	}

	public CursoredList<TwitterProfile> getFollowers() {
		return getFollowersInCursor(-1);
	}
	
	public CursoredList<TwitterProfile> getFollowersInCursor(long cursor) {
		requireUserAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FOLLOWERS)
					.withArgument("cursor", String.valueOf(cursor))
					.build(),
				CursoredTwitterProfileUsersList.class
			).getList();
	}
	
	public CursoredList<TwitterProfile> getFollowers(long userId) {
		return getFollowersInCursor(userId, -1);
	}
	
	public CursoredList<TwitterProfile> getFollowersInCursor(long userId, long cursor) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FOLLOWERS)
					.withArgument("cursor", String.valueOf(cursor))
					.withArgument("user_id", String.valueOf(userId))
					.build(),
				CursoredTwitterProfileUsersList.class
			).getList();
	}

	public CursoredList<TwitterProfile> getFollowers(String screenName) {
		return getFollowersInCursor(screenName, -1);
	}
	
	public CursoredList<TwitterProfile> getFollowersInCursor(String screenName, long cursor) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FOLLOWERS)
					.withArgument("cursor", String.valueOf(cursor))
					.withArgument("screen_name", String.valueOf(screenName))
					.build(),
				CursoredTwitterProfileUsersList.class
			).getList();
	}

	public CursoredList<Long> getFollowerIds() {
		return getFollowerIdsInCursor(-1);
	}
	
	public CursoredList<Long> getFollowerIdsInCursor(long cursor) {
		requireUserAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FOLLOWERS_IDS)
					.withArgument("cursor", String.valueOf(cursor))
					.build(),
				CursoredLongList.class
			).getList();
	}

	public CursoredList<Long> getFollowerIds(long userId) {
		return getFollowerIdsInCursor(userId, -1);
	}
	
	public CursoredList<Long> getFollowerIdsInCursor(long userId, long cursor) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FOLLOWERS_IDS)
					.withArgument("cursor", String.valueOf(cursor))
					.withArgument("user_id", String.valueOf(userId))
					.build(),
				CursoredLongList.class
			).getList();
	}

	public CursoredList<Long> getFollowerIds(String screenName) {
		return getFollowerIdsInCursor(screenName, -1);
	}
	
	public CursoredList<Long> getFollowerIdsInCursor(String screenName, long cursor) {
		requireEitherUserOrAppAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FOLLOWERS_IDS)
					.withArgument("cursor", String.valueOf(cursor))
					.withArgument("screen_name", screenName)
					.build(),
				CursoredLongList.class
			).getList();
	}

	public String follow(long userId) {
		requireUserAuthorization();
		return (String) restTemplate.postForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDSHIPS_CREATE)
					.withArgument("user_id", String.valueOf(userId))
					.build(),
				EMPTY_DATA,
				Map.class
			).get("screen_name");
	}

	public String follow(String screenName) {
		requireUserAuthorization();
		return (String) restTemplate.postForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDSHIPS_CREATE)
					.withArgument("screen_name", screenName)
					.build(),
				EMPTY_DATA,
				Map.class
			).get("screen_name");
	}
	
	public String unfollow(long userId) {
		requireUserAuthorization();
		return (String) restTemplate.postForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDSHIPS_DESTROY)
					.withArgument("user_id", String.valueOf(userId))
					.build(),
				EMPTY_DATA,
				Map.class
			).get("screen_name");
	}

	public String unfollow(String screenName) {
		requireUserAuthorization();
		return (String) restTemplate.postForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDSHIPS_DESTROY)
					.withArgument("screen_name", screenName)
					.build(),
				EMPTY_DATA,
				Map.class
			).get("screen_name");
	}
	
	public TwitterProfile enableNotifications(long userId) {
		requireUserAuthorization();
		return restTemplate.postForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDSHIPS_UPDATE)
					.withArgument("user_id", String.valueOf(userId))
					.withArgument("device", "true")
					.build(),
				EMPTY_DATA,
				TwitterProfile.class);
	}
	
	public TwitterProfile enableNotifications(String screenName) {
		requireUserAuthorization();
		return restTemplate.postForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDSHIPS_UPDATE)
					.withArgument("screen_name", screenName)
					.withArgument("device", "true")
					.build(),
				EMPTY_DATA,
				TwitterProfile.class);
	}

	public TwitterProfile disableNotifications(long userId) {
		requireUserAuthorization();
		return restTemplate.postForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDSHIPS_UPDATE)
					.withArgument("user_id", String.valueOf(userId))
					.withArgument("device", "false")
					.build(),
				EMPTY_DATA,
				TwitterProfile.class);
	}
	
	public TwitterProfile disableNotifications(String screenName) {
		requireUserAuthorization();
		return restTemplate.postForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDSHIPS_UPDATE)
					.withArgument("screen_name", screenName)
					.withArgument("device", "false")
					.build(),
				EMPTY_DATA,
				TwitterProfile.class);
	}

	public CursoredList<Long> getIncomingFriendships() {
		return getIncomingFriendships(-1);
	}
	
	public CursoredList<Long> getIncomingFriendships(long cursor) {
		requireUserAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDSHIPS_INCOMING)
					.withArgument("cursor", String.valueOf(cursor))
					.build(),
				CursoredLongList.class
			).getList();
	}

	public CursoredList<Long> getOutgoingFriendships() {
		return getOutgoingFriendships(-1);
	}
	
	public CursoredList<Long> getOutgoingFriendships(long cursor) {
		requireUserAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.FRIENDSHIPS_OUTGOING)
					.withArgument("cursor", String.valueOf(cursor))
					.build(),
				CursoredLongList.class
			).getList();
	}
	
	private static final MultiValueMap<String, Object> EMPTY_DATA = new LinkedMultiValueMap<String, Object>();
	
}
