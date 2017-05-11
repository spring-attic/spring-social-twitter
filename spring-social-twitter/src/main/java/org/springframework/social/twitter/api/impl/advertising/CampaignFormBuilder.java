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
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.advertising.Campaign;
import org.springframework.social.twitter.api.advertising.CampaignForm;
import org.springframework.social.twitter.api.advertising.ReasonNotServable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Facilitate the creation of the request body for post and put
 * requests made to the management of {@link Campaign} data.
 * 
 * @author Hudson Mendes
 */
public class CampaignFormBuilder extends AbstractTwitterFormBuilder implements CampaignForm {
    private String name;
    private String currency;
    private String fundingInstrumentId;
    private BigDecimal totalBudget;
    private BigDecimal dailyBudget;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private final List<ReasonNotServable> reasonsNotServable;
    private Boolean standardDelivery = true;
    private Boolean paused = false;
    private Boolean deleted = false;

    public CampaignFormBuilder() {
        this.reasonsNotServable = new ArrayList<ReasonNotServable>();
    }

    @Override
    public CampaignForm withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public CampaignForm withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public CampaignForm withFundingInstrument(String fundingInstrumentId) {
        this.fundingInstrumentId = fundingInstrumentId;
        return this;
    }

    @Override
    public CampaignForm withBudget(String totalBudget, String dailyBudget) {
        if(null!=totalBudget)
        	this.totalBudget = new BigDecimal(totalBudget);
        if(null!=dailyBudget)
        	this.dailyBudget = new BigDecimal(dailyBudget);
        return this;
    }

    @Override
    public CampaignForm activeUntil(LocalDateTime endTime) {
        return activeBetween(null, endTime);
    }

    @Override
    public CampaignForm activeFrom(LocalDateTime startTime) {
        return activeBetween(startTime, null);
    }

    @Override
    public CampaignForm activeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        return this;
    }

    @Override
    public CampaignForm thatCantBeServedDueTo(ReasonNotServable... reasons) {
        if (reasons != null)
        	for (ReasonNotServable reason : reasons)
        		this.reasonsNotServable.add(reason);
        return this;
    }

    @Override
    public CampaignForm withStandardDelivery(Boolean standardDelivery) {
        this.standardDelivery = standardDelivery;
        return this;
    }

    @Override
    public CampaignForm paused() {
        this.paused = true;
        return this;
    }

    @Override
    public CampaignForm unpaused() {
        this.paused = false;
        return this;
    }

    @Override
    public CampaignForm deleted() {
        this.deleted = true;
        return this;
    }

    @Override
    public CampaignForm active() {
        this.deleted = false;
        return this;
    }

    @Override
    public MultiValueMap<String, String> toRequestBody() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        appendParameter(params, "name", this.name);
        appendParameter(params, "currency", this.currency);
        appendParameter(params, "funding_instrument_id", this.fundingInstrumentId);

        appendParameter(params, "total_budget_amount_local_micro", translateBigDecimalIntoMicro(this.totalBudget));
        appendParameter(params, "daily_budget_amount_local_micro", translateBigDecimalIntoMicro(this.dailyBudget));

        if (this.startTime != null)
            appendParameter(params, "start_time", this.startTime.toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS));
        if (this.endTime != null)
            appendParameter(params, "end_time", this.endTime.toInstant(ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS));

        appendParameter(params, "reasons_not_servable", this.reasonsNotServable);
        appendParameter(params, "standard_delivery", this.standardDelivery);
        appendParameter(params, "paused", this.paused);
        appendParameter(params, "deleted", this.deleted);

        return params;
    }
}
