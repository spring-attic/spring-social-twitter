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
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.social.twitter.api.Place;

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
		    mapper.setDeserializationConfig(ctxt.getConfig());
		    jp.setCodec(mapper);
		    
            JsonNode dataNode = jp.readValueAsTree().get("places");
            return (List<Place>) mapper.readValue(dataNode, new TypeReference<List<Place>>() {});
		}
	}
}
