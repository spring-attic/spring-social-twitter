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

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

import com.fasterxml.jackson.databind.ObjectMapper;

class StreamDispatcher implements Runnable {

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
		objectMapper.addMixIn(Tweet.class, TweetMixin.class);
		objectMapper.addMixIn(StreamDeleteEvent.class, StreamDeleteEventMixin.class);
		objectMapper.addMixIn(StreamWarningEvent.class, StreamWarningEventMixin.class);
		active = new AtomicBoolean(true);
	}

	public void run() {
		while(active.get()) {
			String line = queue.poll();
			if(line == null || line.length() == 0) return;
			
			// TODO: handle scrub_geo, status_withheld, user_withheld, disconnect, friends, events, 
			
			try {
				if (line.contains("in_reply_to_status_id_str")) { // TODO: This is kinda hacky
					handleTweet(line);
				} else if (line.startsWith("{\"limit")) {
					handleLimit(line);
				} else if (line.startsWith("{\"delete")) {
					handleDelete(line);
				} else if (line.startsWith("{\"warning")) {
					handleWarning(line);
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
			pool.submit((new Runnable() {
				public void run() {
					listener.onDelete(deleteEvent);
				}
			}));
		}
	}

	private void handleLimit(String line) throws IOException {
		final TrackLimitEvent limitEvent = objectMapper.readValue(line, TrackLimitEvent.class);
		for (final StreamListener listener : listeners) {
			pool.submit((new Runnable() {
				public void run() {
					listener.onLimit(limitEvent.getNumberOfLimitedTweets());
				}
			}));
		}
	}

	private void handleTweet(String line) throws IOException {
		final Tweet tweet = objectMapper.readValue(line, Tweet.class);
		for (final StreamListener listener : listeners) {
			pool.submit((new Runnable() {
				public void run() {
					listener.onTweet(tweet);
				}
			}));
		}
	}
	
	private void handleWarning(String line) throws IOException {
		final StreamWarningEvent warningEvent = objectMapper.readValue(line, StreamWarningEvent.class);
		for (final StreamListener listener : listeners) {
			pool.submit((new Runnable() {
				public void run() {
					listener.onWarning(warningEvent);
				}
			}));
		}
	}
	
}
