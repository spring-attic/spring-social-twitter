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
package org.springframework.social.twitter.api;

/**
 * Listener interface for clients consuming data from a Twitter stream.
 * @author Craig Walls
 */
public interface StreamListener {
		
	/**
	 * Called when a new Tweet is available on the stream
	 * @param tweet a tweet available on the stream
	 */
	void onTweet(Tweet tweet);
	
	/**
	 * Called when a delete message is available on the stream
	 * @param deleteEvent a delete event
	 */
	void onDelete(StreamDeleteEvent deleteEvent);

	/**
	 * Called when the stream is being track limited.
	 * @param numberOfLimitedTweets the number of tweets being limited on the stream
	 */
	void onLimit(int numberOfLimitedTweets);
	
	/**
	 * Called when a client is stalling and the stream is in danger of being disconnected.
	 * @param warningEvent a warning event
	 */
	void onWarning(StreamWarningEvent warningEvent);
	
}
