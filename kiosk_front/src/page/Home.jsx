import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Box from "@mui/material/Box";
import "./Home.css";
import { CiCoffeeCup } from "react-icons/ci";
import { FiCoffee } from "react-icons/fi";
import { MenuContext } from "../context/MenuContext";

const Home = () => {
  const navigate = useNavigate();
  const { selectMenuType } = useContext(MenuContext);

  const handleCardClick = (type) => {
    selectMenuType(type);
    navigate("/menu");
  };

  return (
    <section className="home-container">
      <img src="/image/logo.png" alt="Logo" className="logo" />
      <h1 className="title">
        안녕하세요.
        <br />
        아래의 버튼을 선택해주세요.
      </h1>
      <Box className="card-container">
        <Card
          className="card-item"
          variant="outlined"
          onClick={() => handleCardClick("TAKE_AWAY")}
        >
          <CardContent className="card-content">
            <FiCoffee className="icon" />
            <span>{"먹고가기"}</span>
          </CardContent>
        </Card>
        <Card
          className="card-item"
          variant="outlined"
          onClick={() => handleCardClick("CARRY_OUT")}
        >
          <CardContent className="card-content">
            <CiCoffeeCup className="icon" />
            <span>{"포장하기"}</span>
          </CardContent>
        </Card>
      </Box>
    </section>
  );
};

export default Home;
