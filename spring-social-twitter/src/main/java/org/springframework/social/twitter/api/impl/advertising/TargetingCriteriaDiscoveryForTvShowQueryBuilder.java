package org.springframework.social.twitter.api.impl.advertising;

import org.springframework.social.twitter.api.advertising.TargetingCriteriaDiscoveryForTvShowQuery;
import org.springframework.util.MultiValueMap;

public class TargetingCriteriaDiscoveryForTvShowQueryBuilder
        extends AbstractTwitterQueryForEntityBuilder<TargetingCriteriaDiscoveryForTvShowQuery>
        implements TargetingCriteriaDiscoveryForTvShowQuery {

    private String tvMarketLocale;

    @Override
    public TargetingCriteriaDiscoveryForTvShowQuery withLocale(String tvMarketLocale) {
        this.tvMarketLocale = tvMarketLocale;
        return this;
    }

    @Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        appendParameter(map, "tv_market_locale", this.tvMarketLocale);

    }
}
