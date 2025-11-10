import React, { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getPlayerById } from "../api/playerApi";
import { getPlayerProjection } from "../api/modelApi";
import { PlayerContext } from "../components/context/PlayerContext";

const PlayerDetail = () => {
  const { id } = useParams();
  const { selectedPlayer, setSelectedPlayer } = useContext(PlayerContext);
  const [projection, setProjection] = useState(null);

  useEffect(() => {
    getPlayerById(id).then(setSelectedPlayer);
    getPlayerProjection(id).then(setProjection);
  }, [id]);

  if (!selectedPlayer) return <p>Loading...</p>;

  return (
    <div className="p-6 bg-white rounded shadow">
      <h2 className="text-xl font-bold">{selectedPlayer.name}</h2>
      <p>Points: {selectedPlayer.points}</p>
      <p>Rebounds: {selectedPlayer.rebounds}</p>
      <p>Assists: {selectedPlayer.assists}</p>
      <p>Minutes: {selectedPlayer.minutes}</p>
      <p>Offensive Possessions: {selectedPlayer.offensivePossessions}</p>
      {projection && (
        <div className="mt-4 p-4 bg-blue-100 rounded">
          <h3 className="font-bold">Next Game Projection</h3>
          <p>{JSON.stringify(projection)}</p>
        </div>
      )}
    </div>
  );
};

export default PlayerDetail;
