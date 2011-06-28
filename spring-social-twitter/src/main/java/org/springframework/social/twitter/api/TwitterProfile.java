/*
 * Copyright 2010 the original author or authors.
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

import java.io.Serializable;
import java.util.Date;

/**
 * Model class representing a Twitter user's profile information.
 * 
 * @author Craig Walls
 */
public class TwitterProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	private final long id;
	private final String screenName;
	private final String name;
	private final String url;
	private final String profileImageUrl;
	private final String description;
	private final String location;
	private final Date createdDate;
	private String language;
	private int statusesCount;
	private int friendsCount;
	private int followersCount;
	private int favoritesCount;
	private int listedCount;
	private boolean following;
	private boolean followRequestSent;
	private boolean isProtected;
	private boolean notificationsEnabled;
	private boolean verified;
	private boolean geoEnabled;
	private boolean contributorsEnabled;
	private boolean translator;
	private String timeZone;
	private int utcOffset;
	private String sidebarBorderColor;
	private String sidebarFillColor;
	private String backgroundColor;
	private boolean useBackgroundImage;
	private String backgroundImageUrl;
	private boolean backgroundImageTiled;
	private String textColor;
	private String linkColor;
	private boolean showAllInlineMedia;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TwitterProfile(long id, String screenName, String name, String url, String profileImageUrl, String description, String location, Date createdDate) {
		this.id = id;
		this.screenName = screenName;
		this.name = name;
		this.url = url;
		this.profileImageUrl = profileImageUrl;
		this.description = description;
		this.location = location;
		this.createdDate = createdDate;		
	}
	
	/**
	 * The user's Twitter ID
	 * 
	 * @return The user's Twitter ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * The user's Twitter screen name
	 * 
	 * @return The user's Twitter screen name
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * The user's full name
	 * 
	 * @return The user's full name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The user's URL
	 * 
	 * @return The user's URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * The user's description
	 * 
	 * @return The user's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * The user's location
	 * 
	 * @return The user's location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * <p>
	 * The URL of the user's profile image in "normal" size (48x48).
	 * </p>
	 * 
	 * @return The URL of the user's normal-sized profile image.
	 */
	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	/**
	 * <p>
	 * The URL of the user's profile.
	 * </p>
	 * 
	 * @return The URL of the user's profile.
	 */
	public String getProfileUrl() {
		return "http://twitter.com/" + screenName;
	}

	/**
	 * The date that the Twitter profile was created.
	 * 
	 * @return The date that the Twitter profile was created.
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * Whether or not the user has mobile notifications enabled.
	 */
	public boolean isNotificationsEnabled() {
		return notificationsEnabled;
	}
	
	/**
	 * Whether or not the user is verified with Twitter.
	 * See http://support.twitter.com/groups/31-twitter-basics/topics/111-features/articles/119135-about-verified-accounts.
	 */
	public boolean isVerified() {
		return verified;
	}
	
	/**
	 * Whether or not the user has enabled their account with geo location.
	 */
	public boolean isGeoEnabled() {
		return geoEnabled;
	}
	
	/**
	 * The user's preferred language.
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * The number of tweets this user has posted.
	 */
	public int getStatusesCount() {
		return statusesCount;
	}
	
	/**
	 * The number of lists the user is listed on.
	 */
	public int getListedCount() {
		return listedCount;
	}
	
	/**
	 * The number of friends the user has (the number of users this user follows).
	 */
	public int getFriendsCount() {
		return friendsCount;
	}

	/**
	 * The number of followers the user has.
	 */
	public int getFollowersCount() {
		return followersCount;
	}
	
	/**
	 * Whether or not the authenticated user is following this user.
	 */
	public boolean isFollowing() {
		return following;
	}
	
	/**
	 * Whether or not a request has been sent by the authenticating user to follow this user.
	 */
	public boolean isFollowRequestSent() {
		return followRequestSent;
	}
	
	/**
	 * The number of tweets that the user has marked as favorites.
	 */
	public int getFavoritesCount() {
		return favoritesCount;
	}

	/**
	 * Whether or not the user's tweets are protected.
	 */
	public boolean isProtected() {
		return isProtected;
	}
	
	/**
	 * The user's time zone.
	 */
	public String getTimeZone() {
		return timeZone;
	}
	
	/**
	 * The user's UTC offset in seconds.
	 */
	public int getUtcOffset() {
		return utcOffset;
	}
	
	/**
	 * Whether or not this profile is enabled for contributors.
	 */
	public boolean isContributorsEnabled() {
		return contributorsEnabled;
	}
	
	/**
	 * Whether or not this user is a translator.
	 */
	public boolean isTranslator() {
		return translator;
	}
	
	/**
	 * The color of the sidebar border on the user's Twitter profile page.
	 */
	public String getSidebarBorderColor() {
		return sidebarBorderColor;
	}

	/**
	 * The color of the sidebar fill on the user's Twitter profile page.
	 */
	public String getSidebarFillColor() {
		return sidebarFillColor;
	}

	/**
	 * The color of the background of the user's Twitter profile page.
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Whether or not the user's Twitter profile page uses a background image.
	 */
	public boolean useBackgroundImage() {
		return useBackgroundImage;
	}

	/**
	 * The URL to a background image shown on the user's Twitter profile page.
	 */
	public String getBackgroundImageUrl() {
		return backgroundImageUrl;
	}

	/**
	 * Whether or not the background image is tiled.
	 */
	public boolean isBackgroundImageTiled() {
		return backgroundImageTiled;
	}

	/**
	 * The text color on the user's Twitter profile page.
	 */
	public String getTextColor() {
		return textColor;
	}

	/**
	 * The link color on the user's Twitter profile page.
	 */
	public String getLinkColor() {
		return linkColor;
	}
	
	/**
	 * Whether or not the user has selected to see all inline media from everyone.
	 * If false, they will only see inline media from the users they follow.
	 */
	public boolean showAllInlineMedia() {
		return showAllInlineMedia;
	}
}
