/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
 * 
 * @author Craig Walls
 */
class TweetDeserializer extends JsonDeserializer<Tweet> {
    private static final String TIMELINE_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

    @Override
    public Tweet deserialize(final JsonParser jp, final DeserializationContext ctx) throws IOException {
        final JsonNode node = jp.readValueAs(JsonNode.class);
        if (null == node || node.isMissingNode() || node.isNull())
            return null;
        final Tweet tweet = this.deserialize(node);
        jp.skipChildren();
        return tweet;
    }

    public Tweet deserialize(JsonNode node) throws IOException, JsonProcessingException {
        final long id = node.path("id").asLong();
        final String text = node.path("text").asText();
        if (id <= 0 || text == null || text.isEmpty())
            return null;

        final Tweet tweet = readTweetFromJson(node, id, text);
        readReplyParameters(node, tweet);
        readRetweetParameters(node, tweet);
        readFavoriteParameters(node, tweet);
        readTruncatedParameters(node, tweet);
        readSensitivityParameters(node, tweet);
        return tweet;
    }

    @SuppressWarnings("deprecation")
    private Tweet readTweetFromJson(JsonNode node, Long id, String text) throws IOException {
        final JsonNode fromUserNode = node.get("user");
        final String dateFormat = TIMELINE_DATE_FORMAT;

        String fromScreenName = null;
        final JsonNode fromScreenNameNode = fromUserNode.get("screen_name");
        if (fromScreenNameNode != null)
            fromScreenName = fromScreenNameNode.asText();

        Long fromId = null;
        final JsonNode fromIdNode = fromUserNode.get("id");
        if (fromIdNode != null)
            fromId = fromIdNode.asLong();

        String fromImageUrl = null;
        final JsonNode fromImageUrlNode = fromUserNode.get("profile_image_url");
        if (fromImageUrlNode != null)
            fromImageUrl = fromImageUrlNode.asText();

        final Date createdAt = toDate(node.get("created_at").asText(), new SimpleDateFormat(dateFormat, Locale.ENGLISH));
        final String source = node.get("source").asText();

        Long toUserId = null;
        final JsonNode toUserIdNode = node.get("in_reply_to_user_id");
        if (toUserIdNode != null)
            toUserId = toUserIdNode.asLong();

        JsonNode languageCodeNode = node.get("lang");
        String languageCode = languageCodeNode != null && !languageCodeNode.isNull() ? languageCodeNode.asText() : null;

        final Tweet tweet = new Tweet(id, text, createdAt, fromScreenName, fromImageUrl, toUserId, fromId, languageCode, source);
        readUserAndEntities(node, fromUserNode, text, tweet);
        return tweet;
    }

    private void readUserAndEntities(JsonNode node, JsonNode fromUserNode, String text, Tweet tweet) throws IOException {
        final Entities entities = toEntities(node.get("entities"), text);
        tweet.setEntities(entities);

        final Entities extendedEntities = toEntities(node.get("extended_entities"), text);
        tweet.setExtendedEntities(extendedEntities);

        final TwitterProfile user = toProfile(fromUserNode);
        tweet.setUser(user);
    }

    private void readFavoriteParameters(JsonNode node, Tweet tweet) {
        final JsonNode favoritedNode = node.get("favorited");
        final boolean favorited = favoritedNode != null && !favoritedNode.isNull() ? favoritedNode.asBoolean() : false;
        tweet.setFavorited(favorited);

        final JsonNode favoriteCountNode = node.get("favorite_count");
        final Integer favoriteCount = favoriteCountNode != null && !favoriteCountNode.isNull() ? favoriteCountNode.asInt() : null;
        tweet.setFavoriteCount(favoriteCount);
    }

    private void readTruncatedParameters(JsonNode node, Tweet tweet) {
        final JsonNode truncatedNode = node.get("truncated");
        if (truncatedNode != null)
            tweet.setTruncated(truncatedNode.asBoolean());
    }

    private void readSensitivityParameters(JsonNode node, Tweet tweet) {
        final JsonNode possiblySensitiveNode = node.get("possibly_sensitive");
        if (possiblySensitiveNode != null)
            tweet.setPossiblySensitive(possiblySensitiveNode.asBoolean());
    }

    private void readRetweetParameters(JsonNode node, Tweet tweet) throws JsonProcessingException, IOException {
        final JsonNode retweetCountNode = node.get("retweet_count");
        final Integer retweetCount = retweetCountNode != null && !retweetCountNode.isNull() ? retweetCountNode.asInt() : null;
        tweet.setRetweetCount(retweetCount);

        final JsonNode retweetedNode = node.get("retweeted");
        final boolean retweeted = retweetedNode != null && !retweetedNode.isNull() ? retweetedNode.asBoolean() : false;
        tweet.setRetweeted(retweeted);

        final JsonNode retweetedStatusNode = node.get("retweeted_status");
        final Tweet retweetedStatus = retweetedStatusNode != null ? this.deserialize(retweetedStatusNode) : null;
        tweet.setRetweetedStatus(retweetedStatus);
    }

    private void readReplyParameters(JsonNode node, Tweet tweet) {
        final JsonNode inReplyToStatusIdNode = node.get("in_reply_to_status_id");
        final Long inReplyToStatusId = inReplyToStatusIdNode != null && !inReplyToStatusIdNode.isNull() ? inReplyToStatusIdNode.asLong() : null;
        tweet.setInReplyToStatusId(inReplyToStatusId);

        final JsonNode inReplyToUserIdNode = node.get("in_reply_to_user_id");
        final Long inReplyUsersId = inReplyToUserIdNode != null && !inReplyToUserIdNode.isNull() ? inReplyToUserIdNode.asLong() : null;
        tweet.setInReplyToUserId(inReplyUsersId);

        final JsonNode inReplyToScreenNameNode = node.get("in_reply_to_screen_name");
        final String inReplyToScreenName =
            inReplyToScreenNameNode != null && !inReplyToScreenNameNode.isNull() ? inReplyToScreenNameNode.asText() : null;
        tweet.setInReplyToScreenName(inReplyToScreenName);
    }

    private ObjectMapper createMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new TwitterModule());
        return mapper;
    }

    private Date toDate(String dateString, DateFormat dateFormat) {
        if (dateString == null)
            return null;

        try {
            return dateFormat.parse(dateString);
        }
        catch (final ParseException e) {
            return null;
        }
    }

    // passing in text to fetch ticker symbol pseudo-entities
    @SuppressWarnings("deprecation")
    private Entities toEntities(final JsonNode node, String text) throws IOException {
        if (null == node || node.isNull() || node.isMissingNode())
            return null;
        final ObjectMapper mapper = createMapper();
        final Entities entities = mapper.reader(Entities.class).readValue(node);
        extractTickerSymbolEntitiesFromText(text, entities);
        return entities;
    }

    private void extractTickerSymbolEntitiesFromText(String text, Entities entities) {
        final Pattern pattern = Pattern.compile("\\$[A-Za-z]+");
        final Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            final MatchResult matchResult = matcher.toMatchResult();
            final String tickerSymbol = matchResult.group().substring(1);
            final String url = "https://twitter.com/search?q=%24" + tickerSymbol + "&src=ctag";
            entities.getTickerSymbols().add(new TickerSymbolEntity(tickerSymbol, url, new int[] {matchResult.start(), matchResult.end()}));
        }
    }

    @SuppressWarnings("deprecation")
    private TwitterProfile toProfile(final JsonNode node) throws IOException {
        if (null == node || node.isNull() || node.isMissingNode())
            return null;
        final ObjectMapper mapper = createMapper();
        return mapper.reader(TwitterProfile.class).readValue(node);
    }

}