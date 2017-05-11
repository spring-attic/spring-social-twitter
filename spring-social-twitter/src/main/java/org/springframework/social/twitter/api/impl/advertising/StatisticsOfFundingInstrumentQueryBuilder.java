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

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.advertising.StatisticsOfFundingInstrumentQuery;
import org.springframework.social.twitter.api.advertising.StatisticsSnapshot;
import org.springframework.util.MultiValueMap;

/**
 * Facilitates the creation of the query that will be
 * used to filter results from the {@link StatisticsSnapshot} endpoint.
 * 
 * @author Hudson Mendes
 */
public class StatisticsOfFundingInstrumentQueryBuilder extends AbstractTwitterQueryForStatsBuilder<StatisticsOfFundingInstrumentQuery> implements
        StatisticsOfFundingInstrumentQuery {
    private List<String> fundingInstrumentIds;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.social.twitter.api.impl.advertising.TwitterQueryForStatsOfFundingInstrument#withFundingInstruments(java.lang.String)
     */
    @Override
    public StatisticsOfFundingInstrumentQueryBuilder withFundingInstruments(String... fundingInstrumentIds) {
        this.fundingInstrumentIds = new ArrayList<String>();
        for (int i = 0; i < fundingInstrumentIds.length; i++)
            this.fundingInstrumentIds.add(fundingInstrumentIds[i]);
        return this;
    }

    @Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        appendParameter(map, "funding_instrument_ids", this.fundingInstrumentIds);
    }

}
