
import React, { useState, useRef } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const RegisterOTP = () => {

  // URL
  const SEND_OTP_URL = '/api/auth//register/sendOTP';
  const VERIFY_OTP_URL = '/api/auth/register/verifyOTP';
  const RESEND_OTP_URL = '/api/auth/register/resendOTP';

  //check account success
  const [success, setSuccess] = useState(false);

  //navigate page
  const navigate = useNavigate();
  const userRef = useRef();
  // Data holding object
  const [verifyOtpDTO, setVerifyOtpDTO] = useState({
    phone: "",
    otp: ""
  })

  // Handle change input
  // Handle change phone, otp
  const handleChange = (event, field) => {
    let actualValue = event.target.value
    setVerifyOtpDTO({
      ...verifyOtpDTO,
      [field]: actualValue
    })
  }

  // Handle send OTP
  const handleSend = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.get(SEND_OTP_URL,
        { params: { phone: verifyOtpDTO.phone } },
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: false
        }
      );
      console.log(JSON.stringify(response?.data));
      sessionStorage.setItem("curPhone", verifyOtpDTO.phone);
      console.log("phone===="+sessionStorage.getItem("curPhone"));
      setSuccess(true);
      toast.success("OTP đã gửi")
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else {
        toast.error(err.response.data);
      }
    }
  }

  // Handle Resend OTP
  const handleResend = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.get(RESEND_OTP_URL,
        { params: { phone: verifyOtpDTO.phone } },
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: false
        }
      );
      console.log(JSON.stringify(response?.data));
      toast.success("Đã gửi lại OTP")
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else {
        toast.error(err.response.data);
      }
    }
  }

  // Handle submit to verify OTP
  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(VERIFY_OTP_URL,
        verifyOtpDTO,
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: false
        }
      );
      console.log(JSON.stringify(response?.data));
      toast.success("OTP chính xác")
      setVerifyOtpDTO({
        phone : "",
        otp : ""
      });
      navigate("/register")
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else {
        toast.error(err.response.data);
      }
    }
  }

  return (
    <>
      {success ? (
        <section className="u-clearfix u-white u-section-1" id="sec-3c81" data-animation-name="" data-animation-duration="0" data-animation-delay="0" data-animation-direction="">
          <div className="u-custom-color-2 u-expanded-width u-shape u-shape-rectangle u-shape-1"></div>
          <div style={{ minHeight: "0px" }} className="u-align-center u-border-20 u-border-custom-color-1 u-border-no-left u-border-no-right u-border-no-top u-container-style u-custom-border u-group u-radius-46 u-shape-round u-white u-group-1">
            <div className="u-container-layout u-valign-middle-xs u-container-layout-1">
              <h2 className="u-text u-text-custom-color-1 u-text-default u-text-1">Đăng ký</h2>
              <div className="u-form u-login-control u-form-1">
                <form onSubmit={handleSubmit} className="u-clearfix u-form-custom-backend u-form-spacing-10 u-form-vertical u-inner-form" source="custom" name="form" style={{ padding: "0px" }}>
                  <div className="u-form-group u-form-name">
                    <label htmlFor="username-a30d" className="u-label u-text-grey-25 u-label-1">Nhập mã xác thực </label>
                    <input type="text" name="otp" value={verifyOtpDTO.otp} placeholder="Nhập mã" required ref={userRef} onChange={(e) => handleChange(e, "otp")}
                      className="u-border-2 u-border-grey-10 u-grey-10 u-input u-input-rectangle u-input-1" />
                    <i><button style={{ border: "none", backgroundColor: "transparent" }} onClick={handleResend}>Gửi lại mã</button></i>
                  </div>
                  <div className="u-align-left u-form-group u-form-submit">
                    <button type="submit" className="u-btn u-btn-submit u-button-style u-btn-1">Tiếp tục</button>
                  </div>
                  <input type="hidden" value="" name="recaptchaResponse" />
                  <ToastContainer position="top-left"
                    autoClose={5000}
                    hideProgressBar={false}
                    newestOnTop={false}
                    closeOnClick
                    rtl={false}
                    pauseOnFocusLoss
                    draggable
                    pauseOnHover
                    theme="colored" />
                </form>
              </div>
            </div>
          </div>
        </section>
      ) : (
        <section className="u-clearfix u-white u-section-1" id="sec-3c81" data-animation-name="" data-animation-duration="0" data-animation-delay="0" data-animation-direction="">
          <div className="u-custom-color-2 u-expanded-width u-shape u-shape-rectangle u-shape-1"></div>
          <div style={{ minHeight: "0px" }} className="u-align-center u-border-20 u-border-custom-color-1 u-border-no-left u-border-no-right u-border-no-top u-container-style u-custom-border u-group u-radius-46 u-shape-round u-white u-group-1">
            <div className="u-container-layout u-valign-middle-xs u-container-layout-1">
              <h2 className="u-text u-text-custom-color-1 u-text-default u-text-1">Đăng ký</h2>
              <div className="u-form u-login-control u-form-1">
                <form onSubmit={handleSend} className="u-clearfix u-form-custom-backend u-form-spacing-10 u-form-vertical u-inner-form" source="custom" name="form" style={{ padding: "0px" }}>
                  <div className="u-form-group u-form-name">
                    <label htmlFor="username-a30d" className="u-label u-text-grey-25 u-label-1">Số điện thoại </label>
                    <input type="text" name="account" value={verifyOtpDTO.phone} placeholder="Nhập số điện thoại" required ref={userRef} onChange={(e) => handleChange(e, "phone")}
                      className="u-border-2 u-border-grey-10 u-grey-10 u-input u-input-rectangle u-input-1" />
                  </div>
                  <div className="u-align-left u-form-group u-form-submit">
                    <button type="submit" className="u-btn u-btn-submit u-button-style u-btn-1">Tiếp tục</button>
                  </div>
                  <input type="hidden" value="" name="recaptchaResponse" />
                  <ToastContainer position="top-left"
                    autoClose={5000}
                    hideProgressBar={false}
                    newestOnTop={false}
                    closeOnClick
                    rtl={false}
                    pauseOnFocusLoss
                    draggable
                    pauseOnHover
                    theme="colored" />
                </form>
              </div>
            </div>
          </div>
        </section>
      )}
    </>
  )
}

export default RegisterOTP;