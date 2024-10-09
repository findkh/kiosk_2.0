import { createContext, useState } from "react";

export const MenuContext = createContext();

export const MenuProvider = ({ children }) => {
  const [menuType, setMenuType] = useState(""); // 포장/테이크아웃 상태
  const [cartItems, setCartItems] = useState([]); // 카트 상태

  // 메뉴 타입 선택 함수
  const selectMenuType = (type) => {
    setMenuType(type);
  };

  // 카트에 아이템 추가 함수
  const addToCart = (item) => {
    setCartItems((prevItems) => {
      const existingItemIndex = prevItems.findIndex(
        (cartItem) =>
          cartItem.name === item.name &&
          cartItem.selectedOption === item.selectedOption // 옵션 추가
      );
      if (existingItemIndex !== -1) {
        // 기존에 동일한 메뉴가 있을 경우, 개수를 더함
        const updatedItems = [...prevItems];
        updatedItems[existingItemIndex].quantity += item.quantity;
        updatedItems[existingItemIndex].price += item.price;
        return updatedItems;
      } else {
        // 새롭게 카트에 추가
        return [...prevItems, item];
      }
    });
  };

  return (
    <MenuContext.Provider
      value={{ menuType, selectMenuType, cartItems, addToCart }}
    >
      {children}
    </MenuContext.Provider>
  );
};
