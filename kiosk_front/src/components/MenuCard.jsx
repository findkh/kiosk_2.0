import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import ButtonGroup from "@mui/material/ButtonGroup";
import { FaShoppingCart } from "react-icons/fa";
import { BiMinus } from "react-icons/bi";
import { BiPlus } from "react-icons/bi";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";

const MenuCard = ({ name, description, price, image, option, qt }) => {
  return (
    <Card sx={{ width: "280px", mb: 4 }}>
      <CardMedia component="img" alt={name} height="300" image={image} />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {name}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          옵션:
        </Typography>
        {option.length > 0 ? (
          <FormControl>
            <RadioGroup row>
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
          <Typography variant="body2" color="text.secondary">
            없음
          </Typography>
        )}
        <Typography variant="body2" color="text.secondary">
          가격: {price}원
        </Typography>
        <Typography variant="body2" color="text.secondary">
          수량: {qt} 개
        </Typography>
      </CardContent>
      <CardActions sx={{ justifyContent: "space-between" }}>
        <ButtonGroup
          variant="contained"
          sx={{ width: "100%" }}
          aria-label="Basic button group"
        >
          <Button sx={{ flex: 1, fontSize: "1.5rem" }}>
            <BiPlus size={24} />
          </Button>
          <Button sx={{ flex: 1, fontSize: "1.5rem" }}>
            <BiMinus size={24} />
          </Button>
          <Button sx={{ flex: 1, fontSize: "1.5rem" }}>
            <FaShoppingCart size={24} />
          </Button>
        </ButtonGroup>
      </CardActions>
    </Card>
  );
};

export default MenuCard;
