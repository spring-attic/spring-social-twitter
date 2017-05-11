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
import java.util.ArrayList;
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
import java.util.List;

import org.springframework.social.twitter.api.TwitterObject;

/**
 * Represents an Advertising Campaign run for a particular {@link AdvertisingAccount}. 
 * @author Hudson Mendes
 */
public class Campaign extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private final String id;
	private final String accountId;
	private final String name;
	private final String currency;
	private final String fundingInstrumentId;
	private final BigDecimal totalBudget;
	private final BigDecimal dailyBudget;
	private final LocalDateTime startTime;
	private final LocalDateTime endTime;
	private final List<ReasonNotServable> reasonsNotServable;
	private final Boolean standardDelivery;
	private final Boolean paused;
	private final Boolean deleted;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;
	
	public Campaign(
			String id, String name, String accountId, String currency,
			String fundingInstrumentId, BigDecimal totalBudget, BigDecimal dailyBudget,
			LocalDateTime startTime, LocalDateTime endTime,
			LocalDateTime createdAt, LocalDateTime updatedAt,
			List<ReasonNotServable> reasonsNotServable,
			Boolean standardDelivery, Boolean paused, Boolean deleted) {
		
		this.id = id;
		this.name = name;
		this.accountId = accountId;
		this.currency = currency;
		this.fundingInstrumentId = fundingInstrumentId;
		
		this.totalBudget = totalBudget;
		this.dailyBudget = dailyBudget;
		
		this.startTime = startTime;
		this.endTime = endTime;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		
		this.reasonsNotServable = reasonsNotServable != null ? reasonsNotServable : new ArrayList<ReasonNotServable>();
		
		this.standardDelivery = standardDelivery; 
		this.paused = paused;
		this.deleted = deleted;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getCurrency() {
		return currency;
	}

	public String getFundingInstrumentId() {
		return fundingInstrumentId;
	}

	public BigDecimal getTotalBudget() {
		return totalBudget;
	}

	public BigDecimal getDailyBudget() {
		return dailyBudget;
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

	public List<ReasonNotServable> getReasonsNotServable() {
		return reasonsNotServable;
	}

	public Boolean isStandardDelivery() {
		return standardDelivery;
	}

	public Boolean isPaused() {
		return paused;
	}

	public Boolean isServable() {
		Boolean servable = reasonsNotServable.isEmpty();
		return servable;
	}

	public Boolean isDeleted() {
		return deleted;
	}
}
