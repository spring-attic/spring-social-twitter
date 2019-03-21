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

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class OEmbedOptions {

	private Integer maxWidth;
	private boolean hideMedia;
	private boolean hideThread;
	private boolean omitScript;
	private String align;
	private String related;
	private String lang;
	
	/**
	 * Specify the maximum width in pixels that the embedded tweet should be rendered at.
	 * @param maxWidth the maximum width. Must be between 250 and 550.
	 * @return the same OEmbedOptions for additional configuration
	 */
	public OEmbedOptions maxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
		return this;
	}

	/**
	 * Specify that the embedded Tweet should not show images that were posted with the tweet.
	 * By default, images will be shown.
	 * @return the same OEmbedOptions for additional configuration
	 */
	public OEmbedOptions hideMedia() {
		this.hideMedia = true;
		return this;
	}

	/**
	 * Specify that the embedded Tweet should not show the original message if the tweet is a reply.
	 * By default, the thread will be shown.
	 * @return the same OEmbedOptions for additional configuration
	 */
	public OEmbedOptions hideThread() {
		this.hideThread = true;
		return this;
	}

	/**
	 * Specify that the embedded Tweet should not include a &lt;script&gt; element pointing to widgets.js.
	 * This avoids the script from being redundantly loaded in the case that 2 or more embedded tweets are rendered on a page.
	 * By default, the script will be included.
	 * @return the same OEmbedOptions for additional configuration
	 */
	public OEmbedOptions omitScript() {
		this.omitScript = true;
		return this;
	}
	
	/**
	 * Specifies how the embedded tweet should be aligned.
	 * @param align The alignment value. Either left, right, center, or none. Defaults to none.
	 * @return the same OEmbedOptions for additional configuration
	 */
	public OEmbedOptions align(String align) {
		this.align = align;
		return this;
	}
	
	/**
	 * Suggested Twitter accounts that might be related to the embedded tweet. 
	 * Used by Web Intents (https://dev.twitter.com/docs/intents) to render suggestions to the reader.
	 * @param related A comma-separated list of Twitter screen names to recommend.
	 * @return the same OEmbedOptions for additional configuration
	 */
	public OEmbedOptions related(String related) {
		this.related = related;
		return this;
	}
	
	/**
	 * Specifies the language code for the embedded tweet.
	 * @param lang The language code. Example: "fr"
	 * @return the same OEmbedOptions for additional configuration
	 */
	public OEmbedOptions language(String lang) {
		this.lang = lang;
		return this;
	}

	public MultiValueMap<String, String> toRequestParameters() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		if (maxWidth != null) {
			params.set("maxwidth", String.valueOf(maxWidth));
		}
		if (hideMedia) {
			params.set("hide_media", "true");
		}
		if (hideThread) {
			params.set("hide_thread", "true");
		}
		if (omitScript) {
			params.set("omit_script", "true");
		}
		if (align != null) {
			params.set("align", align);
		}
		if (related != null) {
			params.set("related", related);
		}
		if (lang != null) {
			params.set("lang", lang);
		}
		return params;
	}

}
