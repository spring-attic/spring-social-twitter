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
 * Defines the parameters of a filtered stream.
 * You must set at least one of track, follow, or locations.
 * @author Craig Walls
 */
public class FilterStreamParameters extends AbstractStreamParameters {

	protected StringBuffer follow = new StringBuffer();

	/**
	 * Add a user to follow in the stream.
	 * Does not replace any existing follows in the filter.
	 * @param follow the Twitter user ID of a user to follow in the stream.
	 * @return this FilterStreamParameters for building up filter parameters.
	 */
	public FilterStreamParameters follow(long follow) {
		if (this.follow.length() > 0) {
			this.follow.append(',');
		}
		this.follow.append(follow);
		return this;
	}
	
	/**
	 * @return the follow parameters as they'll be sent in the streaming request.
	 */
	public String getFollowParameterValue() {
		return follow.toString();
	}
	
	/**
	 * Whether or not at least one of track, follow, or locations has values.
	 * @return true if the minimum requirements are met.
	 */
	public boolean isValid() {
		return follow.length() > 0 || track.length() > 0 || locations.length() > 0;
	}

	@Override
	public MultiValueMap<String, String> toParameterMap() {
		MultiValueMap<String, String> parameterMap = super.toParameterMap();
		if (follow != null && follow.length() > 0) {
			parameterMap.set("follow", follow.toString());
		}
		return parameterMap;
	}
}
