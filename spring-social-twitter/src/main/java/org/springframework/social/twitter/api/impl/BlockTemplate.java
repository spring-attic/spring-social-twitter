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

import java.util.List;

import org.springframework.social.twitter.api.BlockOperations;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link BlockOperations}, providing a binding to Twitter's block REST resources.
 * @author Craig Walls
 */
class BlockTemplate extends AbstractTwitterOperations implements BlockOperations {
	
	private final RestTemplate restTemplate;
					
	public BlockTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.restTemplate = restTemplate;
	}

	public TwitterProfile block(long userId) {
		requireAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("user_id", String.valueOf(userId));
		return restTemplate.postForObject(buildUri("blocks/create.json"), request, TwitterProfile.class);
	}
	
	public TwitterProfile block(String screenName) {
		requireAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("screen_name", screenName);
		return restTemplate.postForObject(buildUri("blocks/create.json"), request, TwitterProfile.class);
	}
	
	public TwitterProfile unblock(long userId) {
		requireAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("user_id", String.valueOf(userId));
		return restTemplate.postForObject(buildUri("blocks/destroy.json"), request, TwitterProfile.class);
	}
	
	public TwitterProfile unblock(String screenName) {
		requireAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("screen_name", screenName);
		return restTemplate.postForObject(buildUri("blocks/destroy.json"), request, TwitterProfile.class);
	}
	
	public CursoredList<TwitterProfile> getBlockedUsers() {
		return getBlockedUsersInCursor(-1);
	}
	
	public CursoredList<TwitterProfile> getBlockedUsersInCursor(long cursor) {
		requireAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		CursoredList<Long> blockedUserIds = restTemplate.getForObject(buildUri("blocks/list.json", parameters), CursoredLongList.class).getList();
		return getCursoredProfileList(blockedUserIds, blockedUserIds.getPreviousCursor(), blockedUserIds.getNextCursor());
	}

	public CursoredList<Long> getBlockedUserIds() {
		return getBlockedUserIdsInCursor(-1);
	}
	
	public CursoredList<Long> getBlockedUserIdsInCursor(long cursor) {
		requireAuthorization();
		LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("cursor", String.valueOf(cursor));
		return restTemplate.getForObject(buildUri("blocks/ids.json", parameters), CursoredLongList.class).getList();
	}
	
	private CursoredList<TwitterProfile> getCursoredProfileList(List<Long> userIds, long previousCursor, long nextCursor) {
		// TODO: Would be good to figure out how to retrieve profiles in a tighter-than-cursor granularity.
		List<List<Long>> chunks = CursorUtils.chunkList(userIds, 100);
		CursoredList<TwitterProfile> users = new CursoredList<TwitterProfile>(userIds.size(), previousCursor, nextCursor);
		for (List<Long> userIdChunk : chunks) {
			String joinedIds = ArrayUtils.join(userIdChunk.toArray());
			users.addAll(restTemplate.getForObject(buildUri("users/lookup.json", "user_id", joinedIds), TwitterProfileList.class));
		}
		return users;
	}


}
