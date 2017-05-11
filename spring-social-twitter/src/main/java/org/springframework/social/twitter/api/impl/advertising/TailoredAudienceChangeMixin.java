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

import org.springframework.social.twitter.api.advertising.TailoredAudienceChangeOperation;
import org.springframework.social.twitter.api.advertising.TailoredAudienceChangeState;
import org.springframework.social.twitter.api.impl.TwitterObjectMixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Hudson Mendes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TailoredAudienceChangeMixin extends TwitterObjectMixin {

    @JsonCreator
    TailoredAudienceChangeMixin(
            @JsonProperty("id") String id,
            @JsonProperty("tailored_audience_id") String tailoredAudienceId,
            @JsonProperty("input_file_path") String inputFilePath,
            @JsonProperty("operation") TailoredAudienceChangeOperation operation,
            @JsonProperty("state") TailoredAudienceChangeState state) {}

}
