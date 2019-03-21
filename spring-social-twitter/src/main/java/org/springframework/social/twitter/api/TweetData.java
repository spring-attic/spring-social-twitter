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

import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TweetData {

	private String message;

	private Long inReplyToStatusId;
	
	private Float latitude;
	
	private Float longitude;
	
	private boolean displayCoordinates;
	
	private Resource mediaResource;

	private String placeId;
		
	public TweetData(String message) {
		this.message = message;
	}
	
	public TweetData inReplyToStatus(long statusId) {
		this.inReplyToStatusId = statusId;
		return this;
	}
	
	public TweetData atLocation(float longitude, float latitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		return this;
	}
	
	public TweetData atPlace(String placeId) {
		this.placeId = placeId;
		return this;
	}
	
	public TweetData displayCoordinates(boolean displayCoordinates) {
		this.displayCoordinates = displayCoordinates;
		return this;
	}
	
	public TweetData withMedia(Resource mediaResource) {
		this.mediaResource = mediaResource;
		return this;
	}
	
	public boolean hasMedia() {
		return mediaResource != null;
	}
	
	public MultiValueMap<String, Object> toUploadMediaParameters() {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		if (mediaResource != null) {
			params.set("media", mediaResource);
		}
		return params;
	}
	
	/**
	 * Produce request parameters for a Tweet.
	 * @return a {@link MultiValueMap} of request parameters.
	 * @deprecated Use {{@link #toTweetParameters()} instead. Deprecated because the "media" parameter no longer applies in Twitter's API, but kept here for backward compatibility.
	 */
	@Deprecated
	public MultiValueMap<String, Object> toRequestParameters() {
		MultiValueMap<String, Object> tweetParameters = toTweetParameters();
		if (mediaResource != null) {		
			tweetParameters.set("media", mediaResource);		
		}
		return tweetParameters;
	}

	/**
	 * Produce request parameters for a Tweet.
	 * @return a {@link MultiValueMap} of request parameters.
	 */
	public MultiValueMap<String, Object> toTweetParameters() {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.set("status", message);
		if (inReplyToStatusId != null) {
			params.set("in_reply_to_status_id", inReplyToStatusId.toString());
		}
		if (latitude != null && longitude != null) {
			params.set("lat", latitude.toString());
			params.set("long", longitude.toString());
		}
		if (displayCoordinates) {
			params.set("display_coordinates", "true");
		}
		if (placeId != null) {
			params.set("place_id", placeId);
		}
		return params;
	}

}
