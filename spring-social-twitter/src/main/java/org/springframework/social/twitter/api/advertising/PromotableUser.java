package org.springframework.social.twitter.api.advertising;

import java.time.LocalDateTime;

import org.springframework.social.twitter.api.TwitterObject;

public class PromotableUser extends TwitterObject {
	private static final long serialVersionUID = 1987591937358157578L;
	private final Long userId;
    private final String id;
    private final String accountId;
    private final String promotableUserType;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Boolean deleted;

    public PromotableUser(
            Long userId,
            String id,
            String accountId,
            String promotableUserType,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Boolean deleted) {

        this.userId = userId;
        this.id = id;
        this.promotableUserType = promotableUserType;
        this.accountId = accountId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deleted = deleted;
    }

    public Long getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public String getPromotableUserType() {
        return promotableUserType;
    }
}
