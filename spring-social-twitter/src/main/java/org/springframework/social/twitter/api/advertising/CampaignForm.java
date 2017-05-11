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

import java.time.LocalDateTime;

import org.springframework.social.twitter.api.TwitterForm;

/**
 * Describes the contract for the builder of {@link Campaign}'s
 * data that will be posted / patched to the endpoint.
 * 
 * @author Hudson Mendes
 */
public interface CampaignForm extends TwitterForm {

    /**
     * The name of the {@link Campaign}.
     * 
     * @param name is the name of the {@link Campaign}.
     * @return the fluent builder
     */
    public CampaignForm withName(String name);

    /**
     * The default currency in which linked {@link LineItem} will consume the budget.
     * 
     * @param currency of the {@link Campaign}.
     * @return the fluent loader.
     */
    public CampaignForm withCurrency(String currency);

    /**
     * The {@link FundingInstrument} of the {@link Campaign}
     * 
     * @param fundingInstrumentId defines which {@link FundingInstrument} will fund the {@link Campaign}.
     * @return the fluent builder
     */
    public CampaignForm withFundingInstrument(String fundingInstrumentId);

    /**
     * The budget (total and daily) that the {@link Campaign} is allowed to spend.
     * 
     * @param totalBudget describes the total budget.
     * @param dailyBudget describes the daily budget.
     * @return the fluent builder
     */
    public CampaignForm withBudget(String totalBudget, String dailyBudget);

    /**
     * Defines the activity period of the {@link Campaign}.
     * 
     * @param endTime is when the campaign will be stoped.
     * @return the fluent builder
     */
    public CampaignForm activeUntil(LocalDateTime endTime);

    /**
     * Defines the activity period of the {@link Campaign}
     * 
     * @param startTime is when the campaign will start
     * @return the fluent builder
     */
    public CampaignForm activeFrom(LocalDateTime startTime);

    /**
     * Defines the activity period of the {@link Campaign}
     * 
     * @param startTime is when the campaign will start
     * @param endTime is when the campaign will be stoped.
     * @return the fluent builder
     */
    public CampaignForm activeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Defines the reasons for which the {@link Campaign} cannot be served.
     * 
     * @param reasons for which the campaigns cannot be served.
     * @return the fluent loader
     */
    public CampaignForm thatCantBeServedDueTo(ReasonNotServable... reasons);

    /**
     * Defines if the {@link Campaign} uses the standard delivery mechanism or not.
     * 
     * @param standardDelivery is true if the campaign should use the standard mechanism; otherwise, it's false.
     * @return the fluent loader
     */
    public CampaignForm withStandardDelivery(Boolean standardDelivery);

    /**
     * Set the {@link Campaign} to paused.
     * 
     * @return the fluent loader
     */
    public CampaignForm paused();

    /**
     * Unpause the {@link Campaign}.
     * 
     * @return the fluent loader
     */
    public CampaignForm unpaused();

    /**
     * Set the {@link Campaign} to deleted.
     * 
     * @return the fluent loader
     */
    public CampaignForm deleted();

    /**
     * Make a {@link Campaign} active again.
     * 
     * @return the fluent loader
     */
    public CampaignForm active();
}
