package org.springframework.social.twitter.api.advertising;

public enum StatisticsSegmentationType {
    APP_STORE_CATEGORY(false, false),
    CITIES(true, false),
    CONVERSION_TAGS(false, false),
    DEVICES(false, true),
    GENDER(false, false),
    INTERESTS(false, false),
    KEYWORDS(false, false),
    LANGUAGE(false, false),
    LOCATIONS(false, false),
    PLATFORMS(false, false),
    PLATFORM_VERSIONS(false, true),
    POSTAL_CODES(true, false),
    REGIONS(true, false);

    private final Boolean countryRequired;
    private final Boolean platformRequired;

    StatisticsSegmentationType(
            Boolean countryRequired,
            Boolean platformRequired) {

        this.countryRequired = countryRequired;
        this.platformRequired = platformRequired;
    }

    public Boolean getCountryRequired() {
        return countryRequired;
    }

    public Boolean getPlatformRequired() {
        return platformRequired;
    }
}
