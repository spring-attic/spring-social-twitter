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

import org.springframework.social.twitter.api.advertising.AdvertisingAccountFeatureQuery;
import org.springframework.social.twitter.api.advertising.FeatureKey;
import org.springframework.util.MultiValueMap;

/**
 * Facilitates the creation of the query that will be
 * used to filter results from the feature endpoint.
 *
 * @author Hudson Mendes
 */
public class AdvertisingAccountFeatureQueryBuilder
        extends AbstractTwitterQueryForEntityBuilder<AdvertisingAccountFeatureQuery>
        implements AdvertisingAccountFeatureQuery {

    private List<FeatureKey> featureKeys = new ArrayList<FeatureKey>();

    @Override
    public AdvertisingAccountFeatureQuery withFeatureKey(FeatureKey... featureKeys) {
        if(null!=featureKeys) {
            for(final FeatureKey featureKey: featureKeys) {
                this.featureKeys.add(featureKey);
            }
        }
        return this;
    }

    @Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        appendParameter(map, "feature_keys", featureKeys);
    }

}
