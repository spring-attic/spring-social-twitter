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

import org.springframework.social.twitter.api.advertising.PromotedUserReference;
import org.springframework.social.twitter.api.advertising.PromotedUserReferenceQuery;
import org.springframework.social.twitter.api.advertising.PromotedUserReferenceSorting;
import org.springframework.social.twitter.api.advertising.SortDirection;
import org.springframework.util.MultiValueMap;

/**
 * Query builder for {@link PromotedUserReference} queries.
 *
 * @author Hudson Mendes
 */
public class PromotedUserReferenceQueryBuilder
extends AbstractTwitterQueryForEntityBuilder<PromotedUserReferenceQuery>
implements PromotedUserReferenceQuery {

    private PromotedUserReferenceSorting sort;
    private SortDirection direction;

    @Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        final String sort = makeSort();
        if (sort != null)
            appendParameter(map, "sort", sort);
    }

    @Override
    public PromotedUserReferenceQuery sortBy(PromotedUserReferenceSorting sort) {
        this.sort = sort;
        return this;
    }

    @Override
    public PromotedUserReferenceQuery sortBy(PromotedUserReferenceSorting sort, SortDirection direction) {
        this.sort = sort;
        this.direction = direction;
        return null;
    }

    private String makeSort() {
        if (sort != null & direction == null)
            return sort.name();
        else if (sort != null & direction != null)
            return sort + "-" + direction;

        return null;
    }
}
