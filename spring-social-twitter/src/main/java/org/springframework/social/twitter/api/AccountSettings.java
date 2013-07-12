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

public class AccountSettings {

	private boolean alwaysUseHttps;
	private boolean discoverableByEmail;
	private boolean geoEnabled;
	private String language;
	private boolean isProtected;
	private String screenName;
	private boolean showAllInlineMedia;
	private boolean useCookiePersonalization;
	private TimeZone timeZone;
	
	public boolean isAlwaysUseHttps() {
		return alwaysUseHttps;
	}
	
	public boolean isDiscoverableByEmail() {
		return discoverableByEmail;
	}
	
	public boolean isGeoEnabled() {
		return geoEnabled;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public boolean isProtected() {
		return isProtected;
	}
	
	public String getScreenName() {
		return screenName;
	}
	
	public boolean isShowAllInlineMedia() {
		return showAllInlineMedia;
	}
	
	public boolean isUseCookiePersonalization() {
		return useCookiePersonalization;
	}
	
	public TimeZone getTimeZone() {
		return timeZone;
	}
	
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
}
