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
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.social.twitter.api.RateLimitStatus;
import org.springframework.social.twitter.api.ResourceFamily;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Deserializer to read rate limit status information into MultiValueMap
 * @author Jeremy Appel
 */
public class RateLimitStatusDeserializer extends JsonDeserializer<RateLimitStatusHolder> {

	@Override
	public RateLimitStatusHolder deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = jp.readValueAs(JsonNode.class);
		if (null == node || node.isMissingNode() || node.isNull()) {
			return null;
		}
		JsonNode resources = node.get("resources");
		Map<ResourceFamily, List<RateLimitStatus>> rateLimits = new EnumMap<ResourceFamily, List<RateLimitStatus>>(ResourceFamily.class);
		for (Iterator<Entry<String,JsonNode>> resourceFamilyIt = resources.fields(); resourceFamilyIt.hasNext();) {
			Entry<String,JsonNode> resourceFamilyNode = resourceFamilyIt.next();
			List<RateLimitStatus> rateLimitsList = new LinkedList<RateLimitStatus>();
			for (Iterator<Entry<String,JsonNode>> resourceEndpointIt = resourceFamilyNode.getValue().fields(); resourceEndpointIt.hasNext();) {
				Entry<String,JsonNode> endpointNode = resourceEndpointIt.next();
				RateLimitStatus endpointLimit = new RateLimitStatus(endpointNode.getKey(), endpointNode.getValue().get("limit").asInt(), endpointNode.getValue().get("remaining").asInt(), endpointNode.getValue().get("reset").asInt());
				rateLimitsList.add(endpointLimit);
			}
			rateLimits.put(ResourceFamily.getResourceFamily(resourceFamilyNode.getKey()), rateLimitsList);
		}
		return new RateLimitStatusHolder(rateLimits);
	}
}
