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

/**
 * Define a non-restrictive list of possible TargetTypes,
 * that allow you to create targeting criterias.
 *
 * When developping for {@link TailoredAudience}, consider
 * the type 'TAILORED_AUDIENCE' and explore {@link TailoredAudienceOperations} for its operations.
 *
 * @author Hudson Mendes
 *
 */
public enum TargetingCriterionType {
    AGE,
    FOLLOWERS_OF_USER,
    SIMILAR_TO_FOLLOWERS_OF_USER,
    INTEREST,
    LOCATION,
    LANGUAGE,
    PLATFORM,
    PLATFORM_VERSION,
    DEVICE,
    WIFI_ONLY,
    GENDER,
    TV_CHANNEL,
    TV_GENRE,
    TV_SHOW,
    TV_SHOW_AIRING_RESTRICTED,

    NETWORK_OPERATOR,
    NETWORK_ACTIVATION_DURATION_LT,
    NETWORK_ACTIVATION_DURATION_GTE,

    BROAD_KEYWORD,
    UNORDERED_KEYWORD,
    PHRASE_KEYWORD,
    EXACT_KEYWORD,
    NEGATIVE_PHRASE_KEYWORD,
    NEGATIVE_UNORDERED_KEYWORD,
    NEGATIVE_EXACT_KEYWORD,

    TAILORED_AUDIENCE,

    BEHAVIOR,
    NEGATIVE_BEHAVIOR,
    BEHAVIOR_EXPANDED,

    APP_STORE_CATEGORY,
    APP_STORE_CATEGORY_LOOKALIKE,
    EXCLUDE_APP_LIST
}
