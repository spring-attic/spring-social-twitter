package org.springframework.social.twitter.api.impl;

import java.util.List;

import org.springframework.social.twitter.api.ExtendedMedia;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ExtendedEntitiesMixin extends TwitterObjectMixin {

	@JsonCreator
	public ExtendedEntitiesMixin(@JsonProperty("media") List<ExtendedMedia> media) {
	}
}
