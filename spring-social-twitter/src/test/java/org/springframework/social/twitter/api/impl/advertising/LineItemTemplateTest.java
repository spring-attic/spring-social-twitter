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
import java.math.MathContext;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.social.twitter.api.advertising.AdvertisingObjective;
import org.springframework.social.twitter.api.advertising.AdvertisingPlacement;
import org.springframework.social.twitter.api.advertising.AdvertisingProductType;
import org.springframework.social.twitter.api.advertising.AdvertisingSentiment;
import org.springframework.social.twitter.api.advertising.BidUnit;
import org.springframework.social.twitter.api.advertising.LineItem;
import org.springframework.social.twitter.api.advertising.LineItemOptimization;
import org.springframework.social.twitter.api.advertising.LineItemPlacements;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;
import org.springframework.social.twitter.api.impl.DataListHolder;

/**
 * @author Hudson mendes
 */
public class LineItemTemplateTest extends AbstractTwitterApiTest {

    @Test
    public void getLineItem() {
        String mockedAccountId = "hkk5";
        String mockedLineItem = "5woz";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/line_items" +
                                "/" + mockedLineItem +
                                "?with_deleted=true"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("line-items-single"), APPLICATION_JSON));

        LineItem lineItem = twitter.lineItemOperations().getLineItem(mockedAccountId, mockedLineItem);
        assertSingleLineItemContents(lineItem);
    }

    @Test
    public void getLineItems() throws UnsupportedEncodingException {
        String mockedAccountId = "hkk5";
        String mockedCampaignId1 = "x945j";
        String mockedCampaignId2 = "1jvrj";
        String mockedFundingInstrumentId1 = "jrtjh4";
        String mockedFundingInstrumentId2 = "ekffd";
        String mockedLineItemId1 = "z9j";
        String mockedLineItemId2 = "045k";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/line_items" +
                                "?campaign_ids=" + URLEncoder.encode(mockedCampaignId1 + "," + mockedCampaignId2, UTF8) +
                                "&funding_instrument_ids=" + URLEncoder.encode(mockedFundingInstrumentId1 + "," + mockedFundingInstrumentId2, UTF8) +
                                "&line_item_ids=" + URLEncoder.encode(mockedLineItemId1 + "," + mockedLineItemId2, UTF8) +
                                "&with_deleted=false"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("line-items"), APPLICATION_JSON));

        DataListHolder<LineItem> lineItems = twitter.lineItemOperations().getLineItems(
                mockedAccountId,
                new LineItemQueryBuilder()
                        .withCampaigns(mockedCampaignId1, mockedCampaignId2)
                        .withFundingInstruments(mockedFundingInstrumentId1, mockedFundingInstrumentId2)
                        .withLineItems(mockedLineItemId1, mockedLineItemId2)
                        .includeDeleted(false));

        assertLineItemContents(lineItems.getList());
    }

    @Test
    public void createLineItem() throws UnsupportedEncodingException {
        String mockedAccountId = "hkk5";
        String doesntMatterString = "doesn-matter-altered";
        BigDecimal doesntMatterDecimal = new BigDecimal(1.00);
        Boolean doesntMatterBool = false;

        String chainedPostContent = "campaign_id=" + doesntMatterString + "&" +
                "name=" + doesntMatterString + "&" +
                "objective=" + AdvertisingObjective.APP_INSTALLS + "&" +
                "include_sentiment=" + AdvertisingSentiment.POSITIVE_ONLY + "&" +
                "optimization=" + LineItemOptimization.WEBSITE_CONVERSIONS + "&" +
                "bid_unit=" + BidUnit.LINK_CLICK + "&" +
                "product_type=" + AdvertisingProductType.PROMOTED_ACCOUNT + "&" +
                "placements=" + URLEncoder.encode(AdvertisingPlacement.ALL_ON_TWITTER + "," + AdvertisingPlacement.PUBLISHER_NETWORK, UTF8) + "&" +
                "automatically_select_bid=" + doesntMatterBool + "&" +
                "paused=" + !doesntMatterBool + "&" +
                "deleted=" + doesntMatterBool + "&" +
                "total_budget_amount_local_micro=" + doesntMatterDecimal.multiply(new BigDecimal(1000000L)) + "&" +
                "bid_amount_local_micro=" + doesntMatterDecimal.multiply(new BigDecimal(1000000L));

        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/line_items"))
                .andExpect(method(POST))
                .andExpect(content().string(chainedPostContent))
                .andRespond(withSuccess(jsonResource("line-items-single"), APPLICATION_JSON));

        LineItem lineItem = twitter.lineItemOperations().createLineItem(
                mockedAccountId,
                new LineItemFormBuilder()
                        .forCampaign(doesntMatterString)
                        .named(doesntMatterString)
                        .totalBudget(doesntMatterDecimal.toString())
                        .bidAmount(doesntMatterDecimal.toString())
                        .bidUnit(BidUnit.LINK_CLICK)
                        .productType(AdvertisingProductType.PROMOTED_ACCOUNT)
                        .placedOn(AdvertisingPlacement.ALL_ON_TWITTER, AdvertisingPlacement.PUBLISHER_NETWORK)
                        .objective(AdvertisingObjective.APP_INSTALLS)
                        .optimizedFor(LineItemOptimization.WEBSITE_CONVERSIONS)
                        .includingSentiment(AdvertisingSentiment.POSITIVE_ONLY)
                        .paused()
                        .active());

        assertSingleLineItemContents(lineItem);
    }

    @Test
    public void updateLineItem() {
        String mockedAccountId = "hkk5";
        String mockedLineItemId = "l13r";
        String doesntMatterString = "doesn-matter-altered";
        BigDecimal doesntMatterDecimal = new BigDecimal(2.00);
        Boolean doesntMatterBool = true;

        String chainedPostContent = "name=" + doesntMatterString + "&" +
                "include_sentiment=" + AdvertisingSentiment.ALL + "&" +
                "optimization=" + LineItemOptimization.DEFAULT + "&" +
                "automatically_select_bid=" + !doesntMatterBool + "&" +
                "paused=" + !doesntMatterBool + "&" +
                "deleted=" + doesntMatterBool + "&" +
                "bid_amount_local_micro=" + doesntMatterDecimal.multiply(new BigDecimal(1000000L));

        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/line_items/" + mockedLineItemId))
                .andExpect(method(PUT))
                .andExpect(content().string(chainedPostContent))
                .andRespond(withSuccess(jsonResource("line-items-single"), APPLICATION_JSON));

        twitter.lineItemOperations().updateLineItem(
                mockedAccountId,
                mockedLineItemId,
                new LineItemFormBuilder()
                        .named(doesntMatterString)
                        .bidAmount(doesntMatterDecimal.toString())
                        .optimizedFor(LineItemOptimization.DEFAULT)
                        .includingSentiment(AdvertisingSentiment.ALL)
                        .unpaused()
                        .deleted());
    }

    @Test
    public void deleteLineItem() {
        String mockedAccountId = "0ga0yn";
        String mockedLineItemId = "92ph";
        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/line_items/" + mockedLineItemId))
                .andExpect(method(DELETE))
                .andRespond(withSuccess());

        twitter.lineItemOperations().deleteLineItem(mockedAccountId, mockedLineItemId);
    }

    @Test
    public void getLineItemPlacements() throws UnsupportedEncodingException {
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/line_items/placements"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("line-item-placements"), APPLICATION_JSON));

        DataListHolder<LineItemPlacements> lineItemPlacements = twitter.lineItemOperations().getLineItemPlacements(
                new LineItemPlacementsQueryBuilder()
                        .withProductTypes(null));

        assertLineItemPlacementsContents(lineItemPlacements.getList());
    }

    @Test
    public void getLineItemPlacementsWithParams() throws UnsupportedEncodingException {
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/line_items/placements?product_type=PROMOTED_ACCOUNT"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("line-item-placements-params"), APPLICATION_JSON));

        List<AdvertisingProductType> productTypes = new ArrayList<AdvertisingProductType>();
        productTypes.add(AdvertisingProductType.PROMOTED_ACCOUNT);
        DataListHolder<LineItemPlacements> lineItemPlacements = twitter.lineItemOperations().getLineItemPlacements(
                new LineItemPlacementsQueryBuilder()
                        .withProductTypes(productTypes));

        assertLineItemPlacementsWithParamsContents(lineItemPlacements.getList());
    }

	private void assertLineItemPlacementsContents(List<LineItemPlacements> lineItemPlacements) {
        assertEquals(2, lineItemPlacements.size());
        assertEquals(6, lineItemPlacements.get(0).getPlacements().size());
        assertEquals(AdvertisingPlacement.TWITTER_TIMELINE, lineItemPlacements.get(0).getPlacements().get(3).get(1));
	}

    private void assertLineItemPlacementsWithParamsContents(List<LineItemPlacements> lineItemPlacements) {
        assertEquals(1, lineItemPlacements.size());
        assertEquals(1, lineItemPlacements.get(0).getPlacements().size());
        assertEquals(AdvertisingPlacement.ALL_ON_TWITTER, lineItemPlacements.get(0).getPlacements().get(0).get(0));
	}

    private void assertLineItemContents(List<LineItem> lineItems) {
        assertEquals(22, lineItems.size());

        assertEquals("awvv", lineItems.get(0).getId());
        assertEquals("gq0vqj", lineItems.get(0).getAccountId());
        assertEquals("e094", lineItems.get(0).getCampaignId());
        assertEquals(BidUnit.APP_CLICK, lineItems.get(0).getBidUnit());
        assertEquals(LineItemOptimization.DEFAULT, lineItems.get(0).getOptimization());
        assertEquals(AdvertisingObjective.APP_ENGAGEMENTS, lineItems.get(0).getObjective());
        assertEquals(AdvertisingSentiment.POSITIVE_ONLY, lineItems.get(0).getIncludeSentiment());
        assertEquals(AdvertisingProductType.PROMOTED_TWEETS, lineItems.get(0).getProductType());
        assertThat(lineItems.get(0).getPlacements(), CoreMatchers.hasItem(AdvertisingPlacement.ALL_ON_TWITTER));
        assertEquals("USD", lineItems.get(0).getCurrency());
        assertEquals(BigDecimal.valueOf(100), lineItems.get(0).getTotalBudgetAmount());
        assertEquals(BigDecimal.valueOf(10), lineItems.get(0).getBidAmount());
        assertEquals(false, lineItems.get(0).isAutomaticallySetBid());
        assertEquals(true, lineItems.get(0).isPaused());
        assertEquals(false, lineItems.get(0).isDeleted());
        assertEquals(LocalDateTime.of(2015, Month.MAY, 29, 20, 32, 20), lineItems.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2015, Month.MAY, 29, 20, 38, 43), lineItems.get(0).getUpdatedAt());
    }

    private void assertSingleLineItemContents(LineItem lineItem) {
        assertEquals("69ob", lineItem.getId());
        assertEquals("hkk5", lineItem.getAccountId());
        assertEquals("7wdy", lineItem.getCampaignId());
        assertEquals("GBP", lineItem.getCurrency());
        assertEquals(AdvertisingProductType.PROMOTED_ACCOUNT, lineItem.getProductType());
        assertThat(lineItem.getPlacements(), CoreMatchers.hasItem(AdvertisingPlacement.ALL_ON_TWITTER));
        assertThat(lineItem.getPlacements(), CoreMatchers.hasItem(AdvertisingPlacement.PUBLISHER_NETWORK));
        assertEquals(AdvertisingObjective.FOLLOWERS, lineItem.getObjective());
        assertEquals(null, lineItem.getIncludeSentiment());
        assertEquals(LineItemOptimization.DEFAULT, lineItem.getOptimization());
        assertEquals(null, lineItem.getTotalBudgetAmount());
        assertEquals(new BigDecimal(1.75).round(MathContext.DECIMAL32), lineItem.getBidAmount());
        assertEquals(false, lineItem.isPaused());
        assertEquals(false, lineItem.isDeleted());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 11), lineItem.getCreatedAt());
        assertEquals(LocalDateTime.of(2012, Month.NOVEMBER, 30, 22, 58, 11), lineItem.getUpdatedAt());
    }
}
