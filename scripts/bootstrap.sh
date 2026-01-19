#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

if [ ! -f "$ROOT_DIR/.env" ]; then
  cp "$ROOT_DIR/.env.example" "$ROOT_DIR/.env"
  echo "Created .env from .env.example"
fi

if command -v docker >/dev/null 2>&1; then
  docker compose -f "$ROOT_DIR/infra/compose/docker-compose.yml" up -d postgres redis
  echo "Local dependencies are starting (postgres, redis)."
else
  echo "Docker is not installed; skipping dependency startup."
fi
