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

import org.springframework.util.MultiValueMap;

/**
 * Defines the parameters of a user stream.
 * No parameters are required, indicating that only the messages pertaining to the authenticated user will be delivered.
 * @author Craig Walls
 */
public class UserStreamParameters extends AbstractStreamParameters {

	private WithOptions with;
	
	private boolean includeReplies = false;

	public UserStreamParameters with(WithOptions with) {
		this.with = with;
		return this;
	}
	
	/**
	 * Specify whether or not replies should be included in stream.
	 * The default behavior is to only include replies to the authenticated user, but not replies to tweets from those that the user follows.
	 * If set to true, then replies to tweets from followed users will be included in the stream.
	 * @param includeReplies true to include replies to followed users, false to only include replies to authenticated user.
	 * @return this UserStreamParameters for building up filter parameters.
	 */
	public UserStreamParameters includeReplies(boolean includeReplies) {
		this.includeReplies = includeReplies;
		return this;
	}
	
	@Override
	public MultiValueMap<String, String> toParameterMap() {
		MultiValueMap<String, String> parameterMap = super.toParameterMap();
		if (includeReplies) {
			parameterMap.set("replies", "all");
		}
		if (with != null) {
			parameterMap.set("with", with.name().toLowerCase());
		}
		return parameterMap;
	}
	
	public static enum WithOptions { USER, FOLLOWINGS }
}
