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

import java.util.List;
import java.util.Map;

import org.springframework.social.twitter.api.advertising.EventType;
import org.springframework.social.twitter.api.impl.TwitterObjectMixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TargetingCriteriaDiscoveryForEventsMixin extends TwitterObjectMixin {

	@JsonCreator
    TargetingCriteriaDiscoveryForEventsMixin(
            @JsonProperty("country_breakdown_percentage") Map<String, Float> countryBreakdownPercentage,
            @JsonProperty("country_code") String countryCode,
            @JsonProperty("device_breakdown_percentage") Map<String, Float> deviceBreakdownPercentage,
            @JsonProperty("end_time") String endTime,
            @JsonProperty("event_type") EventType eventType,
            @JsonProperty("gender_breakdown_percentage") Map<String, Float> genderBreakdownPercentage,
            @JsonProperty("id") String id,
            @JsonProperty("is_global") boolean isGlobal,
            @JsonProperty("name") String name,
            @JsonProperty("reach") Map<String, Long> reach,
            @JsonProperty("start_time") String startTime,
            @JsonProperty("top_hashtags") List<String> topHashTags,
            @JsonProperty("top_tweets") List<Long> topTweets,
            @JsonProperty("top_users") List<String> topUsers) {}


}
