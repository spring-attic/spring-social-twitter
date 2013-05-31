/*
 * Copyright 2013 the original author or authors.
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

import static org.springframework.social.twitter.api.impl.SearchParametersUtil.*;

import java.net.URI;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.URIBuilder;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

class ClientSearchTemplate extends AbstractOAuth2ApiBinding {

	public ClientSearchTemplate(String clientToken) {
		super(clientToken);
	}
	
	public SearchResults search(SearchParameters searchParameters) {
		MultiValueMap<String, String> parameters = buildQueryParametersFromSearchParameters(searchParameters);
		return getRestTemplate().getForObject(buildUri("search/tweets.json", parameters), SearchResults.class);		
	}
	
	@Override
	protected MappingJackson2HttpMessageConverter getJsonMessageConverter() {
		MappingJackson2HttpMessageConverter converter = super.getJsonMessageConverter();
		converter.setObjectMapper(new ObjectMapper().registerModule(new TwitterModule()));		
		return converter;
	}

	private URI buildUri(String path, MultiValueMap<String, String> parameters) {
		return URIBuilder.fromUri(API_URL_BASE + path).queryParams(parameters).build();
	}
	
	private static final String API_URL_BASE = "https://api.twitter.com/1.1/";

}
