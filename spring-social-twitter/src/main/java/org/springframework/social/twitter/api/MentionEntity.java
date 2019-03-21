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
package org.springframework.social.twitter.api;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>A user mention entity.</p>
 * @author bowen
 */
public class MentionEntity extends TwitterObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private long id;

	@JsonProperty("screen_name")
	private String screenName;

	@JsonProperty("name")
	private String name;

	@JsonProperty("indices")
	private int[] indices;

	public MentionEntity(long id, String screenName, String name, int[] indices) {
		this.id = id;
		this.screenName = screenName;
		this.name = name;
		this.indices = indices;
	}


	public long getId() {
		return this.id;
	}


	public String getName() {
		return this.name;
	}


	public String getScreenName() {
		return this.screenName;
	}


	public int[] getIndices() {
		if (this.indices == null || this.indices.length <= 0) {
			return new int[0];
		}
		return this.indices;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MentionEntity that = (MentionEntity) o;

		if (id != that.id) {
			return false;
		}
		if (name != null ? !name.equals(that.name) : that.name != null) {
			return false;
		}
		if (!Arrays.equals(indices, that.indices)) {
			return false;
		}
		if (screenName != null ? !screenName.equals(that.screenName) : that.screenName != null) {
			return false;
		}

		return true;
	}


	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (screenName != null ? screenName.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (indices != null ? Arrays.hashCode(indices) : 0);
		return result;
	}
}
