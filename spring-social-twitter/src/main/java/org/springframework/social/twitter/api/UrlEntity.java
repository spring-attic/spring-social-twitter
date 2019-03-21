/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
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
 * <p>A representation of a URL found within a tweet entity.</p>
 * @author bowen
 */
public class UrlEntity extends TwitterObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String display;

	private String expanded;

	private String url;

	private int[] indices;

	public UrlEntity(String display, String expanded, String url, int[] indices) {
		this.display = display;
		this.expanded = expanded;
		this.url = url;
		this.indices = indices;
	}


	public String getDisplayUrl() {
		return this.display;
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

		UrlEntity urlEntity = (UrlEntity) o;
		if (display != null ? !display.equals(urlEntity.display) : urlEntity.display != null) {
			return false;
		}
		if (expanded != null ? !expanded.equals(urlEntity.expanded) : urlEntity.expanded != null) {
			return false;
		}
		if (!Arrays.equals(indices, urlEntity.indices)) {
			return false;
		}
		if (url != null ? !url.equals(urlEntity.url) : urlEntity.url != null) {
			return false;
		}
		return true;
	}


	@Override
	public int hashCode() {
		int result = display != null ? display.hashCode() : 0;
		result = 31 * result + (expanded != null ? expanded.hashCode() : 0);
		result = 31 * result + (url != null ? url.hashCode() : 0);
		result = 31 * result + (indices != null ? Arrays.hashCode(indices) : 0);
		return result;
	}
}
