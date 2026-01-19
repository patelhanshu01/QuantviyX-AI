package com.quantivyx.core.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public record AssetAllocation(String symbol,AssetClass assetClass,BigDecimal weight){public AssetAllocation{if(symbol==null||symbol.isBlank()){throw new IllegalArgumentException("symbol is required");}Objects.requireNonNull(assetClass,"assetClass");Objects.requireNonNull(weight,"weight");}}
