import axios from "axios";

const BASE_URL = "http://localhost:5000/model"; // Python ML backend

export const getPlayerProjection = async (playerId) => {
  const res = await axios.get(`${BASE_URL}/predict/${playerId}`);
  return res.data;
};
