package org.springframework.social.twitter.api.impl;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Annotated mixin to add Jackson annotations to TwitterObject. 
 * @author Craig Walls
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class TwitterObjectMixin {

	@JsonAnySetter
	abstract void add(String key, Object value);

}
