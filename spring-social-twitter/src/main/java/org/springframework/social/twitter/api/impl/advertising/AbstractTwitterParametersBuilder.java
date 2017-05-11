/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.twitter.api.impl.advertising;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.MultiValueMap;

/**
 * Provides basic functionality for parameter building related components
 * (for both QueryBuilders and FormBuilders) that shall be especialised
 * to produce contract-specific builders.
 *
 * @author Hudson Mendes
 */
public abstract class AbstractTwitterParametersBuilder {
    private static final BigDecimal MICRO_MULTIPLIER = new BigDecimal(1000000);

    protected static <TValue> void appendParameter(
            MultiValueMap<String, String> params,
            String name,
            TValue value) {

        appendParameter(params, name, value, false);
    }

    @SuppressWarnings({"rawtypes"})
    protected static <TValue> void appendParameter(
            MultiValueMap<String, String> params,
            String name,
            TValue value,
            Boolean showIfNull) {

        if (value == null)
            if (!showIfNull)
                return;
            else {
                params.set(name, (String) null);
                return;
            }
        if (value instanceof String && ((String) value).isEmpty())
            return;
        if (value instanceof ArrayList && ((ArrayList) value).size() == 0)
            return;
        if (value instanceof ArrayList) {
            final ArrayList valueAsList = (ArrayList) value;
            final List<String> valueAsStringList = new ArrayList<>();
            for (Object o : valueAsList)
            	if (o != null)
            		valueAsStringList.add(o.toString());
            params.add(name, String.join(",", valueAsStringList));
        }
        else
            params.set(name, value.toString());

    }

    protected static Long translateBigDecimalIntoMicro(BigDecimal value) {
        if (value == null)
            return null;

        return value.multiply(MICRO_MULTIPLIER).longValue();
    }
}
