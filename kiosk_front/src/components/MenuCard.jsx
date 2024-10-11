import { useState } from "react";
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import AddMenuModal from "./AddMenuModal"; // 모달 컴포넌트 가져오기

const MenuCard = ({ name, description, price, image, option, addToCart }) => {
  const [open, setOpen] = useState(false);
  const [quantity, setQuantity] = useState(1);
  const [selectedOption, setSelectedOption] = useState(option[0]);

  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setQuantity(1);
    setSelectedOption(option[0]);
    setOpen(false);
  };

  const handleAddToCart = () => {
    const item = {
      name,
      price: price * quantity,
      quantity,
      selectedOption,
      originalPrice: price,
    };
    addToCart(item);
    handleClose();
  };

  return (
    <>
      <Card
        sx={{ width: "280px", mb: 4, cursor: "pointer" }}
        onClick={handleOpen}
      >
        <CardMedia component="img" alt={name} height="300" image={image} />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div">
            {name}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            가격: {price}원
          </Typography>
        </CardContent>
      </Card>

      {/* 모달 */}
      <AddMenuModal
        open={open}
        handleClose={handleClose}
        name={name}
        price={price}
        image={image}
        option={option}
        quantity={quantity}
        setQuantity={setQuantity}
        selectedOption={selectedOption}
        setSelectedOption={setSelectedOption}
        handleAddToCart={handleAddToCart}
      />
    </>
  );
};

export default MenuCard;
