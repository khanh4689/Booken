import React, { useState } from "react";

// ✅ Đa ngôn ngữ
const translations = {
  en: {
    title: "Forgot Password",
    emailLabel: "Email",
    enterEmail: "Enter your email",
    sendBtn: "Send Reset Link",
    error: "Something went wrong. Please try again later.",
    successTitle: "Success",
    closeBtn: "Close",
  },
  vi: {
    title: "Quên mật khẩu",
    emailLabel: "Email",
    enterEmail: "Nhập email của bạn",
    sendBtn: "Gửi liên kết đặt lại",
    error: "Đã xảy ra lỗi. Vui lòng thử lại sau.",
    successTitle: "Thành công",
    closeBtn: "Đóng",
  },
};

const ForgotPassword: React.FC = () => {
  // ✅ Tự động lấy ngôn ngữ của trình duyệt
  const browserLang = navigator.language.startsWith("vi") ? "vi" : "en";
  const [lang, setLang] = useState<"en" | "vi">(browserLang);
  const t = translations[lang];

  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");
    setIsError(false);

    try {
      const res = await fetch("http://localhost:8080/api/auth/forgot-password", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email }),
      });

      const text = await res.text();

      if (res.ok) {
        setMessage(text);
        setIsError(false);
        setShowModal(true); // ✅ Hiện modal khi thành công
      } else {
        setMessage(text || t.error);
        setIsError(true);
      }
    } catch (error) {
      setMessage(t.error);
      setIsError(true);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow-lg p-4 rounded-4" style={{ width: "400px" }}>
        {/* Switch Language */}
        <div className="text-end mb-2">
          <button
            className="btn btn-sm btn-outline-secondary"
            onClick={() => setLang(lang === "en" ? "vi" : "en")}
          >
            {lang === "en" ? "VI" : "EN"}
          </button>
        </div>

        <h3 className="text-center mb-3">
          <i className="bi bi-key-fill text-primary me-2"></i> {t.title}
        </h3>

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label fw-bold">
              <i className="bi bi-envelope-at me-2"></i> {t.emailLabel}
            </label>
            <input
              type="email"
              className="form-control"
              placeholder={t.enterEmail}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <button
            type="submit"
            className="btn btn-primary w-100 d-flex justify-content-center align-items-center"
            disabled={loading}
          >
            {loading && (
              <span
                className="spinner-border spinner-border-sm me-2"
                role="status"
                aria-hidden="true"
              ></span>
            )}
            <i className="bi bi-send me-2"></i> {t.sendBtn}
          </button>
        </form>

        {/* ✅ Chỉ hiện lỗi dưới form, thành công sẽ show modal */}
        {isError && message && (
          <div className="alert alert-danger text-center mt-3">{message}</div>
        )}
      </div>

      {/* ✅ Modal Bootstrap */}
      {showModal && (
        <div
          className="modal fade show"
          style={{ display: "block", backgroundColor: "rgba(0,0,0,0.5)" }}
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content rounded-4 shadow">
              <div className="modal-header">
                <h5 className="modal-title text-success">
                  <i className="bi bi-check-circle-fill me-2"></i>
                  {t.successTitle}
                </h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setShowModal(false)}
                ></button>
              </div>
              <div className="modal-body text-center">{message}</div>
              <div className="modal-footer">
                <button
                  className="btn btn-primary"
                  onClick={() => setShowModal(false)}
                >
                  {t.closeBtn}
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ForgotPassword;
