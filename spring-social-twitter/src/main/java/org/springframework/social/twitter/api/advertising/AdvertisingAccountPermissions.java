/*
 * Copyright 2014 the original author or authors.
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
package org.springframework.social.twitter.api.advertising;

import java.util.List;

import org.springframework.social.twitter.api.TwitterObject;

/**
 * Represents an Advertising Account's Permissions
 * 
 * Source: https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/authenticated_user_access
 * 		Retrieve the permissions of the currently authenticated user (access_token) as they relate to this ads account.
 * 
 * @author Chris Latko
 */
public class AdvertisingAccountPermissions extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private final Long userId;
	private final List<AdvertisingPermission> permissions;
	
	public AdvertisingAccountPermissions(Long userId, List<AdvertisingPermission> permissions) {
		this.userId = userId;
		this.permissions = permissions;
	}

	public Long getUserId() {
		return userId;
	}

	public List<AdvertisingPermission> getPermissions() {
		return permissions;
	}
	
}
