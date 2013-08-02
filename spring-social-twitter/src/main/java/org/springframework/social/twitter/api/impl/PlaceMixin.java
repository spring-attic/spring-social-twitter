/*
 * Copyright 2013 the original author or authors.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.social.twitter.api.Place;
import org.springframework.social.twitter.api.Place.GeoPoint;
import org.springframework.social.twitter.api.PlaceType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
class PlaceMixin {
	
	@JsonCreator
	public PlaceMixin(
			@JsonProperty("id") String id, 
			@JsonProperty("name") String name, 
			@JsonProperty("full_name") String fullName,
			@JsonProperty("attributes") @JsonDeserialize(using = StreetAddressDeserializer.class) String streetAddress,
			@JsonProperty("country") String country, 
			@JsonProperty("country_code") String countryCode, 
			@JsonProperty("place_type") @JsonDeserialize(using = PlaceTypeDeserializer.class) PlaceType placeType) {}
	
	@JsonProperty("contained_within")
	List<Place> containedWithin;
	
	@JsonProperty("bounding_box")
	@JsonDeserialize(using=BoundingBoxDeserializer.class)
	List<GeoPoint> boundingBox;

	private static class StreetAddressDeserializer extends JsonDeserializer<String> {
		@Override
		public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			Map<String, String> attributesMap = jp.readValueAs(new TypeReference<Map<String, String>>(){});
			return attributesMap.containsKey("street_address") ? attributesMap.get("street_address") : null;
		}
	}
	
	private static class BoundingBoxDeserializer extends JsonDeserializer<List<GeoPoint>> {
		@Override
		public List<GeoPoint> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			List<GeoPoint> points = new ArrayList<Place.GeoPoint>();
			while (jp.nextToken() != JsonToken.END_OBJECT) {
				String fieldname = jp.getCurrentName();
				if ("type".equals(fieldname)) {
					jp.nextToken();
				}
				if ("coordinates".equals(fieldname)) {
					jp.nextToken();
					jp.nextToken();
					while (jp.nextToken() != JsonToken.END_ARRAY) {
						jp.nextToken();
						double longitude = jp.getDoubleValue();
						jp.nextToken();
						double latitude = jp.getDoubleValue();
						points.add(new Place.GeoPoint(latitude, longitude));
						jp.nextToken();
					}
					jp.nextToken();
				}
			}
			return points;
		}
	}
}
