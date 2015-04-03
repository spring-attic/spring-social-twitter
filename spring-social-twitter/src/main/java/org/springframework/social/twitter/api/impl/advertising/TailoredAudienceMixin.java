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

import org.springframework.social.twitter.api.advertising.TailoredAudience;
import org.springframework.social.twitter.api.advertising.TailoredAudienceListType;
import org.springframework.social.twitter.api.advertising.TailoredAudienceType;
import org.springframework.social.twitter.api.impl.LocalDateTimeDeserializer;
import org.springframework.social.twitter.api.impl.TwitterObjectMixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Mixin class for adding Jackson annotations to {@link TailoredAudience}.
 * 
 * @author Hudson Mendes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TailoredAudienceMixin extends TwitterObjectMixin {

    @JsonCreator
    TailoredAudienceMixin(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("list_type") TailoredAudienceListType listType,
            @JsonProperty("audience_type") TailoredAudienceType audienceType,
            @JsonProperty("audience_size") Integer audienceSize,
            @JsonProperty("partner_source") String partnerSource,
            @JsonProperty("deleted") Boolean deleted,
            @JsonProperty("targetable") Boolean targetable,
            @JsonProperty("reasons_not_targetable") String[] reasonsNotTargetable,
            @JsonProperty("targetable_types") String[] targetableTypes,
            @JsonProperty("created_at") @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime createdAt,
            @JsonProperty("updated_at") @JsonDeserialize(using = LocalDateTimeDeserializer.class) LocalDateTime updatedAt) {}

}
