
import React, { useState, useRef, useEffect } from 'react';
import axios from '../api/axios';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye } from "@fortawesome/free-solid-svg-icons";
const FORGOT_PASS_URL = '/api/auth/forgotPassword/changePassword';
const eye = <FontAwesomeIcon icon={faEye} />;
const ChangePassword = () => {
  //show-hide password 
  const [passwordShown, setPasswordShown] = useState(false);
  const togglePasswordVisiblity = () => {
    setPasswordShown(passwordShown ? false : true);
  };
  const navigate = useNavigate();
  const userRef = useRef();
  const errRef = useRef();
  const [account, setAccount] = useState({
    password: "",
    rePassword: ""
  })

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(FORGOT_PASS_URL,
        account,
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: false
        }
      );
      console.log(JSON.stringify(response?.data));
      setAccount('');
      //Set user's phone number in session
      toast.success("Đổi mật khẩu thành công")
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else if (err.response?.status === 400) {
        toast.error('Chưa nhập mật khẩu');
      } else if (err.response?.status === 401) {
        toast.error('Không có quyền truy cập');
      } else {
        toast.error('Sai mật khẩu');
      }
      errRef.current.focus();
    }

  }

  return (
    <>
      <section className="u-clearfix u-white u-section-1" id="sec-3c81" data-animation-name="" data-animation-duration="0" data-animation-delay="0" data-animation-direction="">
        <div className="u-custom-color-2 u-expanded-width u-shape u-shape-rectangle u-shape-1"></div>
        <div style={{ minHeight: "0px" }} className="u-align-center u-border-20 u-border-custom-color-1 u-border-no-left u-border-no-right u-border-no-top u-container-style u-custom-border u-group u-radius-46 u-shape-round u-white u-group-1">
          <div className="u-container-layout u-valign-middle-xs u-container-layout-1">
            <h2 className="u-text u-text-custom-color-1 u-text-default u-text-1">Quên mật khẩu</h2>
            <div className="u-form u-login-control u-form-1">
              <form onSubmit={handleSubmit} className="u-clearfix u-form-custom-backend u-form-spacing-10 u-form-vertical u-inner-form" source="custom" name="form" style={{ padding: "0px" }}>
                <div className="u-form-group u-form-password login-wrapper">
                  <label for="password-a30d" className="u-label u-text-grey-25 u-label-2">Mật khẩu mới </label>
                  <input type={passwordShown ? "text" : "password"} name="password" placeholder="Nhập mật khẩu"
                    ref={userRef} onChange={(e) => setAccount(e.target.value)}
                    className="u-border-2 u-border-grey-10 u-grey-10 u-input u-input-rectangle u-input-2" required />
                  <i onClick={togglePasswordVisiblity}>{eye}</i>
                </div>
                <div className="u-form-group u-form-password login-wrapper">
                  <label for="password-a30d" className="u-label u-text-grey-25 u-label-2">Xác nhận mật khẩu </label>
                  <input type={passwordShown ? "text" : "password"} name="repassword" placeholder="Nhập lại mật khẩu"
                    ref={userRef} onChange={(e) => setAccount(e.target.value)}
                    className="u-border-2 u-border-grey-10 u-grey-10 u-input u-input-rectangle u-input-2" required />
                  <i onClick={togglePasswordVisiblity}>{eye}</i>
                </div>
                <div className="u-align-left u-form-group u-form-submit">
                  <button type="submit" className="u-btn u-btn-submit u-button-style u-btn-1">Xác nhận</button>
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
    </>
  )
}

export default ChangePassword;