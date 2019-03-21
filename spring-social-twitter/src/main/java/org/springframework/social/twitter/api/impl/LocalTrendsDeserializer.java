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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.social.twitter.api.Trend;
import org.springframework.social.twitter.api.Trends;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Deserializer to read local trends data into a LocalTrendsHolder object.
 * @author Craig Walls
 */
class LocalTrendsDeserializer extends JsonDeserializer<LocalTrendsHolder> {

	@Override
	public LocalTrendsHolder deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.readValueAs(JsonNode.class);
		Iterator<JsonNode> dayIt = node.iterator();
		if(dayIt.hasNext()) {
			JsonNode day = dayIt.next();
			Date createdAt = toDate(day.get("created_at").asText());
			JsonNode trendNodes = day.get("trends");
			List<Trend> trends = new ArrayList<Trend>();
			for(Iterator<JsonNode> trendsIt = trendNodes.iterator(); trendsIt.hasNext(); ) {
				JsonNode trendNode = trendsIt.next();
				trends.add(new Trend(trendNode.get("name").asText(), trendNode.get("query").asText()));
			}
			jp.skipChildren();
			return new LocalTrendsHolder(new Trends(createdAt, trends));
		}
		
		throw new JsonMappingException(jp, "Processing " + LocalTrendsHolder.class.getName());
	}
	
	private static final String LOCAL_TREND_DATE_FORMAT = "yyyy-mm-dd'T'HH:mm:ss'Z'";

	private static Date toDate(String dateString) {
		try {
			return new SimpleDateFormat(LOCAL_TREND_DATE_FORMAT).parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}
}
