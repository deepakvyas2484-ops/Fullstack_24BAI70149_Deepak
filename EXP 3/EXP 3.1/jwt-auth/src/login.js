import React, { useState } from "react";

function Login({ login }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = () => {
    if (username === "Deepak Vyas" && password === "24BAI70149") {
      const payload = {
        username: "Deepak Vyas",
        role: "Student",
      };

      const token = btoa(JSON.stringify(payload));

      login(token);
    } else {
      alert("Invalid Username or Password");
    }
  };

  return (
    <div className="container">
      <div className="card">
        <h1>JWT Authentication</h1>
        <p>Secure Login System</p>

        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={handleLogin}>Login</button>

        <div className="demo">
          <b>Demo Login</b>
          <br />
          Username: <b>Deepak Vyas</b>
          <br />
          Password: <b>24BAI70149</b>
        </div>
      </div>
    </div>
  );
}

export default Login;