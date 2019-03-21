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

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.social.twitter.api.Entities;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.MentionEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.UrlEntity;
import org.springframework.test.web.client.MockRestServiceServer;

public abstract class AbstractTwitterApiTest {

	protected TwitterTemplate twitter;
	
	protected TwitterTemplate appAuthTwitter;
	
	protected MockRestServiceServer mockServer;
	
	protected MockRestServiceServer appAuthMockServer;

	@Before
	public void setup() {
		twitter = new TwitterTemplate("API_KEY", "API_SECRET", "ACCESS_TOKEN", "ACCESS_TOKEN_SECRET");
		mockServer = MockRestServiceServer.createServer(twitter.getRestTemplate());
		appAuthTwitter = new TwitterTemplate("APP_ACCESS_TOKEN");
		appAuthMockServer = MockRestServiceServer.createServer(appAuthTwitter.getRestTemplate());
	}
	
	protected Resource jsonResource(String filename) {
		return new ClassPathResource(filename + ".json", getClass());
	}

	protected void assertSingleTweet(Tweet tweet) {
		assertSingleTweet(tweet, false);
	}
	
	protected void assertSingleTweet(Tweet tweet, boolean isSearchResult) {
		assertEquals("12345", tweet.getId());
		assertEquals("Tweet 1", tweet.getText());
		assertEquals("habuma", tweet.getFromUser());
		assertEquals(112233, tweet.getFromUserId());
		assertEquals("http://a3.twimg.com/profile_images/1205746571/me2_300.jpg", tweet.getProfileImageUrl());
		assertEquals("Spring Social Showcase", tweet.getSource());
		assertEquals(1279042701000L, tweet.getCreatedAt().getTime());
		assertEquals(Long.valueOf(123123123123L), tweet.getInReplyToStatusId());
		if (!isSearchResult) {
			assertEquals(12, tweet.getRetweetCount().intValue());
			assertTrue(tweet.isRetweeted());
		} else {
			assertNull(tweet.getRetweetCount());
		}
		
		assertEquals(1001, (int) tweet.getFavoriteCount());
		
		assertTrue(tweet.isFavorited());
		Entities entities = tweet.getEntities();
		List<HashTagEntity> hashtags = entities.getHashTags();
		assertEquals(1,  hashtags.size());
		assertEquals(2, hashtags.get(0).getIndices().length);
		assertEquals(89, hashtags.get(0).getIndices()[0]);
		assertEquals(98, hashtags.get(0).getIndices()[1]);
		assertEquals("testhash", hashtags.get(0).getText());
		List<UrlEntity> urls = entities.getUrls();
		assertEquals(1, urls.size());
		assertEquals(2, urls.get(0).getIndices().length);
		assertEquals(10, urls.get(0).getIndices()[0]);
		assertEquals(30, urls.get(0).getIndices()[1]);
		assertEquals("fb.me/t35tur1", urls.get(0).getDisplayUrl());
		assertEquals("http://fb.me/t35tur1", urls.get(0).getExpandedUrl());
		assertEquals("http://t.co/t35tur1", urls.get(0).getUrl());
		List<MentionEntity> mentions = entities.getMentions();
		assertEquals(2, mentions.size());
		MentionEntity mention1 = mentions.get(0);
		assertEquals(11223344, mention1.getId());
		assertEquals("Bucky Greenhorn", mention1.getName());
		assertEquals("ukuleleman", mention1.getScreenName());
		assertEquals(2, mention1.getIndices().length);
		assertEquals(3, mention1.getIndices()[0]);
		assertEquals(18, mention1.getIndices()[1]);
		MentionEntity mention2 = mentions.get(1);
		assertEquals(44332211, mention2.getId());
		assertEquals("Jack Diamond", mention2.getName());
		assertEquals("jackdiamond", mention2.getScreenName());
		assertEquals(2, mention2.getIndices().length);
		assertEquals(23, mention2.getIndices()[0]);
		assertEquals(37, mention2.getIndices()[1]);
	}
	
	protected void assertTimelineTweets(List<Tweet> tweets) {
		assertTimelineTweets(tweets, false);
	}
	
	protected void assertTimelineTweets(List<Tweet> tweets, boolean isSearchResult) {
		assertEquals(2, tweets.size());
		assertSingleTweet(tweets.get(0), isSearchResult);
		Tweet tweet2 = tweets.get(1);
		assertEquals("54321", tweet2.getId());
		assertEquals("Tweet 2", tweet2.getText());
		assertEquals("rclarkson", tweet2.getFromUser());
		assertEquals(332211, tweet2.getFromUserId());
		assertEquals("http://a3.twimg.com/profile_images/1205746571/me2_300.jpg", tweet2.getProfileImageUrl());
		assertEquals("Twitter", tweet2.getSource());
		assertEquals(1279654701000L, tweet2.getCreatedAt().getTime());
		if (!isSearchResult) {
			assertEquals(0, tweet2.getRetweetCount().intValue());
		} else {
			assertNull(tweet2.getRetweetCount());
		}
		Entities entities = tweet2.getEntities();
		assertEquals(0, entities.getHashTags().size());
	}
}
