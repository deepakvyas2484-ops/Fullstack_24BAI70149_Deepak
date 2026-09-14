import React, { useState } from "react";
import Login from "./login";
import Dashboard from "./Dashboard";

function App() {
  const [token, setToken] = useState(localStorage.getItem("token"));

  const login = (jwt) => {
    localStorage.setItem("token", jwt);
    setToken(jwt);
  };

  const logout = () => {
    localStorage.removeItem("token");
    setToken(null);
  };

  return (
    <>
      {token ? (
        <Dashboard token={token} logout={logout} />
      ) : (
        <Login login={login} />
      )}
    </>
  );
}

export default App;