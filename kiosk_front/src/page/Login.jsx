import React, { useState } from "react";
import {
  TextField,
  Button,
  Card,
  CardContent,
  Typography,
} from "@mui/material";
import { Box } from "@mui/system";
import axios from "axios";
import { useNavigate } from "react-router-dom"; // useNavigate 추가

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emailError, setEmailError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);

  const navigate = useNavigate(); // navigate 훅 사용

  const handleSubmit = async (e) => {
    e.preventDefault();

    // 유효성 검사
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const isEmailValid = emailPattern.test(email);
    const isPasswordEntered = password.trim() !== "";

    setEmailError(!isEmailValid);
    setPasswordError(!isPasswordEntered);

    if (isEmailValid && isPasswordEntered) {
      try {
        // 서버로 이메일과 비밀번호 전송
        const response = await axios.post(
          "http://localhost:8080/api/v1/token/make",
          {
            email: email,
            password: password,
          }
        );

        // 응답에서 토큰 추출 후 sessionStorage에 저장
        const { accessToken, refreshToken } = response.data;
        sessionStorage.setItem("accessToken", accessToken);
        sessionStorage.setItem("refreshToken", refreshToken);
        sessionStorage.setItem("email", email);

        console.log("Tokens stored in sessionStorage");

        // 대시보드로 이동
        navigate("/dashboard");
      } catch (error) {
        console.error("Error during login:", error);
        alert(error.response.data.error);
      }
    }
  };

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
      }}
    >
      <Card sx={{ width: 400, padding: 3 }}>
        <CardContent>
          <Typography variant="h5" align="center" gutterBottom>
            Login
          </Typography>
          <Box
            component="form"
            onSubmit={handleSubmit}
            sx={{
              display: "flex",
              flexDirection: "column",
              gap: 2,
            }}
          >
            <TextField
              label="Email"
              type="email"
              variant="outlined"
              fullWidth
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              error={emailError}
              helperText={
                emailError ? "Please enter a valid email address" : ""
              }
            />
            <TextField
              label="Password"
              type="password"
              variant="outlined"
              fullWidth
              required
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              error={passwordError}
              helperText={passwordError ? "Password is required" : ""}
            />
            <Button variant="contained" color="primary" type="submit" fullWidth>
              Login
            </Button>
          </Box>
        </CardContent>
      </Card>
    </Box>
  );
}
