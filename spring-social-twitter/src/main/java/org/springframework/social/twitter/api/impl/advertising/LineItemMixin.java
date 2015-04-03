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
import java.time.LocalDateTime;

import org.springframework.social.twitter.api.advertising.AdvertisingObjective;
import org.springframework.social.twitter.api.advertising.AdvertisingPlacement;
import org.springframework.social.twitter.api.advertising.AdvertisingProductType;
import org.springframework.social.twitter.api.advertising.AdvertisingSentiment;
import org.springframework.social.twitter.api.advertising.BidUnit;
import org.springframework.social.twitter.api.advertising.LineItem;
import org.springframework.social.twitter.api.advertising.LineItemOptimization;
import org.springframework.social.twitter.api.impl.LocalDateTimeDeserializer;
import org.springframework.social.twitter.api.impl.TwitterObjectMixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Mixin class for adding Jackson annotations to {@link LineItem}.
 * 
 * @author Hudson Mendes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class LineItemMixin extends TwitterObjectMixin {

    @JsonCreator
    LineItemMixin(
            @JsonProperty("id") String id,
            @JsonProperty("account_id") String accountId,
            @JsonProperty("campaign_id") String campaignId,
            @JsonProperty("name") String name,
            @JsonProperty("bid_unit") BidUnit bidUnit,
            @JsonProperty("optimization") LineItemOptimization optimization,
            @JsonProperty("objective") AdvertisingObjective objective,
            @JsonProperty("include_sentiment") AdvertisingSentiment includeSentiment,
            @JsonProperty("product_type") AdvertisingProductType productType,
            @JsonProperty("placements") AdvertisingPlacement[] placements,
            @JsonProperty("currency") String currency,
            @JsonProperty("total_budget_amount_local_micro") @JsonDeserialize(using = BigDecimalMicroAmountDeserializer.class) BigDecimal totalBudgetAmount,
            @JsonProperty("bid_amount_local_micro") @JsonDeserialize(using = BigDecimalMicroAmountDeserializer.class) BigDecimal bidAmount,
            @JsonProperty("automatically_select_bid") Boolean automaticallySelectBid,
            @JsonProperty("paused") Boolean paused,
            @JsonProperty("deleted") Boolean deleted,
            @JsonProperty("created_at") @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime createdAt,
            @JsonProperty("updated_at") @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime updatedAt) {}

}
