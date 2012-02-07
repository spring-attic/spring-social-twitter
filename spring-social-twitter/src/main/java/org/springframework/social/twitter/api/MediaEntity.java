package org.springframework.social.twitter.api;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A representation of embedded media entity.
 * <p/>
 * User: bowen
 * Date: 12/26/11
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private long id;

    @JsonProperty("media_url")
    private String mediaHttp;

    @JsonProperty("media_url_https")
    private String mediaHttps;

    @JsonProperty("url")
    private String url;

    @JsonProperty("display_url")
    private String display;

    @JsonProperty("expanded_url")
    private String expanded;

    @JsonProperty("type")
    private String type;

    @JsonProperty("indices")
    private int[] indices;


    /**
     * JSON constructor.
     */
    public MediaEntity()
    {

    }


    public long getId()
    {
        return this.id;
    }


    public String getMediaUrl()
    {
        return this.mediaHttp;
    }


    public String getMediaSecureUrl()
    {
        return this.mediaHttps;
    }


    public String getType()
    {
        return this.type;
    }


    public String getDisplayUrl()
    {
        return display;
    }


    public String getExpandedUrl()
    {
        return this.expanded;
    }


    public String getUrl()
    {
        return this.url;
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

        MediaEntity that = (MediaEntity) o;

        if (id != that.id)
        {
            return false;
        }
        if (display != null ? !display.equals(that.display) : that.display != null)
        {
            return false;
        }
        if (expanded != null ? !expanded.equals(that.expanded) : that.expanded != null)
        {
            return false;
        }
        if (!Arrays.equals(indices, that.indices))
        {
            return false;
        }
        if (mediaHttp != null ? !mediaHttp.equals(that.mediaHttp) : that.mediaHttp != null)
        {
            return false;
        }
        if (mediaHttps != null ? !mediaHttps.equals(that.mediaHttps) : that.mediaHttps != null)
        {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null)
        {
            return false;
        }
        if (url != null ? !url.equals(that.url) : that.url != null)
        {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (mediaHttp != null ? mediaHttp.hashCode() : 0);
        result = 31 * result + (mediaHttps != null ? mediaHttps.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (display != null ? display.hashCode() : 0);
        result = 31 * result + (expanded != null ? expanded.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (indices != null ? Arrays.hashCode(indices) : 0);
        return result;
    }
}
