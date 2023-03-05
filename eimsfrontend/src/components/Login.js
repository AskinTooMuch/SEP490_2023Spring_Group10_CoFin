
import React, { useState, useRef } from 'react';
import axios from '../api/axios';
import { Link, useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye } from "@fortawesome/free-solid-svg-icons";
import "../css/login.css"
const eye = <FontAwesomeIcon icon={faEye} />;
const LOGIN_URL = '/api/auth/signin';
const Login = () => {
  //show-hide password 
  const [passwordShown, setPasswordShown] = useState(false);
  const togglePasswordVisiblity = () => {
    setPasswordShown(passwordShown ? false : true);
  };
  //navigate
  const navigate = useNavigate();
  const userRef = useRef();
  //data json send
  const [loginDetail, setLoginDetail] = useState({
    phone: "",
    password: ""
  })

  const handleChange = (event, field) => {
    let actualValue = event.target.value
    setLoginDetail({
      ...loginDetail,
      [field]: actualValue
    })
  }

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post(LOGIN_URL,
        loginDetail,
        {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          },
          withCredentials: false
        }
      );
      console.log(JSON.stringify(response?.data));
      localStorage.setItem("user", loginDetail)
      setLoginDetail('');
      //Set user's phone number, userId, user's facility in session
      const responseJson = response.data;
      sessionStorage.setItem("curUserId", responseJson.userId);
      sessionStorage.setItem("curPhone", loginDetail.phone);
      if (responseJson.facilityId != null) { /* role = USER/EMPLOYEE */
        sessionStorage.setItem("facilityId", responseJson.facilityId);
      }
      toast.success("Đăng nhập thành công")
      navigate("/dashboard");

    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else if (err.response?.status === 400) {
        toast.error('Chưa nhập tài khoản/ mật khẩu');
      } else if (err.response?.status === 401) {
        toast.error('Không có quyền truy cập');
      } else {
        toast.error('Sai tài khoản/ mật khẩu');
      }
    }

  }

  return (
    <>
    
        <section class="u-clearfix u-white u-section-1" id="sec-3c81" data-animation-name="" data-animation-duration="0" data-animation-delay="0" data-animation-direction="">
          <div class="u-custom-color-2 u-expanded-width u-shape u-shape-rectangle u-shape-1"></div>
          <div class="u-align-center u-border-20 u-border-custom-color-1 u-border-no-left u-border-no-right u-border-no-top u-container-style u-custom-border u-group u-radius-46 u-shape-round u-white u-group-1">
            <div class="u-container-layout u-valign-middle-xs u-container-layout-1">
              <h2 class="u-text u-text-custom-color-1 u-text-default u-text-1">Đăng nhập</h2>
              <div class="u-form u-login-control u-form-1">
                <form onSubmit={handleSubmit} class="u-clearfix u-form-custom-backend u-form-spacing-10 u-form-vertical u-inner-form" source="custom" name="form" style={{ padding: "0px" }}>
                  <div class="u-form-group u-form-name">
                    <label for="username-a30d" class="u-label u-text-grey-25 u-label-1">Username </label>
                    <input type="text" name="phone" placeholder="Nhập số điện thoại"
                      ref={userRef} onChange={e => handleChange(e, "phone")} value={loginDetail.phone}
                      class="u-border-2 u-border-grey-10 u-grey-10 u-input u-input-rectangle u-input-1" required />
                  </div>
                  <div class="u-form-group u-form-password login-wrapper">
                    <label for="password-a30d" class="u-label u-text-grey-25 u-label-2">Password </label>
                    <input type={passwordShown ? "text" : "password"} name="password" placeholder="Nhập mật khẩu"
                      ref={userRef} onChange={e => handleChange(e, "password")} value={loginDetail.password}
                      class="u-border-2 u-border-grey-10 u-grey-10 u-input u-input-rectangle u-input-2" required />
                    <i onClick={togglePasswordVisiblity}>{eye}</i>
                  </div>
                  {/* <div class="u-form-checkbox u-form-group ">
                    <input type="checkbox" id="checkbox-a30d" name="remember" value="On" class="u-field-input" />
                    <label for="checkbox-a30d" class="u-field-label u-text-grey-25" style={{ textTransform: "uppercase", fontSize: "0.875rem", letterSpacing: "0px" }}>Remember me</label>
                  </div> */}
                  <div class="u-align-left u-form-group u-form-submit">
                    <button type="submit" class="u-btn u-btn-submit u-button-style u-btn-1">Đăng nhập</button>
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
              <Link to="/forgotpassword" class="u-active-none u-border-2 u-border-active-palette-2-dark-1 u-border-custom-color-1 u-border-hover-palette-1-base u-border-no-left u-border-no-right u-border-no-top u-btn u-button-style u-hover-none u-login-control u-login-forgot-password u-none u-text-custom-color-1 u-text-hover-palette-1-base u-btn-2">Quên mật khẩu</Link>
              <Link to="/register" class="u-active-none u-border-2 u-border-active-palette-2-dark-1 u-border-custom-color-1 u-border-hover-palette-1-base u-border-no-left u-border-no-right u-border-no-top u-btn u-button-style u-hover-none u-login-control u-login-create-account u-none u-text-custom-color-1 u-text-hover-palette-1-base u-btn-3">Bạn chưa có tài khoản?</Link>
            </div>
          </div>
        </section>
      
      

    </>
  )
}

export default Login;