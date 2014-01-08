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

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.twitter.api.DirectMessage;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import com.fasterxml.jackson.databind.ObjectMapper;

class StreamDispatcher implements Runnable {
	private static final Log logger = LogFactory.getLog(StreamDispatcher.class);
	
	private final List<StreamListener> listeners;

	private ObjectMapper objectMapper;
	
	private AtomicBoolean active;

	private final Queue<String> queue;
	
	private final ExecutorService pool;

	public StreamDispatcher(Queue<String> queue, List<StreamListener> listeners) {
		this.queue = queue;
		this.listeners = listeners;
		pool = Executors.newCachedThreadPool();
		objectMapper = new ObjectMapper();
		objectMapper.addMixInAnnotations(Tweet.class, TweetMixin.class);
		objectMapper.addMixInAnnotations(StreamDeleteEvent.class, StreamDeleteEventMixin.class);
		objectMapper.addMixInAnnotations(StreamWarningEvent.class, StreamWarningEventMixin.class);
		objectMapper.addMixInAnnotations(StreamEvent.class, StreamEventMixin.class);
		objectMapper.addMixInAnnotations(DirectMessage.class, DirectMessageMixin.class);
		objectMapper.addMixInAnnotations(TwitterProfile.class, TwitterProfileMixin.class);
		active = new AtomicBoolean(true);
	}

	public void run() {
		while(active.get()) {
			String line = queue.poll();
			if(line == null || line.length() == 0) return;
			
			// TODO: handle scrub_geo, status_withheld, user_withheld, disconnect, friends, events, 
			
			logger.debug(line);
			
			try {
				if (line.contains("in_reply_to_status_id_str")) { // TODO: This is kinda hacky
					handleTweet(line);
				} else if (line.startsWith("{\"limit")) {
					handleLimit(line);
				} else if (line.startsWith("{\"delete")) {
					handleDelete(line);
				} else if (line.startsWith("{\"warning")) {
					handleWarning(line);
				} else if (line.startsWith("{\"event")) {
					handleEvent(line);
				}
				else if (line.startsWith("{\"direct_message")) {
					handleDirectMessage(line);
				}
			} catch (IOException e) {
				// TODO: Should only happen if Jackson doesn't know how to map the line
			}
		}
	}
	
	public void stop() {
		active.set(false);
		pool.shutdown();
	}
	
	private void handleDelete(String line) throws IOException {
		final StreamDeleteEvent deleteEvent = objectMapper.readValue(line, StreamDeleteEvent.class);
		for (final StreamListener listener : listeners) {
			Future<?> result = pool.submit((new Runnable() {
				public void run() {
					listener.onDelete(deleteEvent);
				}
			}));
		}
	}

	private void handleLimit(String line) throws IOException {
		final TrackLimitEvent limitEvent = objectMapper.readValue(line, TrackLimitEvent.class);
		for (final StreamListener listener : listeners) {
			Future<?> result = pool.submit((new Runnable() {
				public void run() {
					listener.onLimit(limitEvent.getNumberOfLimitedTweets());
				}
			}));
		}
	}

	private void handleTweet(String line) throws IOException {
		final Tweet tweet = objectMapper.readValue(line, Tweet.class);
		for (final StreamListener listener : listeners) {
			Future<?> result = pool.submit((new Runnable() {
				public void run() {
					listener.onTweet(tweet);
				}
			}));
		}
	}
	
	private void handleWarning(String line) throws IOException {
		final StreamWarningEvent warningEvent = objectMapper.readValue(line, StreamWarningEvent.class);
		for (final StreamListener listener : listeners) {
			Future<?> result = pool.submit((new Runnable() {
				public void run() {
					listener.onWarning(warningEvent);
				}
			}));
		}
	}
	
	private void handleEvent(String line) throws IOException {
		final StreamEvent event = objectMapper.readValue(line, StreamEvent.class);
		for (final StreamListener listener : listeners) {
			Future<?> result = pool.submit((new Runnable() {
				public void run() {
					listener.onEvent(event);
				}
			}));
		}
	}
	
	private void handleDirectMessage(String line) throws IOException {
		String messagePart = objectMapper.readTree(line).get("direct_message").toString();
		final DirectMessage directMessage = objectMapper.readValue(messagePart, DirectMessage.class);
		for (final StreamListener listener : listeners) {
			Future<?> result = pool.submit((new Runnable() {
				public void run() {
					listener.onDirectMessage(directMessage);
				}
			}));
		}
	}
	
}
