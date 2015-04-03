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
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.Test;
import org.springframework.social.twitter.api.advertising.Campaign;
import org.springframework.social.twitter.api.advertising.ReasonNotServable;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;
import org.springframework.social.twitter.api.impl.DataListHolder;

/**
 * @author Hudson mendes
 */
public class CampaignTemplateTest extends AbstractTwitterApiTest {

    @Test
    public void getCampaign() {
        String mockedAccountId = "0ga0yn";
        String mockedCampaignId = "92ph";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/campaigns" +
                                "/" + mockedCampaignId +
                                "?with_deleted=true"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("ad-campaigns-single"), APPLICATION_JSON));

        Campaign campaign = twitter.campaignOperations().getCampaign(mockedAccountId, mockedCampaignId);
        assertSingleCampaignContents(campaign);
    }

    @Test
    public void getCampaigns() throws UnsupportedEncodingException {
        String mockedAccountId = "0ga0yn";
        String mockedCampaignId1 = "x945j";
        String mockedCampaignId2 = "1jvrj";
        String mockedFundingInstrumentId1 = "jrtjh4";
        String mockedFundingInstrumentId2 = "ekffd";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/campaigns" +
                                "?campaign_ids=" + URLEncoder.encode(mockedCampaignId1 + "," + mockedCampaignId2, UTF8) +
                                "&funding_instrument_ids=" + URLEncoder.encode(mockedFundingInstrumentId1 + "," + mockedFundingInstrumentId2, UTF8) +
                                "&with_deleted=false"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("ad-campaigns"), APPLICATION_JSON));

        DataListHolder<Campaign> campaigns = twitter.campaignOperations().getCampaigns(
                mockedAccountId,
                new CampaignQueryBuilder()
                        .withCampaigns(mockedCampaignId1, mockedCampaignId2)
                        .withFundingInstruments(mockedFundingInstrumentId1, mockedFundingInstrumentId2)
                        .includeDeleted(false));

        assertCampaignContents(campaigns.getList());
    }

    @Test
    public void createCampaign() throws UnsupportedEncodingException {
        String doesntMatterString = "doesn-matter";
        BigDecimal doesntMatterDecimal = new BigDecimal(1.00);
        LocalDateTime doesntMatterDate = LocalDateTime.now();
        Boolean doesntMatterBool = false;
        String mockedAccountId = "1ga1yn";

        String chainedPostContent =
                "name="
                        + doesntMatterString
                        + "&"
                        +
                        "currency="
                        + doesntMatterString
                        + "&"
                        +
                        "funding_instrument_id="
                        + doesntMatterString
                        + "&"
                        +
                        "total_budget_amount_local_micro="
                        + doesntMatterDecimal.multiply(new BigDecimal(1000000L))
                        + "&"
                        +
                        "daily_budget_amount_local_micro="
                        + doesntMatterDecimal.add(new BigDecimal(15)).multiply(new BigDecimal(1000000L))
                        + "&"
                        +
                        "start_time="
                        + URLEncoder.encode(doesntMatterDate.toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS).toString(), "UTF-8")
                        + "&"
                        +
                        "end_time="
                        + URLEncoder.encode(doesntMatterDate.plusDays(1).toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS).toString(),
                                "UTF-8") + "&" +
                        "standard_delivery=" + doesntMatterBool + "&" +
                        "paused=" + !doesntMatterBool + "&" +
                        "deleted=" + !doesntMatterBool;

        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/campaigns"))
                .andExpect(method(POST))
                .andExpect(content().string(chainedPostContent))
                .andRespond(withSuccess(jsonResource("ad-campaigns-single"), APPLICATION_JSON));

        Campaign campaign = twitter.campaignOperations().createCampaign(
                mockedAccountId,
                new CampaignFormBuilder()
                        .withName(doesntMatterString)
                        .withCurrency(doesntMatterString)
                        .withFundingInstrument(doesntMatterString)
                        .withBudget(doesntMatterDecimal.toString(), doesntMatterDecimal.add(new BigDecimal(15)).toString())
                        .activeBetween(doesntMatterDate, doesntMatterDate.plusDays(1))
                        .withStandardDelivery(doesntMatterBool)
                        .paused()
                        .deleted());

        assertSingleCampaignContents(campaign);
    }

    @Test
    public void updateCampaign() throws UnsupportedEncodingException {
        String doesntMatterString = "doesn-matter-altered";
        BigDecimal doesntMatterDecimal = new BigDecimal(2.00);
        LocalDateTime doesntMatterDate = LocalDateTime.now();
        Boolean doesntMatterBool = false;
        String mockedCampaignId = "92ph";
        String mockedAccountId = "1ga1yn";

        String chainedPostContent =
                "name=" + doesntMatterString + "&" +
                        "currency=" + doesntMatterString + "&" +
                        "funding_instrument_id=" + doesntMatterString + "&" +
                        "total_budget_amount_local_micro=" + doesntMatterDecimal.multiply(new BigDecimal(1000000L)) + "&" +
                        "daily_budget_amount_local_micro=" + doesntMatterDecimal.add(new BigDecimal(3)).multiply(new BigDecimal(1000000L)) + "&" +
                        "start_time="
                        + URLEncoder.encode(doesntMatterDate.toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS).toString(), "UTF-8")
                        + "&"
                        +
                        "end_time="
                        + URLEncoder.encode(doesntMatterDate.plusDays(3).toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS).toString(),
                                "UTF-8") + "&" +
                        "standard_delivery=" + !doesntMatterBool + "&" +
                        "paused=" + doesntMatterBool + "&" +
                        "deleted=" + doesntMatterBool;

        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/campaigns/" + mockedCampaignId))
                .andExpect(method(PUT))
                .andExpect(content().string(chainedPostContent))
                .andRespond(withSuccess());

        twitter.campaignOperations().updateCampaign(
                mockedAccountId,
                mockedCampaignId,
                new CampaignFormBuilder()
                        .withName(doesntMatterString)
                        .withCurrency(doesntMatterString)
                        .withFundingInstrument(doesntMatterString)
                        .withBudget(doesntMatterDecimal.toString(), doesntMatterDecimal.add(new BigDecimal(3)).toString())
                        .activeBetween(doesntMatterDate, doesntMatterDate.plusDays(3))
                        .withStandardDelivery(!doesntMatterBool)
                        .unpaused()
                        .active());
    }

    @Test
    public void deleteCampaign() {
        String mockedAccountId = "0ga0yn";
        String mockedCampaignId = "92ph";
        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/campaigns/" + mockedCampaignId))
                .andExpect(method(DELETE))
                .andRespond(withSuccess());

        twitter.campaignOperations().deleteCampaign(mockedAccountId, mockedCampaignId);
    }

    private void assertCampaignContents(List<Campaign> campaigns) {
        assertEquals(1, campaigns.size());

        assertEquals("1850jm", campaigns.get(0).getId());
        assertEquals("C1-oldlalala-generic", campaigns.get(0).getName());
        assertEquals("0ga0yn", campaigns.get(0).getAccountId());
        assertEquals("USD", campaigns.get(0).getCurrency());

        assertEquals(new BigDecimal(45.00), campaigns.get(0).getTotalBudget());
        assertEquals(new BigDecimal(10.00), campaigns.get(0).getDailyBudget());

        assertEquals(LocalDateTime.of(2014, Month.MAY, 15, 00, 12, 00), campaigns.get(0).getStartTime());
        assertEquals(LocalDateTime.of(2014, Month.MAY, 16, 22, 00, 00), campaigns.get(0).getEndTime());
        assertEquals(LocalDateTime.of(2014, Month.MAY, 15, 01, 17, 47), campaigns.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2014, Month.MAY, 16, 20, 41, 38), campaigns.get(0).getUpdatedAt());

        assertThat(campaigns.get(0).getReasonsNotServable(), not(hasItem(ReasonNotServable.DELETED)));
        assertThat(campaigns.get(0).getReasonsNotServable(), hasItem(ReasonNotServable.EXPIRED));
        assertThat(campaigns.get(0).getReasonsNotServable(), hasItem(ReasonNotServable.PAUSED_BY_ADVERTISER));

        assertEquals(true, campaigns.get(0).isStandardDelivery());
        assertEquals(false, campaigns.get(0).isServable());
        assertEquals(true, campaigns.get(0).isPaused());
        assertEquals(false, campaigns.get(0).isDeleted());
    }

    private void assertSingleCampaignContents(Campaign campaign) {
        assertEquals("1ga1yn", campaign.getAccountId());
        assertEquals("92ph", campaign.getId());
        assertEquals("My First Campaign", campaign.getName());
        assertEquals("USD", campaign.getCurrency());
        assertEquals("yyyy", campaign.getFundingInstrumentId());

        assertEquals(new BigDecimal(500.00), campaign.getTotalBudget());
        assertEquals(new BigDecimal(40.00), campaign.getDailyBudget());

        assertEquals(LocalDateTime.of(2015, Month.FEBRUARY, 9, 00, 00, 00), campaign.getStartTime());
        assertEquals(null, campaign.getEndTime());

        assertThat(campaign.getReasonsNotServable(), empty());
        assertEquals(true, campaign.isStandardDelivery());
        assertEquals(false, campaign.isPaused());
        assertEquals(true, campaign.isServable());
        assertEquals(false, campaign.isDeleted());
    }

}
