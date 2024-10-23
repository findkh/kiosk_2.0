import axios from "axios";

export const checkAndRefreshToken = async (navigate) => {
  const accessToken = sessionStorage.getItem("accessToken");
  const refreshToken = sessionStorage.getItem("refreshToken");
  const email = sessionStorage.getItem("email");

  // 액세스 토큰이 없는 경우
  if (!accessToken) {
    navigate("/login");
    throw new Error("No access token found");
  }

  // 액세스 토큰이 유효하지 않은 경우 리프레시 토큰으로 새 액세스 토큰 요청
  if (refreshToken) {
    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/token/refresh",
        null, // 본문은 비워둡니다.
        {
          headers: {
            Authorization: `Bearer ${accessToken}`, // 액세스 토큰을 Authorization 헤더에 추가
          },
          params: {
            refreshToken: refreshToken, // 리프레시 토큰을 쿼리 파라미터로 추가
            email: email, // 이메일도 쿼리 파라미터로 추가
          },
        }
      );

      const { accessToken: newAccessToken } = response.data;

      // 새 액세스 토큰을 세션 스토리지에 저장
      sessionStorage.setItem("accessToken", newAccessToken);

      return newAccessToken; // 새 토큰 반환
    } catch (refreshError) {
      console.error("Error refreshing token:", refreshError);
      navigate("/login"); // 리프레시 실패 시 로그인 페이지로 리다이렉트
      throw new Error("Token refresh failed");
    }
  }

  // 여기에 도달하면 액세스 토큰과 리프레시 토큰 모두가 유효하지 않음을 의미함
  navigate("/login"); // 로그인 페이지로 리다이렉트
  throw new Error("No valid tokens found");
};
