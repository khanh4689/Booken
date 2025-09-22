import React, { useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useTranslation } from "react-i18next";

const Register: React.FC = () => {
  const auth = useContext(AuthContext);
  const { t, i18n } = useTranslation();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const [errors, setErrors] = useState<Record<string, string>>({});
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(false);
  const [errorModalMessage, setErrorModalMessage] = useState<string | null>(null);

  if (!auth) return null;

  const validateField = (field: string, value: string) => {
    let error = "";
    switch (field) {
      case "name":
        if (!value) error = t("nameRequired");
        else if (value.length < 3) error = t("nameMin");
        break;
      case "email":
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!value) error = t("emailRequired");
        else if (!emailRegex.test(value)) error = t("emailInvalid");
        break;
      case "phone":
        const phoneRegex = /^[0-9]{9,11}$/;
        if (!value) error = t("phoneRequired");
        else if (!phoneRegex.test(value)) error = t("phoneInvalid");
        break;
      case "password":
        if (!value) error = t("passwordRequired");
        else if (value.length < 6) error = t("passwordMin");
        break;
      case "confirmPassword":
        if (value !== password) error = t("passwordNotMatch");
        break;
    }
    setErrors((prev) => ({ ...prev, [field]: error }));
  };

  const handleChange =
    (field: string, setter: React.Dispatch<React.SetStateAction<string>>) =>
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const value = e.target.value;
      setter(value);
      validateField(field, value);
    };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    ["name", "email", "phone", "password", "confirmPassword"].forEach(
      (field) => {
        const value =
          field === "name"
            ? name
            : field === "email"
            ? email
            : field === "phone"
            ? phone
            : field === "password"
            ? password
            : confirmPassword;
        validateField(field, value);
      }
    );

    if (Object.values(errors).some((err) => err)) return;

    try {
      setLoading(true);
      await auth.register(name, email, phone, password);
      setShowModal(true);
    } catch (err: any) {
      if (err.response && err.response.data) {
        setErrorModalMessage(err.response.data);
      } else {
        setErrorModalMessage(t("regErrorMsg"));
      }
    } finally {
      setLoading(false);
    }
  };

  const handleGoogleRegister = () => {
    alert("Google Register clicked (OAuth2 Google redirect).");
  };

  return (
    <>
      <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
        <div className="card shadow p-4 rounded-4" style={{ width: "420px" }}>
          {/* Nút đổi ngôn ngữ */}
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

          <h2 className="text-center mb-4">{t("createAccount")}</h2>

          <form onSubmit={handleSubmit}>
            {/* Name */}
            <div className="mb-3">
              <label className="form-label">{t("name")}</label>
              <input
                type="text"
                className={`form-control ${errors.name ? "is-invalid" : ""}`}
                placeholder={t("enterName")}
                value={name}
                onChange={handleChange("name", setName)}
              />
              {errors.name && <div className="invalid-feedback">{errors.name}</div>}
            </div>

            {/* Email */}
            <div className="mb-3">
              <label className="form-label">{t("email")}</label>
              <input
                type="email"
                className={`form-control ${errors.email ? "is-invalid" : ""}`}
                placeholder={t("enterEmail")}
                value={email}
                onChange={handleChange("email", setEmail)}
              />
              {errors.email && <div className="invalid-feedback">{errors.email}</div>}
            </div>

            {/* Phone */}
            <div className="mb-3">
              <label className="form-label">{t("phone")}</label>
              <input
                type="tel"
                className={`form-control ${errors.phone ? "is-invalid" : ""}`}
                placeholder={t("enterPhone")}
                value={phone}
                onChange={handleChange("phone", setPhone)}
              />
              {errors.phone && <div className="invalid-feedback">{errors.phone}</div>}
            </div>

            {/* Password */}
            <div className="mb-3">
              <label className="form-label">{t("password")}</label>
              <input
                type="password"
                className={`form-control ${errors.password ? "is-invalid" : ""}`}
                placeholder={t("enterPassword")}
                value={password}
                onChange={handleChange("password", setPassword)}
              />
              {errors.password && <div className="invalid-feedback">{errors.password}</div>}
            </div>

            {/* Confirm Password */}
            <div className="mb-3">
              <label className="form-label">{t("confirmPassword")}</label>
              <input
                type="password"
                className={`form-control ${errors.confirmPassword ? "is-invalid" : ""}`}
                placeholder={t("confirmPassword")}
                value={confirmPassword}
                onChange={handleChange("confirmPassword", setConfirmPassword)}
              />
              {errors.confirmPassword && (
                <div className="invalid-feedback">{errors.confirmPassword}</div>
              )}
            </div>

            {/* Submit */}
            <button
              type="submit"
              className="btn btn-primary w-100 rounded-3 mb-3"
              disabled={loading}
            >
              {loading ? (
                <>
                  <span
                    className="spinner-border spinner-border-sm me-2"
                    role="status"
                    aria-hidden="true"
                  ></span>
                  {t("registering")}
                </>
              ) : (
                t("register")
              )}
            </button>
          </form>

          {/* Google logo button */}
          <div className="d-flex justify-content-center my-2">
            <button
              type="button"
              onClick={handleGoogleRegister}
              className="btn btn-outline-danger rounded-circle d-flex align-items-center justify-content-center"
              style={{ width: "48px", height: "48px" }}
            >
              <img
                src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/google/google-original.svg"
                alt="Google"
                width="24"
                height="24"
              />
            </button>
          </div>

          {/* Login link */}
          <div className="text-center mt-3">
            <span className="small">{t("alreadyAccount")} </span>
            <a href="/login" className="text-decoration-none fw-semibold">
              {t("login")}
            </a>
          </div>
        </div>
      </div>

      {/* Modal success */}
      {showModal && (
        <div
          className="modal fade show d-block"
          tabIndex={-1}
          style={{ background: "rgba(0,0,0,0.5)" }}
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content rounded-4 shadow">
              <div className="modal-header border-0">
                <h5 className="modal-title text-success">{t("regSuccessTitle")}</h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setShowModal(false)}
                ></button>
              </div>
              <div className="modal-body text-center">
                <p>{t("regSuccessMsg", { email })}</p>
              </div>
              <div className="modal-footer border-0 justify-content-center">
                <button
                  type="button"
                  className="btn btn-primary"
                  onClick={() => setShowModal(false)}
                >
                  {t("ok")}
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Modal error */}
      {errorModalMessage && (
        <div
          className="modal fade show d-block"
          tabIndex={-1}
          style={{ background: "rgba(0,0,0,0.5)" }}
        >
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content rounded-4 shadow">
              <div className="modal-header border-0">
                <h5 className="modal-title text-danger">{t("regErrorTitle")}</h5>
                <button
                  type="button"
                  className="btn-close"
                  onClick={() => setErrorModalMessage(null)}
                ></button>
              </div>
              <div className="modal-body text-center">
                <p>{errorModalMessage}</p>
              </div>
              <div className="modal-footer border-0 justify-content-center">
                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => setErrorModalMessage(null)}
                >
                  {t("close")}
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Register;
