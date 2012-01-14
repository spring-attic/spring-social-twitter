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
    private long id;

    @JsonProperty("screen_name")
    private String screenName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("indices")
    private int[] indices;


    /**
     * JSON constructor.
     */
    public MentionEntity()
    {

    }


    public long getId()
    {
        return this.id;
    }


    public String getName()
    {
        return this.name;
    }


    public String getScreenName()
    {
        return this.screenName;
    }


    public int[] getIndices()
    {
        if (this.indices == null || this.indices.length <= 0)
        {
            return new int[0];
        }
        return this.indices;
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

        if (id != that.id)
        {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null)
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
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (screenName != null ? screenName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (indices != null ? Arrays.hashCode(indices) : 0);
        return result;
    }
}
