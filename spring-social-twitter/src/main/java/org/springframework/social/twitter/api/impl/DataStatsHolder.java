package org.springframework.social.twitter.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.advertising.StatisticsSnapshot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataStatsHolder {

    private final List<StatisticsSnapshot> list;

    @JsonCreator
    public DataStatsHolder(@JsonProperty("data") JsonNode data)
            throws JsonParseException, JsonMappingException, ClassNotFoundException, IOException {

        this.list = makeListFrom(data);
    }

    private List<StatisticsSnapshot> makeListFrom(JsonNode node)
            throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {

        List<StatisticsSnapshot> items = new ArrayList<>();
        if (node.isArray())
            for (JsonNode child : node)
                populateWithItem(items, child);
        else
            populateWithItem(items, node);

        return items;
    }

    private void populateWithItem(
            List<StatisticsSnapshot> list,
            JsonNode node) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException {
        list.add(new ObjectMapper()
                .registerModule(new TwitterModule())
                .readValue(
                        node.toString(),
                        StatisticsSnapshot.class));
    }

    public List<StatisticsSnapshot> getList() {
        return list;
    }
}
