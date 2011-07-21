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
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.social.twitter.api.Place;

class SimilarPlacesDeserializer extends JsonDeserializer<SimilarPlacesResponse> {
	@Override
	public SimilarPlacesResponse deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.setDeserializationConfig(ctxt.getConfig());
	    jp.setCodec(mapper);
	    
        JsonNode tree = jp.readValueAsTree();
		JsonNode resultNode = tree.get("result");
		String token = resultNode.get("token").getTextValue();
		JsonNode placesNode = resultNode.get("places");
		@SuppressWarnings("unchecked")
		List<Place> places = (List<Place>) mapper.readValue(placesNode, new TypeReference<List<Place>>() {});
		return new SimilarPlacesResponse(places, token);
	}
}