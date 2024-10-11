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

  // 카트 아이템 수량 업데이트 함수 (개수 감소 또는 증가)
  const updateCartItemQuantity = (itemName, selectedOption, quantityChange) => {
    setCartItems((prevItems) => {
      const updatedItems = prevItems.map((item) => {
        if (item.name === itemName && item.selectedOption === selectedOption) {
          const updatedQuantity = item.quantity + quantityChange;
          if (updatedQuantity <= 0) {
            return null; // 수량이 0 이하가 되면 null로 변경 (삭제)
          } else {
            return {
              ...item,
              quantity: updatedQuantity,
              price: item.price * (updatedQuantity / item.quantity), // 수량에 따라 가격 조정
            };
          }
        }
        return item;
      });
      // null이 아닌 아이템들만 반환 (수량 0인 아이템 삭제됨)
      return updatedItems.filter((item) => item !== null);
    });
  };

  return (
    <MenuContext.Provider
      value={{
        menuType,
        selectMenuType,
        cartItems,
        addToCart,
        updateCartItemQuantity, // 수량 업데이트 함수 추가
      }}
    >
      {children}
    </MenuContext.Provider>
  );
};
