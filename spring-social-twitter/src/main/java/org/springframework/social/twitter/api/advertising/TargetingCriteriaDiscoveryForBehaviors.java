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

import java.util.List;

import org.springframework.social.twitter.api.TwitterObject;


/**
 * Data discovery for targeting criterias based on behaviors.
 * 
 * @author Hudson Mendes
 */
public class TargetingCriteriaDiscoveryForBehaviors extends TwitterObject {
	private static final long serialVersionUID = 1L;
	private final String audienceSize;
    private final String behaviorTaxonomyId;
    private final String id;
    private final String name;
    private final String partnerSource;
    private final List<String> targetableTypes;
    
    public TargetingCriteriaDiscoveryForBehaviors(
    	    String audienceSize,
    	    String behaviorTaxonomyId,
    	    String id,
    	    String name,
    	    String partnerSource,
    	    List<String> targetableTypes) {

        this.audienceSize = audienceSize;
        this.behaviorTaxonomyId = behaviorTaxonomyId;
        this.id = id;
        this.name = name;
        this.partnerSource = partnerSource;
        this.targetableTypes = targetableTypes;
    }

	public String getAudienceSize() {
		return audienceSize;
	}

	public String getBehaviorTaxonomyId() {
		return behaviorTaxonomyId;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPartnerSource() {
		return partnerSource;
	}

	public List<String> getTargetableTypes() {
		return targetableTypes;
	}

    
}
