import * as React from "react";
import PropTypes from "prop-types";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import CssBaseline from "@mui/material/CssBaseline";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Footer from "../components/Footer";
import ScrollTop from "../components/ScrollTop"; // 스크롤탑 컴포넌트 import
import { useContext } from "react";
import { MenuContext } from "../context/MenuContext";
import menu from "../demo_data/menu.json"; // 메뉴 데이터 import
import MenuCard from "../components/MenuCard";
import Fab from "@mui/material/Fab";
import { FaAngleUp } from "react-icons/fa";

const Menu = (props) => {
  const { addToCart } = useContext(MenuContext);

  return (
    <>
      <React.Fragment>
        <CssBaseline />
        <AppBar>
          <Toolbar>
            <Typography variant="h6" component="div">
              Kiosk Menu
            </Typography>
          </Toolbar>
        </AppBar>
        <Toolbar id="back-to-top-anchor" />
        <Container>
          <Box sx={{ my: 2 }}>
            <h2>메뉴 페이지</h2>

            <Box
              sx={{
                display: "flex",
                flexWrap: "wrap",
                justifyContent: "flex-start", // 왼쪽 정렬
                gap: 2, // 카드 간의 여백 설정
              }}
            >
              {menu.map((menuItem) => (
                <Box
                  key={menuItem.id}
                  sx={{
                    flex: "0 1 280px", // 최소 너비 설정
                    maxWidth: "280px", // 최대 너비 설정
                    boxSizing: "border-box",
                  }}
                >
                  <MenuCard
                    name={menuItem.name}
                    description={menuItem.description}
                    price={menuItem.price}
                    image={menuItem.image}
                    option={menuItem.option}
                    qt={menuItem.qt}
                    addToCart={addToCart} // addToCart를 props로 전달
                  />
                </Box>
              ))}
            </Box>
          </Box>
        </Container>
        <ScrollTop {...props}>
          <Fab size="small" aria-label="scroll back to top">
            <FaAngleUp />
          </Fab>
        </ScrollTop>
      </React.Fragment>
      <Footer />
    </>
  );
};

export default Menu;
