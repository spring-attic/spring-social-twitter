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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.social.twitter.api.advertising.TailoredAudience;
import org.springframework.social.twitter.api.advertising.TailoredAudienceChange;
import org.springframework.social.twitter.api.advertising.TailoredAudienceChangeOperation;
import org.springframework.social.twitter.api.advertising.TailoredAudienceChangeState;
import org.springframework.social.twitter.api.advertising.TailoredAudienceListType;
import org.springframework.social.twitter.api.advertising.TailoredAudienceType;
import org.springframework.social.twitter.api.impl.AbstractTwitterApiTest;
import org.springframework.social.twitter.api.impl.DataListHolder;

/**
 * @author Hudson Mendes
 */
public class TailoredAudienceTemplateTest extends AbstractTwitterApiTest {

    @Test
    public void getTailoredAudience() {
        String mockedAccountId = "hkk5";
        String mockedTailoredAudience = "qq4u";
        mockServer
                .expect(requestTo(
                        "https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/tailored_audiences" +
                                "/" + mockedTailoredAudience +
                                "?with_deleted=true"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("ad-tailored-audiences-single"), APPLICATION_JSON));

        TailoredAudience tailoredAudience = twitter.tailoredAudienceOperations().getTailoredAudience(mockedAccountId, mockedTailoredAudience);
        assertSingleTailoredAudienceContents(tailoredAudience);
    }

    @Test
    public void getTailoredAudiences() {
        String mockedAccountId = "hkk5";
        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/tailored_audiences?with_deleted=false"))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("ad-tailored-audiences"), APPLICATION_JSON));

        DataListHolder<TailoredAudience> tailoredAudiences = twitter.tailoredAudienceOperations()
                .getTailoredAudiences(
                        mockedAccountId,
                        new TailoredAudienceQueryBuilder().includeDeleted(false));

        assertTailoredAudienceContents(tailoredAudiences.getList());
    }

    @Test
    public void createTailoredAudience() {
        String mockedAccountId = "hkk5";
        String doesntMatterString = "doesn-matter";

        String chainedPostContent =
                "name=" + doesntMatterString + "&" +
                        "list_type=" + TailoredAudienceListType.TWITTER_ID;

        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/tailored_audiences"))
                .andExpect(method(POST))
                .andExpect(content().string(chainedPostContent))
                .andRespond(withSuccess(jsonResource("ad-tailored-audiences-single"), APPLICATION_JSON));

        TailoredAudience tailoredAudience = twitter.tailoredAudienceOperations().createTailoredAudience(
                mockedAccountId,
                new TailoredAudienceFormBuilder()
                        .named(doesntMatterString)
                        .ofListType(TailoredAudienceListType.TWITTER_ID));

        assertSingleTailoredAudienceContents(tailoredAudience);
    }

    @Test
    public void deleteTailoredAudience() {
        String mockedAccountId = "0ga0yn";
        String mockedTailoredAudienceId = "qq4u";
        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/tailored_audiences/" + mockedTailoredAudienceId))
                .andExpect(method(DELETE))
                .andRespond(withSuccess());

        twitter.tailoredAudienceOperations().deleteTailoredAudience(mockedAccountId, mockedTailoredAudienceId);
    }

    private void assertSingleTailoredAudienceContents(TailoredAudience audience) {
        assertEquals("qq4u", audience.getId());
        assertEquals("twitter%20ids", audience.getName());

        assertEquals("OTHER", audience.getPartnerSource());
        assertEquals(TailoredAudienceListType.TWITTER_ID, audience.getListType());

        for (String reason : new String[] {"PROCESSING", "TOO_SMALL"})
            assertThat(Arrays.asList(audience.getReasonsNotTargetable()), CoreMatchers.hasItem(reason));

        for (String targetableType : new String[] {"CRM", "EXCLUDED_CRM"})
            assertThat(Arrays.asList(audience.getTargetableTypes()), CoreMatchers.hasItem(targetableType));

        assertEquals(null, audience.getAudienceSize());
        assertEquals(TailoredAudienceType.CRM, audience.getAudienceType());

        assertEquals(false, audience.isDeleted());
        assertEquals(false, audience.isTargetable());

        assertEquals("2015-06-08T20:13:40", audience.getCreatedAt().toString());
        assertEquals("2015-06-08T20:13:40", audience.getUpdatedAt().toString());
    }

    private void assertTailoredAudienceContents(List<TailoredAudience> audiences) {
        assertEquals(1, audiences.size());

        assertEquals("qq4u", audiences.get(0).getId());
        assertEquals("twitter%20ids", audiences.get(0).getName());

        assertEquals("OTHER", audiences.get(0).getPartnerSource());
        assertEquals(TailoredAudienceListType.TWITTER_ID, audiences.get(0).getListType());

        for (String reason : new String[] {"PROCESSING", "TOO_SMALL"})
            assertThat(Arrays.asList(audiences.get(0).getReasonsNotTargetable()), CoreMatchers.hasItem(reason));

        for (String targetableType : new String[] {"CRM", "EXCLUDED_CRM"})
            assertThat(Arrays.asList(audiences.get(0).getTargetableTypes()), CoreMatchers.hasItem(targetableType));

        assertEquals(null, audiences.get(0).getAudienceSize());
        assertEquals(TailoredAudienceType.CRM, audiences.get(0).getAudienceType());

        assertEquals(false, audiences.get(0).isDeleted());
        assertEquals(false, audiences.get(0).isTargetable());

        assertEquals("2015-06-08T20:13:40", audiences.get(0).getCreatedAt().toString());
        assertEquals("2015-06-08T20:13:40", audiences.get(0).getUpdatedAt().toString());
    }

    @Test
    public void createTailoredAudienceFile() {
        String mockedAccountId = "0ga0yn";
        String mockedTailoredAudienceId = "13sf";
        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/tailored_audience_changes"))
                .andExpect(method(POST))
                .andRespond(withSuccess(jsonResource("ad-tailored-audience-changes"), APPLICATION_JSON));

        twitter.tailoredAudienceOperations().createTailoredAudienceChange(
                mockedAccountId,
                new TailoredAudienceChangeFormBuilder()
                        .withTailoredAudience(mockedTailoredAudienceId)
                        .withOperation(TailoredAudienceChangeOperation.ADD)
                        .withInputFilePath("/1.1/ton/data/ta_partner/390472547/oNJvLHs-6e2NUNa.txt"));
    }

    @Test
    public void getTailoredAudienceFile() {
        String mockedAccountId = "0ga0yn";
        String mockedTailoredAudienceChangeId = "13sf";
        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/tailored_audience_changes/"
                        + mockedTailoredAudienceChangeId))
                .andExpect(method(GET))
                .andRespond(withSuccess(jsonResource("ad-tailored-audience-changes"), APPLICATION_JSON));

        TailoredAudienceChange change = twitter.tailoredAudienceOperations().getTailoredAudienceChange(
                mockedAccountId,
                mockedTailoredAudienceChangeId);

        assertEquals("13sf", change.getTailoredAudienceId());
        assertEquals("/ta_partner/3wyo1gwuqF7_Lhr", change.getInputFilePath());
        assertEquals(TailoredAudienceChangeOperation.ADD, change.getOperation());
        assertEquals(TailoredAudienceChangeState.COMPLETED, change.getState());
    }

    @Test
    public void createGlobalOptOut() {
        String mockedAccountId = "0ga0yn";
        mockServer
                .expect(requestTo("https://ads-api.twitter.com/0/accounts/" + mockedAccountId + "/tailored_audiences/global_opt_out"))
                .andExpect(method(PUT))
                .andRespond(withSuccess(jsonResource("ad-global-opt-out"), APPLICATION_JSON));

        twitter.tailoredAudienceOperations().createGlobalOptOut(
                mockedAccountId,
                new GlobalOptOutFormBuilder()
                        .withListType(TailoredAudienceListType.TWITTER_ID)
                        .withInputFilePath("/1.1/ton/data/ta_partner/390472547/oNJvLHs-6e2NUNa.txt"));
    }
}
