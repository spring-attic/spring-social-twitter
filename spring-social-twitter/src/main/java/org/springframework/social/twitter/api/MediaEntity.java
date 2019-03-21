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

/**
 * <p>A representation of embedded media entity.</p>
 * @author bowen
 */
public class MediaEntity extends TwitterObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String mediaHttp;

	private String mediaHttps;

	private String url;

	private String display;

	private String expanded;

	private String type;

	private int[] indices;

	public MediaEntity(long id, String mediaHttp, String mediaHttps, String url, String display, String expanded, String type, int[] indices) {
		this.id = id;
		this.mediaHttp = mediaHttp;
		this.mediaHttps = mediaHttps;
		this.url = url;
		this.display = display;
		this.expanded = expanded;
		this.type = type;
		this.indices = indices;
	}


	public long getId() {
		return this.id;
	}


	public String getMediaUrl() {
		return this.mediaHttp;
	}


	public String getMediaSecureUrl() {
		return this.mediaHttps;
	}


	public String getType() {
		return this.type;
	}


	public String getDisplayUrl() {
		return display;
	}


	public String getExpandedUrl() {
		return this.expanded;
	}


	public String getUrl() {
		return this.url;
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

		MediaEntity that = (MediaEntity) o;

		if (id != that.id) {
			return false;
		}
		if (display != null ? !display.equals(that.display) : that.display != null) {
			return false;
		}
		if (expanded != null ? !expanded.equals(that.expanded) : that.expanded != null) {
			return false;
		}
		if (!Arrays.equals(indices, that.indices)) {
			return false;
		}
		if (mediaHttp != null ? !mediaHttp.equals(that.mediaHttp) : that.mediaHttp != null) {
			return false;
		}
		if (mediaHttps != null ? !mediaHttps.equals(that.mediaHttps) : that.mediaHttps != null) {
			return false;
		}
		if (type != null ? !type.equals(that.type) : that.type != null) {
			return false;
		}
		if (url != null ? !url.equals(that.url) : that.url != null) {
			return false;
		}

		return true;
	}


	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (mediaHttp != null ? mediaHttp.hashCode() : 0);
		result = 31 * result + (mediaHttps != null ? mediaHttps.hashCode() : 0);
		result = 31 * result + (url != null ? url.hashCode() : 0);
		result = 31 * result + (display != null ? display.hashCode() : 0);
		result = 31 * result + (expanded != null ? expanded.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (indices != null ? Arrays.hashCode(indices) : 0);
		return result;
	}
}
