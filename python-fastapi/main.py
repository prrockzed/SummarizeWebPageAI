from fastapi import FastAPI
from pydantic import BaseModel
from dotenv import load_dotenv
import google.generativeai as genai
import os

app = FastAPI()

# Set the Gemini API key
load_dotenv()
genai.configure(api_key=os.getenv("GEMINI_API_KEY"))

# Define request and response schemas
class SummarizeRequest(BaseModel):
    text: str

class SummarizeResponse(BaseModel):
    summary: str

@app.get("/ping")
def ping():
    return {"message": "pong from FastAPI using gemini-2.0-flash"}

@app.post("/summarize", response_model=SummarizeResponse)
def summarize(request: SummarizeRequest):
    try:
        model = genai.GenerativeModel("models/gemini-2.0-flash")  # <--- Use this model name
        response = model.generate_content(f"Summarize this website content:\n\n{request.text}")
        return SummarizeResponse(summary=response.text)
    except Exception as e:
        return SummarizeResponse(summary=f"Error using Gemini API: {str(e)}")
