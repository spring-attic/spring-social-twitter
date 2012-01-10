package org.springframework.social.twitter.api;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * a related tweet context
 * <p/>
 * User: bowen
 * Date: 12/27/11
 */
public class RelatedTweet implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonProperty("kind")
    private String type;

    @JsonProperty("score")
    private float score;

    @JsonProperty("value")
    private Tweet tweet;

    @JsonProperty("annotations")
    private RelatedAnnotation annotation;


    /**
     * JSON constructor.
     */
    public RelatedTweet()
    {

    }


    public String getType()
    {
        return type;
    }


    public float getScore()
    {
        return score;
    }


    public Tweet getTweet()
    {
        return tweet;
    }


    public RelatedAnnotation getAnnotation()
    {
        return this.annotation;
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

        RelatedTweet that = (RelatedTweet) o;

        if (Float.compare(that.score, score) != 0)
        {
            return false;
        }
        if (annotation != null ? !annotation.equals(that.annotation) : that.annotation != null)
        {
            return false;
        }
        if (tweet != null ? !tweet.equals(that.tweet) : that.tweet != null)
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
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (score != +0.0f ? Float.floatToIntBits(score) : 0);
        result = 31 * result + (tweet != null ? tweet.hashCode() : 0);
        result = 31 * result + (annotation != null ? annotation.hashCode() : 0);
        return result;
    }
}
