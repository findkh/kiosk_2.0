import * as React from "react";
import PropTypes from "prop-types";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import CssBaseline from "@mui/material/CssBaseline";
import useScrollTrigger from "@mui/material/useScrollTrigger";
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Fab from "@mui/material/Fab";
import { FaAngleUp } from "react-icons/fa";
import Fade from "@mui/material/Fade";
import { useContext } from "react";
import { MenuContext } from "../context/MenuContext";
import Footer from "../components/Footer";

function ScrollTop(props) {
  const { children, window } = props;
  const trigger = useScrollTrigger({
    target: window ? window() : undefined,
    disableHysteresis: true,
    threshold: 100,
  });

  const handleClick = (event) => {
    const anchor = (event.target.ownerDocument || document).querySelector(
      "#back-to-top-anchor"
    );

    if (anchor) {
      anchor.scrollIntoView({
        block: "center",
      });
    }
  };

  return (
    <Fade in={trigger}>
      <Box
        onClick={handleClick}
        role="presentation"
        sx={{ position: "fixed", bottom: 16, right: 16 }}
      >
        {children}
      </Box>
    </Fade>
  );
}

ScrollTop.propTypes = {
  children: PropTypes.element,
  window: PropTypes.func,
};

const Menu = (props) => {
  const { selectedOption } = useContext(MenuContext); // 선택한 옵션 가져오기

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
            <p>선택한 옵션: {selectedOption}</p>
            {/* 여기에 원하는 콘텐츠를 추가하세요 */}
            <div>
              {/* 예시 메뉴 항목 */}
              <h3>메뉴 항목</h3>
              <ul>
                <li>아이템 1</li>
                <li>아이템 2</li>
                <li>아이템 3</li>
                <li>아이템 1</li>
                <li>아이템 2</li>
                <li>아이템 3</li>
                <li>아이템 1</li>
                <li>아이템 2</li>
                <li>아이템 3</li>
                <li>아이템 1</li>
                <li>아이템 2</li>
                <li>아이템 3</li>
                <li>아이템 1</li>
                <li>아이템 2</li>
                <li>아이템 3</li>
              </ul>
            </div>
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
