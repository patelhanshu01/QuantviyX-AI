from typing import List

from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI(title="ai-quant", version="0.1.0")


class PredictRequest(BaseModel):
    symbols: List[str]
    horizon_days: int = 1


class PredictResponse(BaseModel):
    status: str
    message: str


@app.get("/health")
def health() -> dict:
    return {"status": "ok"}


@app.post("/predict", response_model=PredictResponse)
def predict(payload: PredictRequest) -> PredictResponse:
    # Stub response; real model integration comes later.
    return PredictResponse(
        status="stub",
        message=f"received {len(payload.symbols)} symbols",
    )
