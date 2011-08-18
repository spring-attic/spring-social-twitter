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

import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.StatusDetails;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
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

	public List<Tweet> getPublicTimeline() {
		return restTemplate.getForObject(buildUri("statuses/public_timeline.json"), TweetList.class);
	}

	public List<Tweet> getHomeTimeline() {
		return getHomeTimeline(1, 20, 0, 0);
	}

	public List<Tweet> getHomeTimeline(int page, int pageSize) {
		return getHomeTimeline(page, pageSize, 0, 0);
	}
	
	public List<Tweet> getHomeTimeline(int page, int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		return restTemplate.getForObject(buildUri("statuses/home_timeline.json", parameters), TweetList.class);
	}
	
	public List<Tweet> getUserTimeline() {
		return getUserTimeline(1, 20, 0, 0);
	}

	public List<Tweet> getUserTimeline(int page, int pageSize) {
		return getUserTimeline(page, pageSize, 0, 0);
	}

	public List<Tweet> getUserTimeline(int page, int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		return restTemplate.getForObject(buildUri("statuses/user_timeline.json", parameters), TweetList.class);
	}

	public List<Tweet> getUserTimeline(String screenName) {
		return getUserTimeline(screenName, 1, 20, 0, 0);
	}

	public List<Tweet> getUserTimeline(String screenName, int page, int pageSize) {
		return getUserTimeline(screenName, page, pageSize, 0, 0);
	}

	public List<Tweet> getUserTimeline(String screenName, int page, int pageSize, long sinceId, long maxId) {
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		parameters.set("screen_name", screenName);
		return restTemplate.getForObject(buildUri("statuses/user_timeline.json", parameters), TweetList.class);
	}

	public List<Tweet> getUserTimeline(long userId) {
		return getUserTimeline(userId, 1, 20, 0, 0);
	}

	public List<Tweet> getUserTimeline(long userId, int page, int pageSize) {
		return getUserTimeline(userId, page, pageSize, 0, 0);
	}

	public List<Tweet> getUserTimeline(long userId, int page, int pageSize, long sinceId, long maxId) {
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		parameters.set("user_id", String.valueOf(userId));
		return restTemplate.getForObject(buildUri("statuses/user_timeline.json", parameters), TweetList.class);
	}

	public List<Tweet> getMentions() {
		return getMentions(1, 20, 0, 0);
	}

	public List<Tweet> getMentions(int page, int pageSize) {
		return getMentions(page, pageSize, 0, 0);
	}

	public List<Tweet> getMentions(int page, int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		return restTemplate.getForObject(buildUri("statuses/mentions.json", parameters), TweetList.class);
	}

	public List<Tweet> getRetweetedByMe() {
		return getRetweetedByMe(1, 20, 0, 0);
	}
	
	public List<Tweet> getRetweetedByMe(int page, int pageSize) {
		return getRetweetedByMe(page, pageSize, 0, 0);
	}
	
	public List<Tweet> getRetweetedByMe(int page, int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		return restTemplate.getForObject(buildUri("statuses/retweeted_by_me.json", parameters), TweetList.class);
	}

	public List<Tweet> getRetweetedByUser(long userId) {
		return getRetweetedByUser(userId, 1, 20, 0, 0);
	}
	
	public List<Tweet> getRetweetedByUser(long userId, int page, int pageSize) {
		return getRetweetedByUser(userId, page, pageSize, 0, 0);
	}
	
	public List<Tweet> getRetweetedByUser(long userId, int page, int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		parameters.set("user_id", String.valueOf(userId));
		return restTemplate.getForObject(buildUri("statuses/retweeted_by_user.json", parameters), TweetList.class);
	}

	public List<Tweet> getRetweetedByUser(String screenName) {
		return getRetweetedByUser(screenName, 1, 20, 0, 0);
	}
	
	public List<Tweet> getRetweetedByUser(String screenName, int page, int pageSize) {
		return getRetweetedByUser(screenName, page, pageSize, 0, 0);
	}
	
	public List<Tweet> getRetweetedByUser(String screenName, int page, int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		parameters.set("screen_name", screenName);
		return restTemplate.getForObject(buildUri("statuses/retweeted_by_user.json", parameters), TweetList.class);
	}

	public List<Tweet> getRetweetedToMe() {
		return getRetweetedToMe(1, 20, 0, 0);
	}

	public List<Tweet> getRetweetedToMe(int page, int pageSize) {
		return getRetweetedToMe(page, pageSize, 0, 0);
	}

	public List<Tweet> getRetweetedToMe(int page, int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		return restTemplate.getForObject(buildUri("statuses/retweeted_to_me.json", parameters), TweetList.class);
	}

	public List<Tweet> getRetweetedToUser(long userId) {
		return getRetweetedToUser(userId, 1, 20, 0, 0);
	}
	
	public List<Tweet> getRetweetedToUser(long userId, int page, int pageSize) {
		return getRetweetedToUser(userId, page, pageSize, 0, 0);
	}
	
	public List<Tweet> getRetweetedToUser(long userId, int page, int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		parameters.set("user_id", String.valueOf(userId));
		return restTemplate.getForObject(buildUri("statuses/retweeted_to_user.json", parameters), TweetList.class);
	}

	public List<Tweet> getRetweetedToUser(String screenName) {
		return getRetweetedToUser(screenName, 1, 20, 0, 0);
	}
	
	public List<Tweet> getRetweetedToUser(String screenName, int page, int pageSize) {
		return getRetweetedToUser(screenName, page, pageSize, 0, 0);
	}
	
	public List<Tweet> getRetweetedToUser(String screenName, int page, int pageSize, long sinceId, long maxId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, sinceId, maxId);
		parameters.set("screen_name", screenName);
		return restTemplate.getForObject(buildUri("statuses/retweeted_to_user.json", parameters), TweetList.class);
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
		return restTemplate.getForObject(buildUri("statuses/retweets_of_me.json", parameters), TweetList.class);
	}

	public Tweet getStatus(long tweetId) {
		return restTemplate.getForObject(buildUri("statuses/show/" + tweetId + ".json"), Tweet.class);
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
		return restTemplate.postForObject("https://upload.twitter.com/1/statuses/update_with_media.json", tweetParams, Tweet.class);
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
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("count", String.valueOf(count));
		return restTemplate.getForObject(buildUri("statuses/retweets/" + tweetId + ".json", parameters), TweetList.class);
	}

	public List<TwitterProfile> getRetweetedBy(long tweetId) {
		return getRetweetedBy(tweetId, 1, 100);
	}

	public List<TwitterProfile> getRetweetedBy(long tweetId, int page, int pageSize) {
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, 0, 0);
		return restTemplate.getForObject(buildUri("statuses/" + tweetId + "/retweeted_by.json", parameters), TwitterProfileList.class);
	}

	public List<Long> getRetweetedByIds(long tweetId) {
		return getRetweetedByIds(tweetId, 1,100);
	}

	public List<Long> getRetweetedByIds(long tweetId, int page, int pageSize) {
		requireAuthorization(); // requires authentication, even though getRetweetedBy() does not.
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, 0, 0);
		return restTemplate.getForObject(buildUri("statuses/" + tweetId + "/retweeted_by/ids.json", parameters), LongList.class);
	}

	public List<Tweet> getFavorites() {
		return getFavorites(1, 20);
	}

	public List<Tweet> getFavorites(int page, int pageSize) {
		requireAuthorization();
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		MultiValueMap<String, String> parameters = PagingUtils.buildPagingParametersWithCount(page, pageSize, 0, 0);
		return restTemplate.getForObject(buildUri("favorites.json", parameters), TweetList.class);
	}

	public void addToFavorites(long tweetId) {
		requireAuthorization();
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		restTemplate.postForObject(buildUri("favorites/create/" + tweetId + ".json"), data, String.class);
	}

	public void removeFromFavorites(long tweetId) {
		requireAuthorization();
		MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
		restTemplate.postForObject(buildUri("favorites/destroy/" + tweetId + ".json"), data, String.class);
	}

	@SuppressWarnings("serial")
	private static class LongList extends ArrayList<Long>{}
	
	@SuppressWarnings("serial")
	private static class TweetList extends ArrayList<Tweet> {}
}
