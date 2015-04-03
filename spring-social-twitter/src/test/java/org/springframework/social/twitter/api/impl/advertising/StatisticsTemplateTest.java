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

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.social.twitter.api.advertising.StatisticsGranularity;
import org.springframework.social.twitter.api.advertising.StatisticsMetric;
import org.springframework.social.twitter.api.advertising.StatisticsSegmentationType;
import org.springframework.social.twitter.api.advertising.StatisticsSnapshot;
import org.springframework.social.twitter.api.advertising.StatisticsSnapshotMetric;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;
import org.springframework.test.web.client.MockRestServiceServer;

/**
 * @author Hudson mendes
 */
public class StatisticsTemplateTest extends AbstractTwitterApiTest {

    @Test
    public void segmentedAndUnsegmented() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";

        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId + "?granularity=DAY"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("ad-stats-non-segmented"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshots1 = twitter.statisticsOperations().byAccounts(
                mockedAccountId,
                new StatisticsOfAccountQueryBuilder()
                        .withGranularity(StatisticsGranularity.DAY));

        MockRestServiceServer mockServer2 = MockRestServiceServer.createServer(twitter.getRestTemplate());
        mockServer2
                .expect(requestTo("https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId + "?segmentation_type=GENDER&granularity=DAY"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("ad-stats-segmented"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshots2 = twitter.statisticsOperations().byAccounts(
                mockedAccountId,
                new StatisticsOfAccountQueryBuilder()
                        .withGranularity(StatisticsGranularity.DAY)
                        .withSegmentationType(StatisticsSegmentationType.GENDER));

        Assert.assertNotEquals(snapshots1.size(), snapshots2.size());
        Assert.assertEquals(1, snapshots1.size());

        Assert.assertNotNull(snapshots2.get(0).getSegmentation());
        Assert.assertEquals(StatisticsSegmentationType.GENDER, snapshots2.get(0).getSegmentation().getType());
        Assert.assertEquals("f", snapshots2.get(0).getSegmentation().getValue());
        Assert.assertEquals("Female", snapshots2.get(0).getSegmentation().getDescription());

        Assert.assertNotNull(snapshots2.get(1).getSegmentation());
        Assert.assertEquals(StatisticsSegmentationType.GENDER, snapshots2.get(1).getSegmentation().getType());
        Assert.assertEquals("m", snapshots2.get(1).getSegmentation().getValue());
        Assert.assertEquals("Male", snapshots2.get(1).getSegmentation().getDescription());

        Assert.assertNotNull(snapshots2.get(2).getSegmentation());
        Assert.assertEquals(StatisticsSegmentationType.GENDER, snapshots2.get(2).getSegmentation().getType());
        Assert.assertEquals("unknown", snapshots2.get(2).getSegmentation().getValue());
        Assert.assertEquals("Unknown", snapshots2.get(2).getSegmentation().getDescription());

        List<StatisticsMetric> metrics1 = extractMetricsFromStatisticSnapshots(snapshots1);
        List<StatisticsMetric> metrics2 = extractMetricsFromStatisticSnapshots(snapshots1);
        Assert.assertEquals(metrics1.size(), metrics2.size());
    }

    private List<StatisticsMetric> extractMetricsFromStatisticSnapshots(List<StatisticsSnapshot> snapshots) {
        List<StatisticsMetric> metrics = new ArrayList<>();
        for (StatisticsSnapshot snapshot : snapshots)
            for (StatisticsSnapshotMetric snapshotMetric : snapshot.getMetrics())
                metrics.add(snapshotMetric.getName());
        return metrics;
    }

    @Test
    public void byAccounts() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId +
                                "?granularity=DAY" +
                                "&start_time=" + URLEncoder.encode("2015-05-01T07:00:00Z", UTF8) +
                                "&end_time=" + URLEncoder.encode("2015-05-04T07:00:00Z", UTF8)))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("statistics-snapshot"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshots = twitter.statisticsOperations().byAccounts(
                mockedAccountId,
                new StatisticsOfAccountQueryBuilder()
                        .withGranularity(StatisticsGranularity.DAY)
                        .activeBetween(LocalDateTime.of(2015, Month.MAY, 01, 07, 00, 00), LocalDateTime.of(2015, Month.MAY, 4, 07, 00, 00)));

        assertSnapshotContents(snapshots);
    }

    @Test
    public void byCampaigns() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        String mockedCampaignId1 = "92ph";
        String mockedCampaignId2 = "x902";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId + "/campaigns" +
                                "?campaign_ids=" + URLEncoder.encode(mockedCampaignId1 + "," + mockedCampaignId2, UTF8) +
                                "&granularity=HOUR" +
                                "&metrics=billed_follows" +
                                "&start_time=" + URLEncoder.encode("2015-03-06T07:00:00Z", UTF8) +
                                "&end_time=" + URLEncoder.encode("2015-03-13T07:00:00Z", UTF8)))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("statistics-snapshot"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshots = twitter.statisticsOperations().byCampaigns(
                mockedAccountId,
                new StatisticsOfCampaignQueryBuilder()
                        .withCampaigns(mockedCampaignId1, mockedCampaignId2)
                        .withGranularity(StatisticsGranularity.HOUR)
                        .withStatisticalMetric(StatisticsMetric.billed_follows)
                        .activeBetween(LocalDateTime.of(2015, Month.MARCH, 06, 07, 00, 00), LocalDateTime.of(2015, Month.MARCH, 13, 07, 00, 00)));

        assertSnapshotContents(snapshots);
    }

    @Test
    public void byCampaign() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        String mockedCampaignId = "92ph";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/stats/accounts/"
                                + mockedAccountId
                                + "/campaigns/"
                                + mockedCampaignId
                                + "?granularity=HOUR"
                                + "&metrics="
                                + URLEncoder.encode("billed_follows,promoted_account_follow_rate,billed_charge_local_micro,mobile_conversion_rated",
                                        UTF8) +
                                "&start_time=" + URLEncoder.encode("2015-03-06T07:00:00Z", UTF8) +
                                "&end_time=" + URLEncoder.encode("2015-03-13T07:00:00Z", UTF8)))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("statistics-snapshot"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshot = twitter.statisticsOperations().byCampaign(
                mockedAccountId,
                mockedCampaignId,
                new StatisticsOfCampaignQueryBuilder()
                        .withGranularity(StatisticsGranularity.HOUR)
                        .withStatisticalMetric(
                                StatisticsMetric.billed_follows,
                                StatisticsMetric.promoted_account_follow_rate,
                                StatisticsMetric.billed_charge_local_micro,
                                StatisticsMetric.mobile_conversion_rated)
                        .activeBetween(LocalDateTime.of(2015, Month.MARCH, 06, 07, 00, 00), LocalDateTime.of(2015, Month.MARCH, 13, 07, 00, 00)));

        assertSnapshotSingleContents(snapshot);
    }

    @Test
    public void byFundingInstruments() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        String mockedFundingInstrument1 = "92ph";
        String mockedFundingInstrument2 = "x902";

        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId + "/funding_instruments" +
                                "?funding_instrument_ids=" + URLEncoder.encode(mockedFundingInstrument1 + "," + mockedFundingInstrument2, UTF8) +
                                "&granularity=HOUR" +
                                "&metrics=" + URLEncoder.encode("billed_follows", UTF8) +
                                "&start_time=" + URLEncoder.encode("2015-03-06T07:00:00Z", UTF8) +
                                "&end_time=" + URLEncoder.encode("2015-03-13T07:00:00Z", UTF8)))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("statistics-snapshot"), APPLICATION_JSON));

        List<StatisticsSnapshot> campaigns = twitter.statisticsOperations().byFundingInstruments(
                mockedAccountId,
                new StatisticsOfFundingInstrumentQueryBuilder()
                        .withFundingInstruments(mockedFundingInstrument1, mockedFundingInstrument2)
                        .withGranularity(StatisticsGranularity.HOUR)
                        .withStatisticalMetric(StatisticsMetric.billed_follows)
                        .activeBetween(LocalDateTime.of(2015, Month.MARCH, 06, 07, 00, 00), LocalDateTime.of(2015, Month.MARCH, 13, 07, 00, 00)));

        assertSnapshotContents(campaigns);
    }

    @Test
    public void byFundingInstrument() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        String mockedFundingInstrumentId = "92ph";

        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId + "/funding_instruments" +
                                "/" + mockedFundingInstrumentId +
                                "?granularity=HOUR" +
                                "&metrics=" + URLEncoder.encode("billed_follows", UTF8) +
                                "&start_time=" + URLEncoder.encode("2015-03-06T07:00:00Z", UTF8) +
                                "&end_time=" + URLEncoder.encode("2015-03-13T07:00:00Z", UTF8)))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("statistics-snapshot"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshot = twitter.statisticsOperations().byFundingInstrument(
                mockedAccountId,
                mockedFundingInstrumentId,
                new StatisticsOfFundingInstrumentQueryBuilder()
                        .withGranularity(StatisticsGranularity.HOUR)
                        .withStatisticalMetric(StatisticsMetric.billed_follows)
                        .activeBetween(LocalDateTime.of(2015, Month.MARCH, 06, 07, 00, 00), LocalDateTime.of(2015, Month.MARCH, 13, 07, 00, 00)));

        assertSnapshotSingleContents(snapshot);
    }

    @Test
    public void byLineItems() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        String mockedLineItemId1 = "92ph";
        String mockedLineItemId2 = "x902";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId + "/line_items" +
                                "?line_item_ids=" + URLEncoder.encode(mockedLineItemId1 + "," + mockedLineItemId2, UTF8) +
                                "&granularity=DAY" +
                                "&start_time=" + URLEncoder.encode("2015-03-06T07:00:00Z", UTF8) +
                                "&end_time=" + URLEncoder.encode("2015-03-13T07:00:00Z", UTF8)))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("statistics-snapshot"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshots = twitter.statisticsOperations().byLineItems(
                mockedAccountId,
                new StatisticsOfLineItemQueryBuilder()
                        .withLineItems(mockedLineItemId1, mockedLineItemId2)
                        .withGranularity(StatisticsGranularity.DAY)
                        .activeFrom(LocalDateTime.of(2015, Month.MARCH, 06, 07, 00, 00))
                        .activeUntil(LocalDateTime.of(2015, Month.MARCH, 13, 07, 00, 00)));

        assertSnapshotContents(snapshots);
    }

    @Test
    public void byLineItem() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        String mockedLineItemId = "92ph";

        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId + "/line_items" +
                                "/" + mockedLineItemId +
                                "?granularity=HOUR" +
                                "&metrics=" + URLEncoder.encode("billed_follows", UTF8) +
                                "&start_time=" + URLEncoder.encode("2015-03-06T07:00:00Z", UTF8) +
                                "&end_time=" + URLEncoder.encode("2015-03-13T07:00:00Z", UTF8)))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("statistics-snapshot"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshot = twitter.statisticsOperations().byLineItem(
                mockedAccountId,
                mockedLineItemId,
                new StatisticsOfLineItemQueryBuilder()
                        .withGranularity(StatisticsGranularity.HOUR)
                        .withStatisticalMetric(StatisticsMetric.billed_follows)
                        .activeBetween(LocalDateTime.of(2015, Month.MARCH, 06, 07, 00, 00), LocalDateTime.of(2015, Month.MARCH, 13, 07, 00, 00)));

        assertSnapshotSingleContents(snapshot);
    }

    @Test
    public void byPromotedAccounts() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        String mockedPromotedAccountId1 = "92ph";
        String mockedPromotedAccountId2 = "x902";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId + "/promoted_accounts" +
                                "?promoted_account_ids=" + URLEncoder.encode(mockedPromotedAccountId1 + "," + mockedPromotedAccountId2, UTF8) +
                                "&granularity=HOUR" +
                                "&metrics=" + URLEncoder.encode("billed_follows", UTF8) +
                                "&start_time=" + URLEncoder.encode("2015-03-06T07:00:00Z", UTF8) +
                                "&end_time=" + URLEncoder.encode("2015-03-13T07:00:00Z", UTF8)))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("statistics-snapshot"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshots = twitter.statisticsOperations().byPromotedAccounts(
                mockedAccountId,
                new StatisticsOfPromotedAccountQueryBuilder()
                        .withPromotedAccounts(mockedPromotedAccountId1, mockedPromotedAccountId2)
                        .withGranularity(StatisticsGranularity.HOUR)
                        .withStatisticalMetric(StatisticsMetric.billed_follows)
                        .activeFrom(LocalDateTime.of(2015, Month.MARCH, 06, 07, 00, 00))
                        .activeUntil(LocalDateTime.of(2015, Month.MARCH, 13, 07, 00, 00)));

        assertSnapshotContents(snapshots);
    }

    @Test
    public void byPromotedAccount() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        String mockedPromotedAccountId = "92ph";

        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId + "/promoted_accounts" +
                                "/" + mockedPromotedAccountId +
                                "?granularity=HOUR" +
                                "&metrics=" + URLEncoder.encode("billed_follows", UTF8) +
                                "&start_time=" + URLEncoder.encode("2015-03-06T07:00:00Z", UTF8) +
                                "&end_time=" + URLEncoder.encode("2015-03-13T07:00:00Z", UTF8)))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("statistics-snapshot"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshot = twitter.statisticsOperations().byPromotedAccount(
                mockedAccountId,
                mockedPromotedAccountId,
                new StatisticsOfPromotedAccountQueryBuilder()
                        .withGranularity(StatisticsGranularity.HOUR)
                        .withStatisticalMetric(StatisticsMetric.billed_follows)
                        .activeBetween(LocalDateTime.of(2015, Month.MARCH, 06, 07, 00, 00), LocalDateTime.of(2015, Month.MARCH, 13, 07, 00, 00)));

        assertSnapshotSingleContents(snapshot);
    }

    @Test
    public void byPromotedTweets() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        String mockedPromotedTweetId1 = "92ph";
        String mockedPromotedTweetId2 = "x902";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId + "/promoted_tweets" +
                                "?promoted_tweet_ids=" + URLEncoder.encode(mockedPromotedTweetId1 + "," + mockedPromotedTweetId2, UTF8) +
                                "&granularity=HOUR" +
                                "&metrics=" + URLEncoder.encode("billed_follows", UTF8) +
                                "&start_time=" + URLEncoder.encode("2015-03-06T07:00:00Z", UTF8) +
                                "&end_time=" + URLEncoder.encode("2015-03-13T07:00:00Z", UTF8)))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("statistics-snapshot"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshots = twitter.statisticsOperations().byPromotedTweets(
                mockedAccountId,
                new StatisticsOfPromotedTweetQueryBuilder()
                        .withPromotedTweets(mockedPromotedTweetId1, mockedPromotedTweetId2)
                        .withGranularity(StatisticsGranularity.HOUR)
                        .withStatisticalMetric(StatisticsMetric.billed_follows)
                        .activeFrom(LocalDateTime.of(2015, Month.MARCH, 06, 07, 00, 00))
                        .activeUntil(LocalDateTime.of(2015, Month.MARCH, 13, 07, 00, 00)));

        assertSnapshotContents(snapshots);
    }

    @Test
    public void byPromotedTweet() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        String mockedPromotedTweetId = "92ph";

        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/stats/accounts/" + mockedAccountId + "/promoted_tweets" +
                                "/" + mockedPromotedTweetId +
                                "?granularity=HOUR" +
                                "&metrics=" + URLEncoder.encode("billed_follows", UTF8) +
                                "&start_time=" + URLEncoder.encode("2015-03-06T07:00:00Z", UTF8) +
                                "&end_time=" + URLEncoder.encode("2015-03-13T07:00:00Z", UTF8)))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("statistics-snapshot"), APPLICATION_JSON));

        List<StatisticsSnapshot> snapshot = twitter.statisticsOperations().byPromotedTweet(
                mockedAccountId,
                mockedPromotedTweetId,
                new StatisticsOfPromotedTweetQueryBuilder()
                        .withGranularity(StatisticsGranularity.HOUR)
                        .withStatisticalMetric(StatisticsMetric.billed_follows)
                        .activeBetween(LocalDateTime.of(2015, Month.MARCH, 06, 07, 00, 00), LocalDateTime.of(2015, Month.MARCH, 13, 07, 00, 00)));

        assertSnapshotSingleContents(snapshot);
    }

    private void assertSnapshotContents(List<StatisticsSnapshot> snapshots) {

        StatisticsSnapshot snapshot = snapshots.get(0);

        assertEquals("8484d", snapshot.getId());
        assertEquals(StatisticsGranularity.DAY, snapshot.getGranularity());
        assertEquals("2015-05-01T07:00", snapshot.getStartTime().toString());
        assertEquals("2015-05-04T07:00", snapshot.getEndTime().toString());
        
        List<StatisticsMetric> metrics = new ArrayList<>();
        for (StatisticsSnapshotMetric metric : snapshot.getMetrics())
        	metrics.add(metric.getName());

        assertThat(metrics, hasItem(StatisticsMetric.billed_engagements));
        assertThat(metrics, hasItem(StatisticsMetric.billed_follows));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_account_follow_rate));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_account_follows));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_account_impressions));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_account_profile_visits));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_app_install_attempts));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_app_open_attempts));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_search_clicks));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_search_engagement_rate));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_search_engagements));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_search_favorites));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_search_follows));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_search_impressions));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_search_replies));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_search_retweets));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_search_url_clicks));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_timeline_clicks));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_timeline_engagement_rate));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_timeline_engagements));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_timeline_favorites));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_timeline_follows));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_timeline_impressions));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_timeline_replies));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_timeline_retweets));
        assertThat(metrics, hasItem(StatisticsMetric.promoted_tweet_timeline_url_clicks));

    }

    private void assertSnapshotSingleContents(List<StatisticsSnapshot> snapshots) {
        StatisticsSnapshot snapshot = snapshots.get(0);

        assertEquals("8484d", snapshot.getId());

        assertNotNull(snapshot.getMetric(StatisticsMetric.billed_follows));
        assertThat(
                snapshot.getMetric(StatisticsMetric.billed_follows).entries(),
                hasItems(new Object[] {0L}));
    }
}
