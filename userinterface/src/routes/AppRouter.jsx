import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AuthProvider } from "../context/AuthContext";
import { PlayerProvider } from "../context/PlayerContext";

import Login from "../pages/Login";
import Register from "../pages/Register";
import Dashboard from "../pages/Dashboard";
import PlayerDetail from "../pages/PlayerDetail";
import Predictions from "../pages/Predictions";

const AppRouter = () => (
  <AuthProvider>
    <PlayerProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/player/:id" element={<PlayerDetail />} />
          <Route path="/predictions" element={<Predictions />} />
          <Route path="*" element={<Login />} />
        </Routes>
      </Router>
    </PlayerProvider>
  </AuthProvider>
);

export default AppRouter;
