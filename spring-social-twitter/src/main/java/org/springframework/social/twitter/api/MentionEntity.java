package org.springframework.social.twitter.api;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A user mention entity.
 * <p/>
 * User: bowen
 * Date: 12/26/11
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MentionEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private long userId;

    @JsonProperty("screen_name")
    private String screenName;

    @JsonProperty("name")
    private String fullName;

    @JsonProperty("indices")
    private int[] indices;


    /**
     * JSON constructor.
     */
    public MentionEntity()
    {

    }


    public long getUserId()
    {
        return userId;
    }


    public String getFullName()
    {
        return fullName;
    }


    public String getScreenName()
    {
        return screenName;
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

        MentionEntity that = (MentionEntity) o;

        if (userId != that.userId)
        {
            return false;
        }
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null)
        {
            return false;
        }
        if (!Arrays.equals(indices, that.indices))
        {
            return false;
        }
        if (screenName != null ? !screenName.equals(that.screenName) : that.screenName != null)
        {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (screenName != null ? screenName.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (indices != null ? Arrays.hashCode(indices) : 0);
        return result;
    }
}
