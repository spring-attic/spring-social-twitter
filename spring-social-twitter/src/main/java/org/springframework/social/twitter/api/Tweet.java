/*
 * Copyright 2010 the original author or authors.
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
package org.springframework.social.twitter.api;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a Twitter status update (e.g., a "tweet").
 *
 * @author Craig Walls
 */
public class Tweet implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final long id;

    private final String text;

    private final Date createdAt;

    private final String fromUser;

    private final String profileImageUrl;

    private final Long inReplyToStatusId;

    private final Long inReplyToUserId;

    private final String inReplyToScreenName;

    private final long fromUserId;

    private final String languageCode;

    private final String source;

    private final Tweet retweetedStatus;

    private final int retweetCount;

    private final boolean retweetedByMe;

    private final boolean truncated;

    private final boolean favorited;

    private final Entities entities;

    private final TwitterProfile user;


    public Tweet(long id, String text, Date createdAt, long fromUserId, String fromUser, String profileImageUrl, String languageCode, Long toUserId, String toUserName, Long inReplyToStatusId, String source, int retweetCount, Tweet retweetedTweet, boolean retweetedByMe, boolean truncated, boolean favorited, Entities entities, Tweet retweetedStatus, TwitterProfile user)
    {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.fromUser = fromUser;
        this.profileImageUrl = profileImageUrl;
        this.inReplyToUserId = toUserId;
        this.inReplyToScreenName = toUserName;
        this.inReplyToStatusId = inReplyToStatusId;
        this.fromUserId = fromUserId;
        this.languageCode = languageCode;
        this.source = source;
        this.retweetCount = retweetCount;
        this.retweetedByMe = retweetedByMe;
        this.retweetedStatus = retweetedStatus;
        this.truncated = truncated;
        this.favorited = favorited;
        this.entities = entities;
        this.user = user;
    }


    public String getText()
    {
        return text;
    }


    public TwitterProfile getUser()
    {
        return this.user;
    }


    public Date getCreatedAt()
    {
        return createdAt;
    }


    public String getFromUser()
    {
        return fromUser;
    }


    public long getId()
    {
        return id;
    }


    public String getProfileImageUrl()
    {
        return profileImageUrl;
    }


    public Long getToUserId()
    {
        return this.inReplyToUserId;
    }


    public boolean hasMentions()
    {
        if (this.entities == null)
        {
            return false;
        }
        return !this.entities.getMentions().isEmpty();
    }


    public boolean isRetweet()
    {
        return this.retweetedStatus != null;
    }


    public Tweet getRetweetedStatus()
    {
        return this.retweetedStatus;
    }


    public long getFromUserId()
    {
        return fromUserId;
    }


    public String getInReplyToScreenName()
    {
        return this.inReplyToScreenName;
    }


    public Long getInReplyToUserId()
    {
        return this.inReplyToUserId;
    }


    public Long getInReplyToStatusId()
    {
        return inReplyToStatusId;
    }


    public String getLanguageCode()
    {
        return languageCode;
    }


    public String getSource()
    {
        return source;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Tweet tweet = (Tweet) o;

        if (favorited != tweet.favorited)
        {
            return false;
        }
        if (fromUserId != tweet.fromUserId)
        {
            return false;
        }
        if (id != tweet.id)
        {
            return false;
        }
        if (retweetCount != tweet.retweetCount)
        {
            return false;
        }
        if (retweetedByMe != tweet.retweetedByMe)
        {
            return false;
        }
        if (truncated != tweet.truncated)
        {
            return false;
        }
        if (createdAt != null ? !createdAt.equals(tweet.createdAt) : tweet.createdAt != null)
        {
            return false;
        }
        if (entities != null ? !entities.equals(tweet.entities) : tweet.entities != null)
        {
            return false;
        }
        if (fromUser != null ? !fromUser.equals(tweet.fromUser) : tweet.fromUser != null)
        {
            return false;
        }
        if (inReplyToScreenName != null ? !inReplyToScreenName.equals(tweet.inReplyToScreenName) : tweet.inReplyToScreenName != null)
        {
            return false;
        }
        if (inReplyToStatusId != null ? !inReplyToStatusId.equals(tweet.inReplyToStatusId) : tweet.inReplyToStatusId != null)
        {
            return false;
        }
        if (inReplyToUserId != null ? !inReplyToUserId.equals(tweet.inReplyToUserId) : tweet.inReplyToUserId != null)
        {
            return false;
        }
        if (languageCode != null ? !languageCode.equals(tweet.languageCode) : tweet.languageCode != null)
        {
            return false;
        }
        if (profileImageUrl != null ? !profileImageUrl.equals(tweet.profileImageUrl) : tweet.profileImageUrl != null)
        {
            return false;
        }
        if (retweetedStatus != null ? !retweetedStatus.equals(tweet.retweetedStatus) : tweet.retweetedStatus != null)
        {
            return false;
        }
        if (source != null ? !source.equals(tweet.source) : tweet.source != null)
        {
            return false;
        }
        if (text != null ? !text.equals(tweet.text) : tweet.text != null)
        {
            return false;
        }
        if (user != null ? !user.equals(tweet.user) : tweet.user != null)
        {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (fromUser != null ? fromUser.hashCode() : 0);
        result = 31 * result + (profileImageUrl != null ? profileImageUrl.hashCode() : 0);
        result = 31 * result + (inReplyToStatusId != null ? inReplyToStatusId.hashCode() : 0);
        result = 31 * result + (inReplyToUserId != null ? inReplyToUserId.hashCode() : 0);
        result = 31 * result + (inReplyToScreenName != null ? inReplyToScreenName.hashCode() : 0);
        result = 31 * result + (int) (fromUserId ^ (fromUserId >>> 32));
        result = 31 * result + (languageCode != null ? languageCode.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (retweetedStatus != null ? retweetedStatus.hashCode() : 0);
        result = 31 * result + retweetCount;
        result = 31 * result + (retweetedByMe ? 1 : 0);
        result = 31 * result + (truncated ? 1 : 0);
        result = 31 * result + (favorited ? 1 : 0);
        result = 31 * result + (entities != null ? entities.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
