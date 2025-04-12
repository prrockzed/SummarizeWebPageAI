import React, { useEffect, useState } from 'react';
import { fetchSummaryHistory } from '../api';

interface HistoryEntry {
  url: string;
  summary: string;
  createdAt: string;
}

const HistoryPage: React.FC = () => {
  const [history, setHistory] = useState<HistoryEntry[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const loadHistory = async () => {
      try {
        const data = await fetchSummaryHistory();
        
        const sortedData = [...(data || [])].sort((a, b) => {
          return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();
        });
        
        setHistory(sortedData);
      } catch (error) {
        console.error('Error loading history:', error);
      } finally {
        setIsLoading(false);
      }
    };

    loadHistory();
  }, []);

  return (
    <div className="form-container">
      <h1>Summary History</h1>
      
      {isLoading ? (
        <div className="empty-state">
          <span className="loading-spinner"></span>
          <p>Loading history...</p>
        </div>
      ) : history.length === 0 ? (
        <div className="empty-state">
          <p>No history found</p>
        </div>
      ) : (
        <ul className="history-list">
          {history.map((entry, idx) => (
            <li key={idx} className="history-item">
              <h3 className="history-item-title">URL: {entry.url}</h3>
              <p><strong>Summary:</strong> {entry.summary}</p>
              <p className="history-item-date">
                <small>Created: {new Date(entry.createdAt).toLocaleString()}</small>
              </p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default HistoryPage;
