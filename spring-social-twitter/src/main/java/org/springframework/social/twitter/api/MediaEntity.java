package org.springframework.social.twitter.api;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * A representation of embedded media entity.
 * <p/>
 * User: bowen
 * Date: 12/26/11
 */
public class MediaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private long id;

    @JsonProperty("media_url")
    private String media;

    @JsonProperty("media_url_https")
    private String mediaSsl;

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
}
