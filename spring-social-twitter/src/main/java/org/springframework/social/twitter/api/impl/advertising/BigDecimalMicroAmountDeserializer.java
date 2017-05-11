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
package org.springframework.social.twitter.api.impl.advertising;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Deserializer to get a BigDecimal (money or similar) value from a "micro" value.
 * "Micro" values, in parts of the twitter Ads api is defined by a value multiplied
 * by 1.000.000. This is to preserve the precision of the number (and also to make
 * sure that IEEE typed floating points will not break the number). But within java
 * we can safely work with BigDecimal type instead, for better representation and
 * manipulation of the number.
 * @author Hudson Mendes
 */
public class BigDecimalMicroAmountDeserializer extends JsonDeserializer<BigDecimal> {

	@Override
	public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		return parse(p.getText());
	}

	public static BigDecimal parse(String rawValue) {
		Long microAmount = new Long(rawValue);
		return new BigDecimal(microAmount / 1000000.00).round(MathContext.DECIMAL32);
	}
}
