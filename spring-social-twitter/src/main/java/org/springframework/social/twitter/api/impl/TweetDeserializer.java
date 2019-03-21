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

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.social.twitter.api.Entities;
import org.springframework.social.twitter.api.TickerSymbolEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Custom Jackson deserializer for tweets. Tweets can't be simply mapped like other Twitter model objects because the JSON structure
 * varies between the search API and the timeline API. This deserializer determine which structure is in play and creates a tweet from it.
 * @author Craig Walls
 */
class TweetDeserializer extends JsonDeserializer<Tweet> {

	@Override
	public Tweet deserialize(final JsonParser jp, final DeserializationContext ctx) throws IOException {
		final JsonNode node = jp.readValueAs(JsonNode.class);
		if (null == node || node.isMissingNode() || node.isNull()) {
			return null;
		}
		final Tweet tweet = this.deserialize(node);
		jp.skipChildren();
		return tweet;
	}


	public Tweet deserialize(JsonNode node) throws IOException, JsonProcessingException {
		final String id = node.path("id").asText();
		final String text = node.path("text").asText();
		if (id == null  || text == null || text.isEmpty()) {
			return null;
		}
		JsonNode fromUserNode = node.get("user");
		String dateFormat = TIMELINE_DATE_FORMAT;
		String fromScreenName = fromUserNode.get("screen_name").asText();
		long fromId = fromUserNode.get("id").asLong();
		String fromImageUrl = fromUserNode.get("profile_image_url").asText(); 
		Date createdAt = toDate(node.get("created_at").asText(), new SimpleDateFormat(dateFormat, Locale.ENGLISH));
		String source = node.get("source").asText();
		JsonNode toUserIdNode = node.get("in_reply_to_user_id");
		Long toUserId = toUserIdNode != null ? toUserIdNode.asLong() : null;
		JsonNode languageCodeNode = node.get("lang");
		String languageCode = languageCodeNode != null && !languageCodeNode.isNull() ? languageCodeNode.asText() : null;
		Tweet tweet = new Tweet(id, text, createdAt, fromScreenName, fromImageUrl, toUserId, fromId, languageCode, source);
		JsonNode inReplyToStatusIdNode = node.get("in_reply_to_status_id");
		Long inReplyToStatusId = inReplyToStatusIdNode != null && !inReplyToStatusIdNode.isNull() ? inReplyToStatusIdNode.asLong() : null;
		tweet.setInReplyToStatusId(inReplyToStatusId);
		JsonNode inReplyToUserIdNode = node.get("in_reply_to_user_id");
		Long inReplyUsersId = inReplyToUserIdNode != null && !inReplyToUserIdNode.isNull() ? inReplyToUserIdNode.asLong() : null;
		tweet.setInReplyToUserId(inReplyUsersId);
		tweet.setInReplyToScreenName(node.path("in_reply_to_screen_name").asText());
		JsonNode retweetCountNode = node.get("retweet_count");
		Integer retweetCount = retweetCountNode != null && !retweetCountNode.isNull() ? retweetCountNode.asInt() : null;
		tweet.setRetweetCount(retweetCount);
		JsonNode retweetedNode = node.get("retweeted");
		JsonNode retweetedStatusNode = node.get("retweeted_status");
		boolean retweeted = retweetedNode != null && !retweetedNode.isNull() ? retweetedNode.asBoolean() : false;
		tweet.setRetweeted(retweeted);
		Tweet retweetedStatus = retweetedStatusNode != null ? this.deserialize(retweetedStatusNode) : null;
		tweet.setRetweetedStatus(retweetedStatus);
		JsonNode favoritedNode = node.get("favorited");
		boolean favorited = favoritedNode != null && !favoritedNode.isNull() ? favoritedNode.asBoolean() : false;
		tweet.setFavorited(favorited);
		JsonNode favoriteCountNode = node.get("favorite_count");
		Integer favoriteCount = favoriteCountNode != null && !favoriteCountNode.isNull() ? favoriteCountNode.asInt() : null;
		tweet.setFavoriteCount(favoriteCount);
		Entities entities = toEntities(node.get("entities"), text);
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

	// passing in text to fetch ticker symbol pseudo-entities
	private Entities toEntities(final JsonNode node, String text) throws IOException {
		if (null == node || node.isNull() || node.isMissingNode()) {
			return null;
		}
		final ObjectMapper mapper = this.createMapper();
		Entities entities = mapper.readerFor(Entities.class).readValue(node);
		extractTickerSymbolEntitiesFromText(text, entities);
		return entities;
	}

	private void extractTickerSymbolEntitiesFromText(String text, Entities entities) {
		Pattern pattern = Pattern.compile("\\$[A-Za-z]+");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			MatchResult matchResult = matcher.toMatchResult();
			String tickerSymbol = matchResult.group().substring(1);
			String url = "https://twitter.com/search?q=%24" + tickerSymbol + "&src=ctag";
			entities.getTickerSymbols().add(new TickerSymbolEntity(tickerSymbol, url, new int[] {matchResult.start(), matchResult.end()}));
		}
	}


	private TwitterProfile toProfile(final JsonNode node) throws IOException {
		if (null == node || node.isNull() || node.isMissingNode()) {
			return null;
		}
		final ObjectMapper mapper = this.createMapper();
		return mapper.readerFor(TwitterProfile.class).readValue(node);
	}


	private static final String TIMELINE_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

}
