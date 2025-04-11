// src/api.ts
import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080', // dummy Spring Boot URL
});

export const submitUrlForSummary = async (url: string) => {
  const response = await API.post('/summarize', { url });
  return response.data;
};

export const fetchSummaryHistory = async () => {
  const response = await API.get('/history');
  return response.data;
};

