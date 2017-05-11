/*
 * Copyright 2014 the original author or authors.
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
package org.springframework.social.twitter.api;



/**
 * Represents a suggestion category; a category of users that Twitter may suggest that a user follow.
 * @author Craig Walls
 */
public class SuggestionCategory extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private final String name;
	private final String slug;
	private final int size;

	public SuggestionCategory(String name, String slug, int size) {
		this.name = name;
		this.slug = slug;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public String getSlug() {
		return slug;
	}

	public int getSize() {
		return size;
	}

}
