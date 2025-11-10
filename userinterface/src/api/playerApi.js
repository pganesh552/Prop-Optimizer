import axios from "axios";

const BASE_URL = "http://localhost:8080/players";

export const getAllPlayers = async () => {
  const res = await axios.get(BASE_URL);
  return res.data;
};

export const getPlayerById = async (id) => {
  const res = await axios.get(`${BASE_URL}/${id}`);
  return res.data;
};
