import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import { FaShoppingCart } from "react-icons/fa";

const Footer = () => {
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
    >
      <Typography variant="h6">
        <FaShoppingCart size={24} />
      </Typography>
    </Box>
  );
};

export default Footer;
