/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class SimilarPlacesDeserializer extends JsonDeserializer<SimilarPlacesResponse> {
	@Override
	public SimilarPlacesResponse deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new TwitterModule());
		jp.setCodec(mapper);
		JsonNode node = jp.readValueAs(JsonNode.class);
		JsonNode resultNode = node.get("result");
		String token = resultNode.get("token").textValue();
		JsonNode placesNode = resultNode.get("places");
		@SuppressWarnings("unchecked")
		List<Place> places = (List<Place>) mapper.readerFor(new TypeReference<List<Place>>() {}).readValue(placesNode);
		return new SimilarPlacesResponse(places, token);
	}
}
