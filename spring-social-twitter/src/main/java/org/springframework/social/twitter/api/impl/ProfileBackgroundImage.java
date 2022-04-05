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

public class ProfileBackgroundImage {

	private String image;
	private Boolean tile;
	private Boolean includeEntities;
	private Boolean skipStatus;
	private Boolean use;

	public ProfileBackgroundImage image(String image) {
		this.image = image;
		return this;
	}

	public ProfileBackgroundImage tile(Boolean tile) {
		this.tile = tile;
		return this;
	}

	public ProfileBackgroundImage includeEntities(Boolean includeEntities) {
		this.includeEntities = includeEntities;
		return this;
	}

	public ProfileBackgroundImage skipStatus(Boolean skipStatus) {
		this.skipStatus = skipStatus;
		return this;
	}

	public ProfileBackgroundImage use(Boolean use) {
		this.use = use;
		return this;
	}

	public MultiValueMap<String, Object> toRequestParameters() {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		if (tile != null) {
			params.set("tile", tile.toString());
		}
		if (includeEntities != null) {
			params.set("include_entities", includeEntities.toString());
		}
		if (skipStatus != null) {
			params.set("skip_status", skipStatus.toString());
		}
		if (use != null) {
			params.set("use", use.toString());
			if (use && image != null) {
				params.set("image", image);
			}
		}
		return params;
	}

}
