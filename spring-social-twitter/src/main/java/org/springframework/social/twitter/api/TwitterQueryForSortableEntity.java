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
package org.springframework.social.twitter.api;

import org.springframework.social.twitter.api.advertising.SortDirection;


/**
 * Generic data query builder contract that can be used to build
 * request parameters transfering data specially for GET
 * operations in Api endpoints.
 * 
 * This helps eliminate dependencies that the "api" package may
 * have to the "api.impl" package since contracts by definition
 * should not be coupled to implementations.
 * 
 * @author Hudson mendes
 */
public interface TwitterQueryForSortableEntity<TBuilderInterface extends TwitterQueryForEntity<TBuilderInterface>, TSort> extends
        TwitterQueryForEntity<TBuilderInterface> {

    public TBuilderInterface sortBy(TSort sort);
    public TBuilderInterface sortBy(TSort sort, SortDirection direction);
}
