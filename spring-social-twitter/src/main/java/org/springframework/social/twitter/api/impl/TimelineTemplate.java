/*
 * Copyright 2013 the original author or authors.
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

import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.StatusDetails;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of {@link TimelineOperations}, providing a binding to Twitter's tweet and timeline-oriented REST resources.
 * @author Craig Walls
 */
class TimelineTemplate extends AbstractTwitterOperations implements TimelineOperations {
	
	private final RestTemplate restTemplate;

	public TimelineTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.restTemplate = restTemplate;
	}

	public List<Tweet> getHomeTimeline() {
		return getHomeTimeline(20, 0, 0);
	}

	public List<Tweet> getHomeTimeline(int pageSize) {
		return getHomeTimeline(pageSize, 0, 0);
	}
	
	public List<Tweet> getHomeTimeline(int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(pageSize, sinceId, maxId);
		parameters.set("include_entities", "true");
		return restTemplate.getForObject(buildUri("statuses/home_timeline.json", parameters), TweetList.class);
	}
	
	public List<Tweet> getUserTimeline() {
		return getUserTimeline(20, 0, 0);
	}

	public List<Tweet> getUserTimeline(int pageSize) {
		return getUserTimeline(pageSize, 0, 0);
	}

	public List<Tweet> getUserTimeline(int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(pageSize, sinceId, maxId);
		parameters.set("include_entities", "true");
		return restTemplate.getForObject(buildUri("statuses/user_timeline.json", parameters), TweetList.class);
	}

	public List<Tweet> getUserTimeline(String screenName) {
		return getUserTimeline(screenName, 20, 0, 0);
	}

	public List<Tweet> getUserTimeline(String screenName, int pageSize) {
		return getUserTimeline(screenName, pageSize, 0, 0);
	}

	public List<Tweet> getUserTimeline(String screenName, int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(pageSize, sinceId, maxId);
		parameters.set("screen_name", screenName);
		parameters.set("include_entities", "true");
		return restTemplate.getForObject(buildUri("statuses/user_timeline.json", parameters), TweetList.class);
	}

	public List<Tweet> getUserTimeline(long userId) {
		return getUserTimeline(userId, 20, 0, 0);
	}

	public List<Tweet> getUserTimeline(long userId, int pageSize) {
		return getUserTimeline(userId, pageSize, 0, 0);
	}

	public List<Tweet> getUserTimeline(long userId, int pageSize, long sinceId, long maxId) {
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(pageSize, sinceId, maxId);
		parameters.set("user_id", String.valueOf(userId));
		parameters.set("include_entities", "true");
		return restTemplate.getForObject(buildUri("statuses/user_timeline.json", parameters), TweetList.class);
	}

	public List<Tweet> getMentions() {
		return getMentions(20, 0, 0);
	}

	public List<Tweet> getMentions(int pageSize) {
		return getMentions(pageSize, 0, 0);
	}

	public List<Tweet> getMentions(int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(pageSize, sinceId, maxId);
		parameters.set("include_entities", "true");
		return restTemplate.getForObject(buildUri("statuses/mentions_timeline.json", parameters), TweetList.class);
	}
	
	public List<Tweet> getRetweetsOfMe() {
		return getRetweetsOfMe(1, 20, 0, 0);
	}

	public List<Tweet> getRetweetsOfMe(int page, int pageSize) {
		return getRetweetsOfMe(page, pageSize, 0, 0);
	}

	public List<Tweet> getRetweetsOfMe(int page, int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		parameters.set("include_entities", "true");
		return restTemplate.getForObject(buildUri("statuses/retweets_of_me.json", parameters), TweetList.class);
	}

	public Tweet getStatus(long tweetId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("include_entities", "true");
		return restTemplate.getForObject(buildUri("statuses/show/" + tweetId + ".json", parameters), Tweet.class);
	}

	public Tweet updateStatus(String message) {
		return updateStatus(message, new StatusDetails());
	}

	public Tweet updateStatus(String message, Resource media) {
		return updateStatus(message, media, new StatusDetails());
	}

	public Tweet updateStatus(String message, StatusDetails details) {
		requireAuthorization();
		MultiValueMap<String, Object> tweetParams = new LinkedMultiValueMap<String, Object>();
		tweetParams.add("status", message);
		tweetParams.putAll(details.toParameterMap());
		return restTemplate.postForObject(buildUri("statuses/update.json"), tweetParams, Tweet.class);
	}

	public Tweet updateStatus(String message, Resource media, StatusDetails details) {
		requireAuthorization();
		MultiValueMap<String, Object> tweetParams = new LinkedMultiValueMap<String, Object>();
		tweetParams.add("status", message);
		tweetParams.add("media", media);
		tweetParams.putAll(details.toParameterMap());
		return restTemplate.postForObject(buildUri("statuses/update_with_media.json"), tweetParams, Tweet.class);
	}

	public void deleteStatus(long tweetId) {
		requireAuthorization();
		restTemplate.delete(buildUri("statuses/destroy/" + tweetId + ".json"));
	}

	public void retweet(long tweetId) {
		requireAuthorization();
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		restTemplate.postForObject(buildUri("statuses/retweet/" + tweetId + ".json"), data, String.class);
	}

	public List<Tweet> getRetweets(long tweetId) {
		return getRetweets(tweetId, 100);
	}

	public List<Tweet> getRetweets(long tweetId, int count) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("count", String.valueOf(count));
		parameters.set("include_entities", "true");
		return restTemplate.getForObject(buildUri("statuses/retweets/" + tweetId + ".json", parameters), TweetList.class);
	}

	public List<Tweet> getFavorites() {
		return getFavorites(20);
	}

	public List<Tweet> getFavorites(int pageSize) {
		requireAuthorization();
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(pageSize, 0, 0);
		parameters.set("include_entities", "true");
		return restTemplate.getForObject(buildUri("favorites/list.json", parameters), TweetList.class);
	}

	public void addToFavorites(long tweetId) {
		requireAuthorization();
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		data.set("id", String.valueOf(tweetId));
		restTemplate.postForObject(buildUri("favorites/create.json"), data, String.class);
	}

	public void removeFromFavorites(long tweetId) {
		requireAuthorization();
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		data.set("id", String.valueOf(tweetId));
		restTemplate.postForObject(buildUri("favorites/destroy.json"), data, String.class);
	}

	@SuppressWarnings("serial")
	private static class TweetList extends ArrayList<Tweet> {}
	
}
