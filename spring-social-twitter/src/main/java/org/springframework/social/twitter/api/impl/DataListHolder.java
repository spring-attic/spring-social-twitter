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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Most of the Twitter Ads API responses for <strong>SINGLE</strong>
 * within a "data" attribute within the root result object:
 * https://dev.twitter.com/ads/reference/get/accounts/%3Aaccount_id/targeting_criteria/%3Aid
 * 
 * This forces us to create a holder class that can receive that "data" JsonProperty
 * in its contructor, allowing our Mixin class to launch and do the actual object conversion.
 * 
 * Our {@link DataSingleHolder} is the abstract/generic object that kills all
 * the repetition necessary to create on holder for each of those.
 * 
 * @author Hudson Mendes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataListHolder<TEntity> implements Serializable {
	private static final long serialVersionUID = 1L;
    private final List<TEntity> list;
    private final String dataType;
    private final String nextCursor;
    private final Long totalCount;

    @JsonCreator
    public DataListHolder(
            @JsonProperty("data") List<TEntity> data,
            @JsonProperty("data_type") String dataType,
            @JsonProperty("next_cursor") String nextCursor,
            @JsonProperty("total_count") Long totalCount) {

        this.list = new ArrayList<TEntity>(data);
        this.dataType = dataType;
        this.nextCursor = nextCursor;
        this.totalCount = totalCount;
    }

    public List<TEntity> getList() {
        return this.list;
    }

    public String getDataType() {
        return this.dataType;
    }

    public String getNextCursor() {
        return this.nextCursor;
    }

    public Long getTotalCount() {
        return totalCount;
    }
}
