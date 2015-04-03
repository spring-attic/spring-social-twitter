package org.springframework.social.twitter.api.impl.advertising;

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForBehaviorTaxonomiesQuery;
import org.springframework.util.MultiValueMap;

public class TargetingCriteriaDiscoveryForBehaviorTaxonomiesQueryBuilder
        extends AbstractTwitterQueryForEntityBuilder<TargetingCriteriaDiscoveryForBehaviorTaxonomiesQuery>
        implements TargetingCriteriaDiscoveryForBehaviorTaxonomiesQuery {

	private final List<String> taxonomyIds = new ArrayList<>();
	private final List<String> parentIds = new ArrayList<>();


	@Override
	public TargetingCriteriaDiscoveryForBehaviorTaxonomiesQueryBuilder ofTaxonomyIds(String... taxonomyIds) {
		if(taxonomyIds!=null) {
			for(String taxonomyId: taxonomyIds) {
				this.taxonomyIds.add(taxonomyId);
			}
		}
		return this;
	}

	@Override
	public TargetingCriteriaDiscoveryForBehaviorTaxonomiesQueryBuilder ofParentTaxonomyIds(String... parentIds) {
		if(parentIds!=null) {
			for(String parentId: parentIds) {
				this.parentIds.add(parentId);
			}
		}
		return this;
	}

    @Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        appendParameter(map, "behavior_taxonomy_ids", this.taxonomyIds);
        appendParameter(map, "parent_behavior_taxonomy_ids", this.parentIds);
    }

}
