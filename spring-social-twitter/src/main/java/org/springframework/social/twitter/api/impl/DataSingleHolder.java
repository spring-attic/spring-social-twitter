/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl;

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
 * @author Hudson Mendes
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataSingleHolder<TEntity> {
	private final TEntity data;
	
	@JsonCreator
	DataSingleHolder(@JsonProperty("data") TEntity data) {
		this.data = data;
	}
	
	public TEntity getData() {
		return this.data;
	}
}
