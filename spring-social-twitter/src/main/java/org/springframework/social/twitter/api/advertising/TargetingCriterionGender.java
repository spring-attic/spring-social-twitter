package org.springframework.social.twitter.api.advertising;

public enum TargetingCriterionGender {
    BOTH(null),
    MALE(1),
    FEMALE(2);

    private final Integer value;

    TargetingCriterionGender(Integer number) {
        this.value = number;
    }

    public Integer value() {
        return value;
    }
}
