package org.springframework.social.twitter.api.impl.advertising;

import java.util.ArrayList;
import java.util.List;

import org.springframework.social.twitter.api.advertising.AdvertisingObjective;
import org.springframework.social.twitter.api.advertising.SponsoredTweetQuery;
import org.springframework.util.MultiValueMap;

public class SponsoredTweetQueryBuilder extends AbstractTwitterQueryForEntityBuilder<SponsoredTweetQuery> implements SponsoredTweetQuery {
    private final List<Long> userIds = new ArrayList<>();
    private AdvertisingObjective objective;
    private Boolean trimUser;

    @Override
    public SponsoredTweetQuery ofUsers(Long... userIds) {
        for (Long userId : userIds)
            this.userIds.add(userId);
        return this;
    }

    @Override
    public SponsoredTweetQuery withObjective(AdvertisingObjective objective) {
        this.objective = objective;
        return this;
    }

    @Override
    public SponsoredTweetQuery trimUser(Boolean trimUser) {
        this.trimUser = trimUser;
        return this;
    }

    @Override
    protected void makeParameters(MultiValueMap<String, String> map) {
        appendParameter(map, "user_ids", this.userIds);
        appendParameter(map, "objective", this.objective);
        appendParameter(map, "trim_user", this.trimUser);
    }
}
