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
 * A stream event indicating that a tweet should be removed from the client.
 * @author Craig Walls
 */
public class StreamDeleteEvent extends TwitterObject {

	private final long tweetId;
	
	private final long userId;

	/**
	 * @return the ID of the tweet to delete
	 */
	public long getTweetId() {
		return tweetId;
	}

	/**
	 * @return the ID of the user who posted the tweet
	 */
	public long getUserId() {
		return userId;
	}

	public StreamDeleteEvent(long tweetId, long userId) {
		this.tweetId = tweetId;
		this.userId = userId;
	}
	
}
