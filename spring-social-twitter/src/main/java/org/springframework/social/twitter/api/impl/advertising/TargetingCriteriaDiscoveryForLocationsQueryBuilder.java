package org.springframework.social.twitter.api.impl.advertising;

import org.springframework.social.twitter.api.advertising.LocationType;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForLocationsQuery;
import org.springframework.util.MultiValueMap;

public class TargetingCriteriaDiscoveryForLocationsQueryBuilder
        extends AbstractTwitterQueryForEntityBuilder<TargetingCriteriaDiscoveryForLocationsQuery>
        implements TargetingCriteriaDiscoveryForLocationsQuery {
	private LocationType location;
	private String countryCode;

	
	@Override
	public TargetingCriteriaDiscoveryForLocationsQuery withLocation(LocationType location) {
		this.location = location;
		return this;
	}

	@Override
	public TargetingCriteriaDiscoveryForLocationsQuery withCountryCode(String countryCode) {
		this.countryCode = countryCode;
		return this;
	}

	@Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        appendParameter(map, "location_type", this.location);
        appendParameter(map, "country_code", this.countryCode);
	}

}
