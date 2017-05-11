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

import org.springframework.social.twitter.api.TwitterQueryForEntity;
import org.springframework.social.twitter.api.TwitterQueryForSortableEntity;
import org.springframework.social.twitter.api.advertising.SortDirection;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Basic representation of the QueryString parameters builders
 * that shall be used for querying any data in the APIs.
 * Important: unfortunately, the basic API is a lot less standardized than the ADs Api
 * and therefore we cannot use this base builder for _everything_. However,
 * it's reasonable the Twitter moves towards standardization and then, this
 * builder will become a richer asset to the Api.
 *
 * @author Hudson Mendes
 *
 * @param <TSort> the sort enumberation that varies for each entity being requested.
 */
public abstract class AbstractTwitterQueryForSortableEntityBuilder<TBuilderInterface extends TwitterQueryForEntity<TBuilderInterface>, TSort>
        extends AbstractTwitterQueryForEntityBuilder<TBuilderInterface>
        implements TwitterQueryForSortableEntity<TBuilderInterface, TSort> {

    private TSort sort;
    private SortDirection direction;

    @Override
    @SuppressWarnings("unchecked")
    public TBuilderInterface sortBy(TSort sort) {
        this.sort = sort;
        return (TBuilderInterface) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TBuilderInterface sortBy(TSort sort, SortDirection direction) {
        this.sort = sort;
        this.direction = direction;
        return (TBuilderInterface) this;
    }

    @Override
    public MultiValueMap<String, String> toQueryParameters() {
        final MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        makeParameters(map);

        final MultiValueMap<String, String> parentMap = super.toQueryParameters();
        for (final String parentKey : parentMap.keySet())
            if (parentMap.get(parentKey).size() != 0)
                appendParameter(map, parentKey, parentMap.get(parentKey).get(0));

        final String sort = this.makeSort();
        if (sort != null)
            appendParameter(map, "sort_by", sort);

        return map;
    }

    private String makeSort() {
        if (this.sort == null && this.direction == null)
            return null;

        if (this.direction != null)
            return this.sort + "-" + this.direction;

        return this.sort.toString() + "-" + SortDirection.asc;
    }
}
