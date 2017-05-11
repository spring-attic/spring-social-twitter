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

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.advertising.RetargetingEngagementType;
import org.springframework.social.twitter.api.advertising.TargetingCriterionAgeBucket;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaForm;
import org.springframework.social.twitter.api.advertising.TargetingCriterionGender;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Provides fluent generation of post paramters for the operation
 *
 * @author Hudson Mendes
 */
public class TargetingCriteriaFormBuilder extends AbstractTwitterFormBuilder implements TargetingCriteriaForm {

    private String lineItemId;
    private String eventId;
    private String retargetingEngagementCampaignId;
    private Long retargetingEngagementPromotedUserReferenceId;
    private RetargetingEngagementType retargetingEngagementType;
    private final List<Long> followedUserIds = new ArrayList<>();
    private final List<Long> similarToUserIds = new ArrayList<>();
    private final List<String> broadKeywords = new ArrayList<>();
    private final List<String> exactKeywords = new ArrayList<>();
    private final List<String> unorderedKeywords = new ArrayList<>();
    private final List<String> phraseKeywords = new ArrayList<>();
    private final List<String> negativeExactKeywords = new ArrayList<>();
    private final List<String> negativeUnorderedKeywords = new ArrayList<>();
    private final List<String> negativePhraseKeywords = new ArrayList<>();
    private final List<String> locations = new ArrayList<>();
    private final List<String> interests = new ArrayList<>();
    private TargetingCriterionGender gender;
    private final List<TargetingCriterionAgeBucket> ageBuckets = new ArrayList<>();
    private final List<String> platforms = new ArrayList<>();
    private final List<String> platformVersions = new ArrayList<>();
    private final List<String> devices = new ArrayList<>();
    private Boolean wifiOnly;
    private final List<String> tvChannels = new ArrayList<>();
    private final List<String> tvGenres = new ArrayList<>();
    private final List<String> tvShows = new ArrayList<>();
    private final List<String> tailoredAudiences = new ArrayList<>();
    private final List<String> tailoredAudiencesExpanded = new ArrayList<>();
    private final List<String> tailoredAudiencesExclusions = new ArrayList<>();
    private final List<String> behaviors = new ArrayList<>();
    private final List<String> behaviorsExpanded = new ArrayList<>();
    private final List<String> behavioursNegative = new ArrayList<>();
    private final List<String> languages = new ArrayList<>();
    private final List<String> networkOperators = new ArrayList<>();
    private Integer networkActivationDurationLess;
    private Integer networkActivationDurationMoreOrEqualsTo;
    private final List<String> appStoreCategories = new ArrayList<>();
    private final List<String> appStoreCategiresLookAlike = new ArrayList<>();

    @Override
    public TargetingCriteriaForm forLineItem(String lineItemId) {
        this.lineItemId = lineItemId;
        return this;
    }

    @Override
    public TargetingCriteriaForm forEvent(String eventId) {
        this.eventId = eventId;
        return this;
    }

    @Override
    public TargetingCriteriaForm forRetargetingEngagementOfCampaign(String campaignId) {
        retargetingEngagementCampaignId = campaignId;
        return this;
    }

    @Override
    public TargetingCriteriaForm forRetargetingEngagementOfPromotedUser(Long userId) {
        retargetingEngagementPromotedUserReferenceId = userId;
        return this;
    }

    @Override
    public TargetingCriteriaForm forRetargetingEngagementType(RetargetingEngagementType type) {
        retargetingEngagementType = type;
        return this;
    }

    @Override
    public TargetingCriteriaForm followersOfUsers(Long... followedUserIds) {
        if (followedUserIds != null)
            for (final Long userId : followedUserIds)
                this.followedUserIds.add(userId);
        return this;
    }

    @Override
    public TargetingCriteriaForm similarToUsers(Long... similiarUserIds) {
        if (similiarUserIds != null)
            for (final Long userId : similiarUserIds)
                similarToUserIds.add(userId);
        return this;
    }

    @Override
    public TargetingCriteriaForm withBroadKeywords(String... keywords) {
        if (keywords != null)
            for (final String keyword : keywords)
                broadKeywords.add(keyword);
        return this;
    }

    @Override
    public TargetingCriteriaForm withExactKeywords(String... keywords) {
        if (keywords != null)
            for (final String keyword : keywords)
                exactKeywords.add(keyword);
        return this;
    }

    @Override
    public TargetingCriteriaForm withUnorderedKeywords(String... keywords) {
        if (keywords != null)
            for (final String keyword : keywords)
                unorderedKeywords.add(keyword);
        return this;
    }

    @Override
    public TargetingCriteriaForm withPhraseKeywords(String... keywords) {
        if (keywords != null)
            for (final String keyword : keywords)
                phraseKeywords.add(keyword);
        return this;
    }

    @Override
    public TargetingCriteriaForm withNegativeExactKeywords(String... keywords) {
        if (keywords != null)
            for (final String keyword : keywords)
                negativeExactKeywords.add(keyword);
        return this;
    }

    @Override
    public TargetingCriteriaForm withNegativeUnorderedKeywords(String... keywords) {
        if (keywords != null)
            for (final String keyword : keywords)
                negativeUnorderedKeywords.add(keyword);
        return this;
    }

    @Override
    public TargetingCriteriaForm withNegativePhraseKeywords(String... keywords) {
        if (keywords != null)
            for (final String keyword : keywords)
                negativePhraseKeywords.add(keyword);
        return this;
    }

    @Override
    public TargetingCriteriaForm withLocations(String... locations) {
        if (locations != null)
            for (final String location : locations)
                this.locations.add(location);
        return this;
    }

    @Override
    public TargetingCriteriaForm withInterests(String... interests) {
        if (interests != null)
            for (final String interest : interests)
                this.interests.add(interest);
        return this;
    }

    @Override
    public TargetingCriteriaForm withGender(TargetingCriterionGender gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public TargetingCriteriaForm withAgeBuckets(TargetingCriterionAgeBucket... ageBuckets) {
        if (ageBuckets != null)
            for (final TargetingCriterionAgeBucket age : ageBuckets)
                this.ageBuckets.add(age);
        return this;
    }

    @Override
    public TargetingCriteriaForm withPlatforms(String... platforms) {
        if (platforms != null)
            for (final String platform : platforms)
                this.platforms.add(platform);
        return this;
    }

    @Override
    public TargetingCriteriaForm withPlatformVersions(String... versions) {
        if (versions != null)
            for (final String version : versions)
                platformVersions.add(version);
        return this;
    }

    @Override
    public TargetingCriteriaForm withDevices(String... devices) {
        if (devices != null)
            for (final String device : devices)
                this.devices.add(device);
        return this;
    }

    @Override
    public TargetingCriteriaForm onlyWify(Boolean wifiOnly) {
        this.wifiOnly = wifiOnly;
        return this;
    }

    @Override
    public TargetingCriteriaForm withTvChannels(String... channels) {
        if (channels != null)
            for (final String channel : channels)
                tvChannels.add(channel);
        return this;
    }

    @Override
    public TargetingCriteriaForm withTvGenres(String... genres) {
        if (genres != null)
            for (final String genre : genres)
                tvGenres.add(genre);
        return this;
    }

    @Override
    public TargetingCriteriaForm withTvShows(String... shows) {
        if (shows != null)
            for (final String show : shows)
                tvShows.add(show);
        return this;
    }

    @Override
    public TargetingCriteriaForm withTailoredAudiences(String... tailoredAudiences) {
        if (tailoredAudiences != null)
            for (final String tailoredAudience : tailoredAudiences)
                this.tailoredAudiences.add(tailoredAudience);
        return this;
    }

    @Override
    public TargetingCriteriaForm withTailoredAudiencesExpanded(String... tailoredAudiencesExpanded) {
        if (tailoredAudiencesExpanded != null)
            for (final String expanded : tailoredAudiencesExpanded)
                this.tailoredAudiencesExpanded.add(expanded);
        return this;
    }

    @Override
    public TargetingCriteriaForm withTailoredAudiencesExclusion(String... tailoredAudienceExclusions) {
        if (tailoredAudienceExclusions != null)
            for (final String exclusion : tailoredAudienceExclusions)
                tailoredAudiencesExclusions.add(exclusion);
        return this;
    }

    @Override
    public TargetingCriteriaForm withBehaviors(String... behaviors) {
        if (behaviors != null)
            for (final String behavior : behaviors)
                this.behaviors.add(behavior);
        return this;
    }

    @Override
    public TargetingCriteriaForm withBehaviorsExpaned(String... behaviorsExpanded) {
        if (behaviorsExpanded != null)
            for (final String expanded : behaviorsExpanded)
                this.behaviorsExpanded.add(expanded);
        return this;
    }

    @Override
    public TargetingCriteriaForm withBehaviorsNegative(String... behavioursNegative) {
        if (behavioursNegative != null)
            for (final String negative : behavioursNegative)
                this.behavioursNegative.add(negative);
        return this;
    }

    @Override
    public TargetingCriteriaForm withLanguages(String... languages) {
        if (languages != null)
            for (final String language : languages)
                this.languages.add(language);
        return this;
    }

    @Override
    public TargetingCriteriaForm withNetworkOperators(String... operators) {
        if (operators != null)
            for (final String operator : operators)
                networkOperators.add(operator);
        return this;
    }

    @Override
    public TargetingCriteriaForm withNetworkActivationDurationLess(Integer months) {
        networkActivationDurationLess = months;
        return this;
    }

    @Override
    public TargetingCriteriaForm withNetworkActivationDurationMoreOrEqualsTo(Integer months) {
        networkActivationDurationMoreOrEqualsTo = months;
        return this;
    }

    @Override
    public TargetingCriteriaForm withAppStoreCategories(String... categories) {
        if (categories != null)
            for (final String category : categories)
                appStoreCategories.add(category);
        return this;
    }

    @Override
    public TargetingCriteriaForm withAppStoreCategiresLookAlike(String... categories) {
        if (categories != null)
            for (final String category : categories)
                appStoreCategiresLookAlike.add(category);
        return this;
    }

    @Override
    public MultiValueMap<String, String> toRequestBody() {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        appendParameter(params, "line_item_id", lineItemId);

        appendParameter(params, "broad_keywords", broadKeywords);
        appendParameter(params, "exact_keywords", exactKeywords);
        appendParameter(params, "unordered_keywords", unorderedKeywords);
        appendParameter(params, "phrase_keywords", phraseKeywords);

        appendParameter(params, "negative_exact_keywords", negativeExactKeywords);
        appendParameter(params, "negative_unordered_keywords", negativeUnorderedKeywords);
        appendParameter(params, "negative_phrase_keywords", negativePhraseKeywords);

        appendParameter(params, "locations", locations);
        appendParameter(params, "interests", interests);
        appendParameter(params, "gender", (gender != null ? gender.value() : null));
        appendParameter(params, "age_buckets", ageBuckets);

        appendParameter(params, "followers_of_users", followedUserIds);
        appendParameter(params, "similar_to_followers_of_users", similarToUserIds);
        appendParameter(params, "platforms", platforms);
        appendParameter(params, "platform_versions", platformVersions);
        appendParameter(params, "devices", devices);
        appendParameter(params, "wifi_only", (wifiOnly != null ? (wifiOnly.booleanValue() ? 1 : 0) : ""));

        appendParameter(params, "tv_channels", tvChannels);
        appendParameter(params, "tv_genres", tvGenres);
        appendParameter(params, "tv_shows", tvShows);

        appendParameter(params, "tailored_audiences", tailoredAudiences);
        appendParameter(params, "tailored_audiences_expanded", tailoredAudiencesExpanded);
        appendParameter(params, "tailored_audiences_excluded", tailoredAudiencesExclusions);

        appendParameter(params, "behaviors", behaviors);
        appendParameter(params, "behaviors_expanded", behaviorsExpanded);
        appendParameter(params, "negative_behaviors", behavioursNegative);

        appendParameter(params, "languages", languages);
        appendParameter(params, "event", eventId);

        appendParameter(params, "network_operators", networkOperators);
        appendParameter(params, "network_activation_duration_lt", networkActivationDurationLess);
        appendParameter(params, "network_activation_duration_gte", networkActivationDurationMoreOrEqualsTo);

        appendParameter(params, "app_store_categories", appStoreCategories);
        appendParameter(params, "app_store_categories_lookalike", appStoreCategiresLookAlike);

        appendParameter(params, "campaign_engagement", retargetingEngagementCampaignId);
        appendParameter(params, "user_engagement", retargetingEngagementPromotedUserReferenceId);
        appendParameter(params, "engagement_type", retargetingEngagementType);

        return params;
    }

}
