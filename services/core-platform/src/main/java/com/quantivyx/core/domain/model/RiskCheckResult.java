package com.quantivyx.core.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record RiskCheckResult(
        boolean passed,
        BigDecimal totalWeight,
        BigDecimal cryptoWeight,
        List<RiskViolation> violations) {
    public RiskCheckResult {
        if (totalWeight == null || cryptoWeight == null || violations == null) {
            throw new IllegalArgumentException("result fields are required");
        }
        violations = List.copyOf(violations);
    }
}
