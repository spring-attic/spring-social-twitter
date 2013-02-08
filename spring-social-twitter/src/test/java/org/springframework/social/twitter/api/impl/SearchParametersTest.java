/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.social.twitter.api.GeoCode;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Unit tests for advanced search using SearchParameter
 *
 * @author Rosty Kerei
 */
public class SearchParametersTest extends AbstractTwitterApiTest {

    @Test
    public void testSimple() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=50"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchParameters p = new SearchParameters("#spring");

        SearchResults searchResults = twitter.searchOperations().search(p);

        assertEquals("#spring", p.getQuery());
        assertSearchTweets(searchResults.getTweets());
    }

    @Test
    public void testGeoCode() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&geocode=37.781157%2C-122.39872%2C10mi&count=50"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchParameters p = new SearchParameters("#spring");
        p.setGeoCode(new GeoCode(37.781157, -122.39872, 10, GeoCode.Unit.MILE));

        SearchResults searchResults = twitter.searchOperations().search(p);

        assertEquals("37.781157,-122.39872,10mi", p.getGeoCode().toString());
        assertSearchTweets(searchResults.getTweets());
    }

    @Test
    public void testLang() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&lang=nl&count=50"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchParameters p = new SearchParameters("#spring");
        p.setLang("nl");

        SearchResults searchResults = twitter.searchOperations().search(p);

        assertEquals("nl", p.getLang());
        assertSearchTweets(searchResults.getTweets());
    }

    @Test
    public void testLocale() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&locale=ja&count=50"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchParameters p = new SearchParameters("#spring");
        p.setLocale("ja");

        SearchResults searchResults = twitter.searchOperations().search(p);

        assertEquals("ja", p.getLocale());
        assertSearchTweets(searchResults.getTweets());
    }

    @Test
    public void testResultType() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&result_type=popular&count=50"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchParameters p = new SearchParameters("#spring");
        p.setResultType(SearchParameters.ResultType.POPULAR);

        SearchResults searchResults = twitter.searchOperations().search(p);

        assertEquals(SearchParameters.ResultType.POPULAR, p.getResultType());
        assertSearchTweets(searchResults.getTweets());
    }

    @Test
    public void testCount() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=25"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchParameters p = new SearchParameters("#spring");
        p.setCount(25);

        SearchResults searchResults = twitter.searchOperations().search(p);

        assertEquals(new Integer(25), p.getCount());
        assertSearchTweets(searchResults.getTweets());
    }

    @Test
    public void testUntil() throws ParseException {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=50&until=2012-01-31"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchParameters p = new SearchParameters("#spring");
        p.setUntil(new SimpleDateFormat("yyyy-MM-dd").parse("2012-01-31"));

        SearchResults searchResults = twitter.searchOperations().search(p);

        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2012-01-31"), p.getUntil());
        assertSearchTweets(searchResults.getTweets());
    }

    @Test
    public void testSinceId() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=50&since_id=10"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchParameters p = new SearchParameters("#spring");
        p.setSinceId(10);

        SearchResults searchResults = twitter.searchOperations().search(p);

        assertEquals(new Long(10), p.getSinceId());
        assertSearchTweets(searchResults.getTweets());
    }

    @Test
    public void testMaxId() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=50&max_id=999"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchParameters p = new SearchParameters("#spring");
        p.setMaxId(999);

        SearchResults searchResults = twitter.searchOperations().search(p);

        assertEquals(new Long(999), p.getMaxId());
        assertSearchTweets(searchResults.getTweets());
    }

    @Test
    public void testIncludeEntities() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=50&include_entities=false"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchParameters p = new SearchParameters("#spring");
        p.setIncludeEntities(false);

        SearchResults searchResults = twitter.searchOperations().search(p);

        assertFalse(p.isIncludeEntities());
        assertSearchTweets(searchResults.getTweets());
    }

    @Test
    public void testComplex() throws ParseException {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring"
                        + "&geocode=37.781157%2C-122.39872%2C10mi&lang=nl&locale=ja&result_type=popular"
                        + "&count=25&until=2012-01-31&since_id=10&max_id=999&include_entities=false"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchParameters p = new SearchParameters("#spring");
        p.setGeoCode(new GeoCode(37.781157, -122.398720, 10, GeoCode.Unit.MILE));
        p.setLang("nl");
        p.setLocale("ja");
        p.setResultType(SearchParameters.ResultType.POPULAR);
        p.setCount(25);
        p.setUntil(new SimpleDateFormat("yyyy-MM-dd").parse("2012-01-31"));
        p.setSinceId(10);
        p.setMaxId(999);
        p.setIncludeEntities(false);

        SearchResults searchResults = twitter.searchOperations().search(p);

        assertSearchTweets(searchResults.getTweets());
    }

    private void assertSearchTweets(List<Tweet> tweets) {
        assertTimelineTweets(tweets, true);
        assertEquals("en", tweets.get(0).getLanguageCode());
        assertEquals("de", tweets.get(1).getLanguageCode());
    }
}
