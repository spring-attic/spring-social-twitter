package org.springframework.social.twitter.api;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A collection of related results.
 * <p/>
 * User: bowen
 * Date: 12/26/11
 */
public class RelatedResult implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonProperty("results")
    private List<RelatedTweet> tweets = new LinkedList<RelatedTweet>();

    @JsonProperty("groupName")
    private String group = "";

    @JsonProperty("resultType")
    private String type = "";

    @JsonProperty("annotations")
    private RelatedAnnotation annotation = null;

    @JsonProperty("score")
    private float score = 0f;


    /**
     * JSON constructor.
     */
    public RelatedResult()
    {

    }


    public List<RelatedTweet> getTweets()
    {
        if (this.tweets == null)
        {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.tweets);
    }


    public String getGroup()
    {
        return group;
    }


    public String getType()
    {
        return type;
    }


    public float getScore()
    {
        return score;
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

        RelatedResult that = (RelatedResult) o;

        if (Float.compare(that.score, score) != 0)
        {
            return false;
        }
        if (annotation != null ? !annotation.equals(that.annotation) : that.annotation != null)
        {
            return false;
        }
        if (group != null ? !group.equals(that.group) : that.group != null)
        {
            return false;
        }
        if (tweets != null ? !tweets.equals(that.tweets) : that.tweets != null)
        {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null)
        {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = tweets != null ? tweets.hashCode() : 0;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (annotation != null ? annotation.hashCode() : 0);
        result = 31 * result + (score != +0.0f ? Float.floatToIntBits(score) : 0);
        return result;
    }
}
