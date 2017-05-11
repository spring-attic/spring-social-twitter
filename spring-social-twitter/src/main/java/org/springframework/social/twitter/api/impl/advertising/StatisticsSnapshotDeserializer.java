/*
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl.advertising;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

import org.springframework.social.twitter.api.advertising.StatisticsGranularity;
import org.springframework.social.twitter.api.advertising.StatisticsMetric;
import org.springframework.social.twitter.api.advertising.StatisticsSegmentation;
import org.springframework.social.twitter.api.advertising.StatisticsSegmentationType;
import org.springframework.social.twitter.api.advertising.StatisticsSnapshot;
import org.springframework.social.twitter.api.advertising.StatisticsSnapshotMetric;
import org.springframework.social.twitter.api.impl.LocalDateTimeDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Deserializes the complex object {@link StatisticsSnapshot} Differently from the other REST objects in the domain, the {@link StatisticsSnapshot}
 * has a very fluent interface and changes according to the parameters passed to the endpoint.
 * 
 * This deserializer tackles this complexity by getting the flexible JSON parts and pushing them
 * into a rigit model {@link StatisticsSnapshotMetric}
 * 
 * @author hudson
 *
 */
public class StatisticsSnapshotDeserializer extends JsonDeserializer<StatisticsSnapshot> {
    @Override
    public StatisticsSnapshot deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec codec = p.getCodec();
        JsonNode root = codec.readTree(p);
        JsonNode data = root.get("data");
        if (data == null)
            data = root;

        if (data.size() == 0)
            return null;

        return new StatisticsSnapshot(
                extractId(data),
                extractSegmentation(data),
                extractGranularity(data),
                extractMetrics(data),
                extractStartTime(data),
                extractEndTime(data));
    }

    private String extractId(JsonNode data) {
        return data.get("id").asText();
    }

    private StatisticsSegmentation extractSegmentation(JsonNode data) {
        JsonNode segmentationNode = data.get("segment");
        if (segmentationNode == null)
            return null;

        return new StatisticsSegmentation(
                StatisticsSegmentationType.valueOf(segmentationNode.get("segmentation_type").asText()),
                segmentationNode.get("segmentation_value").asText(),
                segmentationNode.get("name").asText());

    }

    private StatisticsGranularity extractGranularity(JsonNode data) {
        return StatisticsGranularity.valueOf(data.get("granularity").asText());
    }

    private Map<StatisticsMetric, StatisticsSnapshotMetric> extractMetrics(JsonNode data) {
        Map<StatisticsMetric, StatisticsSnapshotMetric> map = new HashMap<StatisticsMetric, StatisticsSnapshotMetric>();
        StatisticsMetric[] values = StatisticsMetric.class.getEnumConstants();
        for (int i = 0; i < values.length; i++) {
            StatisticsMetric metric = values[i];
            JsonNode metricNode = data.get(metric.toString());
            if (metricNode != null) {
                List<Object> entries = new ArrayList<Object>();
                parseMetricEntries(metric, metricNode.spliterator(), entries);
                map.put(metric, new StatisticsSnapshotMetric(metric, entries));
            }
        }

        return map;
    }

    private LocalDateTime extractStartTime(JsonNode data) {
        return LocalDateTimeDeserializer.parse(data.get("start_time").asText());
    }

    private LocalDateTime extractEndTime(JsonNode data) {
        return LocalDateTimeDeserializer.parse(data.get("end_time").asText());
    }

    private void parseMetricEntries(StatisticsMetric metric, Spliterator<JsonNode> iterator, List<Object> entries) {
        Type metricType = metric.getValueType();
        if (metricType == BigDecimal.class)
            dumpEntriesAsDecimals(iterator, entries);
        else if (metricType == Integer.class)
            dumpEntriesAsNumbers(iterator, entries);
        else if (metricType == Object.class)
            dumpEntriesAsHashes(iterator, entries);
    }

    private void dumpEntriesAsDecimals(Spliterator<JsonNode> iterator, final List<Object> entries) {
        iterator.forEachRemaining(new Consumer<JsonNode>() {
			@Override
			public void accept(JsonNode t) {
				String entryValue = t.asText();
	            entries.add(BigDecimalMicroAmountDeserializer.parse(entryValue));
			}
		});
    }

    private void dumpEntriesAsNumbers(Spliterator<JsonNode> iterator, final List<Object> entries) {
        iterator.forEachRemaining(new Consumer<JsonNode>() {
			@Override
			public void accept(JsonNode t) {
	            String entryValue = t.asText();
	            if (!entryValue.contains(".")) {
	                entries.add(new Long(entryValue));
	            }
                else {
                    entries.add(new Float(entryValue));
                }
            }
        });
    }

    private void dumpEntriesAsHashes(Spliterator<JsonNode> iterator, final List<Object> entries) {
        iterator.forEachRemaining(new Consumer<JsonNode>() {
			@Override
			public void accept(JsonNode t) {
	            t.fields().forEachRemaining(new Consumer<Map.Entry<String, JsonNode>>() {
					@Override
					public void accept(Map.Entry<String, JsonNode> j) {
		                String key = j.getKey();
		                String value = j.getValue().asText();
		                entries.add(new AbstractMap.SimpleEntry<String, String>(key, value));
		            }
	            });
	        }
        });
    }
}