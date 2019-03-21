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

import java.util.List;

import org.springframework.social.ApiException;
import org.springframework.social.MissingAuthorizationException;

/**
 * Interface defining the Twitter operations for working with locations.
 * @author Craig Walls
 */
public interface GeoOperations {

	/**
	 * Retrieves information about a plce
	 * @param id the place ID
	 * @return a {@link Place}
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	Place getPlace(String id);

	/**
	 * Retrieves up to 20 places matching the given location.
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @return a list of {@link Place}s that the point is within
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Place> reverseGeoCode(double latitude, double longitude);

	/**
	 * Retrieves up to 20 places matching the given location and criteria
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param granularity the minimal granularity of the places to return. If null, the default granularity (neighborhood) is assumed.
	 * @param accuracy a radius of accuracy around the given point. If given a number, the value is assumed to be in meters. The number may be qualified with "ft" to indicate feet. If null, the default accuracy (0m) is assumed.
	 * @return a list of {@link Place}s that the point is within
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Place> reverseGeoCode(double latitude, double longitude, PlaceType granularity, String accuracy);

	/**
	 * Searches for up to 20 places matching the given location.
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @return a list of {@link Place}s that the point is within
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
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
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	List<Place> search(double latitude, double longitude, PlaceType granularity, String accuracy, String query);

	/**
	 * Finds places similar to a place described in the parameters.
	 * Returns a list of places along with a token that is required for creating a new place.
	 * This method must be called before calling createPlace().
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param name the name that the place is known as
	 * @return a {@link SimilarPlaces} collection, including a token that can be used to create a new place.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	SimilarPlaces findSimilarPlaces(double latitude, double longitude, String name);

	/**
	 * Finds places similar to a place described in the parameters.
	 * Returns a list of places along with a token that is required for creating a new place.
	 * This method must be called before calling createPlace().
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param name the name that the place is known as
	 * @param streetAddress the place's street address. May be null.
	 * @param containedWithin the ID of the place that the place is contained within
	 * @return a {@link SimilarPlaces} collection, including a token that can be used to create a new place.
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	SimilarPlaces findSimilarPlaces(double latitude, double longitude, String name, String streetAddress, String containedWithin);

	/**
	 * Creates a new place.
	 * @param placePrototype the place prototype returned in a {@link SimilarPlaces} from a call to findSimilarPlaces()
	 * @return a {@link Place} object with the newly created place data
	 * @throws ApiException if there is an error while communicating with Twitter.
	 * @throws MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
	 */
	Place createPlace(PlacePrototype placePrototype);
}
