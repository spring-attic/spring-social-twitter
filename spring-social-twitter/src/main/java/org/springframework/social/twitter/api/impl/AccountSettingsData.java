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

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Builder for account settings data used to update a user's account settings.
 * @author Craig Walls
 */
public class AccountSettingsData {

	private Long trendLocationWOEID;
	private Boolean sleepTimeEnabled;
	private Integer startSleepTime;
	private Integer endSleepTime;
	private String timeZone;
	private String lang;
	
	public AccountSettingsData trendLocationWOEID(long trendLocationWOEID) {
		this.trendLocationWOEID = trendLocationWOEID;
		return this;
	}
	
	public AccountSettingsData withSleepTimeEnabled(int startSleepTime, int endSleepTime) {
		this.sleepTimeEnabled = true;
		this.startSleepTime = startSleepTime;
		this.endSleepTime = endSleepTime;
		return this;
	}
	
	public AccountSettingsData withSleepTimeDisabled() {
		this.sleepTimeEnabled = false;
		this.startSleepTime = null;
		this.endSleepTime = null;
		return this;
	}
	
	public AccountSettingsData timeZone(String timeZone) {
		this.timeZone = timeZone;
		return this;
	}

	public AccountSettingsData language(String language) {
		this.lang = language;
		return this;
	}
	
	public MultiValueMap<String, Object> toRequestParameters() {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		if (trendLocationWOEID != null) {
			params.set("trend_location_woeid", trendLocationWOEID.toString());
		}
		if (sleepTimeEnabled != null) {
			params.set("sleep_time_enabled", sleepTimeEnabled.toString());
			if (sleepTimeEnabled) {
				params.set("start_sleep_time", startSleepTime.toString());
				params.set("end_sleep_time", endSleepTime.toString());
			}
		}
		if (timeZone != null) {
			params.set("time_zone", timeZone);
		}
		if (lang != null) {
			params.set("lang", lang);
		}		
		return params;
	}

}
