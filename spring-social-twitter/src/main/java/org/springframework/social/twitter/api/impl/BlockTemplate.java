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


import java.net.URI;

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
public class BlockTemplate extends AbstractTwitterOperations implements BlockOperations {
	
	private final RestTemplate restTemplate;
					
	public BlockTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
		super(isAuthorizedForUser, isAuthorizedForApp);
		this.restTemplate = restTemplate;
	}

	public TwitterProfile block(long userId) {
		requireUserAuthorization();
		return restTemplate.postForObject(
				new TwitterApiBuilderForUri().withResource(TwitterApiUriResourceForStandard.BLOCKS_CREATE).build(),
				new RestRequestBodyBuilder().withField("user_id", String.valueOf(userId)).build(),
				TwitterProfile.class);
	}
	
	public TwitterProfile block(String screenName) {
		requireUserAuthorization();
		return restTemplate.postForObject(
				new TwitterApiBuilderForUri().withResource(TwitterApiUriResourceForStandard.BLOCKS_CREATE).build(),
				new RestRequestBodyBuilder().withField("screen_name", screenName).build(),
				TwitterProfile.class);
	}
	
	public TwitterProfile unblock(long userId) {
		requireUserAuthorization();
		return restTemplate.postForObject(
				new TwitterApiBuilderForUri().withResource(TwitterApiUriResourceForStandard.BLOCKS_DESTROY).build(),
				new RestRequestBodyBuilder().withField("user_id", String.valueOf(userId)).build(),
				TwitterProfile.class);
	}
	
	public TwitterProfile unblock(String screenName) {
		requireUserAuthorization();
		MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
		request.set("screen_name", screenName);
		URI resourceUri = new TwitterApiBuilderForUri().withResource(TwitterApiUriResourceForStandard.BLOCKS_DESTROY).build();
		return restTemplate.postForObject(resourceUri, request, TwitterProfile.class);
	}
	
	public CursoredList<TwitterProfile> getBlockedUsers() {
		return getBlockedUsersInCursor(-1);
	}
	
	public CursoredList<TwitterProfile> getBlockedUsersInCursor(long cursor) {
		requireUserAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.BLOCKS)
					.withArgument("cursor", String.valueOf(cursor))
					.build(),
				CursoredTwitterProfileUsersList.class
			).getList();
	}

	public CursoredList<Long> getBlockedUserIds() {
		return getBlockedUserIdsInCursor(-1);
	}
	
	public CursoredList<Long> getBlockedUserIdsInCursor(long cursor) {
		requireUserAuthorization();
		return restTemplate.getForObject(
				new TwitterApiBuilderForUri()
					.withResource(TwitterApiUriResourceForStandard.BLOCKS_IDS)
					.withArgument("cursor", String.valueOf(cursor))
					.build(),
				CursoredLongList.class
			).getList();
	}

}
