/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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

import org.springframework.social.twitter.api.TwitterObject;

/**
 * Represents a Funding instrument related to an {@link AdvertisingAccount}.
 * 
 * Source: https://dev.twitter.com/ads/campaigns/funding-instruments
 * 		Funding Instruments are the source of campaign budget.
 * 		Funding Instruments can not be created via the Ads API,
 * 		they have to be already established by the advertiser’s
 * 		account manager at Twitter (for credit lines) or via
 * 		ads.twitter.com (for credit cards) to be available.
 * 
 * @author Hudson Mendes
 */
public class FundingInstrument extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private final String id;
	private final FundingInstrumentType type;
	private final String accountId;
	private final String currency;
	private final String description;
	private final Boolean cancelled;
	private final Boolean deleted;
	private final BigDecimal creditLimit;
	private final BigDecimal creditRemaining;
	private final BigDecimal fundedAmount;
	private final LocalDateTime startTime;
	private final LocalDateTime endTime;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	public FundingInstrument(
			String id, FundingInstrumentType type,
			String accountId, String currency, String description,
			Boolean cancelled, Boolean deleted,
			BigDecimal creditLimit, BigDecimal creditRemaining, BigDecimal fundedAmount,
			LocalDateTime startTime, LocalDateTime endTime,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		
		this.id = id;
		this.type = type;
		
		this.accountId = accountId;
		this.currency = currency;
		this.description = description;
		
		this.cancelled = cancelled;
		this.deleted = deleted;
		
		this.creditLimit = creditLimit;
		this.creditRemaining = creditRemaining;
		this.fundedAmount = fundedAmount;
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public String getId() {
		return id;
	}

	public FundingInstrumentType getType() {
		return type;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getCurrency() {
		return currency;
	}

	public String getDescription() {
		return description;
	}

	public Boolean isCancelled() {
		return cancelled;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public BigDecimal getCreditRemaining() {
		return creditRemaining;
	}

	public BigDecimal getFundedAmount() {
		return fundedAmount;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
