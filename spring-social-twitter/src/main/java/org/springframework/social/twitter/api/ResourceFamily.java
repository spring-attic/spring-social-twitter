/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api;

import java.util.HashMap;
import java.util.Map;

/**
 * An enum to facilitate the use of the rate_limit_status endpoint by enumerating all of the resource
 * families that can be searched via the API
 * @author Jeremy Appel
 */
public enum ResourceFamily {
	
	ACCOUNT("account"),
	APPLICATION("application"),
	BLOCKS("blocks"),
	DIRECT_MESSAGES("direct_messages"),
	FOLLOWERS("followers"),
	FRIENDS("friends"),
	FRIENDSHIPS("friendships"),
	GEO("geo"),
	HELP("help"),
	LISTS("lists"),
	SAVED_SEARCHES("saved_searches"),
	SEARCH("search"),
	STATUSES("statuses"),
	TRENDS("trends"),
	USERS("users");
	
	private final String name;
	private static final Map<String, ResourceFamily> stringToEnum =  new HashMap<String, ResourceFamily>();

	static {
		for (ResourceFamily resource : values())
			stringToEnum.put(resource.toString(), resource);
	}
	
	ResourceFamily(String name) {
		this.name = name;
	}
		
	@Override
	public String toString(){
		return name;
	}
		
	public static ResourceFamily getResourceFamily(String name) {
		return stringToEnum.get(name);
	}
	
}
