#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
EXIT_CODE=0

if [ -f "$ROOT_DIR/services/core-platform/pom.xml" ]; then
  mvn -f "$ROOT_DIR/services/core-platform/pom.xml" test || EXIT_CODE=$?
else
  echo "Skipping Java tests: pom.xml not found."
fi

if [ -d "$ROOT_DIR/services/ai-quant/tests" ]; then
  if command -v pytest >/dev/null 2>&1; then
    pytest "$ROOT_DIR/services/ai-quant/tests" || EXIT_CODE=$?
  else
    echo "Skipping Python tests: pytest not installed."
  fi
fi

if [ -d "$ROOT_DIR/services/backtest-sim/tests" ]; then
  if command -v pytest >/dev/null 2>&1; then
    pytest "$ROOT_DIR/services/backtest-sim/tests" || EXIT_CODE=$?
  else
    echo "Skipping Python tests: pytest not installed."
  fi
fi

if [ -f "$ROOT_DIR/web/package.json" ]; then
  if command -v npm >/dev/null 2>&1; then
    npm --prefix "$ROOT_DIR/web" test || EXIT_CODE=$?
  else
    echo "Skipping web tests: npm not installed."
  fi
fi

exit $EXIT_CODE
