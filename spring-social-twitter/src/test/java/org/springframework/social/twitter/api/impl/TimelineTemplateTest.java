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

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.social.DuplicateStatusException;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.twitter.api.Entities;
import org.springframework.social.twitter.api.MessageTooLongException;
import org.springframework.social.twitter.api.OEmbedOptions;
import org.springframework.social.twitter.api.OEmbedTweet;
import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;


/**
 * @author Craig Walls
 */
public class TimelineTemplateTest extends AbstractTwitterApiTest {

	@Test
	public void getHomeTimeline() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/home_timeline.json?count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getHomeTimeline();
		assertTimelineTweets(timeline);
	}

	@Test
	public void getHomeTimeline_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/home_timeline.json?count=100&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getHomeTimeline(100);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getHomeTimeline_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/home_timeline.json?count=100&since_id=1234567&max_id=7654321&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getHomeTimeline(100, 1234567, 7654321);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline();
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=15&user_id=2&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline(2, 15);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=15&since_id=123456&max_id=654321&user_id=2&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline(2, 15, 123456, 654321);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=20&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline("habuma");
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forScreenName_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=24&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline("habuma", 24);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forScreenName_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=24&since_id=112233&max_id=332211&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline("habuma", 24, 112233, 332211);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forScreenName_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=20&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getUserTimeline("habuma");
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forScreenName_paged_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=24&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getUserTimeline("habuma", 24);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forScreenName_paged_withSinceIdAndMaxId_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=24&since_id=112233&max_id=332211&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getUserTimeline("habuma", 24, 112233, 332211);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=20&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline(12345L);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forUserId_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=24&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline(12345L, 24);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forUserId_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=24&since_id=112233&max_id=332211&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline(12345L, 24, 112233, 332211);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forUserId_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=20&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getUserTimeline(12345L);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forUserId_paged_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=24&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getUserTimeline(12345L, 24);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forUserId_paged_withSinceIdAndMaxId_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/user_timeline.json?count=24&since_id=112233&max_id=332211&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getUserTimeline(12345L, 24, 112233, 332211);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getMentions() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/mentions_timeline.json?count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> mentions = twitter.timelineOperations().getMentions();
		assertTimelineTweets(mentions);
	}

	@Test
	public void getMentions_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/mentions_timeline.json?count=50&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> mentions = twitter.timelineOperations().getMentions(50);
		assertTimelineTweets(mentions);
	}

	@Test
	public void getMentions_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/mentions_timeline.json?count=50&since_id=112233&max_id=332211&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> mentions = twitter.timelineOperations().getMentions(50, 112233, 332211);
		assertTimelineTweets(mentions);
	}

	@Test
	public void getRetweetsOfMe() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/retweets_of_me.json?page=1&count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetsOfMe();
		assertTimelineTweets(timeline);
	}

	@Test 
	public void getRetweetsOfMe_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/retweets_of_me.json?page=7&count=25&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetsOfMe(7, 25);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getRetweetsOfMe_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/retweets_of_me.json?page=7&count=25&since_id=2345&max_id=3456&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetsOfMe(7, 25, 2345, 3456);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getStatus() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/show/12345.json?include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		
		Tweet tweet = twitter.timelineOperations().getStatus(12345);
		assertSingleTweet(tweet);
	}

	@Test
	public void getStatus_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/show/12345.json?include_entities=true"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		
		Tweet tweet = appAuthTwitter.timelineOperations().getStatus(12345);
		assertSingleTweet(tweet);
	}

	@Test
	public void getStatus_withTickerSymbolEntity() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/show/12345.json?include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("status_with_tickers"), APPLICATION_JSON));
		
		Tweet tweet = twitter.timelineOperations().getStatus(12345);
		Entities entities = tweet.getEntities();
		List<TickerSymbolEntity> tickerSymbols = entities.getTickerSymbols();
		assertEquals(3, tickerSymbols.size());
		assertEquals("VMW", tickerSymbols.get(0).getTickerSymbol());
		assertEquals("https://twitter.com/search?q=%24VMW&src=ctag", tickerSymbols.get(0).getUrl());
		assertEquals(17, tickerSymbols.get(0).getIndices()[0]);
		assertEquals(21, tickerSymbols.get(0).getIndices()[1]);
		assertEquals("FB", tickerSymbols.get(1).getTickerSymbol());
		assertEquals("https://twitter.com/search?q=%24FB&src=ctag", tickerSymbols.get(1).getUrl());
		assertEquals(23, tickerSymbols.get(1).getIndices()[0]);
		assertEquals(26, tickerSymbols.get(1).getIndices()[1]);
		assertEquals("AAPL", tickerSymbols.get(2).getTickerSymbol());
		assertEquals("https://twitter.com/search?q=%24AAPL&src=ctag", tickerSymbols.get(2).getUrl());
		assertEquals(28, tickerSymbols.get(2).getIndices()[0]);
		assertEquals(33, tickerSymbols.get(2).getIndices()[1]);
	}
	
	@Test
	public void getStatusOEmbed() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/oembed.json?id=357251561744916480"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("oembed"), APPLICATION_JSON));

		OEmbedTweet oembed = twitter.timelineOperations().getStatusOEmbed(String.valueOf(357251561744916480L));
		assertOEmbedTweet(oembed);
	}

	@Test
	public void getStatusOEmbed_withAllOptions() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/oembed.json?maxwidth=300&hide_media=true&hide_thread=true&omit_script=true&align=center&related=springsocial&lang=es&id=357251561744916480"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("oembed"), APPLICATION_JSON));

		OEmbedTweet oembed = twitter.timelineOperations().getStatusOEmbed(String.valueOf(357251561744916480L), 
				new OEmbedOptions().maxWidth(300)
								   .hideMedia()
								   .hideThread()
								   .omitScript()
								   .align("center")
								   .related("springsocial")
								   .language("es"));
		assertOEmbedTweet(oembed);
	}

	@Test
	public void updateStatus() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message");
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withStarInMessage() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test%2AMessage"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		Tweet tweet = twitter.timelineOperations().updateStatus("Test*Message");
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withImage() {
		mockServer.expect(requestTo("https://upload.twitter.com/1.1/media/upload.json"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("media-upload"), APPLICATION_JSON));
	
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&media_ids=553639437322563584"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		Resource photo = getUploadResource("photo.jpg", "PHOTO DATA");
		Tweet tweet = twitter.timelineOperations().updateStatus(new TweetData("Test Message").withMedia(photo));
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withLocation() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&lat=123.1&long=-111.2"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		Tweet tweet = twitter.timelineOperations().updateStatus(new TweetData("Test Message").atLocation(-111.2f, 123.1f));
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withPlace() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&place_id=df51dec6f4ee2b2c"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		Tweet tweet = twitter.timelineOperations().updateStatus(new TweetData("Test Message").atPlace("df51dec6f4ee2b2c"));
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withLocationAndDisplayCoordinates() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&lat=123.1&long=-111.2&display_coordinates=true"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		Tweet tweet = twitter.timelineOperations().updateStatus(new TweetData("Test Message").atLocation(-111.2f, 123.1f).displayCoordinates(true));
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withInReplyToStatus() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message+in+reply+to+%40someone&in_reply_to_status_id=123456"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));

		Tweet tweet = twitter.timelineOperations().updateStatus(new TweetData("Test Message in reply to @someone").inReplyToStatus(123456));
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withImageAndLocation_() {
		mockServer.expect(requestTo("https://upload.twitter.com/1.1/media/upload.json"))
			.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("media-upload"), APPLICATION_JSON));
	
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&lat=-111.2&long=123.1&media_ids=553639437322563584"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		Resource photo = getUploadResource("photo.jpg", "PHOTO DATA");
		Tweet tweet = twitter.timelineOperations().updateStatus(new TweetData("Test Message").atLocation(123.1f, -111.2f).withMedia(photo));
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test(expected = DuplicateStatusException.class)
	public void updateStatus_duplicateTweet() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message"))
				.andRespond(withStatus(FORBIDDEN).body("{\"error\":\"You already said that\"}").contentType(APPLICATION_JSON));
		twitter.timelineOperations().updateStatus("Test Message");
	}
	
	@Test(expected=MessageTooLongException.class)
	public void updateStatus_tweetTooLong() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
			.andExpect(method(POST))
			.andExpect(content().string("status=Really+long+message"))
			.andRespond(withStatus(FORBIDDEN).body("{\"error\":\"Status is over 140 characters.\"}").contentType(APPLICATION_JSON));
		twitter.timelineOperations().updateStatus("Really long message");
	}
	
	@Test(expected = OperationNotPermittedException.class)
	public void updateStatus_forbidden() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message"))
				.andRespond(withStatus(FORBIDDEN).body("{\"error\":\"Forbidden\"}").contentType(APPLICATION_JSON));
		twitter.timelineOperations().updateStatus("Test Message");
	}

	@Test
	public void deleteStatus() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/destroy/12345.json"))
			.andExpect(method(POST))
			.andRespond(withSuccess("{}", APPLICATION_JSON));
		twitter.timelineOperations().deleteStatus(12345L);
		mockServer.verify();
	}
	
	@Test
	public void retweet() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/retweet/363048052157726721.json"))
				.andExpect(method(POST))
			.andRespond(withSuccess(jsonResource("retweet-response"), APPLICATION_JSON));
		Tweet retweet = twitter.timelineOperations().retweet(363048052157726721L);
		assertEquals("363057792615071744", retweet.getId());
		assertEquals("RT @springone2gx: Reg now early bird discount before it goes! Save $$$ &amp; see awesome #springframework #groovy #grails talks https://t.co/zNx\u2026", retweet.getText());
		assertEquals("Reg now early bird discount before it goes! Save $$$ &amp; see awesome #springframework #groovy #grails talks https://t.co/zNxUFLs5Dt #s2gx #java", retweet.getUnmodifiedText());
		mockServer.verify();
	}
	
	@Test(expected=DuplicateStatusException.class)
	public void retweet_duplicateTweet() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/retweet/12345.json"))
				.andExpect(method(POST))
				.andRespond(withStatus(FORBIDDEN).body("{\"error\":\"You already said that\"}").contentType(APPLICATION_JSON));

		twitter.timelineOperations().retweet(12345);
	}

	@Test(expected = OperationNotPermittedException.class)
	public void retweet_forbidden() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/retweet/12345.json"))
				.andExpect(method(POST))
				.andRespond(withStatus(FORBIDDEN).body("{\"error\":\"Forbidden\"}").contentType(APPLICATION_JSON));

		twitter.timelineOperations().retweet(12345);
	}

	@Test(expected = OperationNotPermittedException.class)
	public void retweet_sharingNotAllowed() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/retweet/12345.json"))
				.andExpect(method(POST))
				.andRespond(withStatus(FORBIDDEN).body(jsonResource("error-sharing-notallowed")).contentType(APPLICATION_JSON));

		twitter.timelineOperations().retweet(12345);
	}
	
	@Test
	public void getRetweets() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/retweets/42.json?count=100&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweets(42L);
		assertTimelineTweets(timeline);						
	}

	@Test
	public void getRetweets_withCount() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/retweets/42.json?count=12&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweets(42L, 12);
		assertTimelineTweets(timeline);						
	}

	@Test
	public void getRetweets_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/retweets/42.json?count=100&include_entities=true"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getRetweets(42L);
		assertTimelineTweets(timeline);						
	}

	@Test
	public void getRetweets_withCount_appAuthorization() {
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/statuses/retweets/42.json?count=12&include_entities=true"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getRetweets(42L, 12);
		assertTimelineTweets(timeline);						
	}

	@Test
	public void getFavorites() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		mockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/list.json?count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getFavorites();
		assertTimelineTweets(timeline);
	}

	@Test
	public void getFavorites_paged() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		mockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/list.json?count=50&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getFavorites(50);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getFavorites_userId() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		mockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/list.json?count=20&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getFavorites(12345L);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getFavorites_paged_userId() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		mockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/list.json?count=50&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getFavorites(12345L, 50);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getFavorites_userId_appAuthorization() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/list.json?count=20&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
	 			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getFavorites(12345L);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getFavorites_paged_userId_appAuthorization() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/list.json?count=50&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
	 			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getFavorites(12345L, 50);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getFavorites_screenName() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		mockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/list.json?count=20&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getFavorites("habuma");
		assertTimelineTweets(timeline);
	}

	@Test
	public void getFavorites_paged_screenName() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		mockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/list.json?count=50&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getFavorites("habuma", 50);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getFavorites_screenName_appAuthorization() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/list.json?count=20&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
	 			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getFavorites("habuma");
		assertTimelineTweets(timeline);
	}

	@Test
	public void getFavorites_paged_screenName_appAuthorization() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/list.json?count=50&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
	 			.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getFavorites("habuma", 50);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getFavorites_paged_appAuthorization() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		appAuthMockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/list.json?count=50&include_entities=true"))
				.andExpect(method(GET))
				.andExpect(header("Authorization", "Bearer APP_ACCESS_TOKEN"))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = appAuthTwitter.timelineOperations().getFavorites(50);
		assertTimelineTweets(timeline);
	}

	@Test
	public void addToFavorites() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/create.json"))
			.andExpect(method(POST))
			.andRespond(withSuccess("{}", APPLICATION_JSON));
		twitter.timelineOperations().addToFavorites(42L);
		mockServer.verify();
	}
	
	@Test
	public void removeFromFavorites() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/favorites/destroy.json"))
			.andExpect(method(POST))
			.andRespond(withSuccess("{}", APPLICATION_JSON));
		twitter.timelineOperations().removeFromFavorites(71L);
		mockServer.verify();
	}
	
	// private helper
	private Resource getUploadResource(final String filename, String content) {
		Resource resource = new ByteArrayResource(content.getBytes()) {
			@Override
			public String getFilename() throws IllegalStateException {
				return filename;
			};
		};
		return resource;
	}
	
	private void assertOEmbedTweet(OEmbedTweet oembed) {
		assertEquals("Craig Walls", oembed.getAuthorName());
		assertEquals("https://twitter.com/habuma", oembed.getAuthorUrl());
		assertEquals(3153600000L, oembed.getCacheAge());
		assertNull(oembed.getHeight());
		assertEquals("<blockquote class=\"twitter-tweet\"><p>This does not bode well for those traveling out of DFW today. "
				+ "<a href=\"https://t.co/r1dJT15q3w\">pic.twitter.com/r1dJT15q3w</a></p>&mdash; Craig Walls (@habuma) "
				+ "<a href=\"https://twitter.com/habuma/statuses/357251561744916480\">July 16, 2013</a></blockquote>\n"
				+ "<script async src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>", oembed.getHtml());
		assertEquals("Twitter", oembed.getProviderName());
		assertEquals("https://twitter.com", oembed.getProviderUrl());
		assertEquals("rich", oembed.getType());
		assertEquals("https://twitter.com/habuma/statuses/357251561744916480", oembed.getUrl());
		assertEquals("1.0", oembed.getVersion());
		assertEquals(550, (int) oembed.getWidth());
	}
}
