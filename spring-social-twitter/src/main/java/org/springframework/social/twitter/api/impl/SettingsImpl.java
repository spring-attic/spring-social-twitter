package org.springframework.social.twitter.api.impl;

import org.springframework.social.twitter.api.Settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SettingsImpl implements Settings {

    @Override
    @JsonProperty("host_for_standard_api")
    public String hostForStandardApi() {
        return TwitterApiHosts.getStandardApi();
    }

    @Override
    @JsonProperty("host_for_ads_api")
    public String hostForAdsApi() {
        return TwitterApiHosts.getAdsApi();
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
