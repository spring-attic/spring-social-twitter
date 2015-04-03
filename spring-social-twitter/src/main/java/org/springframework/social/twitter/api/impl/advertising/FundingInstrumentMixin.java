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
package org.springframework.social.twitter.api.impl.advertising;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.social.twitter.api.advertising.FundingInstrument;
import org.springframework.social.twitter.api.advertising.FundingInstrumentType;
import org.springframework.social.twitter.api.impl.LocalDateTimeDeserializer;
import org.springframework.social.twitter.api.impl.TwitterObjectMixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Mixin class for adding Jackson annotations to {@link FundingInstrument}.
 * @author Hudson Mendes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class FundingInstrumentMixin extends TwitterObjectMixin {
	
	@JsonCreator
	FundingInstrumentMixin(
			@JsonProperty("id") String id,
			@JsonProperty("instrument_type") FundingInstrumentType type,
			@JsonProperty("account_id") String accountId,
			@JsonProperty("currency") String currency,
			@JsonProperty("description") String description,
			@JsonProperty("cancelled") Boolean cancelled,
			@JsonProperty("deleted") Boolean deleted,
			@JsonProperty("credit_limit_local_micro") @JsonDeserialize(using=BigDecimalMicroAmountDeserializer.class) BigDecimal creditLimit,
			@JsonProperty("credit_remaining_local_micro") @JsonDeserialize(using=BigDecimalMicroAmountDeserializer.class) BigDecimal creditRemaining,
			@JsonProperty("funded_amount_local_micro") @JsonDeserialize(using=BigDecimalMicroAmountDeserializer.class) BigDecimal fundedAmount,
			@JsonProperty("start_time") @JsonDeserialize(using=LocalDateTimeDeserializer.class) LocalDateTime startTime,
			@JsonProperty("end_time") @JsonDeserialize(using=LocalDateTimeDeserializer.class) LocalDateTime endTime,
			@JsonProperty("created_at") @JsonDeserialize(using=LocalDateTimeDeserializer.class) LocalDateTime createdAt,
			@JsonProperty("updated_at") @JsonDeserialize(using=LocalDateTimeDeserializer.class) LocalDateTime updatedAt) {}
}
