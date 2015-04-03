/*
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl.advertising;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.social.twitter.api.HashTagEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.advertising.ApprovalStatus;
import org.springframework.social.twitter.api.advertising.PromotableUser;
import org.springframework.social.twitter.api.advertising.PromotedTweetReference;
import org.springframework.social.twitter.api.advertising.PromotedUserReference;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;

/**
 * @author Hudson Mendes
 */
public class PromotionsTemplateTest extends AbstractTwitterApiTest {

    @Test
    public void getPromotableUsers() {
        final String mockedAccountId = "0ga0yn";
        mockServer
            .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/promotable_users?with_deleted=true"))
            .andExpect(method(GET))
            .andRespond(withSuccess(jsonResource("ad-promotable-users"), APPLICATION_JSON));

        final List<PromotableUser> promotables = twitter.promotionOperations().getPromotableUsers(
            mockedAccountId,
            new PromotableUserQueryBuilder().includeDeleted(true)).getList();

        Assert.assertNotEquals(0, promotables.size());
        Assert.assertEquals("13phg", promotables.get(0).getId());
        Assert.assertEquals("gq0vqj", promotables.get(0).getAccountId());
        Assert.assertEquals(new Long("390472547"), promotables.get(0).getUserId());
        Assert.assertEquals("FULL", promotables.get(0).getPromotableUserType());
        Assert.assertEquals(false, promotables.get(0).isDeleted());
        Assert.assertEquals("2015-04-13T20:42:39", promotables.get(0).getCreatedAt().toString());
        Assert.assertEquals("2015-04-13T20:42:39", promotables.get(0).getUpdatedAt().toString());
    }

    @Test
    public void getSponsoredTweets() {
        final String mockedAccountId = "0ga0yn";
        mockServer
            .expect(requestTo("https://ads-api.twitter.com/0/accounts/"
                              + mockedAccountId
                              + "/scoped_timeline?scoped_to=none&user_ids=390472547&trim_user=true&count=1"))
            .andExpect(method(GET))
            .andRespond(withSuccess(jsonResource("ad-sponsored-tweets"), APPLICATION_JSON));

        final List<Tweet> tweets = twitter
            .promotionOperations()
            .getSponsoredTweets(
                mockedAccountId,
                new SponsoredTweetQueryBuilder()
                    .ofUsers(new Long("390472547"))
                    .pagedBy(null, 1)
                    .trimUser(true))
            .getList();

        assertTweetsContents(tweets);
    }

    @SuppressWarnings("deprecation")
    private void assertTweetsContents(List<Tweet> tweets) {
        Assert.assertEquals(3, tweets.size());

        Assert.assertEquals("540565364179226624", tweets.get(0).getId());
        Assert.assertEquals(new Long("390472547").longValue(), tweets.get(0).getFromUserId());
        Assert.assertEquals("en", tweets.get(0).getLanguageCode());
        Assert.assertEquals("MemeTV wants to bring meme-worthy TV clips to Tumblr and... http://t.co/cfxJOQ9zpF", tweets.get(0).getText());
        Assert.assertEquals("<a href=\"http://ifttt.com\" rel=\"nofollow\">IFTTT</a>", tweets.get(0).getSource());
        Assert.assertEquals(false, tweets.get(0).isFavorited());
        Assert.assertEquals(Integer.valueOf(0), tweets.get(0).getFavoriteCount());
        Assert.assertEquals(false, tweets.get(0).isRetweeted());
        Assert.assertEquals(Integer.valueOf(0), tweets.get(0).getRetweetCount());
        Assert.assertEquals(false, tweets.get(0).isTruncated());
        Assert.assertEquals(false, tweets.get(0).isPossiblySensitive());
        Assert.assertEquals(0, tweets.get(0).getEntities().getHashTags().size());
        Assert.assertEquals(0, tweets.get(0).getEntities().getTickerSymbols().size());
        Assert.assertEquals(1, tweets.get(0).getEntities().getUrls().size());
        Assert.assertEquals("http://t.co/cfxJOQ9zpF", tweets.get(0).getEntities().getUrls().get(0).getUrl());
        Assert.assertEquals("http://ift.tt/1tLavU9", tweets.get(0).getEntities().getUrls().get(0).getExpandedUrl());
        Assert.assertEquals("ift.tt/1tLavU9", tweets.get(0).getEntities().getUrls().get(0).getDisplayUrl());
        Assert.assertEquals(new Date("Thu Dec 04 17:56:40 GMT 2014").toInstant(), tweets.get(0).getCreatedAt().toInstant());
        Assert.assertThat(
            intArrayToIntegerList(tweets.get(0).getEntities().getUrls().get(0).getIndices()),
            CoreMatchers.hasItems(Integer.valueOf(60), Integer.valueOf(82)));
    }

	@Test
    public void createSponsoredTweet() {
        final String mockedAccountId = "hkk5";
        final String mockedTweetText = "Hey, here is a sponsored tweet that we are creating via #ads API.";
        final long mockedUserId = 390472547L;

        final String chainedPostContent = "status=Hey%2C+here+is+a+sponsored+tweet+that+we+are+creating+via+%23ads+API.&as_user_id=390472547";

        mockServer
            .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/tweet"))
            .andExpect(method(POST))
            .andExpect(content().string(chainedPostContent))
            .andRespond(withSuccess(jsonResource("ad-sponsored-tweets-single"), APPLICATION_JSON));

        final Tweet tweet = twitter.promotionOperations().createSponsoredTweet(
            mockedAccountId,
            new SponsoredTweetFormBuilder()
                .asUser(mockedUserId)
                .withStatus(mockedTweetText));

        Assert.assertEquals(mockedUserId, tweet.getFromUserId());
        Assert.assertEquals(mockedTweetText, tweet.getText());
        
        final List<String> hashtags = new ArrayList<>();
        for (HashTagEntity hashtag : tweet.getEntities().getHashTags())
        	hashtags.add(hashtag.getText());
        Assert.assertThat(hashtags, CoreMatchers.hasItems("ads"));
    }

    @Test
    public void getPromotedTweetReferences() {
        final String mockedAccountId = "0ga0yn";
        final String mockedLineItemId = "u4h4";
        mockServer
            .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/promoted_tweets?line_item_id=" + mockedLineItemId))
            .andExpect(method(GET))
            .andRespond(withSuccess(jsonResource("ad-promoted-tweet-reference"), APPLICATION_JSON));

        final List<PromotedTweetReference> references = twitter.promotionOperations().getPromotedTweetReferences(
            mockedAccountId,
            mockedLineItemId,
            new PromotedTweetReferenceQueryBuilder()).getList();

        Assert.assertNotEquals(0, references.size());
        Assert.assertEquals("tifo", references.get(0).getId());
        Assert.assertEquals("b51j", references.get(0).getLineItemId());
        Assert.assertEquals(Long.valueOf(614564626060062720L), references.get(0).getTweetId());
        Assert.assertEquals(ApprovalStatus.ACCEPTED, references.get(0).getApprovalStatus());
        Assert.assertEquals(false, references.get(0).isPaused());
        Assert.assertEquals(false, references.get(0).isDeleted());
        Assert.assertEquals("2015-06-27T00:21:53", references.get(0).getCreatedAt().toString());
        Assert.assertEquals("2015-06-30T00:21:53", references.get(0).getUpdatedAt().toString());
    }

    @Test
    public void getPromotedTweetReferencesWithoutLineItemId() {
        final String mockedAccountId = "0ga0yn";
        mockServer
            .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/promoted_tweets"))
            .andExpect(method(GET))
            .andRespond(withSuccess(jsonResource("ad-promoted-tweet-reference"), APPLICATION_JSON));

        final List<PromotedTweetReference> references = twitter
            .promotionOperations()
            .getPromotedTweetReferences(mockedAccountId, new PromotedTweetReferenceQueryBuilder())
            .getList();

        Assert.assertNotEquals(0, references.size());
        Assert.assertEquals("tifo", references.get(0).getId());
        Assert.assertEquals("b51j", references.get(0).getLineItemId());
        Assert.assertEquals(Long.valueOf(614564626060062720L), references.get(0).getTweetId());
        Assert.assertEquals(ApprovalStatus.ACCEPTED, references.get(0).getApprovalStatus());
        Assert.assertEquals(false, references.get(0).isPaused());
        Assert.assertEquals(false, references.get(0).isDeleted());
        Assert.assertEquals("2015-06-27T00:21:53", references.get(0).getCreatedAt().toString());
        Assert.assertEquals("2015-06-30T00:21:53", references.get(0).getUpdatedAt().toString());
    }

    @Test
    public void createPromotedTweetReference() {
        final String mockedAccountId = "0ga0yn";
        final String mockedLineItemId = "u4h4";
        final Long tweetId = 614564626060062720L;

        final String chainedPostContent = "line_item_id=" + mockedLineItemId + "&tweet_ids=" + tweetId;

        mockServer
            .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/promoted_tweets"))
            .andExpect(method(POST))
            .andExpect(content().string(chainedPostContent))
            .andRespond(withSuccess(jsonResource("ad-promoted-tweet-reference-creation"), APPLICATION_JSON));

        final List<PromotedTweetReference> references = twitter
            .promotionOperations()
            .createPromotedTweetReference(
                mockedAccountId,
                new PromotedTweetReferenceFormBuilder()
                    .onLineItem(mockedLineItemId)
                    .forTweets(tweetId))
            .getList();

        Assert.assertNotEquals(0, references.size());
        Assert.assertEquals("tifo", references.get(0).getId());
        Assert.assertEquals("u4h4", references.get(0).getLineItemId());
        Assert.assertEquals(Long.valueOf(614564626060062720L), references.get(0).getTweetId());
        Assert.assertEquals(ApprovalStatus.ACCEPTED, references.get(0).getApprovalStatus());
        Assert.assertEquals(false, references.get(0).isPaused());
        Assert.assertEquals(false, references.get(0).isDeleted());
        Assert.assertEquals("2015-06-27T00:21:53", references.get(0).getCreatedAt().toString());
        Assert.assertEquals("2015-06-30T00:21:53", references.get(0).getUpdatedAt().toString());
    }

    @Test
    public void deletePromotedTweetReference() {
        final String mockedAccountId = "0ga0yn";
        final String mockedPromotedTweetId = "ak34";

        mockServer
            .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/promoted_tweets/" + mockedPromotedTweetId))
            .andExpect(method(DELETE))
            .andRespond(withSuccess());

        twitter.promotionOperations().deletePromotedTweetReference(
            mockedAccountId,
            mockedPromotedTweetId);
    }

    @Test
    public void getPromotedUserReferences() {
        final String mockedAccountId = "0ga0yn";
        final String mockedLineItemId = "u4h4";
        mockServer
            .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/promoted_accounts?line_item_id=" + mockedLineItemId))
            .andExpect(method(GET))
            .andRespond(withSuccess(jsonResource("ad-promoted-accounts-reference"), APPLICATION_JSON));

        final List<PromotedUserReference> references = twitter.promotionOperations().getPromotedUserReferences(
            mockedAccountId,
            mockedLineItemId,
            new PromotedUserReferenceQueryBuilder()).getList();

        Assert.assertNotEquals(0, references.size());
        Assert.assertEquals("3goc", references.get(0).getId());
        Assert.assertEquals("b6im", references.get(0).getLineItemId());
        Assert.assertEquals("390472547", references.get(0).getUserId());
        Assert.assertEquals(false, references.get(0).isDeleted());
        Assert.assertEquals(false, references.get(0).isPaused());
        Assert.assertEquals("2015-07-02T00:51:44", references.get(0).getCreatedAt().toString());
        Assert.assertEquals("2015-07-02T00:51:44", references.get(0).getUpdatedAt().toString());
        Assert.assertEquals(ApprovalStatus.ACCEPTED, references.get(0).getApprovalStatus());
    }

    @Test
    public void createPromotedUserReference() {
        final String mockedAccountId = "0ga0yn";
        final String mockedLineItemId = "u4h4";
        final String mockedUserId = "13phg";

        final String chainedPostContent = "line_item_id=" + mockedLineItemId + "&user_id=" + mockedUserId;

        mockServer
            .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/promoted_accounts"))
            .andExpect(method(POST))
            .andExpect(content().string(chainedPostContent))
            .andRespond(withSuccess(jsonResource("ad-promoted-accounts-reference-creation"), APPLICATION_JSON));

        final PromotedUserReference reference = twitter.promotionOperations().createPromotedUserReferences(
            mockedAccountId,
            new PromotedUserReferenceFormBuilder()
                .onLineItem(mockedLineItemId)
                .forUser(mockedUserId));

        Assert.assertEquals("3goc", reference.getId());
        Assert.assertEquals("b6im", reference.getLineItemId());
        Assert.assertEquals("390472547", reference.getUserId());
        Assert.assertEquals(false, reference.isDeleted());
        Assert.assertEquals(false, reference.isPaused());
        Assert.assertEquals("2015-07-02T00:51:44", reference.getCreatedAt().toString());
        Assert.assertEquals("2015-07-02T00:51:44", reference.getUpdatedAt().toString());
        Assert.assertEquals(ApprovalStatus.ACCEPTED, reference.getApprovalStatus());
    }

    @Test
    public void deletePromotedUserReference() {
        final String mockedAccountId = "0ga0yn";
        final String mockedPromotedTweetId = "ak34";

        mockServer
            .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/promoted_accounts/" + mockedPromotedTweetId))
            .andExpect(method(DELETE))
            .andRespond(withSuccess());

        twitter.promotionOperations().deletePromotedUserReference(
            mockedAccountId,
            mockedPromotedTweetId);
    }
}
