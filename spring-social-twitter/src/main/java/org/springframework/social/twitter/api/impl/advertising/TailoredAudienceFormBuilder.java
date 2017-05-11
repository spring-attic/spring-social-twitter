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

import org.springframework.social.twitter.api.advertising.TailoredAudience;
import org.springframework.social.twitter.api.advertising.TailoredAudienceForm;
import org.springframework.social.twitter.api.advertising.TailoredAudienceListType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Facilitate the creation of the request body for post and put
 * requests made to the management of {@link TailoredAudience} data.
 * 
 * @author Hudson Mendes
 */
public class TailoredAudienceFormBuilder extends AbstractTwitterFormBuilder implements TailoredAudienceForm {

    private String name;
    private TailoredAudienceListType listType;

    @Override
    public MultiValueMap<String, String> toRequestBody() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        appendParameter(params, "name", this.name);
        appendParameter(params, "list_type", this.listType);

        return params;
    }

    @Override
    public TailoredAudienceFormBuilder named(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TailoredAudienceFormBuilder ofListType(TailoredAudienceListType listType) {
        this.listType = listType;
        return this;
    }

}
