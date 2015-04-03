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

import org.springframework.social.twitter.api.advertising.AdvertisingProductType;
import org.springframework.social.twitter.api.advertising.LineItemPlacementsQuery;
import org.springframework.util.MultiValueMap;

/**
 * Facilitates the creation of the query that will be
 * used to filter results from the feature endpoint.
 *
 * @author Hudson Mendes
 */
public class LineItemPlacementsQueryBuilder
        extends AbstractTwitterQueryForEntityBuilder<LineItemPlacementsQuery>
        implements LineItemPlacementsQuery {

    private List<AdvertisingProductType> productTypes = new ArrayList<AdvertisingProductType>();

    @Override
    public LineItemPlacementsQuery withProductTypes(List<AdvertisingProductType> productTypes) {
    	if(null!=productTypes) {
    		for(final AdvertisingProductType productType: productTypes) {
    			this.productTypes.add(productType);
    		}
    	}
    	return this;
    }

    @Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        appendParameter(map, "product_type", productTypes);
    }

}
