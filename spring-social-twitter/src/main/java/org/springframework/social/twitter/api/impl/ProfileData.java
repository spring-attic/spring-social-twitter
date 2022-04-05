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

public class ProfileData {

	private String name;
	private String url;
	private String location;
	private String description;
	private Boolean includeEntities;
	private Boolean skipStatus;

	public ProfileData name(String name) {
		this.name = name;
		return this;
	}

	public ProfileData url(String url) {
		this.url = url;
		return this;
	}

	public ProfileData location(String location) {
		this.location = location;
		return this;
	}

	public ProfileData description(String description) {
		this.description = description;
		return this;
	}

	public ProfileData includeEntities(Boolean includeEntities) {
		this.includeEntities = includeEntities;
		return this;
	}

	public ProfileData skipStatus(Boolean skipStatus) {
		this.skipStatus = skipStatus;
		return this;
	}

	public MultiValueMap<String, Object> toRequestParameters() {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		if (name != null) {
			params.set("name", name.toString());
		}
		if (url != null) {
			params.set("url", url.toString());
		}
		if (location != null) {
			params.set("location", location.toString());
		}
		if (description != null) {
			params.set("description", description.toString());
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
