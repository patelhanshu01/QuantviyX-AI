# QuantivyX AI PRD v1.2 - Analysis

## Source metadata
- Title: QuantivyX AI: Product Requirements Document (PRD) v1.2
- Version: 1.2
- Date: January 14, 2026
- Author: Hanshu (Lead Architect)
- Reviewers: Senior Software Engineer Team (Fintech Specialists)
- Status: Draft

## Executive summary
QuantivyX AI is an autonomous, risk-first wealth management platform for retail and institutional users. It combines AI-driven forecasting (TFT), portfolio optimization (Black-Litterman), and sentiment analysis to automate multi-asset portfolios across stocks, ETFs, crypto, and bonds. The document targets production-grade readiness with strong compliance, explainability, and operational controls, aiming for a Toronto-first launch in 2026 and 10,000 users in year 1.

## Product intent and value
- Differentiation: Full autonomy with explainable AI, tax-aware execution, and explicit kill-switches.
- Outcomes: Risk-adjusted returns (target Sharpe > 1.2 for aggressive profiles based on backtests).
- Business model: Freemium with $9.99/month premium tier.
- Market positioning: Secure, regulated alternative to typical retail investing platforms in a post-crypto-ETF and AI-regulated landscape.

## Target users and success criteria
- Passive Investor: 25-45, $50k-$200k assets, wants growth with minimal monitoring; success is 80% retention and growth above benchmark.
- Corporate Admin: compliance and ops teams; success is 99.9% uptime and zero audit violations.
- Risk-Averse Retiree: 55+, capital preservation and explainability; success is low volatility and high trust.

## Core use cases (condensed)
- Onboarding: KYC/AML, risk profiling, bank/broker connection via Plaid.
- Predictive analysis: TFT inference with data confidence scores (DCS).
- Auto-rebalancing: end-of-day/intraday trades with constraint enforcement.
- Risk reporting: PDF/CSV exports for audits.
- Manual override: immediate pause or allocation adjustment.
- Backtesting: walk-forward and stress tests with bias checks.

## Architecture and services
- Microservices: api-gateway, portfolio-core, risk-engine, optimizer, execution-engine, market-data, ai-quant, backtest-sim, compliance-audit, ui-web, observability-service.
- Proposed MVP merges: api-gateway + portfolio-core + risk-engine + optimizer into one Spring Boot app.
- Comms: REST/gRPC for commands, GraphQL for queries.
- Scaling: K8s horizontal scaling, GPU support for ai-quant, observability via Prometheus/OpenTelemetry.

## Data and AI stack
- Data sources: Alpha Vantage, Polygon, yfinance, NewsAPI, X, LunarCrush, FRED.
- Model layer: TFT with SHAP explainability; Random Forest/LSTM for signals.
- Confidence: DCS for data quality, MCS for model confidence.
- Optimizer: Black-Litterman combining equilibrium and AI views scaled by MCS.

## Risk controls and execution
- Profiles: Conservative, Balanced, Aggressive with explicit allocations and risk limits.
- Hard constraints: long-only, unlevered, allocation caps, drawdown limits.
- Churn filter: minimum trade threshold per profile.
- Cost model: trade only when expected alpha exceeds total cost.
- Tax lots: HIFO by default with wash-sale constraints.
- Kill-switches: correlation spikes, volatility regime shifts, liquidity stress.

## Security and compliance
- Auth: OAuth2/OIDC, MFA mandatory.
- Data: AES-256 at rest, tokenized PII.
- Compliance: OSC/SEC/FINRA, GDPR/CCPA, CSA crypto guidance.
- Audits: quarterly third-party audits with exportable reports.
- Ethical AI: bias audits in CI, SHAP-based explainability.

## Non-functional requirements
- Performance: <500ms API latency, 99.99% uptime.
- Scalability: 1M daily trades, auto-scaling on volatility.
- Reliability: 3x data redundancy, chaos testing for kill-switches.
- Observability: ELK + Grafana, automated alerts.

## Roadmap highlights
- MVP Q1 2026: core AI and rebalancing, paper trading.
- Beta Q2 2026: live integrations, onboarding.
- Full launch Q3 2026: compliance certifications, 100k scale.
- Estimated build: 3 months, $50k budget.

## Strengths
- Clear risk-first philosophy with explicit hard constraints.
- Strong compliance and auditability focus for regulated markets.
- Detailed AMS specification with confidence scoring and kill-switch logic.
- Practical MVP merge strategy to reduce early microservice overhead.

## Risks and gaps
- Licensing and regulatory approvals are mentioned but not mapped to concrete milestones or jurisdiction-specific licensing steps.
- Data vendor costs and licensing terms (Alpha Vantage, NewsAPI, X) may exceed the $500/month MVP budget.
- Execution and brokerage integration are stated but not detailed for order routing, regulatory reporting, or market access.
- Performance targets (Sharpe, latency, uptime) are stated but acceptance thresholds for CI gates are left open.
- Multi-asset scope (equities, crypto, bonds) increases complexity for compliance, custody, and tax handling.
- Timeline appears aggressive for a regulated platform with live trading, especially if third-party approvals are required.

## Recommendations
- Define a regulatory plan with licensing requirements, timelines, and responsible owners per jurisdiction.
- Lock data vendor contracts early and align the MVP budget to realistic data and compute costs.
- Establish concrete CI gate thresholds (Sharpe, MDD, CVaR, turnover) per risk profile before live trading.
- Clarify broker/exchange integrations and compliance reporting workflows (trade surveillance, order audit trails).
- Narrow the MVP asset universe if needed (e.g., equities + ETFs first) to de-risk compliance and execution.

## Open questions
- Which specific brokerages will be used for live trading and how will order execution be certified?
- What are the exact pass/fail thresholds for model validation gates in CI?
- How will user suitability, disclosures, and consent be captured for AI-driven trading decisions?
- What is the fallback strategy if data providers are unavailable or increase pricing?

## Appendix: key formulas and rules (from the PRD)
- Position sizing: `delta_w_i = alpha * MCS_i * mu_i / sigma_i`
- Vol targeting: `w_adj = w * sigma_target / sigma_projected`
- Divest logic: `w_new = w * (1 - delta)`
- Churn filter: trade only if `abs(delta_w_i) > tau`
- Cost gate: trade only if `expected_alpha > C_total`
