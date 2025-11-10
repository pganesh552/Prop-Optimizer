import React from "react";
import DashboardLayout from "../components/layout/DashboardLayout";
import PlayerCard from "../components/cards/PlayerCard";

const Dashboard = () => (
  <DashboardLayout>
    <h1 className="text-2xl font-bold mb-4">Dashboard</h1>
    <PlayerCard /> {/* You can map multiple players later */}
  </DashboardLayout>
);

export default Dashboard;
