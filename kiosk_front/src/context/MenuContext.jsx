import { createContext, useState } from "react";

export const MenuContext = createContext();

export const MenuProvider = ({ children }) => {
  const [menuType, setMenuType] = useState("");

  const selectMenuType = (type) => {
    setMenuType(type);
  };

  return (
    <MenuContext.Provider value={{ menuType, selectMenuType }}>
      {children}
    </MenuContext.Provider>
  );
};
