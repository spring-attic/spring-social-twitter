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

/**
 * Represents a new place that could be created. 
 * This is the type returned from calls to {@link GeoOperations#findSimilarPlaces(double, double, String)}.
 * It is the only type that can be given to {@link GeoOperations#createPlace(PlacePrototype)} to create a new place.
 * This guarantees consistency between the query performed when finding similar places and when creating a new place so that the create token will be valid. 
 * @author Craig Walls
 */
public class PlacePrototype {
	
	private final double latitude;
	
	private final double longitude;
	
	private final String name;

	private final String containedWithin;

	private final String createToken;

	private final String streetAddress;

	public PlacePrototype(String createToken, double latitude, double longitude, String name, String streetAddress, String containedWithin) {
		this.createToken = createToken;
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.streetAddress = streetAddress;
		this.containedWithin = containedWithin;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}
	
	public String getStreetAddress() {
		return streetAddress;
	}

	public String getContainedWithin() {
		return containedWithin;
	}

	public String getCreateToken() {
		return createToken;
	}
}
