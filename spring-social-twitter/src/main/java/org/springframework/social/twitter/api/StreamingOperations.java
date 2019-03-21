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

import java.util.List;

/**
 * Defines operations for working with Twitter's streaming API
 * @author Craig Walls
 */
public interface StreamingOperations {
	
	/**
	 * Monitor the firehose stream, given a set of listeners.
	 * Per the documentation at https://dev.twitter.com/docs/api/1.1/get/statuses/firehose, the firehose stream requires special permission.
	 * @param listeners the listeners to monitor the stream
	 * @return the firehose stream
	 */
	Stream firehose(List<StreamListener> listeners);
	
	/**
	 * Monitor the firehose stream, given a set of listeners and a backfill value.
	 * In a reconnect scenario, the backfill parameter indicates how many messages to attempt to receive that may have been missed during the disconnect.
	 * The backfill count may be any value from 1 to 150000 or -1 to -150000. 
	 * Positive backfill values will result in a live feed once the backfilled messages have been delivered.
	 * Negative backfill values will result in a disconnect once the backfilled messages have been delivered.
	 * Per the documentation at https://dev.twitter.com/docs/api/1.1/get/statuses/firehose, the firehose stream requires special permission.
	 * @param backfill the number of messages to backfill. Valid values 1 to 150000 or -1 to -150000.
	 * @param listeners the listeners to monitor the stream
	 * @return the firehose stream
	 */
	Stream firehose(int backfill, List<StreamListener> listeners);

	/**
	 * Monitor the sample stream, given a set of listeners.
	 * @param listeners the listeners to monitor the stream
	 * @return the sample stream
	 */
	Stream sample(List<StreamListener> listeners);
	
	/**
	 * Monitor a filtered stream, given a set of listeners.
	 * @param trackKeywords the terms to track in the stream
	 * @param listeners the listeners to monitor the stream
	 * @return the filtered stream
	 */
	Stream filter(String trackKeywords, List<StreamListener> listeners);

	/**
	 * Monitor a filtered stream, given a set of listeners.
	 * @param parameters the stream's filter parameters
	 * @param listeners the listeners to monitor the stream
	 * @return the filtered stream
	 */
	Stream filter(FilterStreamParameters parameters, List<StreamListener> listeners);

	Stream user(List<StreamListener> listeners);
	
	Stream user(UserStreamParameters parameters, List<StreamListener> listeners);

}
