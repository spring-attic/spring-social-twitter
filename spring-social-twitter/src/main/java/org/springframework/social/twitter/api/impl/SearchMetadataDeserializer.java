package org.springframework.social.twitter.api.impl;

import java.io.IOException;

import org.springframework.social.twitter.api.SearchMetadata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Deserializer to read search metadata into a SearchMetaData object
 * @author Jeremy Appel
 */
class SearchMetadataDeserializer extends JsonDeserializer<SearchMetadata>{

	@Override
	public SearchMetadata deserialize(JsonParser jp, DeserializationContext ctxt) 
			throws IOException, JsonProcessingException {
		JsonNode node = jp.readValueAs(JsonNode.class);
		long max_id = node.get("max_id").asLong();
		long since_id = node.get("since_id").asLong();
		return new SearchMetadata(max_id, since_id);
	}
}
