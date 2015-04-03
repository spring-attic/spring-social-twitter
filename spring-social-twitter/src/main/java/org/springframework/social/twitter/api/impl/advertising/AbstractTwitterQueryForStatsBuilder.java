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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.advertising.StatisticsGranularity;
import org.springframework.social.twitter.api.advertising.StatisticsMetric;
import org.springframework.social.twitter.api.advertising.StatisticsSegmentationType;
import org.springframework.social.twitter.api.impl.TwitterTimeStamp;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Basic representation of the QueryString parameters builders
 * that shall be used for querying any data in the APIs.
 * Important: unfortunately, the basic API is a lot less standardized than the ADs Api
 * and therefore we cannot use this base builder for _everything_. However,
 * it's reasonable the Twitter moves towards standardization and then, this
 * builder will become a richer asset to the Api.
 *
 * @author Hudson Mendes
 */
public abstract class AbstractTwitterQueryForStatsBuilder<TBuilderInterface>
        extends AbstractTwitterParametersBuilder {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private StatisticsSegmentationType segmentation;
    private StatisticsGranularity granularity;
    private List<StatisticsMetric> metrics;

    public MultiValueMap<String, String> toQueryParameters() {
        final MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

        makeParameters(map);

        appendParameter(map, "segmentation_type", this.segmentation);

        appendParameter(map, "granularity", this.granularity);

        appendParameter(map, "metrics", this.metrics);

        if (this.startTime != null)
            appendParameter(map, "start_time", new TwitterTimeStamp(this.startTime).toString());

        if (this.endTime != null)
            appendParameter(map, "end_time", new TwitterTimeStamp(this.endTime).toString());

        return map;
    }

    public TBuilderInterface activeUntil(LocalDateTime endTime) {
        return activeBetween(null, endTime);
    }

    public TBuilderInterface activeFrom(LocalDateTime startTime) {
        return activeBetween(startTime, null);
    }

    @SuppressWarnings("unchecked")
    public TBuilderInterface activeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime != null)
            this.startTime = startTime;
        if (endTime != null)
            this.endTime = endTime;
        return (TBuilderInterface) this;
    }

    @SuppressWarnings("unchecked")
    public TBuilderInterface withGranularity(StatisticsGranularity granularity) {
        this.granularity = granularity;
        return (TBuilderInterface) this;
    }

    @SuppressWarnings("unchecked")
    public TBuilderInterface withSegmentationType(StatisticsSegmentationType segmentation) {
        this.segmentation = segmentation;
        return (TBuilderInterface) this;
    }

    @SuppressWarnings("unchecked")
    public TBuilderInterface withStatisticalMetric(StatisticsMetric... metrics) {
        this.metrics = new ArrayList<StatisticsMetric>();
        if (metrics != null)
            for (final StatisticsMetric metric : metrics)
                this.metrics.add(metric);
        return (TBuilderInterface) this;
    }

    protected abstract void makeParameters(MultiValueMap<String, String> map);
}
