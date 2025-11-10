import React from "react";

const PlayerCard = ({ player }) => (
  <div className="p-4 bg-white rounded shadow mb-4">
    <h2 className="font-bold">{player?.name || "Player Name"}</h2>
    <p>PTS: {player?.points || 0}</p>
    <p>REB: {player?.rebounds || 0}</p>
    <p>AST: {player?.assists || 0}</p>
  </div>
);

export default PlayerCard;
