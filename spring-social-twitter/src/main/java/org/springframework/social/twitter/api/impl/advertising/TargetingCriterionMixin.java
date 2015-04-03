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

import java.time.LocalDateTime;

import org.springframework.social.twitter.api.advertising.TailoredAudienceType;
import org.springframework.social.twitter.api.advertising.TargetingCriterion;
import org.springframework.social.twitter.api.impl.LocalDateTimeDeserializer;
import org.springframework.social.twitter.api.impl.TwitterObjectMixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Mixin class for adding Jackson annotations to {@link TargetingCriterion}.
 * 
 * @author Hudson Mendes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TargetingCriterionMixin extends TwitterObjectMixin {

    @JsonCreator
    TargetingCriterionMixin(
            @JsonProperty("id") String id,
            @JsonProperty("account_id") String accountId,
            @JsonProperty("line_item_id") String lineItemId,
            @JsonProperty("name") String name,
            @JsonProperty("targeting_type") String targetingType,
            @JsonProperty("targeting_value") String targetingValue,
            @JsonProperty("tailored_audience_expansion") Boolean tailordAudienceExpansion,
            @JsonProperty("tailored_audience_type") TailoredAudienceType tailoredAudienceType,
            @JsonProperty("deleted") Boolean deleted,
            @JsonProperty("created_at") @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime createdAt,
            @JsonProperty("updated_at") @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime updatedAt) {}


}
