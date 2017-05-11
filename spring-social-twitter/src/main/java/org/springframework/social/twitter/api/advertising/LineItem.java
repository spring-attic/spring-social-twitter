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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.TwitterObject;

/**
 * Represents an Advertising Group (also known as Line Item).
 * 
 * Source: https://dev.twitter.com/ads/campaigns
 * Line items spend the budget defined by a campaign.
 * Line items pull together the per-engagement bid,
 * the Tweet or account to promote, and the targeting rules.
 * 
 * Source: https://dev.twitter.com/ads/reference/post/accounts/:account_id/line_items
 * Create a line item associated with the specified campaign belonging to the current account.
 * Note that for PROMOTED_ACCOUNT campaigns, associating a Promoted Tweet to the line_item will
 * add timeline placements on mobile in addition to the standard PROMOTED_ACCOUNT placement.
 * 
 * @author Hudson Mendes
 */
public class LineItem extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private final String id;
    private final String accountId;
    private final String campaignId;
    private final String name;

    private final BidUnit bidUnit;
    private final LineItemOptimization optimization;
    private final AdvertisingObjective objective;
    private final AdvertisingSentiment includeSentiment;

    private final AdvertisingProductType productType;
    private final List<AdvertisingPlacement> placements = new ArrayList<>();

    private final String currency;
    private final BigDecimal totalBudgetAmount;
    private final BigDecimal bidAmount;

    private final Boolean paused;
    private final Boolean deleted;
    private final Boolean automaticallySelectBid;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public LineItem(
            String id,
            String accountId,
            String campaignId,
            String name,
            BidUnit bidUnit,
            LineItemOptimization optimization,
            AdvertisingObjective objective,
            AdvertisingSentiment includeSentiment,
            AdvertisingProductType productType,
            AdvertisingPlacement[] placements,
            String currency,
            BigDecimal totalBudgetAmount,
            BigDecimal bidAmount,
            Boolean automaticallySelectBid,
            Boolean paused,
            Boolean deleted,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.accountId = accountId;
        this.campaignId = campaignId;
        this.name = name;

        this.productType = productType;
        if (placements != null)
            for (AdvertisingPlacement placement : placements)
                this.placements.add(placement);

        this.bidUnit = bidUnit;
        this.objective = objective;
        this.includeSentiment = includeSentiment;
        this.optimization = optimization;

        this.currency = currency;
        this.totalBudgetAmount = totalBudgetAmount;
        this.bidAmount = bidAmount;

        this.paused = paused;
        this.deleted = deleted;
        this.automaticallySelectBid = automaticallySelectBid;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public String getName() {
        return name;
    }

    public AdvertisingProductType getProductType() {
        return productType;
    }

    public BidUnit getBidUnit() {
        return bidUnit;
    }

    public LineItemOptimization getOptimization() {
        return optimization;
    }

    public AdvertisingObjective getObjective() {
        return objective;
    }

    public AdvertisingSentiment getIncludeSentiment() {
        return includeSentiment;
    }

    public List<AdvertisingPlacement> getPlacements() {
        return placements;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getTotalBudgetAmount() {
        return totalBudgetAmount;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public Boolean isPaused() {
        return paused;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Boolean isAutomaticallySetBid() {
        return automaticallySelectBid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
