import React, { useState } from 'react';
import { submitUrlForSummary } from '../api';

const FormPage: React.FC = () => {
  const [url, setUrl] = useState('');
  const [summary, setSummary] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!url) return;
    
    setIsLoading(true);
    setError('');
    try {
      const result = await submitUrlForSummary(url);
      setSummary(result.summary || 'Dummy summary response');
    } catch (error) {
      setError('Error fetching summary. Please try again.');
      setSummary('');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="form-container">
      <h1>Summarize a Website</h1>
      <form onSubmit={handleSubmit}>
        <div className="input-group">
          <input
            type="url"
            value={url}
            onChange={(e) => setUrl(e.target.value)}
            placeholder="Enter website URL (e.g., https://example.com)"
            className="input-field"
            required
          />
          <button 
            type="submit" 
            className="submit-btn"
            disabled={isLoading || !url}
          >
            {isLoading ? (
              <>
                <span className="loading-spinner"></span>
                Processing...
              </>
            ) : 'Summarize'}
          </button>
        </div>
      </form>

      {error && (
        <div className="result-container" style={{ borderLeftColor: 'var(--error-color)' }}>
          <p style={{ color: 'var(--error-color)' }}>{error}</p>
        </div>
      )}

      {summary && (
        <div className="result-container">
          <h2 className="result-title">Summary</h2>
          <p>{summary}</p>
        </div>
      )}
    </div>
  );
};

export default FormPage;
