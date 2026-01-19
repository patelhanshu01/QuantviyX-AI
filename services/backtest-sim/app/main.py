from typing import List
from fastapi import FastAPI
from pydantic import BaseModel
app = FastAPI(title="backtest-sim", version="0.1.0")


@app.get("/health")
def health() -> dict:
    return {"status": "ok"}


@app.post("/run")
def run_backtest() -> dict:
    # Stub response; real backtest engine comes later.
    return {"status": "stub", "message": "backtest queued"}
