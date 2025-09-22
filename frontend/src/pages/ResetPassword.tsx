import React, { useState } from "react";
import { useTranslation } from "react-i18next";

const ResetPassword: React.FC = () => {
  const { t, i18n } = useTranslation();

  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [message, setMessage] = useState("");
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (newPassword !== confirmPassword) {
      setMessage(t("passwordMismatch"));
      return;
    }

    try {
      const urlParams = new URLSearchParams(window.location.search);
      const token = urlParams.get("token");

      const res = await fetch("http://localhost:8080/api/auth/reset-password", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ token, newPassword }),
      });

      const text = await res.text();

      if (res.ok) {
        setSuccess(true);
        setMessage(text);
      } else {
        setMessage(text || t("error"));
      }
    } catch (error) {
      setMessage(t("error"));
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow-lg p-4" style={{ width: "400px" }}>
        {/* Switch Language */}
        <div className="text-end mb-2">
          <button
            className="btn btn-sm btn-outline-secondary"
            onClick={() => i18n.changeLanguage(i18n.language === "en" ? "vi" : "en")}
          >
            {i18n.language === "en" ? "VI" : "EN"}
          </button>
        </div>

        <h3 className="text-center mb-3">
          <i className="bi bi-shield-lock-fill text-primary me-2"></i>
          {t("title")}
        </h3>

        {!success ? (
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className="form-label fw-bold">
                <i className="bi bi-key-fill me-2"></i>{t("newPassword")}
              </label>
              <input
                type="password"
                className="form-control"
                placeholder={t("enterNewPassword")}
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                required
              />
            </div>
            <div className="mb-3">
              <label className="form-label fw-bold">
                <i className="bi bi-key me-2"></i>{t("confirmPassword")}
              </label>
              <input
                type="password"
                className="form-control"
                placeholder={t("enterConfirmPassword")}
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit" className="btn btn-success w-100">
              <i className="bi bi-arrow-repeat"></i> {t("resetBtn")}
            </button>

            {message && (
              <div
                className={`alert mt-3 text-center ${
                  success ? "alert-success" : "alert-danger"
                }`}
              >
                {message}
              </div>
            )}
          </form>
        ) : (
          <div className="text-center">
            <i className="bi bi-check-circle-fill text-success fs-1 mb-3"></i>
            <h5 className="mb-3">{t("successTitle")}</h5>
            <p className="text-muted">{message}</p>
            <a href="/login" className="btn btn-primary w-100">
              <i className="bi bi-box-arrow-in-right me-2"></i> {t("goLogin")}
            </a>
          </div>
        )}
      </div>
    </div>
  );
};

export default ResetPassword;
