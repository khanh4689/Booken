// src/i18n.ts
import i18n from "i18next";
import { initReactI18next } from "react-i18next";

const resources = {
  en: {
    translation: {
      // Login
      login: "Login",
      email: "Email",
      password: "Password",
      enterEmail: "Enter email",
      enterPassword: "Enter password",
      forgotPassword: "Forgot password?",
      loginBtn: "Login",
      loginGoogle: "Login with Google",
      noAccount: "Don't have an account?",
      register: "Register",
      emailRequired: "Email is required",
      emailInvalid: "Invalid email format",
      loginSuccess: "Login successful!",
      loginFail: "Login failed!",

      // Register
      createAccount: "Create Account",
      name: "Name",
      enterName: "Enter name",
      phone: "Phone",
      enterPhone: "Enter phone number",
      confirmPassword: "Confirm Password",
      alreadyAccount: "Already have an account?",
      nameRequired: "Name is required",
      nameMin: "Name must be at least 3 characters",
      phoneRequired: "Phone number is required",
      phoneInvalid: "Phone must be 9–11 digits",
      passwordRequired: "Password is required",
      passwordMin: "Password must be at least 6 characters",
      passwordNotMatch: "Passwords do not match",
      registering: "Registering...",
      regSuccessTitle: "🎉 Registration Successful",
      regSuccessMsg: "Your account has been created successfully. Please check your email {{email}} to verify your account.",
      regErrorTitle: "⚠️ Registration Error",
      regErrorMsg: "Register failed! Please try again.",
      ok: "OK",
      close: "Close",

      //Reset password
     title: "Reset Password",
     newPassword: "New Password",
     enterNewPassword: "Enter new password",
     enterConfirmPassword: "Confirm password",
     resetBtn: "Reset Password",
     passwordMismatch: "❌ Passwords do not match!",
     successTitle: "Password Reset Successful!",
     goLogin: "Go to Login",
     error: "⚠️ Something went wrong. Try again!",
        // Forgot password
     titleForgot: "Forgot Password",
     emailLabel: "Email Address",
     sendBtn: "Send Reset Link",
     errorForgot: "⚠️ Something went wrong. Try again!",
    },
  },
  vi: {
    translation: {
        //Login
      login: "Đăng nhập",
      email: "Email",
      password: "Mật khẩu",
      enterEmail: "Nhập email",
      enterPassword: "Nhập mật khẩu",
      forgotPassword: "Quên mật khẩu?",
      loginBtn: "Đăng nhập",
      loginGoogle: "Đăng nhập với Google",
      noAccount: "Bạn chưa có tài khoản?",
      register: "Đăng ký",
      emailRequired: "Email là bắt buộc",
      emailInvalid: "Định dạng email không hợp lệ",
      loginSuccess: "Đăng nhập thành công!",
      loginFail: "Đăng nhập thất bại!",

        // Register
      createAccount: "Tạo tài khoản",
      name: "Họ tên",
      enterName: "Nhập họ tên",
      phone: "Số điện thoại",
      enterPhone: "Nhập số điện thoại",
      confirmPassword: "Xác nhận mật khẩu",
      alreadyAccount: "Bạn đã có tài khoản?",
      nameRequired: "Tên là bắt buộc",
      nameMin: "Tên phải ít nhất 3 ký tự",
      phoneRequired: "Số điện thoại là bắt buộc",
      phoneInvalid: "Số điện thoại phải từ 9–11 chữ số",
      passwordRequired: "Mật khẩu là bắt buộc",
      passwordMin: "Mật khẩu phải ít nhất 6 ký tự",
      passwordNotMatch: "Mật khẩu không khớp",
      registering: "Đang đăng ký...",
      regSuccessTitle: "🎉 Đăng ký thành công",
      regSuccessMsg: "Tài khoản của bạn đã được tạo thành công. Vui lòng kiểm tra email {{email}} để xác minh tài khoản.",
      regErrorTitle: "⚠️ Lỗi đăng ký",
      regErrorMsg: "Đăng ký thất bại! Vui lòng thử lại.",
      ok: "OK",
      close: "Đóng",
      //Reset password
     title: "Đặt lại mật khẩu",
     newPassword: "Mật khẩu mới",
     enterNewPassword: "Nhập mật khẩu mới",
     enterConfirmPassword: "Xác nhận mật khẩu",
     resetBtn: "Đặt lại mật khẩu",
     passwordMismatch: "❌ Mật khẩu không khớp!",
     successTitle: "Đặt lại mật khẩu thành công!",
     goLogin: "Đăng nhập",
     error: "⚠️ Có lỗi xảy ra. Vui lòng thử lại!",
        //Forgot password
     titleForgot: "Quên mật khẩu",
     emailLabel: "Địa chỉ email",
     sendBtn: "Gửi liên kết đặt lại",
     errorForgot: "⚠️ Có lỗi xảy ra. Vui lòng thử lại!",
    },
  },
};

i18n.use(initReactI18next).init({
  resources,
  lng: navigator.language.startsWith("vi") ? "vi" : "en", // ✅ auto detect Chrome language
  fallbackLng: "en",
  interpolation: { escapeValue: false },
});

export default i18n;
