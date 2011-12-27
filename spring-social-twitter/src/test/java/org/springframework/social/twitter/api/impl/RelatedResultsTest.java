package org.springframework.social.twitter.api.impl;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.social.twitter.api.RelatedResultList;

import java.io.IOException;
import java.net.URL;

/**
 * test related results json
 * <p/>
 * User: bowen
 * Date: 12/27/11
 */
public class RelatedResultsTest
{
    @Test
    public void testReader() throws IOException
    {
        final URL url = this.getClass().getResource("related-results.json");
        Assert.assertNotNull(url);
        final RelatedResultList results = this.createMapper().reader(RelatedResultList.class).readValue(url);
        Assert.assertNotNull(results);
        Assert.assertEquals(8, results.getTweetsWithConversation().size());
        Assert.assertEquals(0, results.getTweetFromuser().size());
        Assert.assertEquals(0, results.getTweetsWithReply().size());
    }


    private ObjectMapper createMapper()
    {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new TwitterModule());
        return mapper;
    }
}
