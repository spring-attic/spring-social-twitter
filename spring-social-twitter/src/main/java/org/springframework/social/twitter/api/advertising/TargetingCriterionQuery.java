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
package org.springframework.social.twitter.api.advertising;

import org.springframework.social.twitter.api.TwitterQueryForSortableEntity;

/**
 * Defines the data that will be used to query a list of {@link TargetingCriterion}.
 * 
 * @author Hudson Mendes
 *
 */
public interface TargetingCriterionQuery extends TwitterQueryForSortableEntity<TargetingCriterionQuery, TargetingCriterionSorting> {

    /**
     * Filter the results by a particular {@link LineItem}.
     * 
     * @param lineItemId defines the id of the line item for which we are filtering.
     * @return itself, allowing you to keep going with the setup of your filter.
     */
    public TargetingCriterionQuery withLineItem(String lineItemId);
}
