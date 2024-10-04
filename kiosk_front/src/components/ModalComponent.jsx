import React from "react";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import { IoMdClose } from "react-icons/io";
import { BiMinus, BiPlus } from "react-icons/bi";
import Button from "@mui/material/Button";
import ButtonGroup from "@mui/material/ButtonGroup";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";

// 모달 스타일 설정
const modalStyle = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 4,
  outline: "none", // 외곽선 제거
};

const ModalComponent = ({
  open,
  handleClose,
  name,
  price,
  image,
  option,
  quantity,
  setQuantity,
  selectedOption,
  setSelectedOption,
  handleAddToCart,
}) => {
  const handleIncrease = () => {
    setQuantity((prev) => prev + 1);
  };

  const handleDecrease = () => {
    if (quantity > 1) {
      setQuantity((prev) => prev - 1);
    }
  };

  const handleOptionChange = (event) => {
    setSelectedOption(event.target.value);
  };

  return (
    <Modal
      open={open}
      onClose={handleClose}
      BackdropProps={{
        onClick: (e) => e.stopPropagation(), // 배경 클릭으로 모달이 닫히지 않도록 설정
      }}
    >
      <Box sx={modalStyle}>
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          <Typography variant="h4" component="h2">
            {name}
          </Typography>
          <IconButton onClick={handleClose}>
            <IoMdClose />
          </IconButton>
        </Box>
        <img src={image} alt={name} style={{ width: "100%", height: "auto" }} />
        <Typography>가격: {price}원</Typography>

        {/* 옵션 선택 */}
        {option.length > 0 ? (
          <FormControl sx={{ mt: 2 }}>
            <Typography variant="body2" color="text.secondary">
              옵션 선택:
            </Typography>
            <RadioGroup
              row
              value={selectedOption}
              onChange={handleOptionChange}
            >
              {option.map((opt) => (
                <FormControlLabel
                  key={opt}
                  value={opt}
                  control={<Radio />}
                  label={opt}
                />
              ))}
            </RadioGroup>
          </FormControl>
        ) : (
          <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
            옵션 없음
          </Typography>
        )}

        {/* 수량 조절 */}
        <Box sx={{ display: "flex", alignItems: "center", mt: 2 }}>
          <Typography>수량: </Typography>
          <ButtonGroup sx={{ ml: 2 }}>
            <Button disabled>{quantity}</Button>
            <Button onClick={handleIncrease}>
              <BiPlus />
            </Button>
            <Button onClick={handleDecrease}>
              <BiMinus />
            </Button>
          </ButtonGroup>
        </Box>

        {/* 담기 버튼 */}
        <Button
          variant="contained"
          sx={{ mt: 2, width: "100%" }}
          onClick={handleAddToCart}
        >
          담기
        </Button>
      </Box>
    </Modal>
  );
};

export default ModalComponent;
