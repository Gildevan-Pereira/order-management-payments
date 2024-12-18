package com.ms_payments.controller;

import com.ms_payments.featureflag.RedisFeatureFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feature-flags")
public class FeatureFlagController {

    @Autowired
    private RedisFeatureFlagService featureFlagService;

    @GetMapping("/{featureName}")
    public boolean isFeatureEnabled(@PathVariable String featureName) {
        return featureFlagService.isFeatureEnabled(featureName);
    }

    @PostMapping("/{featureName}")
    public void updateFeatureFlag(
            @PathVariable String featureName,
            @RequestParam boolean enabled) {
        featureFlagService.updateFeatureFlag(featureName, enabled);
    }
}