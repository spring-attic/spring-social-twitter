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
package org.springframework.social.twitter.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;

/**
 * Twitter ApiAdapter implementation.
 * @author Keith Donald
 */
public class TwitterAdapter implements ApiAdapter<Twitter> {

	public boolean test(Twitter twitter) {
		try {
			twitter.userOperations().getUserProfile();
			return true;
		} catch (ApiException e) {
			return false;
		}
	}

	public void setConnectionValues(Twitter twitter, ConnectionValues values) {
		TwitterProfile profile = twitter.userOperations().getUserProfile();
		values.setProviderUserId(Long.toString(profile.getId()));
		values.setDisplayName("@" + profile.getScreenName());
		values.setProfileUrl(profile.getProfileUrl());
		values.setImageUrl(profile.getProfileImageUrl());
	}

	public UserProfile fetchUserProfile(Twitter twitter) {
		TwitterProfile profile = twitter.userOperations().getUserProfile();
		return new UserProfileBuilder().setName(profile.getName()).setUsername(profile.getScreenName()).build();
	}
	
	public void updateStatus(Twitter twitter, String message) {
		twitter.timelineOperations().updateStatus(message);	
	}
	
}
