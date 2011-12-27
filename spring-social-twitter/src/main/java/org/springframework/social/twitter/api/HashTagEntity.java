package org.springframework.social.twitter.api;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A representation of tweet hashtags.
 * <p/>
 * User: bowen
 * Date: 12/26/11
 */
public class HashTagEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonProperty("text")
    private String text;

    @JsonProperty("indices")
    private int[] indices;


    /**
     * JSON constructor.
     */
    public HashTagEntity()
    {

    }


    public String getText()
    {
        return text;
    }


    public int[] getIndices()
    {
        return indices;
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

        HashTagEntity that = (HashTagEntity) o;

        if (!Arrays.equals(indices, that.indices))
        {
            return false;
        }
        if (text != null ? !text.equals(that.text) : that.text != null)
        {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (indices != null ? Arrays.hashCode(indices) : 0);
        return result;
    }
}
