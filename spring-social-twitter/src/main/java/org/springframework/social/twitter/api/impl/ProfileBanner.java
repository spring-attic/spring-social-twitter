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

public class ProfileBanner {

	private String banner;
	private Integer width;
	private Integer height;
	private Integer offsetLeft;
	private Integer offsetTop;

	public ProfileBanner banner(String banner) {
		this.banner = banner;
		return this;
	}

	public ProfileBanner width(Integer width) {
		this.width = width;
		return this;
	}

	public ProfileBanner height(Integer height) {
		this.height = height;
		return this;
	}

	public ProfileBanner offsetLeft(Integer offsetLeft) {
		this.offsetLeft = offsetLeft;
		return this;
	}

	public ProfileBanner offsetTop(Integer offsetTop) {
		this.offsetTop = offsetTop;
		return this;
	}

	public MultiValueMap<String, Object> toRequestParameters() {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		// required
		params.set("banner", banner);
		if (width != null) {
			params.set("width", width.toString());
		}
		if (height != null) {
			params.set("height", height.toString());
		}
		if (offsetLeft != null) {
			params.set("offset_left", offsetLeft.toString());
		}
		if (offsetTop != null) {
			params.set("offset_top", offsetTop.toString());
		}
		return params;
	}

}
