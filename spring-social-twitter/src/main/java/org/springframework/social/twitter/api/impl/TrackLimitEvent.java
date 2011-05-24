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
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.social.twitter.api.impl.TrackLimitEvent.StreamingLimitationEventDeserializer;

/**
 * A stream event indicating that Twitter is track-limiting the stream.
 * This is a sign that a stream's query may not be specific enough, because it matches more tweets than are allowed through the stream.
 * @author Craig Walls
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = StreamingLimitationEventDeserializer.class)
class TrackLimitEvent {

	private final int numberOfLimitedTweets;

	private TrackLimitEvent(int numberOfLimitedTweets) {
		this.numberOfLimitedTweets = numberOfLimitedTweets;
	}
	
	/**
	 * The number of tweets since the start of the streaming connection that matched the query, but were rate limited.
	 */
	public int getNumberOfLimitedTweets() {
		return numberOfLimitedTweets;
	}
	
	static final class StreamingLimitationEventDeserializer extends JsonDeserializer<TrackLimitEvent> {
		@Override
		public TrackLimitEvent deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonNode trackNode = jp.readValueAsTree().get("limit").get("track");
			return new TrackLimitEvent(trackNode.getValueAsInt());
		}
	}
	
}
