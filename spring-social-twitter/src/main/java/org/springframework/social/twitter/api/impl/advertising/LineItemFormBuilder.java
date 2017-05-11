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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.advertising.AdvertisingObjective;
import org.springframework.social.twitter.api.advertising.AdvertisingPlacement;
import org.springframework.social.twitter.api.advertising.AdvertisingProductType;
import org.springframework.social.twitter.api.advertising.AdvertisingSentiment;
import org.springframework.social.twitter.api.advertising.BidUnit;
import org.springframework.social.twitter.api.advertising.LineItem;
import org.springframework.social.twitter.api.advertising.LineItemForm;
import org.springframework.social.twitter.api.advertising.LineItemOptimization;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Facilitate the creation of the request body for post and put
 * requests made to the management of {@link LineItem} data.
 *
 * @author Hudson Mendes
 */
public class LineItemFormBuilder extends AbstractTwitterFormBuilder implements LineItemForm {
    private String campaignId;
    private String name;
    private AdvertisingObjective objective;
    private AdvertisingSentiment includeSentiment;
    private LineItemOptimization optimization;
    private BidUnit bidUnit;
    private AdvertisingProductType productType;
    private final List<AdvertisingPlacement> placements = new ArrayList<>();
    private BigDecimal totalBudgetAmount;
    private BigDecimal bidAmount;
    private Boolean paused;
    private Boolean deleted;
    private Boolean automaticallySelectBid;

    @Override
    public MultiValueMap<String, String> toRequestBody() {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        appendParameter(params, "campaign_id", campaignId);
        appendParameter(params, "name", name);

        appendParameter(params, "objective", objective);
        appendParameter(params, "include_sentiment", includeSentiment);
        appendParameter(params, "optimization", optimization);
        appendParameter(params, "bid_unit", bidUnit);

        appendParameter(params, "product_type", productType);
        appendParameter(params, "placements", placements);

        appendParameter(params, "automatically_select_bid", automaticallySelectBid);
        appendParameter(params, "paused", paused);
        appendParameter(params, "deleted", deleted);

        appendParameter(params, "total_budget_amount_local_micro", translateBigDecimalIntoMicro(totalBudgetAmount));
        appendParameter(params, "bid_amount_local_micro", translateBigDecimalIntoMicro(bidAmount), true);

        return params;
    }

    @Override
    public LineItemForm forCampaign(String campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    @Override
    public LineItemForm named(String name) {
        this.name = name;
        return this;
    }

    @Override
    public LineItemForm productType(AdvertisingProductType productType) {
        this.productType = productType;
        return this;
    }

    @Override
    public LineItemForm placedOn(AdvertisingPlacement... placements) {
        if (placements != null)
            for (final AdvertisingPlacement placement : placements)
                this.placements.add(placement);
        return this;
    }

    @Override
    public LineItemForm objective(AdvertisingObjective objective) {
        this.objective = objective;
        return this;
    }

    @Override
    public LineItemForm includingSentiment(AdvertisingSentiment sentiment) {
        includeSentiment = sentiment;
        return this;
    }

    @Override
    public LineItemForm optimizedFor(LineItemOptimization optimization) {
        this.optimization = optimization;
        return this;
    }

    @Override
    public LineItemForm bidUnit(BidUnit bidUnit) {
        this.bidUnit = bidUnit;
        return this;
    }

    @Override
    public LineItemForm totalBudget(String totalBudgetAmount) {
        if (totalBudgetAmount != null)
            this.totalBudgetAmount = new BigDecimal(totalBudgetAmount);
        else
            this.totalBudgetAmount = null;
        return this;
    }

    @Override
    public LineItemForm bidAmount(String bidAmount) {
        if (bidAmount != null) {
            this.bidAmount = new BigDecimal(bidAmount);
            automaticallySelectBid = false;
        }
        else
            this.bidAmount = null;
        return this;
    }

    @Override
    public LineItemForm automaticallySelectBid(Boolean auto) {
        if (auto != null) {
            automaticallySelectBid = auto;
            if (auto)
                bidAmount = null;
        }
        return this;
    }

    @Override
    public LineItemForm paused() {
        paused = true;
        return this;
    }

    @Override
    public LineItemForm unpaused() {
        paused = false;
        return this;
    }

    @Override
    public LineItemFormBuilder deleted() {
        deleted = true;
        return this;
    }

    @Override
    public LineItemFormBuilder active() {
        deleted = false;
        return this;
    }
}
