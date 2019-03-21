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

/**
 * Defines the base parameters for a user or filtered stream.
 * Subclassed by {@link UserStreamParameters} and {@link FilterStreamParameters}.
 * @author Craig Walls
 */
abstract class AbstractStreamParameters {
	
	protected StringBuffer track = new StringBuffer();
	
	protected StringBuffer locations = new StringBuffer();
	
	protected boolean stallWarnings = false;

	protected String language;

	public AbstractStreamParameters() {}
	
	/**
	 * Add tracking keywords to the filter.
	 * Does not replace any existing tracking keywords in the filter.
	 * @param track the keywords to track.
	 * @return this StreamFilter for building up filter parameters.
	 */
	public AbstractStreamParameters track(String track) {
		if (this.track.length() > 0) {
			this.track.append(',');
		}
		this.track.append(track);
		return this;
	}
	
	/**
	 * Add a location to the filter
	 * Does not replace any existing locations in the filter.
	 * @param west the longitude of the western side of the location's bounding box.
	 * @param south the latitude of the southern side of the location's bounding box.
	 * @param east the longitude of the eastern side of the location's bounding box.
	 * @param north the latitude of the northern side of the location's bounding box.
	 * @return this StreamFilter for building up filter parameters.
	 */
	public AbstractStreamParameters addLocation(float west, float south, float east, float north) {
		if (locations.length() > 0) {
			locations.append(',');
		}
		locations.append(west).append(',').append(south).append(',');
		locations.append(east).append(',').append(north).append(',');
		return this;
	}
	
	public AbstractStreamParameters stallWarnings(boolean stallWarnings) {
		this.stallWarnings = stallWarnings;
		return this;
	}

	public AbstractStreamParameters language(String language) {
		this.language = language;
		return this;
	}
	
	/**
	 * @return the track parameters as they'll be sent in the streaming request.
	 */
	public String getTrackParameterValue() {
		return track.toString();
	}
	
	/**
	 * @return the locations parameters as they'll be sent in the streaming request.
	 */
	public String getLocationsParameterValue() {
		return locations.toString();
	}
	
	public MultiValueMap<String, String> toParameterMap() {
		MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<String, String>();
		if (track != null && track.length() > 0) {
			parameterMap.set("track", track.toString());
		}
		if (locations != null && locations.length() > 0) {
			parameterMap.set("locations", locations.toString());
		}
		if (stallWarnings) {
			parameterMap.set("stall_warnings", "true");
		}
		if (language != null) {
			parameterMap.set("language", language);
		}
		return parameterMap;
	}
}
