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
package org.springframework.social.twitter.api;

import java.util.List;

/**
 * Defines operations for working with Twitter's streaming API
 * @author Craig Walls
 */
public interface StreamingOperations {
	
	/**
	 * Monitor a filtered stream, given a set of listeners
	 * @param trackKeywords the terms to track in the stream
	 * @param listeners the listeners to monitor the stream
	 */
	void filter(String trackKeywords, List<StreamListener> listeners);
	
	/**
	 * Shutdown the open stream
	 */
	void stopStreaming();
	
}
