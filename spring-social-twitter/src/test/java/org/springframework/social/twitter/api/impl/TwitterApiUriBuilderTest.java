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
package org.springframework.social.twitter.api.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * @author Hudson Mendes
 */
public class TwitterApiUriBuilderTest {

    @Test
    public void build_standardHost() {
        String anyResource = "any.json";
        URI standardResult = new TwitterApiBuilderForUri().withResource(anyResource).build();
        assertThat(standardResult.getHost(), equalTo("api.twitter.com"));
    }

    @Test
    public void build_automaticHosts() {
        URI result1 = new TwitterApiBuilderForUri().withResource(TwitterApiUriResourceForAdvertising.ACCOUNTS).build();
        URI result2 = new TwitterApiBuilderForUri().withResource(TwitterApiUriResourceForStandard.BLOCKS).build();
        assertThat(result1.getHost(), not(equalTo(result2.getHost())));
    }

    @Test
    public void build_specificHosts() {
        String anyResource = "any.json";
        URI result1 = new TwitterApiBuilderForUri().forStandardApi().withResource(anyResource).build();
        URI result2 = new TwitterApiBuilderForUri().forAdCampaignsApi().withResource(anyResource).build();
        assertThat(result1.getHost(), not(equalTo(result2.getHost())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_fails_missingResource() {
        new TwitterApiBuilderForUri().build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_fails_invalidResource() {
        new TwitterApiBuilderForUri().withResource((String) null).build();
    }

    @Test
    public void build_resourceOnly() {
        String resource1 = "standard_api_resource_1.js";
        URI result1 = new TwitterApiBuilderForUri().forStandardApi().withResource(resource1).build();

        String resource2 = "standard_api_resource_2.js";
        URI result2 = new TwitterApiBuilderForUri().forStandardApi().withResource(resource2).build();

        assertThat(versionFreePath(result1.getPath()), equalTo(resource1));
        assertThat(versionFreePath(result2.getPath()), equalTo(resource2));
        assertThat(result1.getPath(), not(equalTo(result2.getPath())));
    }

    @Test
    public void build_oneArgument() {
        String anyResource = "standard_api_resource.json";
        String argName = "argument_name";
        String argValue = "argument_value";
        URI result = new TwitterApiBuilderForUri()
                .withResource(anyResource)
                .withArgument(argName, argValue)
                .build();

        assertThat(result, not(nullValue()));
        assertThat(versionFreePath(result.getPath()), equalTo(anyResource));
        assertThat(result.getQuery(), equalTo(argName + "=" + argValue));
    }

    @Test
    public void build_multipleArguments_chained() {
        String anyResource = "standard_api_resource.json";
        URI result = new TwitterApiBuilderForUri()
                .withResource(anyResource)
                .withArgument("argument_name_1", "argument_value_1")
                .withArgument("argument_name_2", "argument_value_2")
                .withArgument("argument_name_3", "argument_value_3")
                .build();

        String chainedQuery =
                "argument_name_1" + "=" + "argument_value_1" + "&" +
                        "argument_name_2" + "=" + "argument_value_2" + "&" +
                        "argument_name_3" + "=" + "argument_value_3";

        assertThat(result, not(nullValue()));
        assertThat(versionFreePath(result.getPath()), equalTo(anyResource));
        assertThat(result.getQuery(), equalTo(chainedQuery));
    }

    @Test
    public void build_multipleArguments_array() {
        String anyResource = "standard_api_resource.json";

        MultiValueMap<String, String> arguments = new LinkedMultiValueMap<>();
        arguments.add("argument_name_A", "argument_value_A");
        arguments.add("argument_name_B", "argument_value_B");
        arguments.add("argument_name_C", "argument_value_C");

        URI result = new TwitterApiBuilderForUri()
                .withResource(anyResource)
                .withArgument(arguments)
                .build();

        String chainedQuery =
                "argument_name_A" + "=" + "argument_value_A" + "&" +
                        "argument_name_B" + "=" + "argument_value_B" + "&" +
                        "argument_name_C" + "=" + "argument_value_C";

        assertThat(result, not(nullValue()));
        assertThat(versionFreePath(result.getPath()), equalTo(anyResource));
        assertThat(result.getQuery(), equalTo(chainedQuery));
    }

    @Test
    public void build_implicitArguments_array() {
        String anyResource = "user/:implicit_id/standard_api_resource.json";
        String implicitArgumentName = "implicit_id";
        String implicitArgumentValue = "831hgk";

        MultiValueMap<String, String> arguments = new LinkedMultiValueMap<>();
        arguments.add(implicitArgumentName, implicitArgumentValue);

        URI result = new TwitterApiBuilderForUri()
                .withResource(anyResource)
                .withArgument(arguments)
                .build();

        String expectedQuery = anyResource.replace(":" + implicitArgumentName, implicitArgumentValue);
        assertThat(result, not(nullValue()));
        assertThat(versionFreePath(result.getPath()), equalTo(expectedQuery));
    }

    private String versionFreePath(String path) {
        final String splitter = "/";
        final String[] frags =  path.split(splitter);
        final List<String> newPath = new ArrayList<>();
        for (final String frag : frags)
        	if (frag != null && !StringUtils.isEmpty(frag.trim()))
        		newPath.add(frag);
        newPath.remove(0);
        return String.join(splitter, newPath);
    }
}
