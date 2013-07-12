package org.springframework.social.twitter.api.impl;

import org.springframework.social.twitter.api.AccountSettings.TimeZone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
class AccountSettingsMixin {

	@JsonProperty("always_use_https")
	private boolean alwaysUseHttps;
	
	@JsonProperty("discoverable_by_email")
	private boolean discoverableByEmail;
	
	@JsonProperty("geo_enabled")
	private boolean geoEnabled;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("protected")
	private boolean isProtected;
	
	@JsonProperty("screen_name")
	private String screenName;
	
	@JsonProperty("show_all_inline_media")
	private boolean showAllInlineMedia;
	
	@JsonProperty("use_cookie_personalization")
	private boolean useCookiePersonalization;
	
	@JsonProperty("time_zone")
	private TimeZone timeZone;

	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class TimeZoneMixin {
		@JsonProperty("name")
		private String name;

		@JsonProperty("tzinfo_name")
		private String tzInfoName;

		@JsonProperty("utc_offset")
		private int utcOffset;
	}
}
