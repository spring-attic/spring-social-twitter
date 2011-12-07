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

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.social.twitter.api.PlaceType;

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
	
	private static class StreetAddressDeserializer extends JsonDeserializer<String> {
		@Override
		public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonNode tree = jp.readValueAsTree();
			return tree.has("street_address") ? tree.get("street_address").asText() : null;
		}
	}
}
