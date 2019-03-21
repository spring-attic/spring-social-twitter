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

/**
 * Represents an oEmbed Tweet object.
 * Useful for embedding content in a page using provider-defined HTML and/or data.
 * See the oEmbed specification at http://oembed.com/ and Twitter's https://dev.twitter.com/docs/api/1.1/get/statuses/oembed resource for more details.
 * @author Craig Walls
 */
public class OEmbedTweet extends TwitterObject {

	private String type;
	private String version;
	
	private String authorName;
	private String authorUrl;
	private String providerName;
	private String providerUrl;
	private long cacheAge;
	private Integer height;
	private Integer width;
	private String html;
	private String url;
	
	public String getType() {
		return type;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	
	public String getAuthorUrl() {
		return authorUrl;
	}
	
	public String getProviderName() {
		return providerName;
	}
	
	public String getProviderUrl() {
		return providerUrl;
	}
	
	public long getCacheAge() {
		return cacheAge;
	}
	
	public Integer getHeight() {
		return height;
	}
	
	public Integer getWidth() {
		return width;
	}
	
	public String getHtml() {
		return html;
	}
	
	public String getUrl() {
		return url;
	}

}
