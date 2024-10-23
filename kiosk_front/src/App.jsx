import { Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./page/Home";
import Menu from "./page/Menu";
import { MenuProvider } from "./context/MenuContext"; // MenuProvider를 import
import Login from "./page/Login";
import Dashboard from "./page/Dashboard";

function App() {
  return (
    <Routes>
      <Route
        path="/"
        element={
          <MenuProvider>
            <Home />
          </MenuProvider>
        }
      />
      <Route
        path="/menu"
        element={
          <MenuProvider>
            <Menu />
          </MenuProvider>
        }
      />
      <Route path="/login" element={<Login />} />
      <Route path="/dashboard" element={<Dashboard />} />
    </Routes>
  );
}

export default App;
