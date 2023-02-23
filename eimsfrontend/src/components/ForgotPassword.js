
import React, { useState, useRef } from 'react';
import axios from '../api/axios';
import { useNavigate} from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const  SEND_OTP_URL= '/api/auth/forgotPassword/sendOTP';
const VERIFY_OTP_URL = '/api/auth/forgotPassword/verifyOTP';
const ForgotPassword = () => {

  const navigate = useNavigate();
  const userRef = useRef();
  const [account, setAccount] = useState({
    phone: ""
  })

  const [otp, setOTP] = useState({
    OTP: ""
  })

  const handleSend = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(SEND_OTP_URL,
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
      toast.success("OTP đã gửi")
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else if (err.response?.status === 400) {
        toast.error('Chưa nhập tài khoản');
      } else if (err.response?.status === 401) {
        toast.error('Không có quyền truy cập');
      } else {
        toast.error('Sai tài khoản');
      }
    }

  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(VERIFY_OTP_URL,
        otp,
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
      toast.success("OTP chính xác")
      navigate("/changepassword")
    } catch (err) {
      if (!err?.response) {
        toast.error('Server không phản hồi');
      } else if (err.response?.status === 400) {
        toast.error('Chưa nhập OTP');
      } else if (err.response?.status === 401) {
        toast.error('Không có quyền truy cập');
      } else {
        toast.error('Sai OTP');
      }
    }

  }

  return (
    <>
      <div className="Auth-form-container">

        <form onSubmit={handleSubmit} className="Auth-form">
          <div className="Auth-form-content">
            <h2 className="Auth-form-title">Quên mật khẩu</h2>
            <div className="form-group mt-3">

              <input type="text" name="account" className="form-control mt-1" placeholder="Nhập số điện thoại"
                required ref={userRef} onChange={(e) => setAccount(e.target.value)}  /> <button onClick={handleSend}>Gửi mã</button>
            </div>
            <div className="form-group mt-3">

              <input type="text" name="OTP" className="form-control mt-1" placeholder="Nhập OTP"
                required ref={userRef} onChange={(e) => setOTP(e.target.value)}
              />

            </div>
            <div className="d-grid gap-2 mt-3">
              <button className="btn btn-info" style={{ backgroundColor: "#1877F2", color: "white" }} type="submit">Tiếp tục</button>
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
            </div>

            <hr />
            
          </div>

        </form>




      </div>

    </>
  )
}

export default ForgotPassword;