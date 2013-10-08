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

public class DeliveryDevice {

	public enum Device {
		sms, none;
	}

	private Device device;
	private Boolean includeEntities;

	public DeliveryDevice device(Device device) {
		this.device = device;
		return this;
	}

	public DeliveryDevice includeEntities(Boolean includeEntities) {
		this.includeEntities = includeEntities;
		return this;
	}

	public MultiValueMap<String, Object> toRequestParameters() {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.set("device", device.toString());
		if (includeEntities != null) {
			params.set("include_entities", includeEntities.toString());
		}
		return params;
	}

}
