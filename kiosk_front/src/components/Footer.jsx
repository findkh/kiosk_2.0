import { useContext } from "react";
import { FaShoppingCart } from "react-icons/fa";
import { MenuContext } from "../context/MenuContext";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

const Footer = () => {
  const { menuType, cartItems } = useContext(MenuContext);

  const handleFooterClick = () => {
    console.log("메뉴 타입:", menuType);
    console.log("장바구니 아이템:", cartItems);
  };

  return (
    <Box
      sx={{
        position: "fixed",
        bottom: 0,
        left: 0,
        right: 0,
        backgroundColor: "white",
        boxShadow: 1,
        p: 2,
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        cursor: "pointer",
      }}
      onClick={handleFooterClick} // 클릭 이벤트 핸들러 추가
    >
      <Typography variant="h6">
        <FaShoppingCart size={24} />
      </Typography>
    </Box>
  );
};

export default Footer;
