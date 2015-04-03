/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.advertising;

/**
 * Reasons for which a {@link Campaign} may not be servable.
 * @author Hudson Mendes
 */
public enum ReasonNotServable {
	ACCOUNT_REJECTED,
	ACCOUNT_UNDER_REVIEW,
	AWAITING_APPROVAL_BY_ADVERTISER,
	BUDGET_EXHAUSTED,
	CONTENT_REVIEW_PROBLEM,
	DELETED,
	EXPIRED,
	FUNDING_PROBLEM,
	INCOMPLETE,
	PAUSED_BY_ADVERTISER,
	STARTS_IN_FUTURE
}
