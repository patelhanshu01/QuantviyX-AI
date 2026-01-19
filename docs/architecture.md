# Architecture Overview

This repository uses a modular monolith for the core decision engine and separates AI inference and backtesting for isolation and scalability.

```mermaid
flowchart LR
  UI[Web UI] --> Core[core-platform]
  Core --> AI[ai-quant]
  Core --> BT[backtest-sim]
  Core --> PG[(Postgres/Timescale)]
  Core --> RD[(Redis)]
```

## Notes
- Command path: core-platform orchestrates risk, optimizer, and execution modules.
- Query path: UI reads portfolio and audit data through core-platform APIs.
