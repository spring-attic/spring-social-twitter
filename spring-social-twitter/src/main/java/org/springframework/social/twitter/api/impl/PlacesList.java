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

import java.io.IOException;
import java.util.List;

import org.springframework.social.twitter.api.Place;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
class PlacesList {
	
	private final List<Place> list;

	@JsonCreator
	public PlacesList(@JsonProperty("result") @JsonDeserialize(using=PlacesDeserializer.class) List<Place> list) {
		this.list = list;
	}

	public List<Place> getList() {
		return list;
	}

	private static class PlacesDeserializer extends JsonDeserializer<List<Place>> {
		@SuppressWarnings("unchecked")
		@Override
		public List<Place> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new TwitterModule());
			jp.setCodec(mapper);
			JsonNode treeNode = jp.readValueAs(JsonNode.class).get("places");
			return (List<Place>) mapper.readerFor(new TypeReference<List<Place>>() {}).readValue(treeNode);
		}
	}
}
