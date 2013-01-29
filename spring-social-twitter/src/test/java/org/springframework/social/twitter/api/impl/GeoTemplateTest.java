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
package org.springframework.social.twitter.api.impl;

import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.List;

import org.junit.Test;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.twitter.api.Place;
import org.springframework.social.twitter.api.PlacePrototype;
import org.springframework.social.twitter.api.PlaceType;
import org.springframework.social.twitter.api.SimilarPlaces;

public class GeoTemplateTest extends AbstractTwitterApiTest {
	
	@Test
	public void getPlace() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/id/0bba15b36bd9e8cc.json"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("geo-place"), APPLICATION_JSON));
		Place place = twitter.geoOperations().getPlace("0bba15b36bd9e8cc");
		assertPlace(place);
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void getPlace_unauthorized() {
		unauthorizedTwitter.geoOperations().getPlace("0bba15b36bd9e8cc");
	}

	@Test
	public void reverseGeoCode_pointOnly() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/reverse_geocode.json?lat=33.050278&long=-96.745833"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("places-list"), APPLICATION_JSON));
		List<Place> places = twitter.geoOperations().reverseGeoCode(33.050278, -96.745833);
		assertPlaces(places);
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void reverseGeoCode_pointOnly_unauthorized() {
		unauthorizedTwitter.geoOperations().reverseGeoCode(33.050278, -96.745833);
	}
	
	@Test
	public void reverseGeoCode_pointAndGranularity() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/reverse_geocode.json?lat=33.050278&long=-96.745833&granularity=city"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("places-list"), APPLICATION_JSON));
		List<Place> places = twitter.geoOperations().reverseGeoCode(33.050278, -96.745833, PlaceType.CITY, null);
		assertPlaces(places);
	}

	@Test
	public void reverseGeoCode_pointAndPOIGranularity() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/reverse_geocode.json?lat=33.050278&long=-96.745833&granularity=poi"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("places-list"), APPLICATION_JSON));
		List<Place> places = twitter.geoOperations().reverseGeoCode(33.050278, -96.745833, PlaceType.POINT_OF_INTEREST, null);
		assertPlaces(places);
	}

	@Test
	public void reverseGeoCode_pointGranularityAndAccuracy() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/reverse_geocode.json?lat=33.050278&long=-96.745833&granularity=city&accuracy=5280ft"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("places-list"), APPLICATION_JSON));
		List<Place> places = twitter.geoOperations().reverseGeoCode(33.050278, -96.745833, PlaceType.CITY, "5280ft");
		assertPlaces(places);
	}

	@Test
	public void reverseGeoCode_pointAndAccuracy() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/reverse_geocode.json?lat=33.050278&long=-96.745833&accuracy=5280ft"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("places-list"), APPLICATION_JSON));
		List<Place> places = twitter.geoOperations().reverseGeoCode(33.050278, -96.745833, null, "5280ft");
		assertPlaces(places);
	}
	
	@Test
	public void search_pointOnly() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/search.json?lat=33.050278&long=-96.745833"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("places-list"), APPLICATION_JSON));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833);
		assertPlaces(places);
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void search_pointOnly_unauthorized() {
		unauthorizedTwitter.geoOperations().search(33.050278, -96.745833);
	}
	
	@Test
	public void search_pointAndGranularity() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/search.json?lat=33.050278&long=-96.745833&granularity=city"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("places-list"), APPLICATION_JSON));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833, PlaceType.CITY, null, null);
		assertPlaces(places);
	}

	@Test
	public void search_pointAndPOIGranularity() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/search.json?lat=33.050278&long=-96.745833&granularity=poi"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("places-list"), APPLICATION_JSON));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833, PlaceType.POINT_OF_INTEREST, null, null);
		assertPlaces(places);
	}

	@Test
	public void search_pointGranularityAndAccuracy() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/search.json?lat=33.050278&long=-96.745833&granularity=city&accuracy=5280ft"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("places-list"), APPLICATION_JSON));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833, PlaceType.CITY, "5280ft", null);
		assertPlaces(places);
	}

	@Test
	public void search_pointAndAccuracy() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/search.json?lat=33.050278&long=-96.745833&accuracy=5280ft"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("places-list"), APPLICATION_JSON));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833, null, "5280ft", null);
		assertPlaces(places);
	}
	
	@Test
	public void search_pointGranularityAccuracyAndQuery() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/search.json?lat=33.050278&long=-96.745833&granularity=city&accuracy=5280ft&query=Public+School"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("places-list"), APPLICATION_JSON));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833, PlaceType.CITY, "5280ft", "Public School");
		assertPlaces(places);
	}
	
	@Test
	public void findSimilarPlaces() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/similar_places.json?lat=37.7821120598956&long=-122.400612831116&name=Twitter+HQ&attribute%3Astreet_address=795+Folsom+St&contained_within=2e056b6d9c0ff3cd"))
			.andExpect(method(GET))
			.andRespond(withSuccess(jsonResource("similar-places"), APPLICATION_JSON));
		SimilarPlaces similarPlaces = twitter.geoOperations().findSimilarPlaces(37.7821120598956, -122.400612831116, "Twitter HQ", "795 Folsom St", "2e056b6d9c0ff3cd");
		assertEquals("9c8072b2a6788ee530e8c8cbb487107c", similarPlaces.getPlacePrototype().getCreateToken());
		assertEquals(37.7821120598956, similarPlaces.getPlacePrototype().getLatitude(), 0.0000001);
		assertEquals(-122.400612831116, similarPlaces.getPlacePrototype().getLongitude(), 0.0000001);
		assertEquals("Twitter HQ", similarPlaces.getPlacePrototype().getName());
		assertEquals("2e056b6d9c0ff3cd", similarPlaces.getPlacePrototype().getContainedWithin());
		assertEquals(2, similarPlaces.size());
		assertEquals("851a1ba23cb8c6ee", similarPlaces.get(0).getId());
		assertEquals("twitter", similarPlaces.get(0).getName());
		assertEquals("twitter, Twitter HQ", similarPlaces.get(0).getFullName());
		assertNull(similarPlaces.get(0).getStreetAddress());
		assertEquals("United States", similarPlaces.get(0).getCountry());
		assertEquals("US", similarPlaces.get(0).getCountryCode());
		assertEquals(PlaceType.POINT_OF_INTEREST, similarPlaces.get(0).getPlaceType());
		assertEquals("247f43d441defc03", similarPlaces.get(1).getId());
		assertEquals("Twitter HQ", similarPlaces.get(1).getName());
		assertEquals("Twitter HQ, San Francisco", similarPlaces.get(1).getFullName());
		assertEquals("795 Folsom St", similarPlaces.get(1).getStreetAddress());
		assertEquals("United States", similarPlaces.get(1).getCountry());
		assertEquals("US", similarPlaces.get(1).getCountryCode());
		assertEquals(PlaceType.POINT_OF_INTEREST, similarPlaces.get(1).getPlaceType());
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void findSimilarPlaces_unauthorized() {
		unauthorizedTwitter.geoOperations().findSimilarPlaces(37.7821120598956, -122.400612831116, "Twitter HQ", "795 Folsom St", "2e056b6d9c0ff3cd");
	}
	
	@Test
	public void createPlace() {
		mockServer.expect(requestTo("https://api.twitter.com/1.1/geo/place.json"))
			.andExpect(method(POST))
			.andExpect(content().string("lat=33.153661&long=-94.973045&name=Restaurant+Mexico&attribute%3Astreet_address=301+W+Ferguson+Rd&contained_within=2e056b6d9c0ff3cd&token=0b699bfda6514e84c7b69cf993c0c23e"))
			.andRespond(withSuccess(jsonResource("geo-place"), APPLICATION_JSON));
		PlacePrototype placePrototype = new PlacePrototype("0b699bfda6514e84c7b69cf993c0c23e", 33.153661, -94.973045, "Restaurant Mexico", "301 W Ferguson Rd", "2e056b6d9c0ff3cd");
		Place place = twitter.geoOperations().createPlace(placePrototype);
		assertPlace(place);
		mockServer.verify();
	}
	
	@Test(expected = NotAuthorizedException.class)
	public void createPlace_unauthorized() {
		PlacePrototype placePrototype = new PlacePrototype("0b699bfda6514e84c7b69cf993c0c23e", 33.153661, -94.973045, "Restaurant Mexico", "301 W Ferguson Rd", "2e056b6d9c0ff3cd");
		unauthorizedTwitter.geoOperations().createPlace(placePrototype);
	}

	private void assertPlace(Place place) {
		assertEquals("0bba15b36bd9e8cc", place.getId());
		assertEquals("Restaurant Mexico", place.getName());
		assertEquals("Restaurant Mexico, Mount Pleasant", place.getFullName());
		assertEquals("301 W Ferguson Rd", place.getStreetAddress());
		assertEquals("United States", place.getCountry());
		assertEquals("US", place.getCountryCode());
		assertEquals(PlaceType.POINT_OF_INTEREST, place.getPlaceType());
	}
	
	private void assertPlaces(List<Place> places) {
		assertEquals(3, places.size());
		assertEquals("488da0de4c92ac8e", places.get(0).getId());
		assertEquals("Plano", places.get(0).getName());
		assertEquals("Plano, TX", places.get(0).getFullName());
		assertNull(places.get(0).getStreetAddress());
		assertEquals("United States", places.get(0).getCountry());
		assertEquals("US", places.get(0).getCountryCode());
		assertEquals(PlaceType.CITY, places.get(0).getPlaceType());
		assertEquals("e0060cda70f5f341", places.get(1).getId());
		assertEquals("Texas", places.get(1).getName());
		assertNull(places.get(1).getStreetAddress());
		assertEquals("Texas, US", places.get(1).getFullName());
		assertEquals("United States", places.get(1).getCountry());
		assertEquals("US", places.get(1).getCountryCode());
		assertEquals(PlaceType.ADMIN, places.get(1).getPlaceType());
		assertEquals("96683cc9126741d1", places.get(2).getId());
		assertEquals("United States", places.get(2).getName());
		assertNull(places.get(2).getStreetAddress());
		assertEquals("United States", places.get(2).getFullName());
		assertEquals("United States", places.get(2).getCountry());
		assertEquals("US", places.get(2).getCountryCode());
		assertEquals(PlaceType.COUNTRY, places.get(2).getPlaceType());
	}
	
}
