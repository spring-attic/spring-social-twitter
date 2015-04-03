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

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.Test;
import org.springframework.social.twitter.api.advertising.RetargetingEngagementType;
import org.springframework.social.twitter.api.advertising.TailoredAudienceType;
import org.springframework.social.twitter.api.advertising.TargetingCriterionAgeBucket;
import org.springframework.social.twitter.api.advertising.TargetingCriterionGender;
import org.springframework.social.twitter.api.advertising.TargetingCriterion;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;
import org.springframework.social.twitter.api.impl.DataListHolder;

/**
 * @author Hudson mendes
 */
public class TargetingCriteriaTemplateTest extends AbstractTwitterApiTest {

    @Test
    public void getTargetingCriterions() {
        final String mockedAccountId = "hkk5";
        final String mockedLineItemId = "oi4h5";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/targeting_criteria" +
                                "?line_item_id=" + mockedLineItemId +
                                "&with_deleted=false"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("ad-targeting-criteria"), APPLICATION_JSON));

        final DataListHolder<TargetingCriterion> targetingCriterias = twitter.targetingCriteriaOperations().getTargetingCriterions(
                mockedAccountId,
                new TargetingCriterionQueryBuilder()
                        .withLineItem(mockedLineItemId)
                        .includeDeleted(false));

        assertTargetCriterionContents(targetingCriterias.getList());
    }

    @Test
    public void getTargetingCriterion() {
        final String mockedAccountId = "hkk5";
        final String mockedTargetingCriteriaId = "2rqqn";
        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/targeting_criteria/" + mockedTargetingCriteriaId))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("ad-targeting-criteria-single"), APPLICATION_JSON));

        final TargetingCriterion criteria = twitter.targetingCriteriaOperations().getTargetingCriterion(mockedAccountId, mockedTargetingCriteriaId);
        assertSingleTargetingCriterionContents(criteria);
    }

    @Test
    public void createTargetingCriterion() {
        final String mockedAccountId = "hkk5";
        final String doesntMatterString = "doesnt-matter";
        final Boolean doesntMatterBool = false;
        final String chainedPostContent =
                "line_item_id=" + doesntMatterString + "&" +
                        "name=" + doesntMatterString + "&" +
                        "targeting_type=" + "APP_STORE_CATEGORY" + "&" +
                        "targeting_value=" + doesntMatterString + "&" +
                        "deleted=" + doesntMatterBool + "&" +
                        "tailored_audience_expansion=" + doesntMatterBool + "&" +
                        "tailored_audience_type=" + TailoredAudienceType.EXCLUDED_MOBILE;

        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/targeting_criteria"))
                .andExpect(method(POST))
                .andExpect(content().string(chainedPostContent))
                .andRespond(withSuccess(jsonResource("ad-targeting-criteria-single"), APPLICATION_JSON));

        final TargetingCriterion criteria = twitter.targetingCriteriaOperations().createTargetingCriterion(
                mockedAccountId,
                new TargetingCriterionFormBuilder()
                        .withLineItem(doesntMatterString)
                        .withName(doesntMatterString)
                        .withTargetedAudienceExpansion(doesntMatterBool)
                        .withTargetedAudienceType(TailoredAudienceType.EXCLUDED_MOBILE)
                        .targeting("APP_STORE_CATEGORY", doesntMatterString)
                        .active());

        assertSingleTargetingCriterionContents(criteria);
    }

    @Test
    public void setTargetingCriteria() {
        final String mockedAccountId = "hkk5";
        final String mockedLineItem = "45j45";
        final String chainedPostContent =
                "line_item_id=" + mockedLineItem + "&" +
                        "broad_keywords=bkw1%2Cbkw2&" +
                        "exact_keywords=ekw1&" +
                        "unordered_keywords=&" +
                        "phrase_keywords=pkw0&" +
                        "negative_exact_keywords=nekw9&" +
                        "negative_unordered_keywords=&" +
                        "negative_phrase_keywords=npkw111&" +
                        "locations=b6b8d75a320f81d9%2C6c6fd550ac2d3d60&" +
                        "interests=19004%2C19002&" +
                        "gender=2&" +
                        "age_buckets=AGE_35_TO_44%2CAGE_OVER_65&" +
                        "followers_of_users=1324%2C297527452457&" +
                        "similar_to_followers_of_users=945%2C12%2C1%2C88888888888&" +
                        "platforms=0%2C4&" +
                        "platform_versions=10%2C14&" +
                        "devices=7%2C23&" +
                        "wifi_only=1&" +
                        "tv_channels=3113%2C6945&" +
                        "tv_genres=9%2C15&" +
                        "tv_shows=10024849207%2C10000271509&" +
                        "tailored_audiences=a6b8d%2Cac6fd&" +
                        "tailored_audiences_expanded=b6b8d%2Cbc6fd&" +
                        "tailored_audiences_excluded=c6b8d%2Ccc6fd&" +
                        "behaviors=afmj%2Cafrr&" +
                        "behaviors_expanded=bfmj%2Cbfrr&" +
                        "negative_behaviors=cfmj%2Ccfrr&" +
                        "languages=en%2Cfi&" +
                        "event=aj12b&" +
                        "network_operators=dfmj%2Cdfrr&" +
                        "network_activation_duration_lt=3&" +
                        "network_activation_duration_gte=3&" +
                        "app_store_categories=efmj%2Cefrr&" +
                        "app_store_categories_lookalike=ffmj%2Cffrr&" +
                        "campaign_engagement=7ako2&" +
                        "user_engagement=12345&" +
                        "engagement_type=ENGAGEMENT";

        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/targeting_criteria"))
                .andExpect(method(PUT))
                .andExpect(content().string(chainedPostContent))
                .andRespond(withSuccess(jsonResource("ad-targeting-criteria"), APPLICATION_JSON));

        final DataListHolder<TargetingCriterion> criteria = twitter.targetingCriteriaOperations().setTargetingCriteria(
                mockedAccountId,
                new TargetingCriteriaFormBuilder()
                        .forLineItem(mockedLineItem)
                        .withBroadKeywords("bkw1", "bkw2")
                        .withExactKeywords("ekw1")
                        .withUnorderedKeywords((String) null)
                        .withPhraseKeywords("pkw0")
                        .withNegativeExactKeywords("nekw9")
                        .withNegativeUnorderedKeywords((String) null)
                        .withNegativePhraseKeywords("npkw111")
                        .withLocations("b6b8d75a320f81d9", "6c6fd550ac2d3d60")
                        .withInterests("19004", "19002")
                        .withGender(TargetingCriterionGender.FEMALE)
                        .withAgeBuckets(TargetingCriterionAgeBucket.AGE_35_TO_44, TargetingCriterionAgeBucket.AGE_OVER_65)
                        .followersOfUsers(1324L, 297527452457L)
                        .similarToUsers(945L, 12L, 1L, 88888888888L)
                        .withPlatforms("0", "4")
                        .withPlatformVersions("10", "14")
                        .withDevices("7", "23")
                        .onlyWify(true)
                        .withTvChannels("3113", "6945")
                        .withTvGenres("9", "15")
                        .withTvShows("10024849207", "10000271509")
                        .withTailoredAudiences("a6b8d", "ac6fd")
                        .withTailoredAudiencesExpanded("b6b8d", "bc6fd")
                        .withTailoredAudiencesExclusion("c6b8d", "cc6fd")
                        .withBehaviors("afmj", "afrr")
                        .withBehaviorsExpaned("bfmj", "bfrr")
                        .withBehaviorsNegative("cfmj", "cfrr")
                        .withLanguages("en", "fi")
                        .forEvent("aj12b")
                        .withNetworkOperators("dfmj", "dfrr")
                        .withNetworkActivationDurationLess(3)
                        .withNetworkActivationDurationMoreOrEqualsTo(3)
                        .withAppStoreCategories("efmj", "efrr")
                        .withAppStoreCategiresLookAlike("ffmj", "ffrr")
                        .forRetargetingEngagementOfCampaign("7ako2")
                        .forRetargetingEngagementOfPromotedUser(12345L)
                        .forRetargetingEngagementType(RetargetingEngagementType.ENGAGEMENT));

        assertTargetCriterionContents(criteria.getList());
    }

    @Test
    public void deleteTargetingCriterions() {
        final String mockedAccountId = "hkk5";
        final String mockedTargetingCriterionId = "oi4h5";
        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/targeting_criteria/" + mockedTargetingCriterionId))
                .andExpect(method(DELETE))
                .andRespond(withSuccess());

        twitter.targetingCriteriaOperations().deleteTargetingCriterion(mockedAccountId, mockedTargetingCriterionId);
    }

    private void assertTargetCriterionContents(List<TargetingCriterion> criterias) {
        assertEquals(4, criterias.size());

        assertEquals("2kzxf", criterias.get(0).getId());
        assertEquals("hkk5", criterias.get(0).getAccountId());
        assertEquals("69ob", criterias.get(0).getLineItemId());
        assertEquals("episod", criterias.get(0).getName());
        assertEquals("SIMILAR_TO_FOLLOWERS_OF_USER", criterias.get(0).getTargetingType());
        assertEquals("819797", criterias.get(0).getTargetingValue());
        assertEquals(false, criterias.get(0).isDeleted());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 11), criterias.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 11), criterias.get(0).getUpdatedAt());

        assertEquals("2kzxi", criterias.get(1).getId());
        assertEquals("hkk5", criterias.get(1).getAccountId());
        assertEquals("69ob", criterias.get(1).getLineItemId());
        assertEquals("matrixsynth", criterias.get(1).getName());
        assertEquals("SIMILAR_TO_FOLLOWERS_OF_USER", criterias.get(1).getTargetingType());
        assertEquals("22231561", criterias.get(1).getTargetingValue());
        assertEquals(false, criterias.get(1).isDeleted());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 12), criterias.get(1).getCreatedAt());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 12), criterias.get(1).getUpdatedAt());

        assertEquals("2kzxj", criterias.get(2).getId());
        assertEquals("hkk5", criterias.get(2).getAccountId());
        assertEquals("69ob", criterias.get(2).getLineItemId());
        assertEquals("Horse_ebooks", criterias.get(2).getName());
        assertEquals("SIMILAR_TO_FOLLOWERS_OF_USER", criterias.get(2).getTargetingType());
        assertEquals("174958347", criterias.get(2).getTargetingValue());
        assertEquals(false, criterias.get(2).isDeleted());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 12), criterias.get(2).getCreatedAt());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 12), criterias.get(2).getUpdatedAt());

        assertEquals("2mq7j", criterias.get(3).getId());
        assertEquals("hkk5", criterias.get(3).getAccountId());
        assertEquals("69ob", criterias.get(3).getLineItemId());
        assertEquals("righteous dude", criterias.get(3).getName());
        assertEquals("PHRASE_KEYWORD", criterias.get(3).getTargetingType());
        assertEquals("righteous dude", criterias.get(3).getTargetingValue());
        assertEquals(true, criterias.get(3).isDeleted());
        assertEquals(LocalDateTime.of(2012, Month.DECEMBER, 05, 05, 11, 15), criterias.get(3).getCreatedAt());
        assertEquals(LocalDateTime.of(2012, Month.DECEMBER, 06, 05, 11, 15), criterias.get(3).getUpdatedAt());
    }

    private void assertSingleTargetingCriterionContents(TargetingCriterion criteria) {
        assertEquals("31l8r", criteria.getId());
        assertEquals("gq0vqj", criteria.getAccountId());
        assertEquals("bcrv", criteria.getLineItemId());
        assertEquals("Mobile audience targeting", criteria.getName());
        assertEquals(false, criteria.isTailoredAudienceExpansion());
        assertEquals(TailoredAudienceType.EXCLUDED_MOBILE, criteria.getTailoredAudienceType());
        assertEquals("TAILORED_AUDIENCE", criteria.getTargetingType());
        assertEquals("qsb3", criteria.getTargetingValue());
        assertEquals(false, criteria.isDeleted());
        assertEquals(LocalDateTime.of(2015, Month.JULY, 23, 23, 14, 37), criteria.getCreatedAt());
        assertEquals(LocalDateTime.of(2015, Month.JULY, 23, 23, 14, 37), criteria.getUpdatedAt());
    }
}
