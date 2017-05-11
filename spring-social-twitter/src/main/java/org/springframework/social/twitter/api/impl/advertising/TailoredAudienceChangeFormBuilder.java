package org.springframework.social.twitter.api.impl.advertising;

import org.springframework.social.twitter.api.advertising.TailoredAudienceChangeForm;
import org.springframework.social.twitter.api.advertising.TailoredAudienceChangeOperation;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TailoredAudienceChangeFormBuilder extends AbstractTwitterFormBuilder implements TailoredAudienceChangeForm {

    private String tailoredAudienceId;
    private String inputFilePath;
    private TailoredAudienceChangeOperation operation;

    @Override
    public TailoredAudienceChangeForm withTailoredAudience(String tailoredAudienceId) {
        this.tailoredAudienceId = tailoredAudienceId;
        return this;
    }

    @Override
    public TailoredAudienceChangeForm withInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
        return this;
    }

    @Override
    public TailoredAudienceChangeForm withOperation(TailoredAudienceChangeOperation operation) {
        this.operation = operation;
        return this;
    }

    @Override
    public MultiValueMap<String, String> toRequestBody() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        appendParameter(params, "tailored_audience_id", this.tailoredAudienceId);
        appendParameter(params, "input_file_path", this.inputFilePath);
        appendParameter(params, "operation", this.operation);

        return params;
    }

}
