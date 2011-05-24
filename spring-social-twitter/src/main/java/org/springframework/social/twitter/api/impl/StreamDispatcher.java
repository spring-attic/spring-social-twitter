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

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.twitter.api.DeleteTweetEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.Tweet;

class StreamDispatcher implements Runnable {

	private final List<StreamListener> listeners;

	private final String line;

	private ObjectMapper objectMapper;

	public StreamDispatcher(List<StreamListener> listeners, String line) {
		this.listeners = listeners;
		this.line = line;
		objectMapper = new ObjectMapper();
		objectMapper.getDeserializationConfig().addMixInAnnotations(Tweet.class, TweetMixin.class);
		objectMapper.getDeserializationConfig().addMixInAnnotations(DeleteTweetEvent.class, DeleteTweetEventMixin.class);
	}

	public void run() {
		try {
			if (line.contains("in_reply_to_status_id_str")) {
				handleTweet();
			} else if (line.startsWith("{\"limit")) {
				handleLimit();				
			} else if (line.startsWith("{\"delete")) {
				handleDelete();				
			}
		} catch (IOException e) {
			// TODO: Should only happen if Jackson doesn't know how to map the line
		}
	}

	private void handleDelete() throws IOException {
		final DeleteTweetEvent deleteEvent = objectMapper.readValue(line, DeleteTweetEvent.class);
		for (final StreamListener listener : listeners) {
			new Thread(new Runnable() {
				public void run() {
					listener.onDelete(deleteEvent);
				}
			}).start();
		}
	}

	private void handleLimit() throws IOException {
		final TrackLimitEvent limitEvent = objectMapper.readValue(line, TrackLimitEvent.class);
		for (final StreamListener listener : listeners) {
			new Thread(new Runnable() {
				public void run() {
					listener.onLimit(limitEvent.getNumberOfLimitedTweets());
				}
			}).start();
		}
	}

	private void handleTweet() throws IOException {
		final Tweet tweet = objectMapper.readValue(line, Tweet.class);
		for (final StreamListener listener : listeners) {
			new Thread(new Runnable() {
				public void run() {
					listener.onTweet(tweet);
				}
			}).start();
		}
	}
}
