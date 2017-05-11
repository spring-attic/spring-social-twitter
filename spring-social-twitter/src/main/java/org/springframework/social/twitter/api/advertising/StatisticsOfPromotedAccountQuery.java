package org.springframework.social.twitter.api.advertising;

import org.springframework.social.twitter.api.TwitterQueryForStats;

public interface StatisticsOfPromotedAccountQuery extends TwitterQueryForStats<StatisticsOfPromotedAccountQuery> {

	public abstract StatisticsOfPromotedAccountQuery withPromotedAccounts(
			String... promotedAccountIds);

}