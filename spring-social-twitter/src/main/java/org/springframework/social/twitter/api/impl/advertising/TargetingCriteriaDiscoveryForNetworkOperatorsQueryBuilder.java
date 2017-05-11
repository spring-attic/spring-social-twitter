package org.springframework.social.twitter.api.impl.advertising;

import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForNetworkOperatorsQuery;
import org.springframework.util.MultiValueMap;

public class TargetingCriteriaDiscoveryForNetworkOperatorsQueryBuilder
        extends AbstractTwitterQueryForEntityBuilder<TargetingCriteriaDiscoveryForNetworkOperatorsQuery>
        implements TargetingCriteriaDiscoveryForNetworkOperatorsQuery {
	private String countryCode;

	@Override
	public TargetingCriteriaDiscoveryForNetworkOperatorsQuery withCountryCode(String countryCode) {
		this.countryCode = countryCode;
		return this;
	}
	
    @Override
    protected void makeParameters(MultiValueMap<String, String> map) {
    	appendParameter(map, "country_code", this.countryCode);
    }

}
