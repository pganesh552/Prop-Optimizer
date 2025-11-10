import axios from "axios";

const BASE_URL = "http://localhost:8080/auth";

export const registerUser = async (userData) => {
  const response = await axios.post(`${BASE_URL}/addNewUser`, userData);
  return response.data;
};

export const loginUser = async (authData) => {
  const response = await axios.post(`${BASE_URL}/generate-token`, authData);
  return response.data; // JWT token
};

export const getUserProfile = async (token) => {
  const response = await axios.get(`${BASE_URL}/user/profile`, {
    headers: { Authorization: `Bearer ${token}` },
  });
  return response.data;
};
