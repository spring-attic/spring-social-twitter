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
import org.springframework.social.twitter.api.BlockOperations;
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
	
	public List<TwitterProfile> getBlockedUsers() {
		return getBlockedUsers(1, 20);
	}
	
	public List<TwitterProfile> getBlockedUsers(int page, int pageSize) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithPerPage(page, pageSize, 0, 0);
		return restTemplate.getForObject(buildUri("blocks/blocking.json", parameters), TwitterProfileList.class);
	}

	public List<Long> getBlockedUserIds() {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("blocks/blocking/ids.json"), LongList.class);
	}
	
	public boolean isBlocking(long userId) {
		return isBlocking(buildUri("blocks/exists.json", "user_id", String.valueOf(userId)));
	}

	public boolean isBlocking(String screenName) {
		return isBlocking(buildUri("blocks/exists.json", "screen_name", screenName));
	}

	// private helpers
	
	private boolean isBlocking(URI blockingExistsUri) {
		try {
			restTemplate.getForObject(blockingExistsUri, String.class);
			return true;
		} catch (ResourceNotFoundException e) {
			return false;
		}
	}

	@SuppressWarnings("serial")
	private static class LongList extends ArrayList<Long>{}

}
