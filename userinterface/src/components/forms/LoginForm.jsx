import React, { useState, useContext } from "react";
import { loginUser, getUserProfile } from "../../api/authApi";
import { AuthContext } from "../../context/AuthContext";

const LoginForm = () => {
  const { saveToken, setUser } = useContext(AuthContext);
  const [formData, setFormData] = useState({ email: "", password: "" });
  const [message, setMessage] = useState("");

  const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = await loginUser(formData);
      saveToken(token);
      const profile = await getUserProfile(token);
      setUser(profile);
      setMessage("Login successful!");
    } catch (err) {
      setMessage(err.response?.data || "Login failed");
    }
  };

  return (
    <form onSubmit={handleSubmit} className="max-w-md mx-auto p-6 bg-white rounded shadow">
      <input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} className="w-full p-2 mb-4 border rounded" required />
      <input type="password" name="password" placeholder="Password" value={formData.password} onChange={handleChange} className="w-full p-2 mb-4 border rounded" required />
      <button type="submit" className="w-full bg-blue-500 text-white p-2 rounded">Login</button>
      {message && <p className="mt-4 text-red-500">{message}</p>}
    </form>
  );
};

export default LoginForm;
