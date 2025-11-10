import React, { useState, useEffect, useContext } from "react";
import { getAllPlayers } from "../../api/playerApi";
import { getPlayerProjection } from "../../api/modelApi";
import { PlayerContext } from "../../context/PlayerContext";
import Loader from "../common/Loader";
import ErrorAlert from "../common/ErrorAlert";

const PlayerSelectForm = () => {
  const { selectedPlayer, setSelectedPlayer } = useContext(PlayerContext);
  const [players, setPlayers] = useState([]);
  const [projection, setProjection] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    setLoading(true);
    getAllPlayers()
      .then((res) => setPlayers(res))
      .catch((err) => setError("Failed to fetch players"))
      .finally(() => setLoading(false));
  }, []);

  const handleChange = (e) => {
    const playerId = e.target.value;
    const player = players.find((p) => p.id === parseInt(playerId));
    setSelectedPlayer(player);
    setProjection(null);
  };

  const handlePredict = async () => {
    if (!selectedPlayer) return;
    setLoading(true);
    try {
      const res = await getPlayerProjection(selectedPlayer.id);
      setProjection(res);
    } catch {
      setError("Prediction failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto p-4 bg-white rounded shadow">
      {error && <ErrorAlert message={error} />}
      {loading && <Loader />}
      <select
        value={selectedPlayer?.id || ""}
        onChange={handleChange}
        className="w-full p-2 mb-4 border rounded"
      >
        <option value="">Select a player</option>
        {players.map((player) => (
          <option key={player.id} value={player.id}>
            {player.name}
          </option>
        ))}
      </select>
      <button
        onClick={handlePredict}
        disabled={!selectedPlayer || loading}
        className="w-full bg-green-500 hover:bg-green-600 text-white p-2 rounded"
      >
        Run Prediction
      </button>

      {projection && (
        <div className="mt-4 p-4 bg-blue-100 rounded">
          <h3 className="font-bold mb-2">Projection Result</h3>
          <pre>{JSON.stringify(projection, null, 2)}</pre>
        </div>
      )}
    </div>
  );
};

export default PlayerSelectForm;
