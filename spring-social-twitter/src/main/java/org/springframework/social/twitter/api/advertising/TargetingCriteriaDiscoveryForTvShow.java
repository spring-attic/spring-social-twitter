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

import org.springframework.social.twitter.api.TwitterObject;


/**
 * Data discovery for targeting criterias based in tv-shows.
 * 
 * @author Hudson Mendes
 */
public class TargetingCriteriaDiscoveryForTvShow extends TwitterObject {
	private static final long serialVersionUID = 1L;
    private final Long id;
    private final Long estimatedUsers;
    private final String name;
    private final String genre;

    public TargetingCriteriaDiscoveryForTvShow(
            Long id,
            Long estimatedUsers,
            String name,
            String genre) {

        this.id = id;
        this.estimatedUsers = estimatedUsers;
        this.name = name;
        this.genre = genre;

    }

    public Long getId() {
        return id;
    }

    public Long getEstimatedUsers() {
        return estimatedUsers;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }
}
