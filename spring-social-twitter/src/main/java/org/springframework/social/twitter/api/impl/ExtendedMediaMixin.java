package org.springframework.social.twitter.api.impl;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ExtendedMediaMixin extends TwitterObjectMixin {
	@JsonCreator
	public ExtendedMediaMixin(@JsonProperty("id") Long id, @JsonProperty("id_str") String idStr,
			@JsonProperty("indices") List<Integer> indices, @JsonProperty("media_url") String mediaUrl,
			@JsonProperty("media_url_https") String mediaUrlHttps, @JsonProperty("url") String url,
			@JsonProperty("display_url") String displayUrl, @JsonProperty("expanded_url") String expandedUrl,
			@JsonProperty("type") String type, @JsonProperty("sizes") Object sizes,
			@JsonProperty("video_info") Map<String, Object> videoInfo,
			@JsonProperty("additional_media_info") Object additionalMediaInfo) {
	}
}
