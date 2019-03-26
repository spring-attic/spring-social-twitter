/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
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
public class TwitterProfile extends TwitterObject implements Serializable {
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
    private String profileBannerUrl;
	
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
		return "https://twitter.com/" + screenName;
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
	 * @return true if the user has mobile notifications enabled
	 */
	public boolean isNotificationsEnabled() {
		return notificationsEnabled;
	}
	
	/**
	 * Whether or not the user is verified with Twitter.
	 * See https://support.twitter.com/groups/31-twitter-basics/topics/111-features/articles/119135-about-verified-accounts.
	 * @return true if the user is verified
	 */
	public boolean isVerified() {
		return verified;
	}
	
	/**
	 * Whether or not the user has enabled their account with geo location.
	 * @return true if geo-location is enabled
	 */
	public boolean isGeoEnabled() {
		return geoEnabled;
	}
	
	/**
	 * @return the user's preferred language.
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * @return the number of tweets this user has posted.
	 */
	public int getStatusesCount() {
		return statusesCount;
	}
	
	/**
	 * @return the number of lists the user is listed on.
	 */
	public int getListedCount() {
		return listedCount;
	}
	
	/**
	 * @return the number of friends the user has (the number of users this user follows).
	 */
	public int getFriendsCount() {
		return friendsCount;
	}

	/**
	 * @return the number of followers the user has.
	 */
	public int getFollowersCount() {
		return followersCount;
	}
	
	/**
	 * Whether or not the authenticated user is following this user.
	 * @return true if the authenticated user is following this user
	 */
	public boolean isFollowing() {
		return following;
	}
	
	/**
	 * Whether or not a request has been sent by the authenticating user to follow this user.
	 * @return true if a follow request has been sent to this user
	 */
	public boolean isFollowRequestSent() {
		return followRequestSent;
	}
	
	/**
	 * @return the number of tweets that the user has marked as favorites.
	 */
	public int getFavoritesCount() {
		return favoritesCount;
	}

	/**
	 * Whether or not the user's tweets are protected.
	 * @return true if the user's tweets are protected
	 */
	public boolean isProtected() {
		return isProtected;
	}
	
	/**
	 * @return the user's time zone.
	 */
	public String getTimeZone() {
		return timeZone;
	}
	
	/**
	 * @return the user's UTC offset in seconds.
	 */
	public int getUtcOffset() {
		return utcOffset;
	}
	
	/**
	 * Whether or not this profile is enabled for contributors.
	 * @return true if contributors are enabled
	 */
	public boolean isContributorsEnabled() {
		return contributorsEnabled;
	}
	
	/**
	 * Whether or not this user is a translator.
	 * @return true if the user is a translator
	 */
	public boolean isTranslator() {
		return translator;
	}
	
	/**
	 * @return the color of the sidebar border on the user's Twitter profile page.
	 */
	public String getSidebarBorderColor() {
		return sidebarBorderColor;
	}

	/**
	 * @return the color of the sidebar fill on the user's Twitter profile page.
	 */
	public String getSidebarFillColor() {
		return sidebarFillColor;
	}

	/**
	 * @return the color of the background of the user's Twitter profile page.
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Whether or not the user's Twitter profile page uses a background image.
	 * @return true if the user's profile page uses a background image
	 */
	public boolean useBackgroundImage() {
		return useBackgroundImage;
	}

	/**
	 * @return the URL to a background image shown on the user's Twitter profile page.
	 */
	public String getBackgroundImageUrl() {
		return backgroundImageUrl;
	}

	/**
	 * Whether or not the background image is tiled.
	 * @return true if the background images is tiled
	 */
	public boolean isBackgroundImageTiled() {
		return backgroundImageTiled;
	}

	/**
	 * @return the text color on the user's Twitter profile page.
	 */
	public String getTextColor() {
		return textColor;
	}

	/**
	 * @return the link color on the user's Twitter profile page.
	 */
	public String getLinkColor() {
		return linkColor;
	}

    /**
     * The URL to the banner image shown on the user's Twitter profile page
     * @return The URL to the banner image shown on the user's Twitter profile page
     */
    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

	/**
	 * Whether or not the user has selected to see all inline media from everyone.
	 * If false, they will only see inline media from the users they follow.
	 * @return true if the user wats to see all inline media
	 */
	public boolean showAllInlineMedia() {
		return showAllInlineMedia;
	}

    @Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		TwitterProfile that = (TwitterProfile) o;

		if (backgroundImageTiled != that.backgroundImageTiled) {
			return false;
		}
		if (contributorsEnabled != that.contributorsEnabled) {
			return false;
		}
		if (favoritesCount != that.favoritesCount) {
			return false;
		}
		if (followRequestSent != that.followRequestSent) {
			return false;
		}
		if (followersCount != that.followersCount) {
			return false;
		}
		if (following != that.following) {
			return false;
		}
		if (friendsCount != that.friendsCount) {
			return false;
		}
		if (geoEnabled != that.geoEnabled) {
			return false;
		}
		if (id != that.id) {
			return false;
		}
		if (isProtected != that.isProtected) {
			return false;
		}
		if (listedCount != that.listedCount) {
			return false;
		}
		if (notificationsEnabled != that.notificationsEnabled) {
			return false;
		}
		if (showAllInlineMedia != that.showAllInlineMedia) {
			return false;
		}
		if (statusesCount != that.statusesCount) {
			return false;
		}
		if (translator != that.translator) {
			return false;
		}
		if (useBackgroundImage != that.useBackgroundImage) {
			return false;
		}
		if (utcOffset != that.utcOffset) {
			return false;
		}
		if (verified != that.verified) {
			return false;
		}
		if (backgroundColor != null ? !backgroundColor.equals(that.backgroundColor) : that.backgroundColor != null) {
			return false;
		}
		if (backgroundImageUrl != null ? !backgroundImageUrl.equals(that.backgroundImageUrl) : that.backgroundImageUrl != null) {
			return false;
		}
		if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) {
			return false;
		}
		if (description != null ? !description.equals(that.description) : that.description != null) {
			return false;
		}
		if (language != null ? !language.equals(that.language) : that.language != null) {
			return false;
		}
		if (linkColor != null ? !linkColor.equals(that.linkColor) : that.linkColor != null) {
			return false;
		}
		if (location != null ? !location.equals(that.location) : that.location != null) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (profileImageUrl != null ? !profileImageUrl.equals(that.profileImageUrl) : that.profileImageUrl != null) {
			return false;
		}
		if (screenName != null ? !screenName.equals(that.screenName) : that.screenName != null) {
			return false;
		}
		if (sidebarBorderColor != null ? !sidebarBorderColor.equals(that.sidebarBorderColor) : that.sidebarBorderColor != null) {
			return false;
		}
		if (sidebarFillColor != null ? !sidebarFillColor.equals(that.sidebarFillColor) : that.sidebarFillColor != null) {
			return false;
		}
		if (textColor != null ? !textColor.equals(that.textColor) : that.textColor != null) {
			return false;
		}
		if (timeZone != null ? !timeZone.equals(that.timeZone) : that.timeZone != null) {
			return false;
		}
		if (url != null ? !url.equals(that.url) : that.url != null) {
			return false;
		}

		return true;
	}


	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (screenName != null ? screenName.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (url != null ? url.hashCode() : 0);
		result = 31 * result + (profileImageUrl != null ? profileImageUrl.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (location != null ? location.hashCode() : 0);
		result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
		result = 31 * result + (language != null ? language.hashCode() : 0);
		result = 31 * result + statusesCount;
		result = 31 * result + friendsCount;
		result = 31 * result + followersCount;
		result = 31 * result + favoritesCount;
		result = 31 * result + listedCount;
		result = 31 * result + (following ? 1 : 0);
		result = 31 * result + (followRequestSent ? 1 : 0);
		result = 31 * result + (isProtected ? 1 : 0);
		result = 31 * result + (notificationsEnabled ? 1 : 0);
		result = 31 * result + (verified ? 1 : 0);
		result = 31 * result + (geoEnabled ? 1 : 0);
		result = 31 * result + (contributorsEnabled ? 1 : 0);
		result = 31 * result + (translator ? 1 : 0);
		result = 31 * result + (timeZone != null ? timeZone.hashCode() : 0);
		result = 31 * result + utcOffset;
		result = 31 * result + (sidebarBorderColor != null ? sidebarBorderColor.hashCode() : 0);
		result = 31 * result + (sidebarFillColor != null ? sidebarFillColor.hashCode() : 0);
		result = 31 * result + (backgroundColor != null ? backgroundColor.hashCode() : 0);
		result = 31 * result + (useBackgroundImage ? 1 : 0);
		result = 31 * result + (backgroundImageUrl != null ? backgroundImageUrl.hashCode() : 0);
		result = 31 * result + (backgroundImageTiled ? 1 : 0);
		result = 31 * result + (textColor != null ? textColor.hashCode() : 0);
		result = 31 * result + (linkColor != null ? linkColor.hashCode() : 0);
		result = 31 * result + (showAllInlineMedia ? 1 : 0);
		return result;
	}

}
