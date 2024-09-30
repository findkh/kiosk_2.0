import { Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./page/Home";
import Menu from "./page/Menu";
import { MenuProvider } from "./context/MenuContext";

function App() {
  return (
    <MenuProvider>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/menu" element={<Menu />} />
      </Routes>
    </MenuProvider>
  );
}

export default App;
