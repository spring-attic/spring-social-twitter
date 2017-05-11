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

import org.springframework.social.twitter.api.advertising.PromotedUserReference;
import org.springframework.social.twitter.api.advertising.PromotedUserReferenceForm;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Builder for {@link PromotedUserReference} input form.
 * 
 * @author Hudson Mendes
 */
public class PromotedUserReferenceFormBuilder extends AbstractTwitterFormBuilder implements PromotedUserReferenceForm {

    private String userId;
    private String lineItemId;

    @Override
    public MultiValueMap<String, String> toRequestBody() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        appendParameter(params, "line_item_id", lineItemId);
        appendParameter(params, "user_id", this.userId);
        return params;
    }

    @Override
    public PromotedUserReferenceForm onLineItem(String lineItemId) {
        this.lineItemId = lineItemId;
        return this;
    }

    @Override
    public PromotedUserReferenceForm forUser(String userId) {
        this.userId = userId;
        return this;
    }

}
