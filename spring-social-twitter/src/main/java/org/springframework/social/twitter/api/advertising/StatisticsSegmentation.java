package org.springframework.social.twitter.api.advertising;

public class StatisticsSegmentation {
    private final StatisticsSegmentationType type;
    private final String value;
    private final String description;

    public StatisticsSegmentation(
            StatisticsSegmentationType type,
            String value,
            String description) {

        this.type = type;
        this.value = value;
        this.description = description;
    }

    public StatisticsSegmentationType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
