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
import java.util.Iterator;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.social.twitter.api.RateLimitStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class RateLimitStatusDeserializer extends JsonDeserializer<RateLimitStatusHolder> {

	@Override
	public RateLimitStatusHolder deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode tree = jp.readValueAsTree();
		if (null == tree || tree.isMissingNode() || tree.isNull()) {
			return null;
		}
		JsonNode resources = tree.get("resources");
		MultiValueMap<String, RateLimitStatus> rateLimits = new LinkedMultiValueMap<String, RateLimitStatus>();
		for (Iterator<Entry<String,JsonNode>> resourceFamilyIt = resources.getFields(); resourceFamilyIt.hasNext();) {
			Entry<String,JsonNode> resourceFamilyNode = resourceFamilyIt.next();
			for (Iterator<Entry<String,JsonNode>> resourceEndpointIt = resourceFamilyNode.getValue().getFields(); resourceEndpointIt.hasNext();) {
				Entry<String,JsonNode> endpointNode = resourceEndpointIt.next();
				RateLimitStatus endpointLimit = new RateLimitStatus(endpointNode.getKey(), endpointNode.getValue().get("limit").asInt(), endpointNode.getValue().get("remaining").asInt(), endpointNode.getValue().get("reset").asInt());
				rateLimits.add(resourceFamilyNode.getKey(), endpointLimit);
			}
		}
		return new RateLimitStatusHolder(rateLimits);
	}
}
