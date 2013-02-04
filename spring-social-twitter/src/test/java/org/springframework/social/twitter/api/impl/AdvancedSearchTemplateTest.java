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
import org.springframework.social.twitter.api.AdvancedSearchOperations;
import org.springframework.social.twitter.api.GeoCode;
import org.springframework.social.twitter.api.SearchResults;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Unit tests for advanced search
 *
 * @author Rosty Kerei
 */
public class AdvancedSearchTemplateTest extends AbstractTwitterApiTest {

    @Test
    public void testSimple() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchResults searchResults = twitter.advancedSearchOperations().search("#spring");
    }

    @Test
    public void testGeoCode() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&geocode=37.781157%2C-122.39872%2C10mi"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        AdvancedSearchOperations aso = twitter.advancedSearchOperations();

        SearchResults searchResults = aso.setGeoCode(new GeoCode(37.781157, -122.39872, 10, GeoCode.Unit.MILE))
                .search("#spring");

        assertEquals("37.781157,-122.39872,10mi", aso.getGeoCode().toString());
    }

    @Test
    public void testLang() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&lang=nl"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        AdvancedSearchOperations aso = twitter.advancedSearchOperations();
        SearchResults searchResults = aso.setLang("nl").search("#spring");

        assertEquals("nl", aso.getLang());
    }

    @Test
    public void testLocale() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&locale=ja"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        AdvancedSearchOperations aso = twitter.advancedSearchOperations();
        SearchResults searchResults = aso.setLocale("ja").search("#spring");

        assertEquals("ja", aso.getLocale());
    }

    @Test
    public void testResultType() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&result_type=popular"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        AdvancedSearchOperations aso = twitter.advancedSearchOperations();
        SearchResults searchResults = aso.setResultType(AdvancedSearchOperations.ResultType.POPULAR).search("#spring");

        assertEquals(AdvancedSearchOperations.ResultType.POPULAR, aso.getResultType());
    }

    @Test
    public void testCount() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&count=25"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        AdvancedSearchOperations aso = twitter.advancedSearchOperations();
        SearchResults searchResults = aso.setCount(25).search("#spring");

        assertEquals(25, aso.getCount());
    }

    @Test
    public void testUntil() throws ParseException {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&until=2012-01-31"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        AdvancedSearchOperations aso = twitter.advancedSearchOperations();
        SearchResults searchResults = aso.setUntil(new SimpleDateFormat("yyyy-MM-dd").parse("2012-01-31"))
                .search("#spring");

        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2012-01-31"), aso.getUntil());
    }

    @Test
    public void testSinceId() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&since_id=10"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        AdvancedSearchOperations aso = twitter.advancedSearchOperations();
        SearchResults searchResults = aso.setSinceId(10).search("#spring");

        assertEquals(10, aso.getSinceId());
    }

    @Test
    public void testMaxId() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&max_id=999"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        AdvancedSearchOperations aso = twitter.advancedSearchOperations();
        SearchResults searchResults = aso.setMaxId(999).search("#spring");

        assertEquals(999, aso.getMaxId());
    }

    @Test
    public void testIncludeEntities() {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring&include_entities=false"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        AdvancedSearchOperations aso = twitter.advancedSearchOperations();
        SearchResults searchResults = aso.setIncludeEntities(false).search("#spring");

        assertFalse(aso.isIncludeEntities());
    }

    @Test
    public void testComplex() throws ParseException {
        mockServer.expect(requestTo("https://api.twitter.com/1.1/search/tweets.json?q=%23spring"
                        + "&geocode=37.781157%2C-122.39872%2C10mi&lang=nl&locale=ja&result_type=popular"
                        + "&count=25&until=2012-01-31&since_id=10&max_id=999&include_entities=false"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("search"), APPLICATION_JSON));

        SearchResults searchResults = twitter.advancedSearchOperations()
                .setGeoCode(new GeoCode(37.781157, -122.398720, 10, GeoCode.Unit.MILE))
                .setLang("nl")
                .setLocale("ja")
                .setResultType(AdvancedSearchOperations.ResultType.POPULAR)
                .setCount(25)
                .setUntil(new SimpleDateFormat("yyyy-MM-dd").parse("2012-01-31"))
                .setSinceId(10)
                .setMaxId(999)
                .setIncludeEntities(false)
                .search("#spring");
    }
}
