package org.springframework.social.twitter.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * a collection of related results
 * <p/>
 * User: bowen
 * Date: 12/27/11
 */
public class RelatedResultList extends ArrayList<RelatedResult>
{
    private static final long serialVersionUID = 1L;

    private static final String TWEETS_WITH_CONVERSATION = "TweetsWithConversation";

    private static final String TWEETS_WITH_REPLY = "TweetsWithReply";

    private static final String TWEETS_FROM_USER = "TweetsFromUser";


    public RelatedResultList()
    {

    }


    public List<Tweet> getTweetFromuser()
    {
        final RelatedResult result = this.getResult(TWEETS_FROM_USER);
        if (null == result)
        {
            return Collections.emptyList();
        }
        return this.repackageTweets(result.getTweets());
    }


    public List<Tweet> getTweetsWithReply()
    {
        final RelatedResult result = this.getResult(TWEETS_WITH_REPLY);
        if (null == result)
        {
            return Collections.emptyList();
        }
        return this.repackageTweets(result.getTweets());
    }


    public List<Tweet> getTweetsWithConversation()
    {
        final RelatedResult result = this.getResult(TWEETS_WITH_CONVERSATION);
        if (null == result)
        {
            return Collections.emptyList();
        }
        return this.repackageTweets(result.getTweets());
    }


    public RelatedResult getResult(final String group)
    {
        for (final RelatedResult result : this)
        {
            if (result.getGroup().equalsIgnoreCase(group))
            {
                return result;
            }
        }
        return null;
    }


    private List<Tweet> repackageTweets(final Collection<RelatedTweet> ctxs)
    {
        if (null == ctxs || ctxs.isEmpty())
        {
            return Collections.emptyList();
        }

        final List<Tweet> list = new ArrayList<Tweet>(ctxs.size());
        for (final RelatedTweet tweet : ctxs)
        {
            if (tweet.getTweet() != null)
            {
                list.add(tweet.getTweet());
            }
        }
        return Collections.unmodifiableList(list);
    }
}
