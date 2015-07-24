package org.springframework.social.twitter.api.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Created by Oyku Gencay (oyku at gencay.net) on 12.08.2014.
 *
 */
public class CoordinatesDeserializer extends JsonDeserializer<Coordinates> {

    @Override
    public Coordinates deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final JsonNode node = jp.readValueAs(JsonNode.class);
        if (null == node || node.isMissingNode() || node.isNull()) {
            return null;
        }
        final String type = node.path("type").asText();
        JsonNode coordinatesNode = node.path("coordinates");
        if(coordinatesNode.isArray() && (coordinatesNode).size() == 2){
            final double longitude = coordinatesNode.get(0).asDouble();
            final double latitude = coordinatesNode.get(1).asDouble();
            Coordinates coordinates = new Coordinates(type, longitude, latitude);
            jp.skipChildren();
            return coordinates;
        } else {
            return null;
        }
    }
}
