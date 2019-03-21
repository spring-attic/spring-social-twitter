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

import org.springframework.social.twitter.api.GeoOperations;
import org.springframework.social.twitter.api.Place;
import org.springframework.social.twitter.api.PlacePrototype;
import org.springframework.social.twitter.api.PlaceType;
import org.springframework.social.twitter.api.SimilarPlaces;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class GeoTemplate extends AbstractTwitterOperations implements GeoOperations {

	private final RestTemplate restTemplate;

	public GeoTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp) {
		super(isAuthorizedForUser, isAuthorizedForApp);
		this.restTemplate = restTemplate;
	}

	public Place getPlace(String placeId) {
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("geo/id/" + placeId + ".json"), Place.class);
	}
	
	public List<Place> reverseGeoCode(double latitude, double longitude) {
		return reverseGeoCode(latitude, longitude, null, null);
	}
	
	public List<Place> reverseGeoCode(double latitude, double longitude, PlaceType granularity, String accuracy) {
		requireUserAuthorization();
		MultiValueMap<String, String> parameters = buildGeoParameters(latitude, longitude, granularity, accuracy, null);
		return restTemplate.getForObject(buildUri("geo/reverse_geocode.json", parameters), PlacesList.class).getList();
	}
	
	public List<Place> search(double latitude, double longitude) {
		return search(latitude, longitude, null, null, null);
	}
	
	public List<Place> search(double latitude, double longitude, PlaceType granularity, String accuracy, String query) {
		requireUserAuthorization();
		MultiValueMap<String, String> parameters = buildGeoParameters(latitude, longitude, granularity, accuracy, query);
		return restTemplate.getForObject(buildUri("geo/search.json", parameters), PlacesList.class).getList();
	}
	
	public SimilarPlaces findSimilarPlaces(double latitude, double longitude, String name) {
		return findSimilarPlaces(latitude, longitude, name, null, null);
	}
	
	public SimilarPlaces findSimilarPlaces(double latitude, double longitude, String name, String streetAddress, String containedWithin) {
		requireUserAuthorization();
		MultiValueMap<String, String> parameters = buildPlaceParameters(latitude, longitude, name, streetAddress, containedWithin);
		SimilarPlacesResponse response = restTemplate.getForObject(buildUri("geo/similar_places.json", parameters), SimilarPlacesResponse.class);
		PlacePrototype placePrototype = new PlacePrototype(response.getToken(), latitude, longitude, name, streetAddress, containedWithin);	
		return new SimilarPlaces(response.getPlaces(), placePrototype);
	}
	
	public Place createPlace(PlacePrototype placePrototype) {
		requireUserAuthorization();
		MultiValueMap<String, String> request = buildPlaceParameters(placePrototype.getLatitude(), placePrototype.getLongitude(), placePrototype.getName(), placePrototype.getStreetAddress(), placePrototype.getContainedWithin());
		request.set("token", placePrototype.getCreateToken());
		return restTemplate.postForObject("https://api.twitter.com/1.1/geo/place.json", request, Place.class);		
	}
	
	// private helpers
	
	private MultiValueMap<String, String> buildGeoParameters(double latitude, double longitude, PlaceType granularity, String accuracy, String query) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("lat", String.valueOf(latitude));
		parameters.set("long", String.valueOf(longitude));
		if(granularity != null) {
			parameters.set("granularity", granularity.equals(PlaceType.POINT_OF_INTEREST) ? "poi" : granularity.toString().toLowerCase());
		}		
		if(accuracy != null) {
			parameters.set("accuracy", accuracy);
		}
		if(query != null ) {
			parameters.set("query", query);
		}
		return parameters;
	}
	
	private MultiValueMap<String, String> buildPlaceParameters(double latitude, double longitude, String name, String streetAddress, String containedWithin) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("lat", String.valueOf(latitude));
		parameters.set("long", String.valueOf(longitude));
		parameters.set("name", name);
		if(streetAddress != null) {
			parameters.set("attribute:street_address", streetAddress);
		}
		if(containedWithin != null) {
			parameters.set("contained_within", containedWithin);
		}
		return parameters;
	}

}
