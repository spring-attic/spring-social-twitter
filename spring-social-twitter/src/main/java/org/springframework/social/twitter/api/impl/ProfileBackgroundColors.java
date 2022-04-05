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
package org.springframework.social.twitter.api.impl;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ProfileBackgroundColors {

	private String backgroundColor;
	private String linkColor;
	private String sidebarBorderColor;
	private String sidebarFillColor;
	private String textColor;
	private Boolean includeEntities;
	private Boolean skipStatus;

	public ProfileBackgroundColors backgroundColor(String profileBackgroundColor) {
		this.backgroundColor = profileBackgroundColor;
		return this;
	}

	public ProfileBackgroundColors linkColor(String profileLinkColor) {
		this.linkColor = profileLinkColor;
		return this;
	}

	public ProfileBackgroundColors sidebarBorderColor(String profileSidebarBorderColor) {
		this.sidebarBorderColor = profileSidebarBorderColor;
		return this;
	}

	public ProfileBackgroundColors sidebarFillColor(String profileSidebarFillColor) {
		this.sidebarFillColor = profileSidebarFillColor;
		return this;
	}

	public ProfileBackgroundColors textColor(String textColor) {
		this.textColor = textColor;
		return this;
	}

	public ProfileBackgroundColors includeEntities(Boolean includeEntities) {
		this.includeEntities = includeEntities;
		return this;
	}

	public ProfileBackgroundColors skipStatus(Boolean skipStatus) {
		this.skipStatus = skipStatus;
		return this;
	}

	public MultiValueMap<String, Object> toRequestParameters() {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		if (backgroundColor != null) {
			params.set("profile_background_color", backgroundColor.toString());
		}
		if (linkColor != null) {
			params.set("profile_link_color", linkColor.toString());
		}
		if (sidebarBorderColor != null) {
			params.set("profile_sidebar_border_color", sidebarBorderColor.toString());
		}
		if (sidebarFillColor != null) {
			params.set("profile_sidebar_fill_color", sidebarFillColor.toString());
		}
		if (textColor != null) {
			params.set("profile_text_color", textColor.toString());
		}
		if (includeEntities != null) {
			params.set("include_entities", includeEntities.toString());
		}
		if (skipStatus != null) {
			params.set("skip_status", skipStatus.toString());
		}
		return params;
	}

}
