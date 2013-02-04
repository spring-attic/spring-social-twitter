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
package org.springframework.social.twitter.api;

import java.util.Date;

/**
 * Interface defining the advanced search operations for Twitter.
 * See specifications: https://dev.twitter.com/docs/api/1.1/get/search/tweets
 *
 * @author Rosty Kerei
 */
public interface AdvancedSearchOperations {

    /**
     * Searches Twitter
     *
     * @param query The search query string
     * @return a {@link SearchResults} containing the search results metadata and a list of matching {@link Tweet}s
     * @throws org.springframework.social.ApiException if there is an error while communicating with Twitter.
     * @throws org.springframework.social.MissingAuthorizationException if TwitterTemplate was not created with OAuth credentials.
     * @see SearchResults
     * @see Tweet
     */
    SearchResults search(String query);

    /**
     * Sets optional <code>geocode</code> parameter. Restricts tweets by users located within a given radius
     * of the given latitude/longitude.
     *
     * @param geoCode GeoCode object stuffed with coordinates and radius
     * @return
     * @see GeoCode
     */
    <T extends AdvancedSearchOperations> T setGeoCode(GeoCode geoCode);

    /**
     * Sets optional <code>lang</code> parameter. Restricts tweets to the given language, given by an ISO 639-1 code.
     *
     * @param lang an ISO 639-1 language code
     * @return
     */
    <T extends AdvancedSearchOperations> T setLang(String lang);

    /**
     * Sets optional <code>locale</code> parameter. Specify the language of the query you are sending
     * (only ja is currently effective).
     *
     * @param locale locale
     * @return
     */
    <T extends AdvancedSearchOperations> T setLocale(String locale);

    /**
     * Sets optional <code>result_type</code> parameter. Specifies what type of search results. Default is <code>mixed</code>.
     *
     * @param resultType type of preferred result type
     * @return
     * @see ResultType
     */
    <T extends AdvancedSearchOperations> T setResultType(ResultType resultType);

    /**
     * Sets optional <code>count</code> parameter. Restricts the number of tweets to return, up to a maximum of 100.
     * Defaults to 15.
     *
     * @param count number of tweets to return
     * @return
     */
    <T extends AdvancedSearchOperations> T setCount(int count);

    /**
     * Sets optional <code>until</code> parameter. Restricts search to tweets generated before the given date.
     *
     * @param untilDate date to search until
     * @return
     */
    <T extends AdvancedSearchOperations> T setUntil(Date untilDate);

    /**
     * Sets optional <code>since_id</code> parameter. Restricts search results with an ID greater than the specified one.
     *
     * @param sinceId The minimum {@link Tweet} ID to return in the results
     * @return
     */
    <T extends AdvancedSearchOperations> T setSinceId(long sinceId);

    /**
     * Sets optional <code>max_id</code> parameter. Restricts search results with an ID less or equel than the specified one.
     *
     * @param maxId The maximum {@link Tweet} ID to return in the results
     * @return
     */
    <T extends AdvancedSearchOperations> T setMaxId(long maxId);

    /**
     * Sets optional <code>include_entities</code> parameter. The entities node will be excluded when set to false.
     *
     * @param includeEntities
     * @return
     */
    <T extends AdvancedSearchOperations> T setIncludeEntities(boolean includeEntities);

    /**
     * Returns <code>geo_code</code> search parameter
     *
     * @return geoCode
     */
    GeoCode getGeoCode();

    /**
     * Returns <code>lang</code> search parameter
     *
     * @return lang
     */
    String getLang();

    /**
     * Returns <code>locale</code> search parameter
     *
     * @return locale
     */
    String getLocale();

    /**
     * Returns <code>result_type</code> search parameter
     *
     * @return result_type
     */
    ResultType getResultType();

    /**
     * Returns <code>count</code> search parameter
     *
     * @return count
     */
    int getCount();

    /**
     * Returns <code>until</code> search parameter
     *
     * @return until
     */
    Date getUntil();

    /**
     * Returns <code>since_id</code> search parameter
     *
     * @return since_id
     */
    long getSinceId();

    /**
     * Returns <code>max_id</code> search parameter
     *
     * @return max_id
     */
    long getMaxId();

    /**
     * Returns <code>include_entities</code> search parameter
     *
     * @return include_entities
     */
    boolean isIncludeEntities();

    /**
     * ResultType enumeration. Used by setResultType/getResultType
     */
    public enum ResultType {
        MIXED("mixed"), RECENT("recent"), POPULAR("popular");

        private String resultType;

        private ResultType(String resultType) {
            this.resultType = resultType;
        }

        @Override
        public String toString() {
            return this.resultType;
        }
    }
}
