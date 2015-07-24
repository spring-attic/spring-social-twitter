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

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.social.twitter.api.StreamEvent;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.StreamEventMixin.StreamEventDeserializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using=StreamEventDeserializer.class)
abstract class StreamEventMixin extends TwitterObjectMixin {

    private static TwitterProfile toProfile(final JsonNode node) throws IOException {
        if (null == node || node.isNull() || node.isMissingNode()) {
            return null;
        }
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new TwitterModule());
        return mapper.reader(TwitterProfile.class).readValue(node);
    }

    static final class StreamEventDeserializer extends JsonDeserializer<StreamEvent> {
		@Override
		public StreamEvent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			final JsonNode node = jp.readValueAs(JsonNode.class);
			if (null == node || node.isMissingNode() || node.isNull()) {
				return null;
			}
			
			TwitterProfile source = toProfile(node.get("source"));
			TwitterProfile target = toProfile(node.get("target"));
			
			return new StreamEvent(
					node.get("event").asText(), 
					source, 
					target);
		}
	}
	
}
