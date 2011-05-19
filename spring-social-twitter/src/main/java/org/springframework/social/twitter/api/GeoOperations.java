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

import java.util.List;

/**
 * Interface defining the Twitter operations for working with locations.
 * @author Craig Walls
 */
public interface GeoOperations {

	/**
	 * Retrieves information about a plce
	 * @param id the place ID
	 * @return a {@link Place}
	 */
	Place getPlace(String id);

	/**
	 * Retrieves up to 20 places matching the given location.
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @return a list of {@link Place}s that the point is within
	 */
	List<Place> reverseGeoCode(double latitude, double longitude);

	/**
	 * Retrieves up to 20 places matching the given location and criteria
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param granularity the minimal granularity of the places to return. If null, the default granularity (neighborhood) is assumed.
	 * @param accuracy a radius of accuracy around the given point. If given a number, the value is assumed to be in meters. The number may be qualified with "ft" to indicate feet. If null, the default accuracy (0m) is assumed.
	 * @return a list of {@link Place}s that the point is within
	 */
	List<Place> reverseGeoCode(double latitude, double longitude, PlaceType granularity, String accuracy);

	/**
	 * Searches for up to 20 places matching the given location.
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @return a list of {@link Place}s that the point is within
	 */
	List<Place> search(double latitude, double longitude);

	/**
	 * Searches for up to 20 places matching the given location and criteria
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param granularity the minimal granularity of the places to return. If null, the default granularity (neighborhood) is assumed.
	 * @param accuracy a radius of accuracy around the given point. If given a number, the value is assumed to be in meters. The number may be qualified with "ft" to indicate feet. If null, the default accuracy (0m) is assumed.
	 * @param query a free form text value to help find places by name. If null, no query will be applied to the search.
	 * @return a list of {@link Place}s that the point is within
	 */
	List<Place> search(double latitude, double longitude, PlaceType granularity, String accuracy, String query);

}
