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
 * Model class representing Twitter account settings.
 * @author Craig Walls
 */
public class AccountSettings extends TwitterObject {

	private boolean alwaysUseHttps;
	private boolean discoverableByEmail;
	private boolean discoverableByMobilePhone;
	private boolean displaySensitiveMedia;
	private boolean geoEnabled;
	private String language;
	private boolean isProtected;
	private String screenName;
	private boolean useCookiePersonalization;
	private SleepTime sleepTime;
	private TimeZone timeZone;
	private List<TrendLocation> trendLocation;
	
	/**
	 * @return true if the account is set to always use HTTPS; false otherwise.
	 */
	public boolean isAlwaysUseHttps() {
		return alwaysUseHttps;
	}
	
	/**
	 * @return true if the account is discoverable by email; false otherwise.
	 */
	public boolean isDiscoverableByEmail() {
		return discoverableByEmail;
	}
	
	/**
	 * @return true if the account is discoverable by mobile phone; false otherwise.
	 */
	public boolean isDiscoverableByMobilePhone() {
		return discoverableByMobilePhone;
	}
	
	/**
	 * @return true if the account's tweets may only be visible to approved users; false if the tweets are public.
	 */
	public boolean isDisplaySensitiveMedia() {
		return displaySensitiveMedia;
	}
	
	/**
	 * @return true if the account is geo-enabled; false otherwise.
	 */
	public boolean isGeoEnabled() {
		return geoEnabled;
	}
	
	/**
	 * @return The account's language.
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * @return true if the account is set for tweet visibility to be only for authorized u; false otherwise.
	 */
	public boolean isProtected() {
		return isProtected;
	}
	
	/**
	 * @return The account's screen name.
	 */
	public String getScreenName() {
		return screenName;
	}
	
	/**
	 * @return true if the account is set to tailor content based on recent website visits; false otherwise.
	 */
	public boolean isUseCookiePersonalization() {
		return useCookiePersonalization;
	}
	
	/**
	 * @return The account's time zone.
	 */
	public TimeZone getTimeZone() {
		return timeZone;
	}
	
	/**
	 * @return The account's mobile update sleep time settings.
	 */
	public SleepTime getSleepTime() {
		return sleepTime;
	}
	
	/**
	 * @return The account's trend location settings.
	 */
	public List<TrendLocation> getTrendLocation() {
		return trendLocation;
	}
	
	/**
	 * The account's mobile update sleep time settings.
	 */
	public static class SleepTime {
		private boolean enabled;
		private Integer startTime;
		private Integer endTime;
		
		/**
		 * @return true if a sleep time for mobile updates is enabled; false if not.
		 */
		public boolean isEnabled() {
			return enabled;
		}
		
		/**
		 * @return The start time in hours since midnight. (Where 0=midnight, 23=11pm.) 
		 */
		public Integer getStartTime() {
			return startTime;
		}

		/**
		 * @return The end time in hours since midnight. (Where 0=midnight, 23=11pm.) 
		 */
		public Integer getEndTime() {
			return endTime;
		}
	}
	
	/**
	 * The account's timezone settings.
	 */
	public static class TimeZone {
		private String name;
		private String tzInfoName;
		private int utcOffset;
		
		public String getName() {
			return name;
		}
		
		public String getTZInfoName() {
			return tzInfoName;
		}
		
		/**
		 * @return The UTF offset (in seconds)
		 */
		public int getUTCOffset() {
			return utcOffset;
		}
	}
	
	/**
	 * The account's trend location settings.
	 */
	public static class TrendLocation {
		private String country;
		private String countryCode;
		private String name;
		private long parentId;
		private String url;
		private long whereOnEarthID;
		
		public String getCountry() {
			return country;
		}
		
		public String getCountryCode() {
			return countryCode;
		}
		
		public String getName() {
			return name;
		}
		
		public long getParentId() {
			return parentId;
		}
		
		public String getUrl() {
			return url;
		}
		
		public long getWhereOnEarthID() {
			return whereOnEarthID;
		}
	}
}
