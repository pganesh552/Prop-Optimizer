import React from "react";
import { Link } from "react-router-dom";

const Sidebar = () => (
  <aside className="bg-gray-100 w-64 p-4 min-h-screen">
    <h2 className="text-lg font-bold mb-4">Navigation</h2>
    <ul className="space-y-2">
      <li><Link to="/dashboard" className="hover:text-blue-500">Dashboard</Link></li>
      <li><Link to="/predictions" className="hover:text-blue-500">Predictions</Link></li>
    </ul>
  </aside>
);

export default Sidebar;
