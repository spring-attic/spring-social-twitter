package org.springframework.social.twitter.api;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A representation of a URL found within a tweet entity.
 * <p/>
 * User: bowen
 * Date: 12/26/11
 */
public class UrlEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonProperty("display_url")
    private String display;

    @JsonProperty("expanded_url")
    private String expanded;

    @JsonProperty("url")
    private String url;

    @JsonProperty("indices")
    private int[] indices;


    /**
     * JSON constructor.
     */
    public UrlEntity()
    {

    }


    public String getDisplay()
    {
        return display;
    }


    public String getExpanded()
    {
        return expanded;
    }


    public String getUrl()
    {
        return url;
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

        UrlEntity urlEntity = (UrlEntity) o;

        if (display != null ? !display.equals(urlEntity.display) : urlEntity.display != null)
        {
            return false;
        }
        if (expanded != null ? !expanded.equals(urlEntity.expanded) : urlEntity.expanded != null)
        {
            return false;
        }
        if (!Arrays.equals(indices, urlEntity.indices))
        {
            return false;
        }
        if (url != null ? !url.equals(urlEntity.url) : urlEntity.url != null)
        {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = display != null ? display.hashCode() : 0;
        result = 31 * result + (expanded != null ? expanded.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (indices != null ? Arrays.hashCode(indices) : 0);
        return result;
    }
}
