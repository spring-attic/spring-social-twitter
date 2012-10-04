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

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.client.match.RequestMatchers.*;
import static org.springframework.test.web.client.response.ResponseCreators.*;

import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.social.DuplicateStatusException;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.twitter.api.MessageTooLongException;
import org.springframework.social.twitter.api.StatusDetails;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;


/**
 * @author Craig Walls
 */
public class TimelineTemplateTest extends AbstractTwitterApiTest {

	@Test
	public void getPublicTimeline() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/public_timeline.json?include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getPublicTimeline();
		assertTimelineTweets(timeline);
	}

	@Test
	public void getHomeTimeline() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/home_timeline.json?page=1&count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getHomeTimeline();
		assertTimelineTweets(timeline);
	}

	@Test
	public void getHomeTimeline_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/home_timeline.json?page=3&count=100&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getHomeTimeline(3, 100);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getHomeTimeline_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/home_timeline.json?page=3&count=100&since_id=1234567&max_id=7654321&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getHomeTimeline(3, 100, 1234567, 7654321);
		assertTimelineTweets(timeline);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getHomeTimeline_unauthorized() {
		unauthorizedTwitter.timelineOperations().getHomeTimeline();
	}

	@Test
	public void getUserTimeline() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/user_timeline.json?page=1&count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline();
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/user_timeline.json?page=2&count=15&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline(2, 15);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/user_timeline.json?page=2&count=15&since_id=123456&max_id=654321&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline(2, 15, 123456, 654321);
		assertTimelineTweets(timeline);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getUserTimeline_unauthorized() {
		unauthorizedTwitter.timelineOperations().getUserTimeline();
	}
	
	@Test
	public void getUserTimeline_forScreenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/user_timeline.json?page=1&count=20&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline("habuma");
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forScreenName_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/user_timeline.json?page=6&count=24&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline("habuma", 6, 24);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forScreenName_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/user_timeline.json?page=6&count=24&since_id=112233&max_id=332211&screen_name=habuma&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline("habuma", 6, 24, 112233, 332211);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forUserId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/user_timeline.json?page=1&count=20&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline(12345);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forUserId_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/user_timeline.json?page=6&count=24&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline(12345, 6, 24);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getUserTimeline_forUserId_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/user_timeline.json?page=6&count=24&since_id=112233&max_id=332211&user_id=12345&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getUserTimeline(12345, 6, 24, 112233, 332211);
		assertTimelineTweets(timeline);
	}

	@Test
	public void getMentions() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/mentions.json?page=1&count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> mentions = twitter.timelineOperations().getMentions();
		assertTimelineTweets(mentions);
	}

	@Test
	public void getMentions_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/mentions.json?page=3&count=50&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> mentions = twitter.timelineOperations().getMentions(3, 50);
		assertTimelineTweets(mentions);
	}

	@Test
	public void getMentions_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/mentions.json?page=3&count=50&since_id=112233&max_id=332211&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> mentions = twitter.timelineOperations().getMentions(3, 50, 112233, 332211);
		assertTimelineTweets(mentions);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getMentions_unauthorized() {
		unauthorizedTwitter.timelineOperations().getMentions();
	}

	@Test
	public void getRetweetedByMe() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_by_me.json?page=1&count=20&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedByMe();
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedByMe_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_by_me.json?page=5&count=42&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedByMe(5, 42);
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedByMe_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_by_me.json?page=5&count=42&since_id=24680&max_id=86420&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedByMe(5, 42, 24680, 86420);
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedByUser_userId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_by_user.json?page=1&count=20&user_id=12345678&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedByUser(12345678);
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedByUser_userId_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_by_user.json?page=5&count=42&user_id=12345678&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedByUser(12345678, 5, 42);
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedByUser_userId_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_by_user.json?page=5&count=42&since_id=24680&max_id=86420&user_id=12345678&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedByUser(12345678, 5, 42, 24680, 86420);
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedByUser_screenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_by_user.json?page=1&count=20&screen_name=habuma&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedByUser("habuma");
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedByUser_screenName_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_by_user.json?page=5&count=42&screen_name=habuma&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedByUser("habuma", 5, 42);
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedByUser_screenName_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_by_user.json?page=5&count=42&since_id=24680&max_id=86420&screen_name=habuma&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedByUser("habuma", 5, 42, 24680, 86420);
		assertTimelineTweets(timeline);		
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void getRetweetedByMe_unauthorized() {
		unauthorizedTwitter.timelineOperations().getRetweetedByMe();
	}
	
	@Test
	public void getRetweetedToMe() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_to_me.json?page=1&count=20&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedToMe();
		assertTimelineTweets(timeline);				
	}

	@Test
	public void getRetweetedToMe_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_to_me.json?page=4&count=40&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedToMe(4, 40);
		assertTimelineTweets(timeline);				
	}

	@Test
	public void getRetweetedToMe_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_to_me.json?page=4&count=40&since_id=12345&max_id=54321&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedToMe(4, 40, 12345, 54321);
		assertTimelineTweets(timeline);				
	}

	@Test(expected = NotAuthorizedException.class)
	public void getRetweetedToMe_unauthorized() {
		unauthorizedTwitter.timelineOperations().getRetweetedToMe();
	}

	@Test
	public void getRetweetedToUser_userId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_to_user.json?page=1&count=20&user_id=12345678&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedToUser(12345678);
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedToUser_userId_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_to_user.json?page=5&count=42&user_id=12345678&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedToUser(12345678, 5, 42);
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedToUser_userId_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_to_user.json?page=5&count=42&since_id=24680&max_id=86420&user_id=12345678&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedToUser(12345678, 5, 42, 24680, 86420);
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedToUser_screenName() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_to_user.json?page=1&count=20&screen_name=habuma&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedToUser("habuma");
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedToUser_screenName_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_to_user.json?page=5&count=42&screen_name=habuma&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedToUser("habuma", 5, 42);
		assertTimelineTweets(timeline);		
	}

	@Test
	public void getRetweetedToUser_screenName_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweeted_to_user.json?page=5&count=42&since_id=24680&max_id=86420&screen_name=habuma&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetedToUser("habuma", 5, 42, 24680, 86420);
		assertTimelineTweets(timeline);		
	}
	
	@Test
	public void getRetweetsOfMe() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweets_of_me.json?page=1&count=20&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetsOfMe();
		assertTimelineTweets(timeline);				
	}

	@Test
	public void getRetweetsOfMe_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweets_of_me.json?page=7&count=25&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetsOfMe(7, 25);
		assertTimelineTweets(timeline);				
	}

	@Test
	public void getRetweetsOfMe_paged_withSinceIdAndMaxId() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweets_of_me.json?page=7&count=25&since_id=2345&max_id=3456&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweetsOfMe(7, 25, 2345, 3456);
		assertTimelineTweets(timeline);				
	}

	@Test(expected = NotAuthorizedException.class)
	public void getRetweetsOfMe_unauthorized() {
		unauthorizedTwitter.timelineOperations().getRetweetsOfMe();
	}
	
	@Test
	public void getStatus() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/show/12345.json?include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		
		Tweet tweet = twitter.timelineOperations().getStatus(12345);
		assertSingleTweet(tweet);
	}
	
	@Test
	public void updateStatus() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message");
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test(expected = NotAuthorizedException.class)
	public void updateStatus_unauthorized() {
		unauthorizedTwitter.timelineOperations().updateStatus("Shouldn't work");
	}

	@Test
	public void updateStatus_withImage() {
		mockServer.expect(requestTo("https://upload.twitter.com/1/statuses/update_with_media.json"))
				.andExpect(method(POST))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		// TODO: Match body content to ensure fields and photo are included
		Resource photo = getUploadResource("photo.jpg", "PHOTO DATA");
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message", photo);
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withLocation() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&lat=123.1&long=-111.2"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));

		StatusDetails details = new StatusDetails();
		details.setLocation(123.1f, -111.2f);
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message", details);
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withLocationAndDisplayCoordinates() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&lat=123.1&long=-111.2&display_coordinates=true"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));

		StatusDetails details = new StatusDetails();
		details.setLocation(123.1f, -111.2f);
		details.setDisplayCoordinates(true);
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message", details);
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withInReplyToStatus() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message+in+reply+to+%40someone&in_reply_to_status_id=123456"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));

		StatusDetails details = new StatusDetails();
		details.setInReplyToStatusId(123456);
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message in reply to @someone", details);
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withWrapLinks() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message&wrap_links=true"))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));

		StatusDetails details = new StatusDetails();
		details.setWrapLinks(true);
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message", details);
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test
	public void updateStatus_withImageAndLocation() {
		mockServer.expect(requestTo("https://upload.twitter.com/1/statuses/update_with_media.json"))
				.andExpect(method(POST))
				.andRespond(withSuccess(jsonResource("status"), APPLICATION_JSON));
		// TODO: Match body content to ensure fields and photo are included
		Resource photo = getUploadResource("photo.jpg", "PHOTO DATA");
		StatusDetails details = new StatusDetails();
		details.setLocation(123.1f, -111.2f);
		Tweet tweet = twitter.timelineOperations().updateStatus("Test Message", photo, details);
		assertSingleTweet(tweet);
		mockServer.verify();
	}

	@Test(expected = NotAuthorizedException.class)
	public void updateStatus_withLocation_unauthorized() {
		StatusDetails details = new StatusDetails();
		details.setLocation(123.1f, -111.2f);
		unauthorizedTwitter.timelineOperations().updateStatus("Test Message", details);
	}

	@Test(expected = DuplicateStatusException.class)
	public void updateStatus_duplicateTweet() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message"))
				.andRespond(withStatus(FORBIDDEN).body("{\"error\":\"You already said that\"}").contentType(APPLICATION_JSON));
		twitter.timelineOperations().updateStatus("Test Message");
	}
	
	@Test(expected=MessageTooLongException.class)
	public void updateStatus_tweetTooLong() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/update.json"))
			.andExpect(method(POST))
			.andExpect(content().string("status=Really+long+message"))
			.andRespond(withStatus(FORBIDDEN).body("{\"error\":\"Status is over 140 characters.\"}").contentType(APPLICATION_JSON));
		twitter.timelineOperations().updateStatus("Really long message");
	}
	
	@Test(expected = OperationNotPermittedException.class)
	public void updateStatus_forbidden() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/update.json"))
				.andExpect(method(POST))
				.andExpect(content().string("status=Test+Message"))
				.andRespond(withStatus(FORBIDDEN).body("{\"error\":\"Forbidden\"}").contentType(APPLICATION_JSON));
		twitter.timelineOperations().updateStatus("Test Message");
	}

	@Test
	public void deleteStatus() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/destroy/12345.json"))
			.andExpect(method(DELETE))
			.andRespond(withSuccess("{}", APPLICATION_JSON));
		twitter.timelineOperations().deleteStatus(12345L);
		mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void deleteStatus_unauthorized() {
		unauthorizedTwitter.timelineOperations().deleteStatus(12345L);
	}
	
	@Test
	public void retweet() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweet/12345.json"))
				.andExpect(method(POST))
			.andRespond(withSuccess("{}", APPLICATION_JSON));

		twitter.timelineOperations().retweet(12345);

		mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void retweet_unauthorized() {
		unauthorizedTwitter.timelineOperations().retweet(12345L);
	}

	@Test(expected=DuplicateStatusException.class)
	public void retweet_duplicateTweet() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweet/12345.json"))
				.andExpect(method(POST))
				.andRespond(withStatus(FORBIDDEN).body("{\"error\":\"You already said that\"}").contentType(APPLICATION_JSON));

		twitter.timelineOperations().retweet(12345);
	}

	@Test(expected = OperationNotPermittedException.class)
	public void retweet_forbidden() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweet/12345.json"))
				.andExpect(method(POST))
				.andRespond(withStatus(FORBIDDEN).body("{\"error\":\"Forbidden\"}").contentType(APPLICATION_JSON));

		twitter.timelineOperations().retweet(12345);
	}

	@Test(expected = OperationNotPermittedException.class)
	public void retweet_sharingNotAllowed() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweet/12345.json"))
				.andExpect(method(POST))
				.andRespond(withStatus(FORBIDDEN).body(jsonResource("error-sharing-notallowed")).contentType(APPLICATION_JSON));

		twitter.timelineOperations().retweet(12345);
	}
	
	@Test
	public void getRetweets() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweets/42.json?count=100&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweets(42L);
		assertTimelineTweets(timeline);						
	}

	@Test
	public void getRetweets_withCount() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/retweets/42.json?count=12&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("timeline"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getRetweets(42L, 12);
		assertTimelineTweets(timeline);						
	}

	@Test
	public void getRetweetedBy() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/42/retweeted_by.json?page=1&count=100&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("retweeted-by"), APPLICATION_JSON));
		List<TwitterProfile> retweetedBy = twitter.timelineOperations().getRetweetedBy(42L);
		assertEquals(2, retweetedBy.size());
		assertEquals("royclarkson", retweetedBy.get(0).getScreenName());
		assertEquals("kdonald", retweetedBy.get(1).getScreenName());
		
		mockServer.verify();
	}

	@Test
	public void getRetweetedBy_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/42/retweeted_by.json?page=2&count=25&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("retweeted-by"), APPLICATION_JSON));
		List<TwitterProfile> retweetedBy = twitter.timelineOperations().getRetweetedBy(42L, 2, 25);
		assertEquals(2, retweetedBy.size());
		assertEquals("royclarkson", retweetedBy.get(0).getScreenName());
		assertEquals("kdonald", retweetedBy.get(1).getScreenName());
		
		mockServer.verify();
	}

	@Test
	public void getRetweetedByIds() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/42/retweeted_by/ids.json?page=1&count=100&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("retweeted-by-ids"), APPLICATION_JSON));
		List<Long> retweetedByIds = twitter.timelineOperations().getRetweetedByIds(42L);
		assertEquals(3, retweetedByIds.size());
		assertEquals(12345, (long) retweetedByIds.get(0));
		assertEquals(9223372036854775807L, (long) retweetedByIds.get(1));
		assertEquals(34567, (long) retweetedByIds.get(2));
	}

	@Test
	public void getRetweetedByIds_paged() {
		mockServer.expect(requestTo("https://api.twitter.com/1/statuses/42/retweeted_by/ids.json?page=3&count=60&include_entities=true"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("retweeted-by-ids"), APPLICATION_JSON));
		List<Long> retweetedByIds = twitter.timelineOperations().getRetweetedByIds(42L, 3, 60);
		assertEquals(3, retweetedByIds.size());
		assertEquals(12345, (long) retweetedByIds.get(0));
		assertEquals(9223372036854775807L, (long) retweetedByIds.get(1));
		assertEquals(34567, (long) retweetedByIds.get(2));
	}

	@Test(expected = NotAuthorizedException.class)
	public void getRetweetedByIds_unauthorized() {
		unauthorizedTwitter.timelineOperations().getRetweetedByIds(12345L);
	}
	
	@Test
	public void getFavorites() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		mockServer.expect(requestTo("https://api.twitter.com/1/favorites.json?page=1&count=20&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getFavorites();
		assertTimelineTweets(timeline);
	}

	@Test
	public void getFavorites_paged() {
		// Note: The documentation for /favorites.json doesn't list the count parameter, but it works anyway.
		mockServer.expect(requestTo("https://api.twitter.com/1/favorites.json?page=3&count=50&include_entities=true"))
				.andExpect(method(GET))
				.andRespond(withSuccess(jsonResource("favorite"), APPLICATION_JSON));
		List<Tweet> timeline = twitter.timelineOperations().getFavorites(3, 50);
		assertTimelineTweets(timeline);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getFavorites_unauthorized() {
		unauthorizedTwitter.timelineOperations().getFavorites();
	}

	@Test
	public void addToFavorites() {
		mockServer.expect(requestTo("https://api.twitter.com/1/favorites/create/42.json"))
			.andExpect(method(POST))
			.andRespond(withSuccess("{}", APPLICATION_JSON));
		twitter.timelineOperations().addToFavorites(42L);
		mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void addToFavorites_unauthorized() {
		unauthorizedTwitter.timelineOperations().addToFavorites(12345L);
	}

	@Test
	public void removeFromFavorites() {
		mockServer.expect(requestTo("https://api.twitter.com/1/favorites/destroy/71.json"))
			.andExpect(method(POST))
			.andRespond(withSuccess("{}", APPLICATION_JSON));
		twitter.timelineOperations().removeFromFavorites(71L);
		mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void removeFromFavorites_unauthorized() {
		unauthorizedTwitter.timelineOperations().removeFromFavorites(12345L);
	}
	
	// private helper
	private Resource getUploadResource(final String filename, String content) {
		Resource resource = new ByteArrayResource(content.getBytes()) {
			public String getFilename() throws IllegalStateException {
				return filename;
			};
		};
		return resource;
	}
	
}
