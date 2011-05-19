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
import static org.springframework.social.test.client.RequestMatchers.*;
import static org.springframework.social.test.client.ResponseCreators.*;

import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.social.twitter.api.Place;
import org.springframework.social.twitter.api.PlaceType;

public class GeoTemplateTest extends AbstractTwitterApiTest {
	
	@Test
	public void getPlace() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/id/0bba15b36bd9e8cc.json"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("geo-place.json", getClass()), responseHeaders));
		Place place = twitter.geoOperations().getPlace("0bba15b36bd9e8cc");
		assertEquals("0bba15b36bd9e8cc", place.getId());
	}

	@Test
	public void reverseGeoCode_pointOnly() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/reverse_geocode.json?lat=33.050278&long=-96.745833"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("places-list.json", getClass()), responseHeaders));
		List<Place> places = twitter.geoOperations().reverseGeoCode(33.050278, -96.745833);
		assertPlaces(places);
	}
	
	@Test
	public void reverseGeoCode_pointAndGranularity() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/reverse_geocode.json?lat=33.050278&long=-96.745833&granularity=city"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("places-list.json", getClass()), responseHeaders));
		List<Place> places = twitter.geoOperations().reverseGeoCode(33.050278, -96.745833, PlaceType.CITY, null);
		assertPlaces(places);
	}

	@Test
	public void reverseGeoCode_pointAndPOIGranularity() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/reverse_geocode.json?lat=33.050278&long=-96.745833&granularity=poi"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("places-list.json", getClass()), responseHeaders));
		List<Place> places = twitter.geoOperations().reverseGeoCode(33.050278, -96.745833, PlaceType.POINT_OF_INTEREST, null);
		assertPlaces(places);
	}

	@Test
	public void reverseGeoCode_pointGranularityAndAccuracy() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/reverse_geocode.json?lat=33.050278&long=-96.745833&granularity=city&accuracy=5280ft"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("places-list.json", getClass()), responseHeaders));
		List<Place> places = twitter.geoOperations().reverseGeoCode(33.050278, -96.745833, PlaceType.CITY, "5280ft");
		assertPlaces(places);
	}

	@Test
	public void reverseGeoCode_pointAndAccuracy() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/reverse_geocode.json?lat=33.050278&long=-96.745833&accuracy=5280ft"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("places-list.json", getClass()), responseHeaders));
		List<Place> places = twitter.geoOperations().reverseGeoCode(33.050278, -96.745833, null, "5280ft");
		assertPlaces(places);
	}
	
	@Test
	public void search_pointOnly() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/search.json?lat=33.050278&long=-96.745833"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("places-list.json", getClass()), responseHeaders));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833);
		assertPlaces(places);
	}
	
	@Test
	public void search_pointAndGranularity() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/search.json?lat=33.050278&long=-96.745833&granularity=city"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("places-list.json", getClass()), responseHeaders));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833, PlaceType.CITY, null, null);
		assertPlaces(places);
	}

	@Test
	public void search_pointAndPOIGranularity() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/search.json?lat=33.050278&long=-96.745833&granularity=poi"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("places-list.json", getClass()), responseHeaders));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833, PlaceType.POINT_OF_INTEREST, null, null);
		assertPlaces(places);
	}

	@Test
	public void search_pointGranularityAndAccuracy() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/search.json?lat=33.050278&long=-96.745833&granularity=city&accuracy=5280ft"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("places-list.json", getClass()), responseHeaders));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833, PlaceType.CITY, "5280ft", null);
		assertPlaces(places);
	}

	@Test
	public void search_pointAndAccuracy() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/search.json?lat=33.050278&long=-96.745833&accuracy=5280ft"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("places-list.json", getClass()), responseHeaders));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833, null, "5280ft", null);
		assertPlaces(places);
	}
	
	@Test
	public void search_pointGranularityAccuracyAndQuery() {
		mockServer.expect(requestTo("https://api.twitter.com/1/geo/search.json?lat=33.050278&long=-96.745833&granularity=city&accuracy=5280ft&query=Public+School"))
			.andExpect(method(GET))
			.andRespond(withResponse(new ClassPathResource("places-list.json", getClass()), responseHeaders));
		List<Place> places = twitter.geoOperations().search(33.050278, -96.745833, PlaceType.CITY, "5280ft", "Public School");
		assertPlaces(places);
	}

	private void assertPlaces(List<Place> places) {
		assertEquals(3, places.size());
		assertEquals("488da0de4c92ac8e", places.get(0).getId());
		assertEquals("Plano", places.get(0).getName());
		assertEquals("Plano, TX", places.get(0).getFullName());
		assertEquals("United States", places.get(0).getCountry());
		assertEquals("US", places.get(0).getCountryCode());
		assertEquals(PlaceType.CITY, places.get(0).getPlaceType());
		assertEquals("e0060cda70f5f341", places.get(1).getId());
		assertEquals("Texas", places.get(1).getName());
		assertEquals("Texas, US", places.get(1).getFullName());
		assertEquals("United States", places.get(1).getCountry());
		assertEquals("US", places.get(1).getCountryCode());
		assertEquals(PlaceType.ADMIN, places.get(1).getPlaceType());
		assertEquals("96683cc9126741d1", places.get(2).getId());
		assertEquals("United States", places.get(2).getName());
		assertEquals("United States", places.get(2).getFullName());
		assertEquals("United States", places.get(2).getCountry());
		assertEquals("US", places.get(2).getCountryCode());
		assertEquals(PlaceType.COUNTRY, places.get(2).getPlaceType());
	}
	
}
