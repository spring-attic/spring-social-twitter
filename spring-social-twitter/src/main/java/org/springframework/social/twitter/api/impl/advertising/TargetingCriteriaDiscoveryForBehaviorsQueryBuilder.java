package org.springframework.social.twitter.api.impl.advertising;

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.advertising.SortDirection;
import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForBehaviorsQuery;
import org.springframework.util.MultiValueMap;

public class TargetingCriteriaDiscoveryForBehaviorsQueryBuilder
extends AbstractTwitterQueryForEntityBuilder<TargetingCriteriaDiscoveryForBehaviorsQuery>
implements TargetingCriteriaDiscoveryForBehaviorsQuery {

    private final List<String> behaviorIds = new ArrayList<String>();
    private String condition;

    @Override
    public TargetingCriteriaDiscoveryForBehaviorsQuery ofBehaviors(String... behaviorIds) {
        if (behaviorIds != null)
            for (final String behaviorId : behaviorIds)
                this.behaviorIds.add(behaviorId);
        return this;
    }

    @Override
    public TargetingCriteriaDiscoveryForBehaviorsQuery sortBy(String field, SortDirection direction) {
        if (field != null && direction != null)
            condition = field + "-" + direction.name();
        else if (field != null)
            condition = field;
        return this;
    }

    @Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        appendParameter(map, "behavior_ids", behaviorIds);
        appendParameter(map, "sort_by", condition);
    }

}
