package com.quantivyx.core.domain.model;

import java.math.BigDecimal;

public enum RiskProfile {
  CONSERVATIVE(new BigDecimal("0.05")),
  BALANCED(new BigDecimal("0.10")),
  AGGRESSIVE(new BigDecimal("0.40"));

  private final BigDecimal maxCryptoWeight;

  RiskProfile(BigDecimal maxCryptoWeight) {
    this.maxCryptoWeight = maxCryptoWeight;
  }

  public BigDecimal maxCryptoWeight() {
    return maxCryptoWeight;
  }
}
