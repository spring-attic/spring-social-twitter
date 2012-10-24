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
import java.util.Map;

import org.springframework.social.twitter.api.RateLimitStatus;
import org.springframework.social.twitter.api.ResourceFamily;
import org.springframework.social.twitter.api.SuggestionCategory;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of the {@link UserOperations} interface providing binding to Twitters' user-oriented REST resources.
 * @author Craig Walls
 */
class UserTemplate extends AbstractTwitterOperations implements UserOperations {
	
	private final RestTemplate restTemplate;

	public UserTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.restTemplate = restTemplate;
	}

	public long getProfileId() {
		requireAuthorization();
		return getUserProfile().getId();
	}

	public String getScreenName() {
		requireAuthorization();
		return getUserProfile().getScreenName();
	}

	public TwitterProfile getUserProfile() {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("account/verify_credentials.json"), TwitterProfile.class);
	}

	public TwitterProfile getUserProfile(String screenName) {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("users/show.json", "screen_name", screenName), TwitterProfile.class);
	}
	
	public TwitterProfile getUserProfile(long userId) {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("users/show.json", "user_id", String.valueOf(userId)), TwitterProfile.class);
	}

	public List<TwitterProfile> getUsers(long... userIds) {
		requireAuthorization();
		String joinedIds = ArrayUtils.join(userIds);
		return restTemplate.getForObject(buildUri("users/lookup.json", "user_id", joinedIds), TwitterProfileList.class);
	}

	public List<TwitterProfile> getUsers(String... screenNames) {
		requireAuthorization();
		String joinedScreenNames = ArrayUtils.join(screenNames);
		return restTemplate.getForObject(buildUri("users/lookup.json", "screen_name", joinedScreenNames), TwitterProfileList.class);
	}

	public List<TwitterProfile> searchForUsers(String query) {
		return searchForUsers(query, 1, 20);
	}

	public List<TwitterProfile> searchForUsers(String query, int page, int pageSize) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithPerPage(page, pageSize, 0, 0);
		parameters.set("q", query);
		return restTemplate.getForObject(buildUri("users/search.json", parameters), TwitterProfileList.class);
	}

	public List<SuggestionCategory> getSuggestionCategories() {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("users/suggestions.json"), SuggestionCategoryList.class);
	}

	public List<TwitterProfile> getSuggestions(String slug) {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("users/suggestions/" + slug + ".json"), TwitterProfileUsersList.class).getList();
	}

	public Map<ResourceFamily, List<RateLimitStatus>> getRateLimitStatus(ResourceFamily... resources) {
		requireAuthorization();
		String joinedResources = ArrayUtils.join(resources);
		return restTemplate.getForObject(buildUri("account/rate_limit_status.json", "resources", joinedResources), RateLimitStatusHolder.class).getRateLimits();
	}
}
