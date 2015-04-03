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
 * Represents a {@link PromotableUser} account that has been actually
 * promoted through the Twitter Ads Api creatives namespace.
 * 
 * @author Hudson Mendes
 */
public class PromotedUserReference extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private final String id;
    private final String lineItemId;
    private final String userId;
    private final Boolean paused;
    private final Boolean deleted;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final ApprovalStatus approvalStatus;

    public PromotedUserReference(
            String id,
            String lineItemId,
            String userId,
            Boolean paused,
            Boolean deleted,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            ApprovalStatus approvalStatus) {

        this.id = id;
        this.lineItemId = lineItemId;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Boolean isPaused() {
        return paused;
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
