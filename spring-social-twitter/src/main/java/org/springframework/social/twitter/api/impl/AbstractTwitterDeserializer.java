package org.springframework.social.twitter.api.impl;

import java.io.IOException;

import org.springframework.social.twitter.api.TwitterProfile;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

abstract class AbstractTwitterDeserializer<T> extends JsonDeserializer<T> {

	protected ObjectMapper createMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new TwitterModule());
		return mapper;
	}

	protected TwitterProfile toProfile(final JsonNode node) throws IOException {
		if (null == node || node.isNull() || node.isMissingNode()) {
			return null;
		}
		final ObjectMapper mapper = this.createMapper();
		return mapper.reader(TwitterProfile.class).readValue(node);
	}
}
