package org.springframework.social.twitter.api.impl.advertising;

import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForInterestsQuery;
import org.springframework.util.MultiValueMap;

public class TargetingCriteriaDiscoveryForInterestsQueryBuilder
        extends AbstractTwitterQueryForEntityBuilder<TargetingCriteriaDiscoveryForInterestsQuery>
        implements TargetingCriteriaDiscoveryForInterestsQuery {
	private String language;
	
	@Override
	public TargetingCriteriaDiscoveryForInterestsQuery withLanguage(String language) {
		this.language = language;
		return this;
	}

    @Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        appendParameter(map, "lang", this.language);
    }

}
