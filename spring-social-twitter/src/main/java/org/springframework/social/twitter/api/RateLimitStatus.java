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

import java.util.Date;

/**
 * Carries data concerning the rate limit status.
 */
public class RateLimitStatus {

	private final String endpoint;

	private final int quarterOfHourLimit;
	
	private final int remainingHits;
	
	private final long resetTimeInSeconds;
	
	private final Date resetTime;
	
	public RateLimitStatus(String endpoint, int quarterOfHourLimit, int remainingHits, long resetTimeInSeconds) {
		this.endpoint = endpoint;
		this.quarterOfHourLimit = quarterOfHourLimit;
		this.remainingHits = remainingHits;
		this.resetTimeInSeconds = resetTimeInSeconds;
		this.resetTime = new Date(resetTimeInSeconds * 1000);
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	
	public int getQuarterOfHourLimit() {
		return quarterOfHourLimit;
	}

	public int getRemainingHits() {
		return remainingHits;
	}

	public long getResetTimeInSeconds() {
		return resetTimeInSeconds;
	}
	
	public Date getResetTime() {
		return resetTime;
	}

	/**
	 * The approximate number of seconds until the rate limits are reset.
	 * Note that this method recalculates the seconds until reset on each call.
	 * @return the approximate number of seconds until the rate limits are reset.
	 */
	public int getSecondsUntilReset() {
		return (int) (resetTimeInSeconds - System.currentTimeMillis() / 1000);
	}
}
