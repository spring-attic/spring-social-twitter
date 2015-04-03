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

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.social.twitter.api.advertising.AppStore;
import org.springframework.social.twitter.api.advertising.EventType;
import org.springframework.social.twitter.api.advertising.SortDirection;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForAppStoreCategories;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForBehaviorTaxonomies;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForBehaviors;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForDevices;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForEvents;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForInterests;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForLanguages;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForLocations;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForNetworkOperators;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForPlatformVersions;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForPlatforms;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvChannel;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvGenre;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvMarket;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvShow;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;
import org.springframework.social.twitter.api.impl.DataListHolder;

/**
 * @author Hudson Mendes
 */
public class TargetingCriteriaDiscoveryTemplateTest extends AbstractTwitterApiTest {

    @Test
    public void appStoreCategories() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/app_store_categories"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-app_store_categories"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForAppStoreCategories> discoveries =
                twitter.targetingCriteriaDiscoveryOperations().appStoreCategories(
                        new TargetingCriteriaDiscoveryForAppStoreCategoriesQueryBuilder());

        assertAppStoreCategoriesDiscoveries(discoveries.getList());
    }

    private void assertAppStoreCategoriesDiscoveries(List<TargetingCriteriaDiscoveryForAppStoreCategories> actual) {
        Assert.assertEquals(84, actual.size());

        Assert.assertEquals("Food & Drink", actual.get(13).getName());
        Assert.assertEquals("IOS", actual.get(13).getOsType());
        Assert.assertEquals("APP_STORE_CATEGORY", actual.get(13).getTargetingType());
        Assert.assertEquals("qouf", actual.get(13).getTargetingValue());

        Assert.assertEquals("Games: Card", actual.get(23).getName());
        Assert.assertEquals("ANDROID", actual.get(23).getOsType());
        Assert.assertEquals("APP_STORE_CATEGORY", actual.get(23).getTargetingType());
        Assert.assertEquals("qowi", actual.get(23).getTargetingValue());
    }

    @Test
    public void appStoreCategoriesWithParams() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/app_store_categories?store=GOOGLE_PLAY&q=music"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-app_store_categories-params"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForAppStoreCategories> discoveries =
                twitter.targetingCriteriaDiscoveryOperations().appStoreCategories(
                        new TargetingCriteriaDiscoveryForAppStoreCategoriesQueryBuilder()
                                .inAppStore(AppStore.GOOGLE_PLAY)
                                .withQuery("music"));

        assertAppStoreCategoriesDiscoveriesWithParams(discoveries.getList());
    }

    private void assertAppStoreCategoriesDiscoveriesWithParams(List<TargetingCriteriaDiscoveryForAppStoreCategories> actual) {
        Assert.assertEquals(2, actual.size());

        Assert.assertEquals("Music & Audio", actual.get(1).getName());
        Assert.assertEquals("ANDROID", actual.get(1).getOsType());
        Assert.assertEquals("APP_STORE_CATEGORY", actual.get(1).getTargetingType());
        Assert.assertEquals("qox2", actual.get(1).getTargetingValue());
    }

    @Test
    public void behaviorTaxonomies() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/behavior_taxonomies"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-behavior_taxonomies"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForBehaviorTaxonomies> discoveries =
                twitter.targetingCriteriaDiscoveryOperations().behaviorTaxonomies(
                        new TargetingCriteriaDiscoveryForBehaviorTaxonomiesQueryBuilder());

        assertBehaviorTaxonomiesDiscoveries(discoveries.getList());
    }

    private void assertBehaviorTaxonomiesDiscoveries(List<TargetingCriteriaDiscoveryForBehaviorTaxonomies> actual) {
        Assert.assertEquals(194, actual.size());

        Assert.assertEquals("CPG brands", actual.get(8).getName());
        Assert.assertEquals("2014-12-08T21:55:58Z", actual.get(8).getCreatedAt());
        Assert.assertEquals("9", actual.get(8).getId());
        Assert.assertEquals(null, actual.get(8).getParentId());
        Assert.assertEquals("2015-01-21T21:47:09Z", actual.get(8).getUpdatedAt());

        Assert.assertEquals("DMA", actual.get(49).getName());
        Assert.assertEquals("2014-12-08T21:55:58Z", actual.get(49).getCreatedAt());
        Assert.assertEquals("1e", actual.get(49).getId());
        Assert.assertEquals("1a", actual.get(49).getParentId());
        Assert.assertEquals("2015-01-21T21:47:13Z", actual.get(49).getUpdatedAt());
    }

    @Test
    public void behaviorTaxonomiesWithParams() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/behavior_taxonomies?behavior_taxonomy_ids=33&parent_behavior_taxonomy_ids=31"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-behavior_taxonomies-params"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForBehaviorTaxonomies> discoveries =
                twitter.targetingCriteriaDiscoveryOperations().behaviorTaxonomies(
                        new TargetingCriteriaDiscoveryForBehaviorTaxonomiesQueryBuilder()
                                .ofParentTaxonomyIds("31")
                                .ofTaxonomyIds("33"));

        assertBehaviorTaxonomiesDiscoveriesWithParams(discoveries.getList());
    }

    private void assertBehaviorTaxonomiesDiscoveriesWithParams(List<TargetingCriteriaDiscoveryForBehaviorTaxonomies> actual) {
        Assert.assertEquals(1, actual.size());

        Assert.assertEquals("Frequent flyers", actual.get(0).getName());
        Assert.assertEquals("2015-01-21T21:47:24Z", actual.get(0).getCreatedAt());
        Assert.assertEquals("33", actual.get(0).getId());
        Assert.assertEquals("31", actual.get(0).getParentId());
        Assert.assertEquals("2015-01-21T21:47:24Z", actual.get(0).getUpdatedAt());
    }

    @Test
    public void behaviors() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/behaviors"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-behaviors"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForBehaviors> discoveries = twitter.targetingCriteriaDiscoveryOperations().behaviors(
                new TargetingCriteriaDiscoveryForBehaviorsQueryBuilder());

        assertBehaviorsDiscoveries(discoveries.getList());
    }

    private void assertBehaviorsDiscoveries(List<TargetingCriteriaDiscoveryForBehaviors> actual) {
        Assert.assertEquals(200, actual.size());

        Assert.assertEquals("Pasta sauce", actual.get(4).getName());
        Assert.assertEquals("5331840", actual.get(4).getAudienceSize());
        Assert.assertEquals("w", actual.get(4).getBehaviorTaxonomyId());
        Assert.assertEquals("lfmj", actual.get(4).getId());
        Assert.assertEquals("Datalogix", actual.get(4).getPartnerSource());

        Assert.assertEquals("Deli condiments", actual.get(8).getName());
        Assert.assertEquals("964380", actual.get(8).getAudienceSize());
        Assert.assertEquals("y", actual.get(8).getBehaviorTaxonomyId());
        Assert.assertEquals("lfmn", actual.get(8).getId());
        Assert.assertEquals("Datalogix", actual.get(8).getPartnerSource());
    }

    @Test
    public void behaviorsWithParams() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/behaviors?behavior_ids=lfrz&sort_by=name-asc"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-behaviors-params"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForBehaviors> discoveries = twitter.targetingCriteriaDiscoveryOperations().behaviors(
                new TargetingCriteriaDiscoveryForBehaviorsQueryBuilder()
                        .ofBehaviors("lfrz")
                        .sortBy("name", SortDirection.asc));

        assertBehaviorsDiscoveriesWithParams(discoveries.getList());
    }

    private void assertBehaviorsDiscoveriesWithParams(List<TargetingCriteriaDiscoveryForBehaviors> actual) {
        Assert.assertEquals(2, actual.size());

        Assert.assertEquals("In market: new vehicle", actual.get(0).getName());
        Assert.assertEquals("4521480", actual.get(0).getAudienceSize());
        Assert.assertEquals("3", actual.get(0).getBehaviorTaxonomyId());
        Assert.assertEquals("lfs0", actual.get(0).getId());
        Assert.assertEquals("Datalogix", actual.get(0).getPartnerSource());
    }

    @Test
    public void devices() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/devices"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-devices"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForDevices> discoveries = twitter.targetingCriteriaDiscoveryOperations().devices(
                new TargetingCriteriaDiscoveryForDevicesQueryBuilder());

        assertDevicesDiscoveries(discoveries.getList());
    }

    private void assertDevicesDiscoveries(List<TargetingCriteriaDiscoveryForDevices> actual) {
        Assert.assertEquals(132, actual.size());

        Assert.assertEquals("HTC Butterfly", actual.get(12).getName());
        Assert.assertEquals("iPad 4", actual.get(35).getName());
    }

    @Test
    public void devicesWithParams() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/devices?q=apple"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-devices-params"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForDevices> discoveries = twitter.targetingCriteriaDiscoveryOperations().devices(
                new TargetingCriteriaDiscoveryForDevicesQueryBuilder()
                        .withQuery("apple"));

        assertDevicesDiscoveriesWithParams(discoveries.getList());
    }

    private void assertDevicesDiscoveriesWithParams(List<TargetingCriteriaDiscoveryForDevices> actual) {
        Assert.assertEquals(8, actual.size());

        Assert.assertEquals("iPhone 4", actual.get(1).getName());
        Assert.assertEquals("iPhone 4S", actual.get(2).getName());
    }

    @Test
    public void events() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/events"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-events"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForEvents> discoveries = twitter.targetingCriteriaDiscoveryOperations().events(
                new TargetingCriteriaDiscoveryForEventsQueryBuilder());

        assertEventsDiscoveries(discoveries.getList());
    }

    private void assertEventsDiscoveries(List<TargetingCriteriaDiscoveryForEvents> actual) {
//        Assert.assertEquals(132, actual.size());

//        Assert.assertEquals("HTC Butterfly", actual.get(12).getName());
//        Assert.assertEquals("iPad 4", actual.get(35).getName());
    }

    @Test
    public void eventsWithParams() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/events?event_types=MUSIC_AND_ENTERTAINMENT&country_codes=JP&start_time=2015-07-17T00%3A00%3A00Z&end_time=2015-07-25T00%3A00%3A00Z"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-events-params"), APPLICATION_JSON));
    	List<EventType> eventTypes = new ArrayList<EventType>();
    	eventTypes.add(EventType.MUSIC_AND_ENTERTAINMENT);
    	List<String> countryCodes = new ArrayList<String>();
        countryCodes.add("JP");
    	LocalDateTime startTime = LocalDateTime.parse("2015-07-17T00:00:00");
        LocalDateTime endTime = LocalDateTime.parse("2015-07-25T00:00:00");

        
        final DataListHolder<TargetingCriteriaDiscoveryForEvents> discoveries = twitter.targetingCriteriaDiscoveryOperations().events(
                new TargetingCriteriaDiscoveryForEventsQueryBuilder()
                .withEventTypes(eventTypes)
                .withCountryCodes(countryCodes)
                .withStartTime(startTime)
                .withEndTime(endTime));

        assertEventsDiscoveriesWithParams(discoveries.getList());
    }

    private void assertEventsDiscoveriesWithParams(List<TargetingCriteriaDiscoveryForEvents> actual) {
        Assert.assertEquals(2, actual.size());

        Assert.assertEquals(92, actual.get(0).getCountryBreakdownPercentage().size());
        Assert.assertEquals("0.02", actual.get(0).getCountryBreakdownPercentage().get("HK").toString());
        Assert.assertEquals(null, actual.get(0).getCountryCode());
        Assert.assertEquals("43.77", actual.get(0).getDeviceBreakdownPercentage().get("IOS").toString());
        Assert.assertEquals("2015-07-27T00:00:00Z", actual.get(0).getEndTime());
        Assert.assertEquals(EventType.MUSIC_AND_ENTERTAINMENT, actual.get(0).getEventType());
        Assert.assertEquals("47.17", actual.get(0).getGenderBreakdownPercentage().get("female").toString());
        Assert.assertEquals("1w", actual.get(0).getId());
        Assert.assertEquals(true, actual.get(0).isGlobal());
        Assert.assertEquals("Tomorrowland", actual.get(0).getName());
        Assert.assertEquals("122426138", actual.get(0).getReach().get("total_impressions").toString());
        Assert.assertEquals("2015-07-24T00:00:00Z", actual.get(0).getStartTime());
        Assert.assertEquals(0, actual.get(0).getTopHashTags().size());
        Assert.assertEquals(1000, actual.get(0).getTopTweets().size());
        Assert.assertEquals(100, actual.get(0).getTopUsers().size());
    }

    @Test
    public void interests() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/interests"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-interests"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForInterests> discoveries = twitter.targetingCriteriaDiscoveryOperations().interests(
                new TargetingCriteriaDiscoveryForInterestsQueryBuilder());

        assertInterestsDiscoveries(discoveries.getList());
    }

    private void assertInterestsDiscoveries(List<TargetingCriteriaDiscoveryForInterests> actual) {
        Assert.assertEquals(200, actual.size());

        Assert.assertEquals("Books and literature/Cookbooks, food, and wine", actual.get(3).getName());
        Assert.assertEquals("INTEREST", actual.get(3).getTargetingType());
        Assert.assertEquals("1004", actual.get(3).getTargetingValue());

        Assert.assertEquals("Gaming/Roleplaying games", actual.get(52).getName());
        Assert.assertEquals("INTEREST", actual.get(52).getTargetingType());
        Assert.assertEquals("4006", actual.get(52).getTargetingValue());
    }

    @Test
    public void interestsWithParams() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/interests?q=bird"))


                .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-interests-params"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForInterests> discoveries = twitter.targetingCriteriaDiscoveryOperations().interests(
                new TargetingCriteriaDiscoveryForInterestsQueryBuilder()
                        .withQuery("bird"));

        assertInterestsDiscoveriesWithParams(discoveries.getList());
    }

    private void assertInterestsDiscoveriesWithParams(List<TargetingCriteriaDiscoveryForInterests> actual) {
        Assert.assertEquals(2, actual.size());

        Assert.assertEquals("Hobbies and interests/Birdwatching", actual.get(0).getName());
        Assert.assertEquals("INTEREST", actual.get(0).getTargetingType());
        Assert.assertEquals("13002", actual.get(0).getTargetingValue());
    }

    @Test
    public void languages() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/languages"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-languages"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForLanguages> discoveries = twitter.targetingCriteriaDiscoveryOperations().languages(
                new TargetingCriteriaDiscoveryForLanguagesQueryBuilder());

        assertLanguagesDiscoveries(discoveries.getList());
    }

    private void assertLanguagesDiscoveries(List<TargetingCriteriaDiscoveryForLanguages> actual) {
        Assert.assertEquals(21, actual.size());

        Assert.assertEquals("Farsi", actual.get(4).getName());
        Assert.assertEquals("LANGUAGE", actual.get(4).getTargetingType());
        Assert.assertEquals("fa", actual.get(4).getTargetingValue());

        Assert.assertEquals("French", actual.get(6).getName());
        Assert.assertEquals("LANGUAGE", actual.get(6).getTargetingType());
        Assert.assertEquals("fr", actual.get(6).getTargetingValue());
    }

    @Test
    public void languagesWithParams() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/languages?q=norwegian"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-languages-params"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForLanguages> discoveries = twitter.targetingCriteriaDiscoveryOperations().languages(
                new TargetingCriteriaDiscoveryForLanguagesQueryBuilder()
                        .withQuery("norwegian"));

        assertLanguagesDiscoveriesWithParams(discoveries.getList());
    }

    private void assertLanguagesDiscoveriesWithParams(List<TargetingCriteriaDiscoveryForLanguages> actual) {
        Assert.assertEquals(1, actual.size());

        Assert.assertEquals("Norwegian", actual.get(0).getName());
        Assert.assertEquals("LANGUAGE", actual.get(0).getTargetingType());
        Assert.assertEquals("no", actual.get(0).getTargetingValue());
    }

    @Test
    public void locations() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/locations"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-locations"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForLocations> discoveries = twitter.targetingCriteriaDiscoveryOperations().locations(
                new TargetingCriteriaDiscoveryForLocationsQueryBuilder());

        assertLocationsDiscoveries(discoveries.getList());
    }

    private void assertLocationsDiscoveries(List<TargetingCriteriaDiscoveryForLocations> actual) {
        Assert.assertEquals(200, actual.size());

        Assert.assertEquals("00603, PR, US", actual.get(2).getName());
        Assert.assertEquals("POSTAL_CODE", actual.get(2).getLocationType());
        Assert.assertEquals("LOCATION", actual.get(2).getTargetingType());
        Assert.assertEquals("84e681a9fb76656f", actual.get(2).getTargetingValue());

        Assert.assertEquals("01012, BR", actual.get(145).getName());
        Assert.assertEquals("POSTAL_CODE", actual.get(145).getLocationType());
        Assert.assertEquals("LOCATION", actual.get(145).getTargetingType());
        Assert.assertEquals("2bb6204f6b9fcfb7", actual.get(145).getTargetingValue());
    }

    @Test
    public void locationsWithParams() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/locations?q=Palo%2BAlto"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-locations-params"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForLocations> discoveries = twitter.targetingCriteriaDiscoveryOperations().locations(
                new TargetingCriteriaDiscoveryForLocationsQueryBuilder()
                        .withQuery("Palo Alto"));

        assertLocationsDiscoveriesWithParams(discoveries.getList());
    }

    private void assertLocationsDiscoveriesWithParams(List<TargetingCriteriaDiscoveryForLocations> actual) {
        Assert.assertEquals(8, actual.size());

        Assert.assertEquals("94301, CA, USA", actual.get(6).getName());
        Assert.assertEquals("POSTAL_CODE", actual.get(6).getLocationType());
        Assert.assertEquals("LOCATION", actual.get(6).getTargetingType());
        Assert.assertEquals("205edf12aa812e1c", actual.get(6).getTargetingValue());
    }

    @Test
    public void networkOperators() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/network_operators"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-network_operators"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForNetworkOperators> discoveries =
                twitter.targetingCriteriaDiscoveryOperations().networkOperators(
                        new TargetingCriteriaDiscoveryForNetworkOperatorsQueryBuilder());

        assertNetworkOperatorsDiscoveries(discoveries.getList());
    }

    private void assertNetworkOperatorsDiscoveries(List<TargetingCriteriaDiscoveryForNetworkOperators> actual) {
        Assert.assertEquals(200, actual.size());

        Assert.assertEquals("BC Tel Mobility (Telus)", actual.get(61).getName());
        Assert.assertEquals("CA", actual.get(61).getCountryCode());
        Assert.assertEquals("NETWORK_OPERATOR", actual.get(61).getTargetingType());
        Assert.assertEquals("5k", actual.get(61).getTargetingValue());

        Assert.assertEquals("Cellular One of East Texas", actual.get(99).getName());
        Assert.assertEquals("US", actual.get(99).getCountryCode());
        Assert.assertEquals("NETWORK_OPERATOR", actual.get(99).getTargetingType());
        Assert.assertEquals("2k", actual.get(99).getTargetingValue());
    }

    @Test
    public void networkOperatorsWithParams() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/network_operators?country_code=US&q=Mobile"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-network_operators-params"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForNetworkOperators> discoveries =
                twitter.targetingCriteriaDiscoveryOperations().networkOperators(
                        new TargetingCriteriaDiscoveryForNetworkOperatorsQueryBuilder()
                                .withQuery("Mobile")
                                .withCountryCode("US"));

        assertNetworkOperatorsDiscoveriesWithParams(discoveries.getList());
    }

    private void assertNetworkOperatorsDiscoveriesWithParams(List<TargetingCriteriaDiscoveryForNetworkOperators> actual) {
        Assert.assertEquals(7, actual.size());

        Assert.assertEquals("SeaMobile", actual.get(3).getName());
        Assert.assertEquals("US", actual.get(3).getCountryCode());
        Assert.assertEquals("NETWORK_OPERATOR", actual.get(3).getTargetingType());
        Assert.assertEquals("1x", actual.get(3).getTargetingValue());
    }

    @Test
    public void platformVersions() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/platform_versions"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-platform_versions"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForPlatformVersions> discoveries =
                twitter.targetingCriteriaDiscoveryOperations().platformVersions(
                        new TargetingCriteriaDiscoveryForPlatformVersionsQueryBuilder());

        assertPlatformVersionsDiscoveries(discoveries.getList());
    }

    private void assertPlatformVersionsDiscoveries(List<TargetingCriteriaDiscoveryForPlatformVersions> actual) {
        Assert.assertEquals(22, actual.size());

        Assert.assertEquals("4.2", actual.get(2).getName());
        Assert.assertEquals("4.2", actual.get(2).getNumber());
        Assert.assertEquals("iOS", actual.get(2).getPlatform());
        Assert.assertEquals("PLATFORM_VERSION", actual.get(2).getTargetingType());
        Assert.assertEquals("3", actual.get(2).getTargetingValue());

        Assert.assertEquals("Honeycomb", actual.get(17).getName());
        Assert.assertEquals("3.0", actual.get(17).getNumber());
        Assert.assertEquals("Android", actual.get(17).getPlatform());
        Assert.assertEquals("PLATFORM_VERSION", actual.get(17).getTargetingType());
        Assert.assertEquals("g", actual.get(17).getTargetingValue());
    }

    @Test
    public void platformVersionsWithParams() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/platform_versions?q=cupcake"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-platform_versions-params"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForPlatformVersions> discoveries =
                twitter.targetingCriteriaDiscoveryOperations().platformVersions(
                        new TargetingCriteriaDiscoveryForPlatformVersionsQueryBuilder()
                                .withQuery("cupcake"));

        assertPlatformVersionsDiscoveriesWithParams(discoveries.getList());
    }

    private void assertPlatformVersionsDiscoveriesWithParams(List<TargetingCriteriaDiscoveryForPlatformVersions> actual) {
        Assert.assertEquals(1, actual.size());

        Assert.assertEquals("Cupcake", actual.get(0).getName());
        Assert.assertEquals("1.5", actual.get(0).getNumber());
        Assert.assertEquals("Android", actual.get(0).getPlatform());
        Assert.assertEquals("PLATFORM_VERSION", actual.get(0).getTargetingType());
        Assert.assertEquals("b", actual.get(0).getTargetingValue());
    }

    @Test
    public void platforms() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/platforms"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-platforms"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForPlatforms> discoveries = twitter.targetingCriteriaDiscoveryOperations().platforms(
                new TargetingCriteriaDiscoveryForPlatformsQueryBuilder());

        assertPlatformsDiscoveries(discoveries.getList());
    }

    private void assertPlatformsDiscoveries(List<TargetingCriteriaDiscoveryForPlatforms> actual) {
        Assert.assertEquals(5, actual.size());

        Assert.assertEquals("iOS", actual.get(0).getName());
        Assert.assertEquals("PLATFORM", actual.get(0).getTargetingType());
        Assert.assertEquals("0", actual.get(0).getTargetingValue());

        Assert.assertEquals("Desktop and laptop computers", actual.get(4).getName());
        Assert.assertEquals("PLATFORM", actual.get(4).getTargetingType());
        Assert.assertEquals("4", actual.get(4).getTargetingValue());
    }

    @Test
    public void platformsWithParams() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/platforms?q=blackberry"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-platforms-params"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForPlatforms> discoveries = twitter.targetingCriteriaDiscoveryOperations().platforms(
                new TargetingCriteriaDiscoveryForPlatformsQueryBuilder()
                        .withQuery("blackberry"));

        assertPlatformsDiscoveriesWithParams(discoveries.getList());
    }

    private void assertPlatformsDiscoveriesWithParams(List<TargetingCriteriaDiscoveryForPlatforms> actual) {
        Assert.assertEquals(1, actual.size());

        Assert.assertEquals("BlackBerry phones and tablets", actual.get(0).getName());
        Assert.assertEquals("PLATFORM", actual.get(0).getTargetingType());
        Assert.assertEquals("2", actual.get(0).getTargetingValue());
    }

    @Test
    public void tvShows() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/tv_shows?tv_market_locale=pt-BR"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-tv_shows"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForTvShow> discoveries = twitter.targetingCriteriaDiscoveryOperations().tvShow(
                new TargetingCriteriaDiscoveryForTvShowQueryBuilder()
                .withLocale("pt-BR"));

        assertTvShowsDiscoveries(discoveries.getList());
    }

    private void assertTvShowsDiscoveries(List<TargetingCriteriaDiscoveryForTvShow> actual) {
        Assert.assertEquals(50, actual.size());

        Assert.assertEquals(new Long("10032876335"), actual.get(0).getId());
        Assert.assertEquals(new Long("1000"), actual.get(0).getEstimatedUsers());
        Assert.assertEquals("Debate 2014 - Presidente", actual.get(0).getName());
        Assert.assertEquals("SPECIAL", actual.get(0).getGenre());

        Assert.assertEquals(new Long("10032994279"), actual.get(1).getId());
        Assert.assertEquals(new Long("1000"), actual.get(1).getEstimatedUsers());
        Assert.assertEquals("2014 FIBA World Cup", actual.get(1).getName());
        Assert.assertEquals("SPORTS", actual.get(1).getGenre());
    }

    @Test
    public void tvShowsWithQuery() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/tv_shows?tv_market_locale=pt-BR&q=Glee"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-tv_shows-params"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForTvShow> discoveries = twitter.targetingCriteriaDiscoveryOperations().tvShow(
                new TargetingCriteriaDiscoveryForTvShowQueryBuilder()
                .withLocale("pt-BR")
                .withQuery("Glee"));

        assertTvShowsDiscoveriesWithSearch(discoveries.getList());
    }

    private void assertTvShowsDiscoveriesWithSearch(List<TargetingCriteriaDiscoveryForTvShow> actual) {
        Assert.assertEquals(1, actual.size());

        Assert.assertEquals(new Long("10028587441"), actual.get(0).getId());
        Assert.assertEquals(new Long("99754"), actual.get(0).getEstimatedUsers());
        Assert.assertEquals("Glee", actual.get(0).getName());
        Assert.assertEquals("DRAMA", actual.get(0).getGenre());
    }

    @Test
    public void tvMarkets() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/tv_markets"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-tv_markets"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForTvMarket> discoveries = twitter.targetingCriteriaDiscoveryOperations().tvMarkets(
                new TargetingCriteriaDiscoveryForTvMarketQueryBuilder());

        assertTvMarketsDiscoveries(discoveries.getList());
    }

    private void assertTvMarketsDiscoveries(List<TargetingCriteriaDiscoveryForTvMarket> actual) {
        Assert.assertEquals(17, actual.size());

        Assert.assertEquals("6", actual.get(0).getId());
        Assert.assertEquals("France", actual.get(0).getName());
        Assert.assertEquals("FR", actual.get(0).getCountryCode());
        Assert.assertEquals("fr-FR", actual.get(0).getLocale());

        Assert.assertEquals("i", actual.get(1).getId());
        Assert.assertEquals("Chile", actual.get(1).getName());
        Assert.assertEquals("CL", actual.get(1).getCountryCode());
        Assert.assertEquals("es-CL", actual.get(1).getLocale());
    }

    @Test
    public void tvGenres() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/tv_genres"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-tv_genres"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForTvGenre> discoveries = twitter.targetingCriteriaDiscoveryOperations().tvGenres(
                new TargetingCriteriaDiscoveryForTvGenreQueryBuilder());

        assertTvGenresDiscoveries(discoveries.getList());
    }

    private void assertTvGenresDiscoveries(List<TargetingCriteriaDiscoveryForTvGenre> actual) {
        Assert.assertEquals(20, actual.size());

        Assert.assertEquals(new Long("2"), actual.get(0).getId());
        Assert.assertEquals("COMEDY", actual.get(0).getName());

        Assert.assertEquals(new Long("11"), actual.get(1).getId());
        Assert.assertEquals("SPORTS", actual.get(1).getName());
    }

    @Test
    public void tvChannels() {
        mockServer
        .expect(requestTo("https://ads-api.twitter.com/0/targeting_criteria/tv_channels"))
        .andExpect(method(GET))
        .andRespond(withSuccess(jsonResource("ad-targetings-tv_channels"), APPLICATION_JSON));

        final DataListHolder<TargetingCriteriaDiscoveryForTvChannel> discoveries = twitter.targetingCriteriaDiscoveryOperations().tvChannels(
                new TargetingCriteriaDiscoveryForTvChannelQueryBuilder());

        assertTvChannelsDiscoveries(discoveries.getList());
    }

    private void assertTvChannelsDiscoveries(List<TargetingCriteriaDiscoveryForTvChannel> actual) {
        Assert.assertEquals(200, actual.size());

        Assert.assertEquals(new Long("1003"), actual.get(0).getId());
        Assert.assertEquals("ESPN", actual.get(0).getName());

        Assert.assertEquals(new Long("1004"), actual.get(1).getId());
        Assert.assertEquals("Cartoon Network", actual.get(1).getName());
    }


}
