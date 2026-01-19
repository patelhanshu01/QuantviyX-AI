package com.quantivyx.core.domain.model;

public record RiskViolation(RiskViolationCode code, String message) {
	public RiskViolation {
		if (code == null) {
			throw new IllegalArgumentException("RiskViolationCode is required");
		}
		if (message == null || message.isBlank()) {
			throw new IllegalArgumentException("Message is required");
		}
	}
}
