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

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.social.twitter.api.DirectMessage;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

/**
 * @author Craig Walls
 */
public class StreamingTemplateTest extends AbstractTwitterApiTest {

	@Test
	@Ignore
	public void filter() throws Exception {
		mockServer.expect(requestTo("http://stream.twitter.com/1/statuses/filter.json"))
			.andExpect(method(POST))
			.andExpect(content().string("track=dallas"))
			.andRespond(withSuccess(new ClassPathResource("filter-stream-track.json"), APPLICATION_JSON));
		
		MockStreamListener listener = new MockStreamListener(7) {
			protected void shutdown() {
//				twitter.streamingOperations().stopStreaming();
			}
		};
		
		twitter.streamingOperations().filter("dallas", Arrays.<StreamListener>asList(listener));
		Thread.sleep(1000); // delay while stream is read
		assertEquals(4, listener.tweetsReceived.size());
		assertEquals(2, listener.deletesReceived.size());
		assertEquals(1, listener.limitsReceived.size());
		assertEquals(2, listener.eventsReceived.size());
		assertEquals(1, listener.directMessagesReceived.size());
	}
	
	@Test
	@Ignore
	public void filter_streamClosedByTwitter() throws Exception {
		for(int i=0;i<3;i++) { // expect the stream to be reopened 3 times
			mockServer.expect(requestTo("http://stream.twitter.com/1/statuses/filter.json"))
				.andExpect(method(POST))
			.andExpect(content().string("track=dallas"))
				.andRespond(withSuccess(new ClassPathResource("filter-stream-track.json"), APPLICATION_JSON));
		}
		
		MockStreamListener listener = new MockStreamListener(21) {
			protected void shutdown() {
//				twitter.streamingOperations().stopStreaming();
			}
		};
		
		twitter.streamingOperations().filter("dallas", Arrays.<StreamListener>asList(listener));
		Thread.sleep(1000); // delay while stream is read
		assertEquals(12, listener.tweetsReceived.size());
		assertEquals(6, listener.deletesReceived.size());
		assertEquals(3, listener.limitsReceived.size());
	}
	
	private abstract static class MockStreamListener implements StreamListener {

		List<Tweet> tweetsReceived = new ArrayList<Tweet>();
		List<StreamDeleteEvent> deletesReceived = new ArrayList<StreamDeleteEvent>();
		List<Integer> limitsReceived = new ArrayList<Integer>();
		List<StreamWarningEvent> warningsReceived = new ArrayList<StreamWarningEvent>();
		List<StreamEvent> eventsReceived = new ArrayList<StreamEvent>();
		List<DirectMessage> directMessagesReceived = new ArrayList<DirectMessage>();
		private int stopAfter = Integer.MAX_VALUE;
		
		public MockStreamListener(int stopAfter) {
			this.stopAfter = stopAfter;
		}
		
		public void onTweet(Tweet tweet) {
			tweetsReceived.add(tweet);
			messageReceived();
		}

		public void onDelete(StreamDeleteEvent deleteEvent) {
			deletesReceived.add(deleteEvent);
			messageReceived();
		}

		public void onLimit(int numberOfLimitedTweets) {
			limitsReceived.add(numberOfLimitedTweets);
			messageReceived();
		}
		
		public void onWarning(StreamWarningEvent warningEvent) {
			warningsReceived.add(warningEvent);
			messageReceived();
		}
		
		public void onEvent(StreamEvent event) {
			eventsReceived.add(event);
			messageReceived();
		}
		
		public void onDirectMessage(DirectMessage directMessage) {
			directMessagesReceived.add(directMessage);
			messageReceived();
		}

		private void messageReceived() {
			stopAfter--;
			if(stopAfter == 0) {
				shutdown();
			}
		}
		
		protected abstract void shutdown();
	}
}
