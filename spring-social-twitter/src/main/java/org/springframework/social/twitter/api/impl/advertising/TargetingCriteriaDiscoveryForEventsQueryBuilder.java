package org.springframework.social.twitter.api.impl.advertising;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.social.twitter.api.advertising.EventType;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForEventsQuery;
import org.springframework.util.MultiValueMap;

public class TargetingCriteriaDiscoveryForEventsQueryBuilder
        extends AbstractTwitterQueryForEntityBuilder<TargetingCriteriaDiscoveryForEventsQuery>
        implements TargetingCriteriaDiscoveryForEventsQuery {
	private List<String> eventIds;
	private List<EventType> eventTypes;
	private List<String> countryCodes;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

	
	@Override
	public TargetingCriteriaDiscoveryForEventsQuery withEventIds(List<String> eventIds) {
		this.eventIds = eventIds;
		return this;
	}

	@Override
	public TargetingCriteriaDiscoveryForEventsQuery withEventTypes(List<EventType> eventTypes) {
		this.eventTypes = eventTypes;
		return this;
	}

	@Override
	public TargetingCriteriaDiscoveryForEventsQuery withCountryCodes(List<String> countryCodes) {
		this.countryCodes = countryCodes;
		return this;
	}

	@Override
	public TargetingCriteriaDiscoveryForEventsQuery withStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
		return this;
	}

	@Override
	public TargetingCriteriaDiscoveryForEventsQuery withEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
		return this;
	}

	@Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        appendParameter(map, "ids", this.eventIds);
        appendParameter(map, "event_types", this.eventTypes);
        appendParameter(map, "country_codes", this.countryCodes);
        if(null!=this.startTime) {
        	appendParameter(map, "start_time", this.startTime.toInstant(ZoneOffset.UTC));
        }
        if(null!=this.endTime) {
        	appendParameter(map, "end_time", this.endTime.toInstant(ZoneOffset.UTC));
        }
	}


}
