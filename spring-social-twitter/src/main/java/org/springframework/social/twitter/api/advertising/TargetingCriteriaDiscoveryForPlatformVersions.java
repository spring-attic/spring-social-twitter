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
 * Data discovery for targeting criterias based on platform versions.
 * 
 * @author Hudson Mendes
 */
public class TargetingCriteriaDiscoveryForPlatformVersions extends TwitterObject {
	private static final long serialVersionUID = 1L;
    private final String name;
    private final String number;
    private final String platform;
    private final String targetingType;
    private final String targetingValue;

    public TargetingCriteriaDiscoveryForPlatformVersions(
    	    String name,
    	    String number,
    	    String platform,
    	    String targetingType,
    	    String targetingValue) {

        this.name = name;
        this.number = number;
        this.platform = platform;
        this.targetingType = targetingType;
        this.targetingValue = targetingValue;
    }

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public String getPlatform() {
		return platform;
	}

	public String getTargetingType() {
		return targetingType;
	}

	public String getTargetingValue() {
		return targetingValue;
	}

    
}
