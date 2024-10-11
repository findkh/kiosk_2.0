import { useContext, useState } from "react";
import { FaShoppingCart } from "react-icons/fa";
import { MenuContext } from "../context/MenuContext";
import {
  Box,
  Typography,
  Modal,
  List,
  ListItem,
  IconButton,
  Button,
  Divider,
} from "@mui/material";
import { BiMinus, BiPlus } from "react-icons/bi";

const Footer = () => {
  const { cartItems, updateCartItemQuantity } = useContext(MenuContext);
  const [open, setOpen] = useState(false);

  const handleFooterClick = () => {
    setOpen(true); // Open modal
  };

  const handleClose = () => {
    setOpen(false); // Close modal
  };

  const calculateTotalPrice = (items) => {
    return items.reduce((total, item) => {
      return total + item.price;
    }, 0);
  };

  return (
    <>
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
        onClick={handleFooterClick}
      >
        <Typography variant="h6">
          <FaShoppingCart size={24} /> 장바구니
        </Typography>
      </Box>

      <Modal open={open} onClose={handleClose}>
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: 600,
            bgcolor: "background.paper",
            boxShadow: 24,
            p: 4,
            borderRadius: 2,
          }}
        >
          <Typography variant="h6" sx={{ mb: 2 }}>
            장바구니 목록
          </Typography>
          <List>
            {cartItems.map((item, index) => (
              <ListItem key={index}>
                <Box
                  sx={{
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: "center",
                    width: "100%",
                  }}
                >
                  <Typography>
                    {item.name} ({item.selectedOption}) - ₩{item.price}
                  </Typography>
                  <Box sx={{ display: "flex", alignItems: "center" }}>
                    <IconButton
                      onClick={() =>
                        updateCartItemQuantity(
                          item.name,
                          item.selectedOption,
                          -1
                        )
                      }
                      aria-label="decrease"
                    >
                      <BiMinus />
                    </IconButton>
                    <Typography sx={{ display: "inline-block", mx: 1 }}>
                      {item.quantity}
                    </Typography>
                    <IconButton
                      onClick={() =>
                        updateCartItemQuantity(
                          item.name,
                          item.selectedOption,
                          1
                        )
                      }
                      aria-label="increase"
                    >
                      <BiPlus />
                    </IconButton>
                  </Box>
                </Box>
              </ListItem>
            ))}
            <Divider sx={{ my: 2 }} />
            <Typography variant="h6">
              총 가격: ₩{calculateTotalPrice(cartItems)}
            </Typography>
          </List>
          <Button
            variant="contained"
            color="primary"
            sx={{ mt: 2, width: "100%" }}
          >
            결제하기
          </Button>
        </Box>
      </Modal>
    </>
  );
};

export default Footer;
