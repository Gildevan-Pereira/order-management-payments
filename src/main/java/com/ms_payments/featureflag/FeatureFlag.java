package com.ms_payments.featureflag;

public interface FeatureFlag {

    boolean isFeatureEnabled(String featureName);

    void updateFeatureFlag(String featureName, boolean enabled);
}
