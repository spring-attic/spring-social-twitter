/*
 * Copyright 2011 the original author or authors.
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
package org.springframework.social.twitter.api.impl;

import org.springframework.social.twitter.api.AdvancedSearchOperations;
import org.springframework.social.twitter.api.GeoCode;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of {@link org.springframework.social.twitter.api.AdvancedSearchOperations}, providing a binding to Twitter's search.
 *
 * @author Rosty Kerei
 */
public class AdvancedSearchTemplate extends AbstractTwitterOperations implements AdvancedSearchOperations {

    private final RestTemplate restTemplate;

    private GeoCode geoCode;
    private String lang;
    private String locale;
    private ResultType resultType;
    private Integer count;
    private Date untilDate;
    private Long sinceId;
    private Long maxId;
    private boolean includeEntities = true;

    public AdvancedSearchTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
        super(isAuthorizedForUser);
        this.restTemplate = restTemplate;
    }

    public SearchResults search(String query) {
        Assert.notNull(query);

        requireAuthorization();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

        parameters.set("q", query);

        if (this.geoCode != null) {
            parameters.set("geocode", this.geoCode.toString());
        }

        if (this.lang != null) {
            parameters.set("lang", this.lang);
        }

        if (this.locale != null) {
            parameters.set("locale", this.locale);
        }

        if (this.resultType != null) {
            parameters.set("result_type", this.resultType.toString());
        }

        if (this.count != null) {
            parameters.set("count", String.valueOf(this.count));
        }

        if (this.untilDate != null) {
            parameters.set("until", new SimpleDateFormat("yyyy-MM-dd").format(this.untilDate));
        }

        if (this.sinceId != null) {
            parameters.set("since_id", String.valueOf(this.sinceId));
        }

        if (this.maxId != null) {
            parameters.set("max_id", String.valueOf(this.maxId));
        }

        if (!this.includeEntities) {
            parameters.set("include_entities", "false");
        }

        return restTemplate.getForObject(buildUri("search/tweets.json", parameters),SearchResults.class);
    }

    public <T extends AdvancedSearchOperations> T setGeoCode(GeoCode geoCode) {
        this.geoCode = geoCode;
        return (T) this;
    }

    public <T extends AdvancedSearchOperations> T setLang(String lang) {
        this.lang = lang;
        return (T) this;
    }

    public <T extends AdvancedSearchOperations> T setLocale(String locale) {
        this.locale = locale;
        return (T) this;
    }

    public <T extends AdvancedSearchOperations> T setResultType(ResultType resultType) {
        this.resultType = resultType;
        return (T) this;
    }

    public <T extends AdvancedSearchOperations> T setCount(int count) {
        this.count = count;
        return (T) this;
    }

    public <T extends AdvancedSearchOperations> T setUntil(Date untilDate) {
        this.untilDate = untilDate;
        return (T) this;
    }

    public <T extends AdvancedSearchOperations> T setSinceId(long sinceId) {
        this.sinceId = sinceId;
        return (T) this;
    }

    public <T extends AdvancedSearchOperations> T setMaxId(long maxId) {
        this.maxId = maxId;
        return (T) this;
    }

    public <T extends AdvancedSearchOperations> T setIncludeEntities(boolean includeEntities) {
        this.includeEntities = includeEntities;
        return (T) this;
    }

    public GeoCode getGeoCode() {
        return this.geoCode;
    }

    public String getLang() {
        return this.lang;
    }

    public String getLocale() {
        return this.locale;
    }

    public ResultType getResultType() {
        return this.resultType;
    }

    public int getCount() {
        return this.count;
    }

    public Date getUntil() {
        return this.untilDate;
    }

    public long getSinceId() {
        return this.sinceId;
    }

    public long getMaxId() {
        return this.maxId;
    }

    public boolean isIncludeEntities() {
        return this.includeEntities;
    }
}
