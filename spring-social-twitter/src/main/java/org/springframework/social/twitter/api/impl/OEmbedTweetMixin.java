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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class OEmbedTweetMixin extends TwitterObjectMixin {

	@JsonProperty("type")
	private String type;

	@JsonProperty("version")
	private String version;
	
	@JsonProperty("author_name")
	private String authorName;

	@JsonProperty("author_url")
	private String authorUrl;

	@JsonProperty("provider_name")
	private String providerName;
	
	@JsonProperty("provider_url")
	private String providerUrl;
	
	@JsonProperty("cache_age")
	private long cacheAge;
	
	@JsonProperty("height")
	private Integer height;
	
	@JsonProperty("width")
	private Integer width;
	
	@JsonProperty("html")
	private String html;
	
	@JsonProperty("url")
	private String url;

}
