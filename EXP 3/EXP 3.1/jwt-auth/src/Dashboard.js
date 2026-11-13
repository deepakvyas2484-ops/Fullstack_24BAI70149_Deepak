import React from "react";

function Dashboard({ token, logout }) {
  const user = JSON.parse(atob(token));

  return (
    <div className="container">
      <div className="card">

        <h1>Dashboard</h1>

        <div className="avatar">
          {user.username.charAt(0).toUpperCase()}
        </div>

        <h2>Welcome, {user.username}</h2>

        <p>
          <strong>Role:</strong> {user.role}
        </p>

        <button onClick={logout}>Logout</button>

      </div>
    </div>
  );
}

export default Dashboard;