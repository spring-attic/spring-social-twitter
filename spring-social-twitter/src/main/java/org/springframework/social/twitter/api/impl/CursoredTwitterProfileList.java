/*
 * Copyright 2011 the original author or authors.
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

import java.util.List;

import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.TwitterProfile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holder for list of TwitterProfile objects pulled from a JSON object's "users" property.
 * @author Jeremy Appel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class CursoredTwitterProfileUsersList {
	private final CursoredList<TwitterProfile> list;

	@JsonCreator
	public CursoredTwitterProfileUsersList(
			@JsonProperty("users") List<TwitterProfile> list,
			@JsonProperty("previous_cursor") long previousCursor,
			@JsonProperty("next_cursor") long nextCursor) {
		this.list = new CursoredList<TwitterProfile>(list, previousCursor, nextCursor);
	}

	@JsonIgnore
	public CursoredList<TwitterProfile> getList() {
		return list;
	}
}
