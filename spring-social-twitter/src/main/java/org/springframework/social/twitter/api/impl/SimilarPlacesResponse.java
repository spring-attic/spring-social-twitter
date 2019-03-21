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
package org.springframework.social.twitter.api.impl;

import java.util.List;

import org.springframework.social.twitter.api.Place;

/**
 * Represents the results of a similar places search. Includes places that match the search criteria and a token that may be used to create a new place.
 * @author Craig Walls
 */
class SimilarPlacesResponse {
	
	private final String token;
	private final List<Place> places;
	
	public SimilarPlacesResponse(List<Place> places, String token) {
		this.places = places;
		this.token = token;
	}
	
	/**
	 * The list of places found in a similar places search.
	 * @return
	 */
	public List<Place> getPlaces() {
		return places;
	}

	/**
	 * A token that may be used to create a new place.
	 */
	public String getToken() {
		return token;
	}
}
