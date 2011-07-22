/*
 * Copyright 2011 the original author or authors.
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

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonIgnoreType;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.twitter.api.CursoredList;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonIgnoreType
class CursoredLongList {

	private final CursoredList<Long> list;

	@JsonCreator
	public CursoredLongList(
			@JsonProperty("ids") List<Long> collection,
			@JsonProperty("previous_cursor") long previousCursor,
			@JsonProperty("next_cursor") long nextCursor) {
		list = new CursoredList<Long>(collection, previousCursor, nextCursor);
	}

	@JsonIgnore
	public CursoredList<Long> getList() {
		return list;
	}

}
