/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
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
 * Twitter search parameters.
 * See specifications: https://dev.twitter.com/docs/api/1.1/get/search/tweets
 *
 * @author Rosty Kerei
 */
public class SearchParameters  {

	private String query;
	private GeoCode geoCode;
	private String lang;
	private String locale;
	private ResultType resultType;
	private Integer count;
	private Date untilDate;
	private Long sinceId;
	private Long maxId;
	private boolean includeEntities = true;

	/**
	 * Constructs SearchParameter object
	 *
	 * @param query Search keywords
	 */
	public SearchParameters(String query) {
		this.query = query;
	}

	/**
	 * Sets optional <code>geocode</code> parameter. Restricts tweets by users located within a given radius
	 * of the given latitude/longitude.
	 *
	 * @param geoCode GeoCode object stuffed with coordinates and radius
	 * @see GeoCode
	 * @return The same SearchParameters for additional configuration.
	 */
	public SearchParameters geoCode(GeoCode geoCode) {
		this.geoCode = geoCode;
		return this;
	}

	/**
	 * Sets optional <code>lang</code> parameter. Restricts tweets to the given language, given by an ISO 639-1 code.
	 *
	 * @param lang an ISO 639-1 language code
	 * @return The same SearchParameters for additional configuration.
	 */
	public SearchParameters lang(String lang) {
		this.lang = lang;
		return this;
	}

	/**
	 * Sets optional <code>locale</code> parameter. Specify the language of the query you are sending
	 * (only ja is currently effective).
	 *
	 * @param locale locale
	 * @return The same SearchParameters for additional configuration.
	 */
	public SearchParameters locale(String locale) {
		this.locale = locale;
		return this;
	}

	/**
	 * Sets optional <code>result_type</code> parameter. Specifies what type of search results. Default is <code>mixed</code>.
	 *
	 * @param resultType type of preferred result type
	 * @return The same SearchParameters for additional configuration.
	 * @see ResultType
	 */
	public SearchParameters resultType(ResultType resultType) {
		this.resultType = resultType;
		return this;
	}

	/**
	 * Sets optional <code>count</code> parameter. Restricts the number of tweets to return, up to a maximum of 100.
	 * Defaults to 15.
	 *
	 * @param count number of tweets to return
	 * @return The same SearchParameters for additional configuration.
	 */
	public SearchParameters count(int count) {
		this.count = count;
		return this;
	}

	/**
	 * Sets optional <code>until</code> parameter. Restricts search to tweets generated before the given date.
	 *
	 * @param untilDate date to search until
	 * @return The same SearchParameters for additional configuration.
	 */
	public SearchParameters until(Date untilDate) {
		this.untilDate = untilDate;
		return this;
	}

	/**
	 * Sets optional <code>since_id</code> parameter. Restricts search results with an ID greater than the specified one.
	 *
	 * @param sinceId The minimum {@link org.springframework.social.twitter.api.Tweet} ID to return in the results
	 * @return The same SearchParameters for additional configuration.
	 */
	public SearchParameters sinceId(long sinceId) {
		this.sinceId = sinceId;
		return this;
	}

	/**
	 * Sets optional <code>max_id</code> parameter. Restricts search results with an ID less or equel than the specified one.
	 *
	 * @param maxId The maximum {@link org.springframework.social.twitter.api.Tweet} ID to return in the results
	 * @return The same SearchParameters for additional configuration.
	 */
	public SearchParameters maxId(long maxId) {
		this.maxId = maxId;
		return this;
	}

	/**
	 * Sets optional <code>include_entities</code> parameter. The entities node will be excluded when set to false.
	 *
	 * @param includeEntities Include entities node
	 * @return The same SearchParameters for additional configuration.
	 */
	public SearchParameters includeEntities(boolean includeEntities) {
		this.includeEntities = includeEntities;
		return this;
	}

	/**
	 * Returns query, <code>q</code> parameter
	 *
	 * @return query
	 */
	public String getQuery() {
		return this.query;
	}

	/**
	 * Returns <code>geo_code</code> search parameter
	 *
	 * @return geoCode
	 */
	public GeoCode getGeoCode() {
		return this.geoCode;
	}

	/**
	 * Returns <code>lang</code> search parameter
	 *
	 * @return lang
	 */
	public String getLang() {
		return this.lang;
	}

	/**
	 * Returns <code>locale</code> search parameter
	 *
	 * @return locale
	 */
	public String getLocale() {
		return this.locale;
	}

	/**
	 * Returns <code>result_type</code> search parameter
	 *
	 * @return result_type
	 */
	public ResultType getResultType() {
		return this.resultType;
	}

	/**
	 * Returns <code>count</code> search parameter
	 *
	 * @return count
	 */
	public Integer getCount() {
		return this.count;
	}

	/**
	 * Returns <code>until</code> search parameter
	 *
	 * @return until
	 */
	public Date getUntil() {
		return this.untilDate;
	}

	/**
	 * Returns <code>since_id</code> search parameter
	 *
	 * @return since_id
	 */
	public Long getSinceId() {
		return this.sinceId;
	}

	/**
	 * Returns <code>max_id</code> search parameter
	 *
	 * @return max_id
	 */
	public Long getMaxId() {
		return this.maxId;
	}

	/**
	 * Returns <code>include_entities</code> search parameter
	 *
	 * @return include_entities
	 */
	public boolean isIncludeEntities() {
		return this.includeEntities;
	}

	/**
	 * ResultType enumeration. Used by setResultType/getResultType
	 */
	public static enum ResultType {
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		SearchParameters other = (SearchParameters) o;
		return other.query.equals(this.query)
			&& other.count == this.count
			&& bothNullOrEquals(other.geoCode, this.geoCode)
			&& other.includeEntities == this.includeEntities
			&& bothNullOrEquals(other.lang, this.lang)
			&& bothNullOrEquals(other.locale, this.locale)
			&& bothNullOrEquals(other.maxId, this.maxId)
			&& bothNullOrEquals(other.resultType, this.resultType)
			&& bothNullOrEquals(other.sinceId, this.sinceId)
			&& bothNullOrEquals(other.untilDate, this.untilDate);
	}
	
	private boolean bothNullOrEquals(Object o1, Object o2) {
		return (o1 == null && o2 == null) || (o1 != null && o2 != null && o1.equals(o2));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((sinceId == null) ? 0 : sinceId.hashCode());
		result = prime * result + ((maxId == null) ? 0 : maxId.hashCode());
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
		result = prime * result + ((geoCode == null) ? 0 : geoCode.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((resultType == null) ? 0 : resultType.hashCode());
		result = prime * result + ((untilDate == null) ? 0 : untilDate.hashCode());
		result = prime * result + (includeEntities ? 0 : 1);
		return result;
	}
}
