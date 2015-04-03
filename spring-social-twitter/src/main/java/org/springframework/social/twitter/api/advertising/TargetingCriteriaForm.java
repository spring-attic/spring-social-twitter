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

import org.springframework.social.twitter.api.TwitterForm;

/**
 * Allow setting then entire set of {@link TargetingCriterion} for
 * a particular {@link LineItem} all at once
 *
 * @author Hudson Mendes
 */
public interface TargetingCriteriaForm extends TwitterForm {

    /**
     * A reference to the line item you are operating with in the request.
     * Example Values: 43853bh6lk3n
     *
     * @param lineItemId identifies the {@link LineItem}
     * @return the fluent builder
     */
    public TargetingCriteriaForm forLineItem(String lineItemId);

    /**
     * An event id to scope targeting to audiences associated with the event.
     * Only one event can be targeted per line item.
     * Example Values: aj12b
     *
     * @param eventId identifies the event
     * @return the fluent builder
     */
    public TargetingCriteriaForm forEvent(String eventId);

    /**
     * The {@link Campaign} ID for use with Tweet Engager Retargeting.
     * Example Values: 7ako2
     *
     * @param campaignId identifies the campaign
     * @return the fluent builder
     */
    public TargetingCriteriaForm forRetargetingEngagementOfCampaign(String campaignId);

    /**
     * The {@link PromotedUserReference} ID for use with Tweet Engager Retargeting.
     *
     * @param userId identifies the link between {@link PromotableUser} and {@link LineItem}
     * @return the fluent builder
     */
    public TargetingCriteriaForm forRetargetingEngagementOfPromotedUser(Long userId);

    /**
     * The engagement type for use with Tweet Engager Retargeting.
     * Valid values include IMPRESSION or ENGAGEMENT
     * Example Values: IMPRESSION
     *
     * @param type identifies the type of engagement
     * @return the fluent builder
     */
    public TargetingCriteriaForm forRetargetingEngagementType(RetargetingEngagementType type);

    /**
     * A comma-separated string of Twitter User identifiers to scope targeting to followers of on this line item.
     * 100 users can be associated with a line item and the user IDs must correspond to promotable users
     * for the current advertising account. Targeting deleted or invalid users will return an error block
     * with an INVALID_TWITTER_USER_ID message.
     * Example Values: 6253282, 7588892
     *
     * @param followedUserIds identifies the ids of the users for whose followers we want to advertise.
     * @return the fluent builder
     */
    public TargetingCriteriaForm followersOfUsers(Long... followedUserIds);

    /**
     * A comma-separated string of Twitter User identifiers to scope targeting to similar followers of on this line item.
     * 100 users can be associated with a line item. Targeting deleted or invalid users will return an error block with
     * an INVALID_TWITTER_USER_ID message
     * Example Values: 6253282, 7588892
     *
     * @param similiarUserIds the ids of the users for whose similars we want to advertise.
     * @return the fluent builder.
     */
    public TargetingCriteriaForm similarToUsers(Long... similiarUserIds);

    /**
     * A comma-separated string of broad keywords to target. These are typically used to match expandable words appearing
     * in any order within search queries for PROMOTED_TWEETS_IN_SEARCH. A maximum of 1,000 keywords may be attached to a
     * line item.
     * Example Values: good, bad, happy, shiny, people
     *
     * @param keywords to be advertised against
     * @return the fluent builder
     */
    public TargetingCriteriaForm withBroadKeywords(String... keywords);

    /**
     * A comma-separated string of exact match keywords to target. These are typically used to match a very specific search
     * query for PROMOTED_TWEETS_IN_SEARCH. A maximum of 1,000 keywords may be attached to a line item.
     * Example Values: %23freebandnames, http%3A%2F%2Fen.wikipedia.org%2Fwiki%2FIn_Watermelon_Sugar
     *
     * @param keywords to be advertised against
     * @return the fluent builder
     */
    public TargetingCriteriaForm withExactKeywords(String... keywords);

    /**
     * A comma-separated string of â€œunorderedâ€� keywords to target. These are typically used to match words appearing in any order
     * within search queries for PROMOTED_TWEETS_IN_SEARCH. A maximum of 1,000 keywords may be attached to a line item.
     * Example Values: good, bad, happy, shiny, people
     *
     * @param keywords to be advertised against
     * @return the fluent builder
     */
    public TargetingCriteriaForm withUnorderedKeywords(String... keywords);

    /**
     * A comma-separated string of â€œphraseâ€� keywords to target. These are typically used to match phrases appearing within search queries for
     * PROMOTED_TWEETS_IN_SEARCH. A maximum of 1,000 keywords may be attached to a line item. *
     * Example Values: happy shiny people
     *
     * @param keywords to be advertised against
     * @return the fluent builder
     */
    public TargetingCriteriaForm withPhraseKeywords(String... keywords);

    /**
     * A comma-separated string of â€œnegative exact matchâ€� keywords to target. These are typically used to restrict a very specific search query
     * for
     * PROMOTED_TWEETS_IN_SEARCH. A maximum of 1,000 keywords may be attached to a line item.
     *
     * Example Values: %23freebandnames, http%3A%2F%2Fen.wikipedia.org%2Fwiki%2FIn_Watermelon_Sugar
     *
     * @param keywords not to be advertised against.
     * @return the fluent builder
     */
    public TargetingCriteriaForm withNegativeExactKeywords(String... keywords);

    /**
     * A comma-separated string of â€œnegative unorderedâ€� keywords to target. These are typically used to restrict matching keywords appearing in
     * any
     * order within search queries for PROMOTED_TWEETS_IN_SEARCH. A maximum of 1,000 keywords may be attached to a line item.
     * Example Values: good, bad, happy, shiny, people
     *
     * @param keywords not to be advertised against.
     * @return the fluent builder
     */
    public TargetingCriteriaForm withNegativeUnorderedKeywords(String... keywords);

    /**
     * A comma-separated string of â€œnegative phraseâ€� keywords to target. These are typically used to restrict specific phrases from matching
     * within
     * search queries for PROMOTED_TWEETS_IN_SEARCH. A maximum of 1,000 keywords may be attached to a line item.
     * Example Values: happy shiny people
     *
     * @param keywords not to be advertised against.
     * @return the fluent builder
     */
    public TargetingCriteriaForm withNegativePhraseKeywords(String... keywords);

    /**
     * A comma-separated string of location identifiers to scope targeting to on this line item. 250 locations may be associated with a line item.
     * Example Values: b6b8d75a320f81d9, 6c6fd550ac2d3d60
     *
     * @param locations to which we want to advertise.
     * @return the fluent builder
     */
    public TargetingCriteriaForm withLocations(String... locations);

    /**
     * A comma-separated string of interest identifiers to scope targeting to on this line item. 100 interests may be associated with a line item.
     * Example Values: 19004, 19002
     *
     * @param interests to which we want to advertise.
     * @return the fluent builder
     */
    public TargetingCriteriaForm withInterests(String... interests);

    /**
     * A comma-separated string of gender identifiers to scope targeting to on this line item. Use â€œ1â€� to limit to males, or â€œ2â€�, to limit to
     * females.
     * Example Values: 1 (MALE, in our ENUM)
     *
     * @param gender to which we want to advertise.
     * @return the fluent builder
     */
    public TargetingCriteriaForm withGender(TargetingCriterionGender gender);

    /**
     * A comma-separated string of age bucket identifiers to scope targeting to on this line item, only required when using targeting_type=AGE. See
     * the age_buckets enum for a list of possible values.
     * Example Values: AGE_25_TO_34
     *
     * @param ageBuckets to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withAgeBuckets(TargetingCriterionAgeBucket... ageBuckets);

    /**
     * A comma-separated string of Platform identifiers to scope targeting to on this line item.
     * Example Values: 0, 4
     *
     * @param platforms to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withPlatforms(String... platforms);

    /**
     * A comma-separated string of platform version identifiers to scope targeting to on this line item.
     * Example Values: 10, 14
     *
     * @param versions to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withPlatformVersions(String... versions);

    /**
     * A comma-separated string of device identifiers to scope targeting to on this line item.
     * Example Values: 7, 23
     *
     * @param devices to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withDevices(String... devices);

    /**
     * If true, will only target users of mobile devices using wifi connections. If false, will target mobile consumers on carrier networks as well.
     * Use targeting_value 1 for true, and 0 for false.
     * Example Values: 1
     *
     * @param wifiOnly true says to only advertise to wifi users; false states to anyone. Default is false.
     * @return the fluent builder
     */
    public TargetingCriteriaForm onlyWify(Boolean wifiOnly);

    /**
     * A comma-separated string of TV channel identifiers to scope targeting to on this line item.
     * Example Values: 3113,6945
     *
     * @param channels to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withTvChannels(String... channels);

    /**
     * A comma-separated string of TV genre identifiers to scope targeting to on this line item.
     * Example Values: 9,15
     *
     * @param genres to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withTvGenres(String... genres);

    /**
     * A comma-separated string of TV show identifiers to scope targeting to on this line item.
     * Example Values: 10024849207,10000271509
     *
     * @param shows to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withTvShows(String... shows);

    /**
     * A comma-separated string of tailored audience identifiers to scope targeting to on this line item.
     * Example Values: b6b8d, 6c
     *
     * @param tailoredAudiences to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withTailoredAudiences(String... tailoredAudiences);

    /**
     * A comma-separated string of tailored audience identifiers that should be expanded and to scope targeting to on this line item.
     * Example Values: b6b8d, 6c6fd
     *
     * @param tailoredAudiencesExpanded to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withTailoredAudiencesExpanded(String... tailoredAudiencesExpanded);

    /**
     * A comma-separated string of tailored audience identifiers to scope targeting to on this line item. Excluding audiences is available for
     * tailored_audience_type CRM and WEB.
     * Example Values: b6b8d, 6c6fd
     *
     * @param tailoredAudienceExclusions to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withTailoredAudiencesExclusion(String... tailoredAudienceExclusions);

    /**
     * A comma-separated string of behavior identifiers to scope targeting to on this line item.
     * Example Values: lfmj, lfrr
     *
     * @param behaviors to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withBehaviors(String... behaviors);

    /**
     * A comma-separated string of behavior identifiers that should be expanded and to scope targeting to on this line item.
     * Example Values: lfmj, lfrr
     *
     * @param behaviorsExpanded to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withBehaviorsExpaned(String... behaviorsExpanded);

    /**
     * A comma-separated string of behavior identifiers scope targeting to on this line item.
     * Example Values: lfmj, lfrr
     *
     * @param behavioursNegative to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withBehaviorsNegative(String... behavioursNegative);

    /**
     * A comma-separated string of language identifiers to scope targeting to on this line item.
     * Example Values: en, fi
     *
     * @param languages to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withLanguages(String... languages);

    /**
     * A comma-separated string of network operator identifiers to scope targeting to on this line item.
     * Example Values: lfmj, lfrr
     *
     * @param operators to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withNetworkOperators(String... operators);

    /**
     * An integer in months, less-than, for network activation duration.
     * Example Values: 3
     *
     * @param months to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withNetworkActivationDurationLess(Integer months);

    /**
     * An integer in months, greater-than-or-equal-to, for network activation duration.
     * Example Values: 3
     *
     * @param months to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withNetworkActivationDurationMoreOrEqualsTo(Integer months);

    /**
     * A comma-separated string of app store category identifiers to scope targeting to on this line item.
     * Example Values: lfmj, lfrr
     *
     * @param categories to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withAppStoreCategories(String... categories);

    /**
     * A comma-separated string of app store category identifiers. Targeting will be scoped to lookalike app store categories on this line item.
     * Example Values: lfmj, lfrr
     *
     * @param categories to which we want to advertise
     * @return the fluent builder
     */
    public TargetingCriteriaForm withAppStoreCategiresLookAlike(String... categories);
}
