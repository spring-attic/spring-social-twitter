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

import org.springframework.social.twitter.api.TwitterObject;

/**
 * Represents a Targeting Criteria to drive a {@link Campaign}.
 * 
 * Source: https://dev.twitter.com/ads/campaigns/keyword-targeting-timelines
 * Keyword targeting is already fundamental to our Promoted Tweets in Search product,
 * and now we are now expanding the power of keyword targeting to timelines, giving
 * campaigns better reach. Keyword targeting in timeline enables platforms to target
 * Twitter users based on keywords in their recent Tweets.
 * 
 * @author Hudson Mendes
 */
public class TargetingCriterion extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private final String id;
    private final String accountId;
    private final String lineItemId;
    private final String name;
    private final String targetingType;
    private final String targetingValue;
    private final Boolean tailoredAudienceExpansion;
    private final TailoredAudienceType tailoredAudienceType;
    private final Boolean deleted;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public TargetingCriterion(
            String id,
            String accountId,
            String lineItemId,
            String name,
            String targetingType,
            String targetingValue,
            Boolean tailoredAudienceExpansion,
            TailoredAudienceType tailoredAudienceType,
            Boolean deleted,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.accountId = accountId;
        this.lineItemId = lineItemId;
        this.name = name;

        this.targetingType = targetingType;
        this.targetingValue = targetingValue;

        this.tailoredAudienceExpansion = tailoredAudienceExpansion;
        this.tailoredAudienceType = tailoredAudienceType;

        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public String getName() {
        return name;
    }

    public String getTargetingType() {
        return targetingType;
    }

    public String getTargetingValue() {
        return targetingValue;
    }

    public Boolean isTailoredAudienceExpansion() {
        return tailoredAudienceExpansion;
    }

    public TailoredAudienceType getTailoredAudienceType() {
        return tailoredAudienceType;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
