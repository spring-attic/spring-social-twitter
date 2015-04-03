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

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterObject;

/**
 * Represents a link between {@link LineItem} and {@link Tweet},
 * part of the creative namespace of the Twitter Ads Api.
 * 
 * @author Hudson Mendes
 */
public class PromotedTweetReference extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private final String id;
    private final String lineItemId;
    private final Long tweetId;
    private final Boolean paused;
    private final Boolean deleted;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final ApprovalStatus approvalStatus;

    public PromotedTweetReference(
            String id,
            String lineItemId,
            Long tweetId,
            Boolean paused,
            Boolean deleted,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            ApprovalStatus approvalStatus) {

        this.id = id;
        this.lineItemId = lineItemId;
        this.tweetId = tweetId;
        this.paused = paused;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.approvalStatus = approvalStatus;
    }

    public String getId() {
        return id;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public Boolean isPaused() {
        return paused;
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

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }
}
