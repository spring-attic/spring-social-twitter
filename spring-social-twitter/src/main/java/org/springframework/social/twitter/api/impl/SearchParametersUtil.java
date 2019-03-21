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
package org.springframework.social.twitter.api.impl;

import java.text.SimpleDateFormat;

import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

class SearchParametersUtil {

	public static MultiValueMap<String, String> buildQueryParametersFromSearchParameters(SearchParameters searchParameters) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("q", searchParameters.getQuery());
		if (searchParameters.getGeoCode() != null) {
			parameters.set("geocode", searchParameters.getGeoCode().toString());
		}
		if (searchParameters.getLang() != null) {
			parameters.set("lang", searchParameters.getLang());
		}
		if (searchParameters.getLocale() != null) {
			parameters.set("locale", searchParameters.getLocale());
		}
		if (searchParameters.getResultType() != null) {
			parameters.set("result_type", searchParameters.getResultType().toString());
		}
		parameters.set("count", searchParameters.getCount() != null ? String.valueOf(searchParameters.getCount()) : String.valueOf(DEFAULT_RESULTS_PER_PAGE));
		if (searchParameters.getUntil() != null) {
			parameters.set("until", new SimpleDateFormat("yyyy-MM-dd").format(searchParameters.getUntil()));
		}
		if (searchParameters.getSinceId() != null) {
			parameters.set("since_id", String.valueOf(searchParameters.getSinceId()));
		}
		if (searchParameters.getMaxId() != null) {
			parameters.set("max_id", String.valueOf(searchParameters.getMaxId()));
		}
		if (!searchParameters.isIncludeEntities()) {
			parameters.set("include_entities", "false");
		}
		return parameters;
	}

	public static final int DEFAULT_RESULTS_PER_PAGE = 50;
	
}
