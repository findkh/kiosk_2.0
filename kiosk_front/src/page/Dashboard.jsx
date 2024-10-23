import React from "react";
import useTokenRefresh from "../hooks/useTokenRefresh"; // 커스텀 훅 import

export default function Dashboard() {
  useTokenRefresh(); // 토큰 유효성 검사 및 리프레시

  return (
    <div>
      <h1>Dashboard</h1>
      {/* 대시보드 내용 */}
    </div>
  );
}
