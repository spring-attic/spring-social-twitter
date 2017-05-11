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

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.social.twitter.api.advertising.Campaign;
import org.springframework.social.twitter.api.impl.DataListHolder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Hudson Mendes
 */
public class DataListHolderTest {

    @Test
    public void pagingParse() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(Campaign.class, CampaignMixin.class);
        ClassPathResource resource = new ClassPathResource("ad-campaigns.json", getClass());

        DataListHolder<Campaign> holder = mapper.readValue(
                resource.getFile(),
                new TypeReference<DataListHolder<Campaign>>() {});

        Assert.assertEquals(new Long("71"), holder.getTotalCount());
        Assert.assertEquals("18l3ms5c", holder.getNextCursor());
    }
}
