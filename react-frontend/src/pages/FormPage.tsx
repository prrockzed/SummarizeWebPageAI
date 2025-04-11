// src/pages/FormPage.tsx
import React, { useState } from 'react';
import { submitUrlForSummary } from '../api';

const FormPage: React.FC = () => {
  const [url, setUrl] = useState('');
  const [summary, setSummary] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const result = await submitUrlForSummary(url);
      setSummary(result.summary || 'Dummy summary response');
    } catch (error) {
      setSummary('Error fetching summary.');
    }
  };

  return (
    <div className="p-4">
      <h1 className="text-xl mb-2">Summarize a Website</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          placeholder="Enter website URL"
          className="border p-2 mr-2"
        />
        <button type="submit" className="bg-blue-500 text-white px-4 py-2">Summarize</button>
      </form>
      {summary && <div className="mt-4"><strong>Summary:</strong> <p>{summary}</p></div>}
    </div>
  );
};

export default FormPage;

