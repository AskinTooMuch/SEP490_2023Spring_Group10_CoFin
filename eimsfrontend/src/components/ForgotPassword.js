import React, { useState, useRef } from 'react';
import axios from '../api/axios';
import { firebase, auth } from '../firebase/firebasesdk'
import { RecaptchaVerifier, signInWithPhoneNumber } from "firebase/auth";
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Box } from '@mui/material';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faInfoCircle } from '@fortawesome/free-solid-svg-icons';

const ForgotPassword = () => {
  // API URL
  const CHECK_PHONE = "/api/auth/forgotPassword/check"
  //
  const [phoneNumber, setPhoneNumber] = useState("");
  const [otp, setOtp] = useState('');
  const [step, setStep] = useState('INPUT_PHONE_NUMBER');
  const [result, setResult] = useState('');
  const [buttonDisabled, setButtonDisabled] = useState(false);
  //navigate page
  const navigate = useNavigate();
  // Create RecaptchaVerifier instance
  function onCaptchVerify() {
    if (!window.recaptchaVerifier) {
      window.recaptchaVerifier = new RecaptchaVerifier(
        "recaptcha-container",
        {
          size: "invisible",
          "expired-callback": () => { },
        },
        auth
      );
    }
  }

  // Check phone number
  const checkPhone = async (e) => {
    e.preventDefault();
    let response;
    try {
      response = await axios.get(CHECK_PHONE,
        {
          params: { phone: phoneNumber },
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: true
        }
      );
      if (response.data === 1) {
        handleSend();
      }
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else {
        if ((err.response.data === null) || (err.response.data === '')) {
          toast.error('Có lỗi xảy ra, vui lòng thử lại');
        } else {
          toast.error(err.response.data);
        }
      }
    }
  }

  // Handle send OTP
  const handleSend = () => {
    onCaptchVerify();
    const appVerifier = window.recaptchaVerifier;
    const replaced = phoneNumber.replace(phoneNumber[0], "+84")
    console.log(phoneNumber)
    console.log(replaced)
    sessionStorage.setItem("curPhone", phoneNumber);
    sessionStorage.setItem("currPhone", replaced);
    auth.signInWithPhoneNumber(replaced, appVerifier).then((result) => {
      setResult(result);
      setStep('VERIFY_OTP');
      setButtonDisabled(true); //Disable button after sending OTP
      setTimeout(() => {
        setButtonDisabled(false); //Enable button after one minute
      }, 60000);
      toast.success("Mã OTP đã được gửi đến số điện thoại của bạn")
    })
      .catch((err) => {
        console.log(err);
        toast.error("OTP không gửi được");
      });
  }

  // Handle resend OTP
  const handleResend = (e) => {
    e.preventDefault();
    const element = document.getElementById('recaptcha-container');
    if (window.recaptchaVerifier) {
      window.recaptchaVerifier.clear();
      element.remove();
    }
    const myDiv = document.createElement('div');
    myDiv.id = 'recaptcha-container';
    document.body.appendChild(myDiv);
    // Create a new RecaptchaVerifier instance
    window.recaptchaVerifier = new firebase.auth.RecaptchaVerifier(
      "recaptcha-container",
      {
        size: "invisible"
      }
    );
    const phoneNumber = sessionStorage.getItem("currPhone")
    auth.signInWithPhoneNumber(phoneNumber, window.recaptchaVerifier)
      .then((result2) => {
        setResult(result2);
        setStep('VERIFY_OTP');
        setButtonDisabled(true); //Disable button after sending OTP
        setTimeout(() => {
          setButtonDisabled(false); //Enable button after one minute
        }, 60000);
        toast.success("Đã gửi lại mã OTP")
        // Clear the RecaptchaVerifier instance

        console.log('window.recaptchaVerifier:', window.recaptchaVerifier);
      })
      .catch((err) => {
        console.log(err);
        toast.error("OTP không gửi được");
      });
  }

  const ValidateOtp = () => {
    if (otp === null) return;

    result.confirm(otp).then((result) => {
      setTimeout(() => {
        navigate("/changepassword") //Enable button after one minute
      }, 1000);
      sessionStorage.setItem("phone", phoneNumber);
      toast.success("Mã OTP chính xác")
    })
      .catch((err) => {
        toast.error("Vui lòng nhập đúng mã OTP")
        console.clear();
      })
  }
  return (
    <>
      {step === 'VERIFY_OTP' &&
        <section className="u-clearfix u-white u-section-1" id="sec-3c81" data-animation-name="" data-animation-duration="0" data-animation-delay="0" data-animation-direction="">
          <div className="u-custom-color-2 u-expanded-width u-shape u-shape-rectangle u-shape-1"></div>
          <div style={{ minHeight: "0px" }} className="u-align-center u-border-20 u-border-custom-color-1 u-border-no-left u-border-no-right u-border-no-top u-container-style u-custom-border u-group u-radius-46 u-shape-round u-white u-group-1">
            <div className="u-container-layout u-valign-middle-xs u-container-layout-1">
              <h2 className="u-text u-text-custom-color-1 u-text-default u-text-1">Quên mật khẩu</h2>
              <div className="u-form u-login-control u-form-1">
                <div className="u-form-group u-form-name">
                  <label htmlFor="username-a30d" className="u-label u-text-grey-25 u-label-1">Nhập mã xác thực </label>
                  <input type="text" name="otp" placeholder="Nhập mã" onChange={(e) => setOtp(e.target.value)}
                    className="u-border-2 u-border-grey-10 u-grey-10 u-input u-input-rectangle u-input-1" />
                </div>
                <div className="u-align-left u-form-group u-form-submit">
                  <button onClick={ValidateOtp} className="u-btn u-btn-submit u-button-style u-btn-1">Tiếp tục</button>
                </div>
                <Box mt={3} >
                  <div id="recaptcha-container"></div>
                  <button className='btn btn-light' style={{ width: "35%" }} disabled={buttonDisabled} onClick={handleResend}>
                    {buttonDisabled ? 'Vui lòng đợi 1 phút để gửi lại mã' : 'Gửi lại mã'}</button>
                </Box>
              </div>
            </div>
          </div>
        </section>
      }

      {step === 'INPUT_PHONE_NUMBER' &&
        <section className="u-clearfix u-white u-section-1" id="sec-3c81" data-animation-name="" data-animation-duration="0" data-animation-delay="0" data-animation-direction="">
          <div className="u-custom-color-2 u-expanded-width u-shape u-shape-rectangle u-shape-1"></div>
          <div style={{ minHeight: "0px" }} className="u-align-center u-border-20 u-border-custom-color-1 u-border-no-left u-border-no-right u-border-no-top u-container-style u-custom-border u-group u-radius-46 u-shape-round u-white u-group-1">
            <div className="u-container-layout u-valign-middle-xs u-container-layout-1">
              <h2 className="u-text u-text-custom-color-1 u-text-default u-text-1">Quên mật khẩu</h2>
              <div className="u-form u-login-control u-form-1">
                <form onSubmit={checkPhone}>
                  <div className="u-form-group u-form-name">
                    <label htmlFor="username-a30d" className="u-label u-text-grey-25 u-label-1">Số điện thoại </label>
                    <span id="phonenote" data-text="Số điện thoại Việt Nam, bắt đầu bằng 03|5|7|8|9"
                      className="tip invalid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                    <input type="text" name="account" value={phoneNumber} placeholder="Nhập số điện thoại" onChange={(e) => setPhoneNumber(e.target.value)}
                      className="u-border-2 u-border-grey-10 u-grey-10 u-input u-input-rectangle u-input-1" />
                  </div>
                  <div className="u-align-left u-form-group u-form-submit">
                    <div id="recaptcha-container"></div>
                    <button className="u-btn u-btn-submit u-button-style u-btn-1">Tiếp tục</button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </section>
      }
    </>
  )
}

export default ForgotPassword;