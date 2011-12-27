/*
 * Copyright 2011 the original author or authors.
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

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.social.twitter.api.Entities;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Custom Jackson deserializer for tweets. Tweets can't be simply mapped like other Twitter model objects because the JSON structure
 * varies between the search API and the timeline API. This deserializer determine which structure is in play and creates a tweet from it.
 *
 * @author Craig Walls
 */
class TweetDeserializer extends JsonDeserializer<Tweet>
{
    @Override
    public Tweet deserialize(final JsonParser jp, final DeserializationContext ctx) throws IOException
    {
        final JsonNode tree = jp.readValueAsTree();
        if (null == tree || tree.isMissingNode() || tree.isNull())
        {
            return null;
        }
        final Tweet tweet = this.deserialize(tree);
        jp.skipChildren();
        return tweet;
    }


    public Tweet deserialize(final JsonNode tree) throws IOException
    {
        if (null == tree || tree.isMissingNode() || tree.isNull())
        {
            return null;
        }

        final Long id = tree.path("id").asLong();
        final String text = tree.path("text").asText();
        if (null == id || null == text)
        {
            throw new IOException("Unable to determine tweet id or text.");
        }

        final JsonNode fromUserNode = tree.get("user");
        final String fromScreenName;
        final long fromId;
        final String fromImageUrl;
        String dateFormat = TIMELINE_DATE_FORMAT;
        if (fromUserNode != null)
        {
            fromScreenName = fromUserNode.get("screen_name").asText();
            fromId = fromUserNode.get("id").asLong();
            fromImageUrl = fromUserNode.get("profile_image_url").asText();
        }
        else
        {
            fromScreenName = tree.get("from_user").asText();
            fromId = tree.get("from_user_id").asLong();
            fromImageUrl = tree.get("profile_image_url").asText();
            dateFormat = SEARCH_DATE_FORMAT;
        }
        Date createdAt = toDate(tree.get("created_at").asText(), new SimpleDateFormat(dateFormat, Locale.ENGLISH));
        Long toUserId = toLong(tree.get("in_reply_to_user_id"));
        Long inReplyToStatusId = toLong(tree.path("in_reply_to_status_id"));
        String toUserName = toString(tree.path("in_reply_to_screen_name"));
        String languageCode = toString(tree.path("iso_language_code"));
        String source = toString(tree.path("source"));
        Long retweetedCount = toLong(tree.path("retweet_count"));
        boolean retweetedByMe = toBoolean(tree.path("current_user_retweet"));
        boolean truncated = toBoolean(tree.path("truncated"));
        boolean favorited = toBoolean(tree.path("favorited"));
        JsonNode retweetedNode = tree.get("retweeted_status");
        Tweet retweetedStatus = retweetedNode != null ? this.deserialize(retweetedNode) : null;
        Entities entities = toEntities(tree.get("entities"));
        TwitterProfile user = toProfile(fromUserNode);
        return new Tweet(id, text, createdAt, fromId, fromScreenName, fromImageUrl, languageCode, toUserId, toUserName, inReplyToStatusId, source, retweetedCount != null ? retweetedCount.intValue() : 0, retweetedStatus, retweetedByMe, truncated, favorited, entities, retweetedStatus, user);
    }


    private ObjectMapper createMapper()
    {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new TwitterModule());
        return mapper;
    }


    private TwitterProfile toProfile(final JsonNode node) throws IOException
    {
        if (null == node || node.isNull() || node.isMissingNode())
        {
            return null;
        }
        final ObjectMapper mapper = this.createMapper();
        return mapper.readValue(node, TwitterProfile.class);
    }


    private Entities toEntities(final JsonNode node) throws IOException
    {
        if (null == node || node.isNull() || node.isMissingNode())
        {
            return null;
        }
        final ObjectMapper mapper = this.createMapper();
        return mapper.readValue(node, Entities.class);
    }


    private boolean toBoolean(final JsonNode node)
    {
        if (null == node || node.isNull() || node.isMissingNode())
        {
            return false;
        }
        return node.asBoolean(false);
    }


    private Long toLong(final JsonNode node)
    {
        if (null == node || node.isNull() || node.isMissingNode())
        {
            return null;
        }
        return node.asLong();
    }


    private String toString(final JsonNode node)
    {
        if (null == node || node.isNull() || node.isMissingNode())
        {
            return null;
        }
        return node.asText();
    }


    private Date toDate(String dateString, DateFormat dateFormat)
    {
        if (dateString == null || dateString.isEmpty())
        {
            return null;
        }

        try
        {
            return dateFormat.parse(dateString);
        }
        catch (ParseException e)
        {
            return null;
        }
    }


    private static final String TIMELINE_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

    private static final String SEARCH_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
}
