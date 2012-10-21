package org.springframework.social.twitter.api.impl;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.social.twitter.api.SearchMetadata;

/**
 * Deserializer to read search metadata into a SearchMetaData object
 * @author Jeremy Appel
 */
class SearchMetadataDeserializer extends JsonDeserializer<SearchMetadata>{

	@Override
	public SearchMetadata deserialize(JsonParser jp, DeserializationContext ctxt) 
			throws IOException, JsonProcessingException {
		JsonNode tree = jp.readValueAsTree();
		int max_id = tree.get("max_id").asInt();
		long since_id = tree.get("since_id").asLong();
		return new SearchMetadata(max_id, since_id);
	}
}
