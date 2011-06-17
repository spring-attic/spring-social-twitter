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
package org.springframework.social.twitter.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the results of a similar places search. 
 * Includes places that match the search criteria and a {@link PlacePrototype} that can be used to create a new place.
 * @author Craig Walls
 */
@SuppressWarnings("serial")
public class SimilarPlaces extends ArrayList<Place> {
	
	private final PlacePrototype placePrototype;
	
	public SimilarPlaces(List<Place> places, PlacePrototype placePrototype) {
		super(places);
		this.placePrototype = placePrototype;
	}

	/**
	 * A prototype place that matches the criteria for the call to {@link GeoOperations#findSimilarPlaces(double, double, String)}, 
	 * including a create token that can be used to create the place.
	 */
	public PlacePrototype getPlacePrototype() {
		return placePrototype;
	}
	
}
