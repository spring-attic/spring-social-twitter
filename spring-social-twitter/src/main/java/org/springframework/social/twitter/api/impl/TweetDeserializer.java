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

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.twitter.api.Entities;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

/**
 * Custom Jackson deserializer for tweets. Tweets can't be simply mapped like other Twitter model objects because the JSON structure
 * varies between the search API and the timeline API. This deserializer determine which structure is in play and creates a tweet from it.
 * @author Craig Walls
 */
class TweetDeserializer extends JsonDeserializer<Tweet> {

	@Override
	public Tweet deserialize(final JsonParser jp, final DeserializationContext ctx) throws IOException {
		final JsonNode tree = jp.readValueAsTree();
		if (null == tree || tree.isMissingNode() || tree.isNull()) {
			return null;
		}
		final Tweet tweet = this.deserialize(tree);
		jp.skipChildren();
		return tweet;
	}


	public Tweet deserialize(JsonNode tree) throws IOException, JsonProcessingException {
		final long id = tree.path("id").asLong();
		final String text = tree.path("text").asText();
		if (id <= 0 || text == null || text.isEmpty()) {
			return null;
		}
		JsonNode fromUserNode = tree.get("user");
		String dateFormat = TIMELINE_DATE_FORMAT;
		String fromScreenName = fromUserNode.get("screen_name").asText();
		long fromId = fromUserNode.get("id").asLong();
		String fromImageUrl = fromUserNode.get("profile_image_url").asText(); 
		Date createdAt = toDate(tree.get("created_at").asText(), new SimpleDateFormat(dateFormat, Locale.ENGLISH));
		String source = tree.get("source").asText();
		JsonNode toUserIdNode = tree.get("in_reply_to_user_id");
		Long toUserId = toUserIdNode != null ? toUserIdNode.getLongValue() : null;
		JsonNode languageCodeNode = tree.get("iso_language_code");
		String languageCode = languageCodeNode != null ? languageCodeNode.asText() : null;
		Tweet tweet = new Tweet(id, text, createdAt, fromScreenName, fromImageUrl, toUserId, fromId, languageCode, source);
		JsonNode inReplyToStatusIdNode = tree.get("in_reply_to_status_id");
		Long inReplyToStatusId = inReplyToStatusIdNode != null && !inReplyToStatusIdNode.isNull() ? inReplyToStatusIdNode.getLongValue() : null;
		tweet.setInReplyToStatusId(inReplyToStatusId);
		JsonNode inReplyToUserIdNode = tree.get("in_reply_to_user_id");
		Long inReplyUsersId = inReplyToUserIdNode != null && !inReplyToUserIdNode.isNull() ? inReplyToUserIdNode.getLongValue() : null;
		tweet.setInReplyToUserId(inReplyUsersId);
		tweet.setInReplyToScreenName(tree.path("in_reply_to_screen_name").getTextValue());
		JsonNode retweetCountNode = tree.get("retweet_count");
		Integer retweetCount = retweetCountNode != null && !retweetCountNode.isNull() ? retweetCountNode.getIntValue() : null;
		tweet.setRetweetCount(retweetCount);
		JsonNode retweetedNode = tree.get("retweeted");
		JsonNode retweetedStatusNode = tree.get("retweeted_status");
		boolean retweeted = retweetedNode != null && !retweetedNode.isNull() ? retweetedNode.getBooleanValue() : false;
		tweet.setRetweeted(retweeted);
		Tweet retweetedStatus = retweetedStatusNode != null ? this.deserialize(retweetedStatusNode) : null;
		tweet.setRetweetedStatus(retweetedStatus);
		JsonNode favoritedNode = tree.get("favorited");
		boolean favorited = favoritedNode != null && !favoritedNode.isNull() ? favoritedNode.getBooleanValue() : false;
		tweet.setFavorited(favorited);
		Entities entities = toEntities(tree.get("entities"));
		tweet.setEntities(entities);
		TwitterProfile user = toProfile(fromUserNode);
		tweet.setUser(user);
		return tweet;
	}

	private ObjectMapper createMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new TwitterModule());
		return mapper;
	}
	
	private Date toDate(String dateString, DateFormat dateFormat) {
		if (dateString == null) {
			return null;
		}
	
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	private Entities toEntities(final JsonNode node) throws IOException {
		if (null == node || node.isNull() || node.isMissingNode()) {
			return null;
		}
		final ObjectMapper mapper = this.createMapper();
		return mapper.readValue(node, Entities.class);
	}

	private TwitterProfile toProfile(final JsonNode node) throws IOException {
		if (null == node || node.isNull() || node.isMissingNode()) {
			return null;
		}
		final ObjectMapper mapper = this.createMapper();
		return mapper.readValue(node, TwitterProfile.class);
	}


	private static final String TIMELINE_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

}
