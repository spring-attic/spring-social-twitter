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
 * Data discovery for targeting criterias based on interests.
 * 
 * @author Hudson Mendes
 */
public class TargetingCriteriaDiscoveryForInterests extends TwitterObject {
	private static final long serialVersionUID = 1L;
    private final String name;
    private final String targetingType;
    private final String targetingValue;

    public TargetingCriteriaDiscoveryForInterests(
    		String name,
    	    String targetingType,
    		String targetingValue) {

        this.name = name;
        this.targetingType = targetingType;
        this.targetingValue = targetingValue;
    }

	public String getName() {
		return name;
	}

	public String getTargetingType() {
		return targetingType;
	}

	public String getTargetingValue() {
		return targetingValue;
	}

    
}
