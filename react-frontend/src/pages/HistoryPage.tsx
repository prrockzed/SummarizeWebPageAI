// src/pages/HistoryPage.tsx
import React, { useEffect, useState } from 'react';
import { fetchSummaryHistory } from '../api';

const HistoryPage: React.FC = () => {
  const [history, setHistory] = useState<any[]>([]);

  useEffect(() => {
    const loadHistory = async () => {
      const data = await fetchSummaryHistory();
      setHistory(data || []);
    };

    loadHistory();
  }, []);

  return (
    <div className="p-4">
      <h1 className="text-xl mb-2">Summary History</h1>
      <ul>
        {history.length === 0 && <li>No history found (dummy)</li>}
        {history.map((entry, idx) => (
          <li key={idx} className="border p-2 mb-2">
            <p><strong>URL:</strong> {entry.url}</p>
            <p><strong>Summary:</strong> {entry.summary}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default HistoryPage;

