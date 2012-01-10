package org.springframework.social.twitter.api;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * an annotation context for related results
 * <p/>
 * User: bowen
 * Date: 12/27/11
 */
public class RelatedAnnotation implements Serializable
{
    private static final long serialVersionUID = 1L;

    @JsonProperty("ConversationRole")
    private String role = "";

    @JsonProperty("FromUser")
    private String user = "";


    /**
     * JSON constructor.
     */
    public RelatedAnnotation()
    {

    }


    public String getRole()
    {
        return this.role;
    }


    public String getUser()
    {
        return this.user;
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

        RelatedAnnotation that = (RelatedAnnotation) o;

        if (role != null ? !role.equals(that.role) : that.role != null)
        {
            return false;
        }

        return true;
    }


    @Override
    public int hashCode()
    {
        return role != null ? role.hashCode() : 0;
    }
}
