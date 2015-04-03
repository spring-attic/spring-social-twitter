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
 * Represents a Tailored Audience.
 * 
 * Source: https://support.twitter.com/articles/20172017-tailored-audiences
 * Tailored audiences are a way to target your existing users and customers
 * to create highly relevant campaigns.
 * 
 * @author Hudson Mendes
 */
public class TailoredAudience extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private final String id;
    private final String name;
    private final TailoredAudienceType audienceType;
    private final TailoredAudienceListType listType;
    private final Integer audienceSize;
    private final String partnerSource;
    private final Boolean deleted;
    private final Boolean targetable;
    private final String[] reasonsNotTargetable;
    private final String[] targetableTypes;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public TailoredAudience(
            String id,
            String name,
            TailoredAudienceListType listType,
            TailoredAudienceType audienceType,
            Integer audienceSize,
            String partnerSource,
            Boolean deleted,
            Boolean targetable,
            String[] reasonsNotTargetable,
            String[] targetableTypes,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        this.id = id;
        this.name = name;

        this.audienceType = audienceType;
        this.listType = listType;

        this.audienceSize = audienceSize;
        this.partnerSource = partnerSource;

        this.deleted = deleted;
        this.targetable = targetable;

        this.reasonsNotTargetable = reasonsNotTargetable;
        this.targetableTypes = targetableTypes;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TailoredAudienceType getAudienceType() {
        return audienceType;
    }

    public TailoredAudienceListType getListType() {
        return listType;
    }

    public Integer getAudienceSize() {
        return audienceSize;
    }

    public String getPartnerSource() {
        return partnerSource;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public Boolean isTargetable() {
        return targetable;
    }

    public String[] getReasonsNotTargetable() {
        return reasonsNotTargetable;
    }

    public String[] getTargetableTypes() {
        return targetableTypes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
