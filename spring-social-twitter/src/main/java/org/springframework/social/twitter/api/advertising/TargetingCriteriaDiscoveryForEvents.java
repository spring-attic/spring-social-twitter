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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.social.twitter.api.TwitterObject;


/**
 * Data discovery for targeting criterias based on events.
 *
 * @author Hudson Mendes
 */
public class TargetingCriteriaDiscoveryForEvents extends TwitterObject {
    private static final long serialVersionUID = 1L;

    private final Map<String, Float> countryBreakdownPercentage = new HashMap<String, Float>();
    private final String countryCode;
    private final Map<String, Float> deviceBreakdownPercentage = new HashMap<String, Float>();
    private final String endTime;
    private final EventType eventType;
    private final Map<String, Float> genderBreakdownPercentage = new HashMap<String, Float>();
    private final String id;
    private final boolean isGlobal;
    private final String name;
    private final Map<String, Long> reach = new HashMap<String, Long>();
    private final String startTime;
    private final List<String> topHashTags = new ArrayList<String>();
    private final List<Long> topTweets = new ArrayList<Long>();
    private final List<String> topUsers = new ArrayList<String>();

    public TargetingCriteriaDiscoveryForEvents(
            final Map<String, Float> countryBreakdownPercentage,
            final String countryCode,
            final Map<String, Float> deviceBreakdownPercentage,
            final String endTime,
            final EventType eventType,
            final Map<String, Float> genderBreakdownPercentage,
            final String id,
            final boolean isGlobal,
            final String name,
            final Map<String, Long> reach,
            final String startTime,
            final List<String> topHashTags,
            final List<Long> topTweets,
            final List<String> topUsers) {

        this.countryBreakdownPercentage.putAll(countryBreakdownPercentage);
        this.countryCode = countryCode;
        this.deviceBreakdownPercentage.putAll(deviceBreakdownPercentage);
        this.endTime = endTime;
        this.eventType = eventType;
        this.genderBreakdownPercentage.putAll(genderBreakdownPercentage);
        this.id = id;
        this.isGlobal = isGlobal;
        this.name = name;
        this.reach.putAll(reach);
        this.startTime = startTime;
        this.topHashTags.addAll(topHashTags);
        this.topTweets.addAll(topTweets);
        this.topUsers.addAll(topUsers);
    }

    public Map<String, Float> getCountryBreakdownPercentage() {
        return countryBreakdownPercentage;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Map<String, Float> getDeviceBreakdownPercentage() {
        return deviceBreakdownPercentage;
    }

    public String getEndTime() {
        return endTime;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Map<String, Float> getGenderBreakdownPercentage() {
        return genderBreakdownPercentage;
    }

    public String getId() {
        return id;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public String getName() {
        return name;
    }

    public Map<String, Long> getReach() {
        return reach;
    }

    public String getStartTime() {
        return startTime;
    }

    public List<String> getTopHashTags() {
        return topHashTags;
    }

    public List<Long> getTopTweets() {
        return topTweets;
    }

    public List<String> getTopUsers() {
        return topUsers;
    }



}
