import React, { useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useTranslation } from "react-i18next";

const Login: React.FC = () => {
  const auth = useContext(AuthContext);
  const { t, i18n } = useTranslation();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emailError, setEmailError] = useState("");

  const [modalMessage, setModalMessage] = useState("");
  const [modalType, setModalType] = useState<"success" | "error">("success");
  const [showModal, setShowModal] = useState(false);

  if (!auth) return null;

  const validateEmail = (value: string) => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!value) {
      return t("emailRequired");
    } else if (!regex.test(value)) {
      return t("emailInvalid");
    }
    return "";
  };

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setEmail(value);
    setEmailError(validateEmail(value));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const emailValidation = validateEmail(email);
    if (emailValidation) {
      setEmailError(emailValidation);
      return;
    }

    try {
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      const data = await res.json();

      if (res.ok) {
        // ✅ Thành công
        await auth.login(email, password);
        setModalMessage(data.message || t("loginSuccess"));
        setModalType("success");
      } else {
        // ❌ Lỗi backend trả về
        setModalMessage(data.error || t("loginFail"));
        setModalType("error");
      }
    } catch (error) {
      setModalMessage(t("loginFail"));
      setModalType("error");
    } finally {
      setShowModal(true);
    }
  };

  const handleGoogleLogin = () => {
    setModalMessage(t("loginGoogle"));
    setModalType("success");
    setShowModal(true);
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow p-4 rounded-4" style={{ width: "380px" }}>
        {/* Nút chuyển ngôn ngữ */}
        <div className="d-flex justify-content-end mb-2">
          <button
            className="btn btn-sm btn-outline-secondary me-2"
            onClick={() => i18n.changeLanguage("en")}
          >
            EN
          </button>
          <button
            className="btn btn-sm btn-outline-secondary"
            onClick={() => i18n.changeLanguage("vi")}
          >
            VI
          </button>
        </div>

        <h2 className="text-center mb-4">{t("login")}</h2>
        <form onSubmit={handleSubmit}>
          {/* Email */}
          <div className="mb-3">
            <label className="form-label">{t("email")}</label>
            <input
              type="email"
              className={`form-control ${emailError ? "is-invalid" : ""}`}
              placeholder={t("enterEmail")}
              value={email}
              onChange={handleEmailChange}
            />
            {emailError && <div className="invalid-feedback">{emailError}</div>}
          </div>

          {/* Password */}
          <div className="mb-2">
            <label className="form-label">{t("password")}</label>
            <input
              type="password"
              className="form-control"
              placeholder={t("enterPassword")}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          {/* Quên mật khẩu */}
          <div className="text-end mb-3">
            <a href="/forgot-password" className="text-decoration-none small">
              {t("forgotPassword")}
            </a>
          </div>

          {/* Submit */}
          <button
            type="submit"
            className="btn btn-primary w-100 rounded-3 mb-3"
          >
            {t("loginBtn")}
          </button>

          {/* Login with Google */}
          <div className="d-flex justify-content-center my-3">
            <button
              type="button"
              onClick={handleGoogleLogin}
              className="btn btn-outline-danger rounded-circle d-flex align-items-center justify-content-center"
              style={{ width: "50px", height: "50px" }}
            >
              <img
                src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/google/google-original.svg"
                alt="Google"
                width="24"
                height="24"
              />
            </button>
          </div>
        </form>

        {/* Đăng ký */}
        <div className="text-center mt-4">
          <span className="small">{t("noAccount")} </span>
          <a href="/register" className="text-decoration-none fw-semibold">
            {t("register")}
          </a>
        </div>
      </div>

      {/* ✅ Modal Thông báo */}
      {showModal && (
        <div
          className="modal fade show"
          style={{ display: "block", backgroundColor: "rgba(0,0,0,0.5)" }}
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content rounded-4 shadow">
              <div
                className={`modal-header ${
                  modalType === "success" ? "bg-success" : "bg-danger"
                } text-white`}
              >
                <h5 className="modal-title">
                  {modalType === "success" ? t("success") : t("error")}
                </h5>
                <button
                  type="button"
                  className="btn-close btn-close-white"
                  onClick={() => setShowModal(false)}
                ></button>
              </div>
              <div className="modal-body text-center">{modalMessage}</div>
              <div className="modal-footer">
                <button
                  className="btn btn-secondary"
                  onClick={() => setShowModal(false)}
                >
                  OK
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Login;
