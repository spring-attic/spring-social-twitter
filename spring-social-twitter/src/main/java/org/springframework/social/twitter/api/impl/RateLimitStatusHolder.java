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

import java.util.List;
import java.util.Map;

import org.springframework.social.twitter.api.RateLimitStatus;
import org.springframework.social.twitter.api.ResourceFamily;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Holds a Map&lt;ResourceFamily, List&lt;RateLimitStatus&gt;&gt; object deserialized from Twitter's rate limit status
 * JSON structure.
 * @author Jeremy Appel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = RateLimitStatusDeserializer.class)
public class RateLimitStatusHolder {
	
	private final Map<ResourceFamily, List<RateLimitStatus>> rateLimitsResultMap;

	public RateLimitStatusHolder(Map<ResourceFamily, List<RateLimitStatus>> rateLimitsResultMap) {
		this.rateLimitsResultMap = rateLimitsResultMap;
	}
	
	/**
	 * The map of rate limit statuses per resource family
	 * @return Map&lt;ResourceFamily, List&lt;RateLimitStatus&gt;&gt;
	 */
	public Map<ResourceFamily, List<RateLimitStatus>> getRateLimits() {
		return rateLimitsResultMap;
	}

}
