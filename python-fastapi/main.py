from fastapi import FastAPI, Request
from pydantic import BaseModel

app = FastAPI()

class SummarizeRequest(BaseModel):
    text: str

class SummarizeResponse(BaseModel):
    summary: str

@app.get("/ping")
def ping():
    return {"message": "pong from fastapi"}

@app.post("/summarize", response_model=SummarizeResponse)
def summarize(request: SummarizeRequest):
    # Dummy logic (can replace with OpenAI later)
    return SummarizeResponse(summary=f"Dummy summary for: {request.text[:30]}...")

