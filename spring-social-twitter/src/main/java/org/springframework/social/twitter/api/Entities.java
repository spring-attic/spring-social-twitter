package org.springframework.social.twitter.api;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A json representation of entities found within twitter status objects.
 * <p/>
 * User: bowen
 * Date: 12/26/11
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entities implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonProperty("urls")
    private List<UrlEntity> urls = new LinkedList<UrlEntity>();

    @JsonProperty("hashtags")
    private List<HashTagEntity> tags = new LinkedList<HashTagEntity>();

    @JsonProperty("user_mentions")
    private List<MentionEntity> mentions = new LinkedList<MentionEntity>();


    /**
     * JSON constructor.
     */
    public Entities()
    {

    }


    public List<UrlEntity> getUrls()
    {
        if (this.urls == null)
        {
            return Collections.emptyList();
        }
        return this.urls;
    }


    public List<HashTagEntity> getTags()
    {
        if (this.tags == null)
        {
            return Collections.emptyList();
        }
        return this.tags;
    }


    public List<MentionEntity> getMentions()
    {
        if (this.mentions == null)
        {
            return Collections.emptyList();
        }
        return this.mentions;
    }


    public boolean hasUrls()
    {
        return this.urls != null && !this.urls.isEmpty();
    }


    public boolean hasTags()
    {
        return this.tags != null && !this.tags.isEmpty();
    }


    public boolean hasMentions()
    {
        return this.mentions != null && !this.mentions.isEmpty();
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

        Entities entities = (Entities) o;

        if (mentions != null ? !mentions.equals(entities.mentions) : entities.mentions != null)
        {
            return false;
        }
        if (tags != null ? !tags.equals(entities.tags) : entities.tags != null)
        {
            return false;
        }
        if (urls != null ? !urls.equals(entities.urls) : entities.urls != null)
        {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = urls != null ? urls.hashCode() : 0;
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (mentions != null ? mentions.hashCode() : 0);
        return result;
    }
}
