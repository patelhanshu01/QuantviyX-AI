package com.quantivyx.core.domain.service;

import com.quantivyx.core.domain.model.AssetAllocation;
import com.quantivyx.core.domain.model.AssetClass;
import com.quantivyx.core.domain.model.RiskCheckResult;
import com.quantivyx.core.domain.model.RiskProfile;
import com.quantivyx.core.domain.model.RiskViolation;
import com.quantivyx.core.domain.model.RiskViolationCode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RiskCheckService {

    public RiskCheckResult check(RiskProfile profile, List<AssetAllocation> allocations) {
        Objects.requireNonNull(profile, "profile");
        Objects.requireNonNull(allocations, "allocations");

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal crypto = BigDecimal.ZERO;
        List<RiskViolation> violations = new ArrayList<>();

        for (AssetAllocation allocation : allocations) {
            BigDecimal weight = allocation.weight();
            if (weight.signum() < 0) {
                violations.add(new RiskViolation(
                        RiskViolationCode.NEGATIVE_WEIGHT,
                        "Negative weight for " + allocation.symbol()));
            }
            total = total.add(weight);

            if (allocation.assetClass() == AssetClass.CRYPTO) {
                crypto = crypto.add(weight);
            }
        }

        if (total.compareTo(BigDecimal.ONE) > 0) {
            violations.add(new RiskViolation(
                    RiskViolationCode.TOTAL_WEIGHT_EXCEEDS_1,
                    "Total weight exceeds 1.0"));
        }

        if (crypto.compareTo(profile.maxCryptoWeight()) > 0) {
            violations.add(new RiskViolation(
                    RiskViolationCode.CRYPTO_CAP_EXCEEDED,
                    "Crypto weight exceeds profile cap"));
        }

        return new RiskCheckResult(violations.isEmpty(), total, crypto, violations);
    }
}
