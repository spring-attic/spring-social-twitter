package org.springframework.social.twitter.api.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by Oyku Gencay (oyku at gencay.net) on 12.08.2014.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = CoordinatesDeserializer.class)
abstract class CoordinatesMixin extends TwitterObjectMixin{
}
