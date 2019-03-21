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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

/**
 * Mixin class for adding Jackson annotations to TwitterProfile.
 * @author Craig Walls
 */
@JsonIgnoreProperties(ignoreUnknown=true)
abstract class TwitterProfileMixin extends TwitterObjectMixin {
	@JsonCreator
	TwitterProfileMixin(
			@JsonProperty("id") long id, 
			@JsonProperty("screen_name") String screenName, 
			@JsonProperty("name") String name, 
			@JsonProperty("url") String url, 
			@JsonProperty("profile_image_url") String profileImageUrl, 
			@JsonProperty("description") String description, 
			@JsonProperty("location") String location, 
			@JsonProperty("created_at") @JsonDeserialize(using=TimelineDateDeserializer.class) Date createdDate) {}
	
	@JsonProperty("notifications")
	private boolean notificationsEnabled;

	@JsonProperty("lang")
	private String language;

	@JsonProperty("statuses_count")
	private int statusesCount;

	@JsonProperty("listed_count")
	private int listedCount;

	@JsonProperty("friends_count")
	private int friendsCount;

	@JsonProperty("followers_count")
	private int followersCount;

	@JsonProperty("favourites_count")
	private int favoritesCount;

	@JsonProperty("following")
	private boolean following;
	
	@JsonProperty("follow_request_sent")
	private boolean followRequestSent;

	@JsonProperty("protected")
	private boolean isProtected;

	@JsonProperty("verified")
	private boolean verified;
	
	@JsonProperty("geo_enabled")
	private boolean geoEnabled;
	
	@JsonProperty("contributors_enabled")
	private boolean contributorsEnabled;

	@JsonProperty("is_translator")
	private boolean translator;

	@JsonProperty("time_zone")
	private String timeZone;
	
	@JsonProperty("utc_offset")
	private int utcOffset;

	@JsonProperty("profile_use_background_image")
	private boolean useBackgroundImage;
	
	@JsonProperty("profile_sidebar_border_color")
	private String sidebarBorderColor;

	@JsonProperty("profile_sidebar_fill_color")
	private String sidebarFillColor;

	@JsonProperty("profile_background_color")
	private String backgroundColor;

	@JsonProperty("profile_background_image_url")
	private String backgroundImageUrl;

	@JsonProperty("profile_background_tile")
	private boolean backgroundImageTiled;
	
	@JsonProperty("profile_text_color")
	private String textColor;

	@JsonProperty("profile_link_color")
	private String linkColor;

	@JsonProperty("show_all_inline_media")
	private boolean showAllInlineMedia;

    @JsonProperty("profile_banner_url")
    private String profileBannerUrl;
}
