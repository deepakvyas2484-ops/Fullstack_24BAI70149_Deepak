import { useState } from "react";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import "./App.css";

import Login from "./pages/Login";
import Home from "./pages/Home";
import Admin from "./pages/Admin";
import Editor from "./pages/Editor";
import Viewer from "./pages/Viewer";
import Unauthorized from "./pages/Unauthorized";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {

  // Stores logged-in user's role
  const [userRole, setUserRole] = useState("");

  return (
    <BrowserRouter>

      {/* Navigation Bar */}
      <div className="navbar">
        <div className="logo">RBAC System</div>

        <div className="nav-links">
          <Link to="/">Login</Link>
          <Link to="/home">Home</Link>
          <Link to="/admin">Admin</Link>
          <Link to="/editor">Editor</Link>
          <Link to="/viewer">Viewer</Link>
        </div>
      </div>

      <Routes>

        {/* Login Page */}
        <Route
          path="/"
          element={<Login setUserRole={setUserRole} />}
        />

        {/* Home Page */}
        <Route
          path="/home"
          element={<Home />}
        />

        {/* Admin Route */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute
              userRole={userRole}
              allowedRoles={["Admin"]}
            >
              <Admin />
            </ProtectedRoute>
          }
        />

        {/* Editor Route */}
        <Route
          path="/editor"
          element={
            <ProtectedRoute
              userRole={userRole}
              allowedRoles={["Admin", "Editor"]}
            >
              <Editor />
            </ProtectedRoute>
          }
        />

        {/* Viewer Route */}
        <Route
          path="/viewer"
          element={
            <ProtectedRoute
              userRole={userRole}
              allowedRoles={["Admin", "Editor", "Viewer"]}
            >
              <Viewer />
            </ProtectedRoute>
          }
        />

        {/* Unauthorized Page */}
        <Route
          path="/unauthorized"
          element={<Unauthorized />}
        />

      </Routes>

    </BrowserRouter>
  );
}

export default App;