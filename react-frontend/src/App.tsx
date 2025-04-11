// src/App.tsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import FormPage from './pages/FormPage';
import HistoryPage from './pages/HistoryPage';

const App: React.FC = () => {
  return (
    <Router>
      <div className="p-4">
        <nav className="mb-4">
          <Link to="/" className="mr-4">Submit URL</Link>
          <Link to="/history">History</Link>
        </nav>
        <Routes>
          <Route path="/" element={<FormPage />} />
          <Route path="/history" element={<HistoryPage />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
