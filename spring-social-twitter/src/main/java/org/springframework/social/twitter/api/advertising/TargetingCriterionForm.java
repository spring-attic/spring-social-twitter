package org.springframework.social.twitter.api.advertising;

import org.springframework.social.twitter.api.TwitterForm;

/**
 * Describes the contract for the builder of {@link TargetingCriterion}'s
 * data that will be posted / patched to the endpoint.
 * 
 * @author Hudson Mendes
 */
public interface TargetingCriterionForm extends TwitterForm {

    /**
     * The {@link LineItem} to which {@link TargetingCriterion} is related to.
     * 
     * @param lineItemId is the id of the {@link LineItem}
     * @return the fluent builder
     */
    public abstract TargetingCriterionForm withLineItem(String lineItemId);

    /**
     * The name of the {@link TargetingCriterion}
     * 
     * @param name of the {@link TargetingCriterion};
     * @return the fluent builder
     */
    public abstract TargetingCriterionForm withName(String name);

    /**
     * The targeting definition of the {@link TargetingCriterion}.
     * 
     * @param targetingType is the type of targeting chosen.
     * @param targetingValue is the value of the targeting chose.
     * @return the fluent builder
     */
    public abstract TargetingCriterionForm targeting(String targetingType, String targetingValue);

    /**
     * The targeting definition of the {@link TargetingCriterion}.
     * 
     * @param targetingType is the type of targeting chosen; maybe any of the set out in {@link TargetingCriterionType}.
     * @param targetingValue is the value of the targeting chose.
     * @return the fluent builder
     */
    public abstract TargetingCriterionForm targeting(TargetingCriterionType targetingType, String targetingValue);

    /**
     * Deletes the {@link TargetingCriterion}
     * 
     * @return the fluent builder
     */
    public abstract TargetingCriterionForm active();

    /**
     * Reactivate the {@link TargetingCriterion}.
     * 
     * @return the fluent builder.
     */
    public abstract TargetingCriterionForm deleted();

    /**
     * A boolean value for expansion. Only available for Tailored Audience CRM.
     * 
     * @param targetAudienceExpansion if expansion should be run on tailored audiences
     * @return the fluent builder
     */
    public abstract TargetingCriterionForm withTargetedAudienceExpansion(final Boolean targetAudienceExpansion);

    /**
     * A value for the tailored audience type.
     * 
     * @param tailoredAudienceType Example Values: WEB, CRM, MOBILE, EXCLUDED_WEB,EXCLUDED_CRM, EXCLUDED_MOBILE
     * @return the fluent builder
     */
    public abstract TargetingCriterionForm withTargetedAudienceType(final TailoredAudienceType tailoredAudienceType);

}
