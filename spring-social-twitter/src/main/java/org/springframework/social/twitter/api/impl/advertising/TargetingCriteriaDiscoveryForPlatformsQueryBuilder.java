package org.springframework.social.twitter.api.impl.advertising;

import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForPlatformsQuery;
import org.springframework.util.MultiValueMap;

public class TargetingCriteriaDiscoveryForPlatformsQueryBuilder
        extends AbstractTwitterQueryForEntityBuilder<TargetingCriteriaDiscoveryForPlatformsQuery>
        implements TargetingCriteriaDiscoveryForPlatformsQuery {
	private String language;

	@Override
	public TargetingCriteriaDiscoveryForPlatformsQuery withLanguage(String language) {
		this.language = language;
		return this;
	}

	@Override
    protected void makeParameters(MultiValueMap<String, String> map) {
		appendParameter(map, "lang", this.language);
	}

}
