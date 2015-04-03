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

import java.net.URLEncoder;

import org.springframework.social.twitter.api.TwitterQueryForData;
import org.springframework.social.twitter.api.TwitterQueryForEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * Basic representation of the QueryString parameters builders
 * that shall be used for querying any data in the APIs.
 * Important: unfortunately, the basic API is a lot less standardized than the ADs Api
 * and therefore we cannot use this base builder for _everything_. However,
 * it's reasonable the Twitter moves towards standardization and then, this
 * builder will become a richer asset to the Api.
 * 
 * @author Hudson Mendes
 */
public abstract class AbstractTwitterQueryForEntityBuilder<TBuilderInterface extends TwitterQueryForData<TBuilderInterface>>
        extends AbstractTwitterQueryForDataBuilder<TBuilderInterface>
        implements TwitterQueryForEntity<TBuilderInterface> {
	private String query;
    private String cursor;
    private Integer pageSize;

    @Override
    @SuppressWarnings("unchecked")
    public TBuilderInterface pagedBy(String cursor, Integer pageSize) {
        this.cursor = cursor;
        this.pageSize = pageSize;
        return (TBuilderInterface) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TBuilderInterface withQuery(String query) {
        this.query = query;
        return (TBuilderInterface) this;
    }

    @Override
    public MultiValueMap<String, String> toQueryParameters() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        makeParameters(map);

        MultiValueMap<String, String> parentMap = super.toQueryParameters();
        for (String parentKey : parentMap.keySet())
            if (parentMap.get(parentKey).size() != 0)
                appendParameter(map, parentKey, parentMap.get(parentKey).get(0));

        try {
        if (this.query != null)
            appendParameter(map, "q", URLEncoder.encode(this.query, "UTF-8"));
        } catch(Exception e) {
        	appendParameter(map, "q", this.query);
        }
        
        if (!StringUtils.isEmpty(this.cursor))
            appendParameter(map, "cursor", this.cursor);

        if (this.pageSize != null)
            appendParameter(map, "count", this.pageSize);

        return map;
    }
}
