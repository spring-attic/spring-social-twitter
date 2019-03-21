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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * Represents the results of a similar places search. 
 * Includes places that match the search criteria and a {@link PlacePrototype} that can be used to create a new place.
 * @author Craig Walls
 */
@SuppressWarnings("serial")
public class SimilarPlaces extends ArrayList<Place> {
	
	private final PlacePrototype placePrototype;

	private Map<String, Object> extraData;

	public SimilarPlaces(List<Place> places, PlacePrototype placePrototype) {
		super(places);
		this.placePrototype = placePrototype;
		this.extraData = new HashMap<String, Object>();
	}

	/**
	 * A prototype place that matches the criteria for the call to {@link GeoOperations#findSimilarPlaces(double, double, String)}, 
	 * including a create token that can be used to create the place.
	 * @return the PlacePrototype
	 */
	public PlacePrototype getPlacePrototype() {
		return placePrototype;
	}
	
	/**
	 * @return Any fields in response from Twitter that are otherwise not mapped to any properties.
	 */
	public Map<String, Object> getExtraData() {
		return extraData;
	}
	
	/**
	 * {@link JsonAnySetter} hook. Called when an otherwise unmapped property is being processed during JSON deserialization.
	 * @param key The property's key.
	 * @param value The property's value.
	 */
	protected void add(String key, Object value) {
		extraData.put(key, value);
	}
	
}
