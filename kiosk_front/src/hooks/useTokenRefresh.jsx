import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { checkAndRefreshToken } from "../util/tokenUtils";

const useTokenRefresh = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const refreshToken = async () => {
      try {
        const validToken = await checkAndRefreshToken(navigate); // navigate를 인자로 전달
        // validToken을 사용하여 추가적인 요청 처리
      } catch (error) {
        console.error("Token validation error:", error);
        // 로그인 페이지로 리다이렉트
        navigate("/login");
      }
    };

    refreshToken();
  }, [navigate]);
};

export default useTokenRefresh;
