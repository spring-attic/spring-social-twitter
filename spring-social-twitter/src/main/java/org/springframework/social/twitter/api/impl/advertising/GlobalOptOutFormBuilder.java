package org.springframework.social.twitter.api.impl.advertising;

import org.springframework.social.twitter.api.advertising.GlobalOptOutForm;
import org.springframework.social.twitter.api.advertising.TailoredAudienceListType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class GlobalOptOutFormBuilder extends AbstractTwitterFormBuilder implements GlobalOptOutForm {
    private String tonFilePath;
    private TailoredAudienceListType listType;

    @Override
    public MultiValueMap<String, String> toRequestBody() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        appendParameter(params, "input_file_path", this.tonFilePath);
        appendParameter(params, "list_type", this.listType);
        return params;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.social.twitter.api.impl.advertising.GlobalOptOutForm#withInputFilePath(java.lang.String)
     */
    @Override
    public GlobalOptOutFormBuilder withInputFilePath(String tonFilePath) {
        this.tonFilePath = tonFilePath;
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.social.twitter.api.impl.advertising.GlobalOptOutForm#withListType(org.springframework.social.twitter.api.advertising.
     * TailoredAudienceListType)
     */
    @Override
    public GlobalOptOutForm withListType(TailoredAudienceListType listType) {
        this.listType = listType;
        return this;
    }

}
