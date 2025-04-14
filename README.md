# SummarizeWebPageAI

A full-stack microservices-based web app that summarizes any public website using an LLM (via Gemini API), and maintains a searchable history of all summaries. Built with React, Java Spring Boot, Scala, FastAPI, PostgreSQL — and designed for containerization and Kubernetes deployment using Helm.

---

## 🚀 Tech Stack

| Layer        | Technology                     |
|--------------|---------------------------------|
| Frontend     | React.js (Vite)                |
| Backend API  | Java Spring Boot               |
| Library      | Scala (Gradle-based)           |
| AI Service   | Python FastAPI + Gemini API    |
| Database     | PostgreSQL                     |
| Deployment   | Docker                         |

---

## 🧩 Project Structure

```
SummarizeWebPageAI/
│
├── java-backend       # Spring Boot API server
├── python-fastapi     # FastAPI service using Gemini API for summarization
├── react-frontend     # React frontend with form and history page
└── scala-lib          # Scala library (called from Java backend)
```

---

## ⚙️ Functionality

- Input a website URL via the frontend.
- Java Spring Boot backend uses the Scala library for database operations.
- Calls the Python FastAPI service (Gemini API) to summarize the website content.
- Stores the URL and summary in PostgreSQL.
- View all previous summaries from the history page.

---

## 🛠️ Prerequisites

Make sure you have the following installed:

- **Node.js & npm**
- **Java (JDK 17+)**
- **Python 3.9+ & pip**
- **PostgreSQL**
- **Docker** (for containerization)

---

## 🧪 Local Setup & Running

### 1️⃣ React Frontend

```bash
cd react-frontend
npm install
npm run dev
```

Runs on: `http://localhost:5173`

---

### 2️⃣ Scala Library Setup

In a **new terminal**:

```bash
cd scala-lib
./gradlew dependencies
./gradlew build

# Copy Scala JAR to Java backend
mkdir -p ../java-backend/libs
cp build/libs/scala-lib.jar ../java-backend/libs
```

---

### 3️⃣ Java Spring Boot Backend

```bash
cd java-backend
./gradlew dependencies
./gradlew clean build
./gradlew bootRun
```

Runs on: `http://localhost:8080`

---

### 4️⃣ Python FastAPI Service (Gemini)

```bash
cd python-fastapi
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
```

Create a `.env` file inside `python-fastapi/` with your Gemini API key:

```
GEMINI_API_KEY=your_api_key_goes_here
```

Then run the server:

```bash
uvicorn main:app --host 0.0.0.0 --port 8000
```

Runs on: `http://localhost:8000`

---

### 5️⃣ PostgreSQL Setup

Launch PostgreSQL, then execute the following:

```sql
-- Access psql shell
psql -U postgres

-- Inside psql
CREATE DATABASE web_summarizer;
CREATE USER postgres WITH PASSWORD 'postgres';
GRANT ALL PRIVILEGES ON DATABASE web_summarizer TO postgres;
```

To view data:

```bash
psql -U postgres -d web_summarizer
SELECT * FROM summary;
```

---

## 📦 Dockerize

### 1️⃣ React Dockerization

```bash
cd react-frontend
docker build -t frontend-image .
docker run --name frontend-container-1 -d -p 4173:4173 frontend-image
```

Runs on: `http://localhost:4173`

### Note: Create Docker Network

```bash
docker network create backend-network
```

### 2️⃣ Python Dockerization

```bash
cd python-fastapi
docker build -t fastapi-image .
docker run \
  --env-file .env \
  --network backend-network \
  -p 8000:8000 \
  --name fastapi-container-1 \
  -d fastapi-image
```

Runs on: `http://localhost:8000`

### 3️⃣ Java Spring Boot Backend

```bash
cd java-backend
docker build -t backend-image .
docker run \
  --name backend-container-1 \
  --network backend-network \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/web_summarizer \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  -e FASTAPI_URL=http://fastapi-container-1:8000/summarize \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/web_summarizer \
  -e DB_USER=postgres \
  -e DB_PASSWORD=postgres \
  -d backend-image
```

Runs on: `http://localhost:8080`

---

# 📑 Docker Compose

Rather than dockerizing react-frontend, java-backend, python-fastapi separately, run the following command once to use the service directly.

```bash
docker-compose up --build
```

---

## 💡 How It Works

1. Open `http://localhost:5173`
2. Enter a website URL and click **Summarize**
3. Backend fetches and sends content to the Python service
4. Python uses the Gemini API to generate a summary
5. Java backend logs the summary to PostgreSQL via Scala library
6. Visit **History** page to view all past summaries

---

## ✅ Features Completed

- [x] React Frontend
- [x] Java Spring Boot Backend
- [x] Scala Library for DB access
- [x] Python FastAPI + Gemini
- [x] PostgreSQL Integration
- [x] Dockerization

---

## 📸 Screenshots

<details>
  <summary>📷 Click to expand</summary>
  
  <br>
  
  ![Screenshot 2025-04-13 at 7 30 58 PM (2)](https://github.com/user-attachments/assets/0b6b72e3-1b18-4e2f-bb05-0c55d310399a)

  ![Screenshot 2025-04-13 at 7 31 14 PM (2)](https://github.com/user-attachments/assets/f59f9f25-06d7-45bc-a8ac-d7a3332f17fb)

  ![Screenshot 2025-04-13 at 7 31 19 PM (2)](https://github.com/user-attachments/assets/7089ddd3-cf8f-4622-8f3d-305d4ceae871)

</details>

---

## 📜 License

GNU General Public License v3.0
