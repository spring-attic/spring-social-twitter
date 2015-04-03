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
package org.springframework.social.twitter.api.advertising;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.social.twitter.api.TwitterObject;

/**
 * Represents a snapshot of the statistics on advertising
 * for any of the perspectives requested to the twitter ads api.
 *
 * @author Hudson Mendes
 */
public class StatisticsSnapshot extends TwitterObject {
    private static final long serialVersionUID = 1L;
    private final String id;
    private final StatisticsSegmentation segmentation;
    private final StatisticsGranularity granularity;
    private final Map<StatisticsMetric, StatisticsSnapshotMetric> metrics = new HashMap<StatisticsMetric, StatisticsSnapshotMetric>();
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public StatisticsSnapshot(
            String id,
            StatisticsSegmentation segmentation,
            StatisticsGranularity granularity,
            Map<StatisticsMetric, StatisticsSnapshotMetric> metrics,
            LocalDateTime startTime,
            LocalDateTime endTime) {

        this.id = id;
        this.segmentation = segmentation;
        this.granularity = granularity;
        this.metrics.putAll(metrics);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public StatisticsSegmentation getSegmentation() {
        return segmentation;
    }

    public StatisticsGranularity getGranularity() {
        return granularity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public StatisticsSnapshotMetric getMetric(StatisticsMetric key) {
        return metrics.get(key);
    }

    public StatisticsSnapshotMetric[] getMetrics() {
        final Collection<StatisticsSnapshotMetric> metrics = this.metrics.values();
        return metrics.toArray(new StatisticsSnapshotMetric[metrics.size()]);
    }

    public Boolean hasMetrics(StatisticsMetric... metrics) {
        if (metrics == null)
            return false;

        if (metrics.length == 0)
            return false;

        for (final StatisticsMetric metric : metrics)
            if (!this.metrics.containsKey(metric))
                return false;

        return true;
    }
}
