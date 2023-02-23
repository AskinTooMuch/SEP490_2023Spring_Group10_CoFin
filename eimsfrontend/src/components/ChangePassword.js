
import React, { useState, useRef, useEffect } from 'react';
import axios from '../api/axios';
import { useNavigate} from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
const FORGOT_PASS_URL = '/api/auth/forgotPassword';
const ChangePassword = () => {

  const navigate = useNavigate();
  const userRef = useRef();
  const errRef = useRef();
  const [account, setAccount] = useState({
    password: "",
    newPassword:""
  })



  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(VERIFY_OTP_URL,
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
      errRef.current.focus();
    }

  }

  return (
    <>
      <div className="Auth-form-container">

        <form onSubmit={handleSubmit} className="Auth-form">
          <div className="Auth-form-content">
            <h2 className="Auth-form-title">Đổi mật khẩu</h2>
            <div className="form-group mt-3">

              <input type="text" name="account" className="form-control mt-1" placeholder="Mật khẩu mới"
                required ref={userRef} onChange={(e) => setAccount(e.target.value)}value={account} /> <button onClick={handleSend}></button>
            </div>
            <div className="form-group mt-3">

              <input type="text" name="OTP" className="form-control mt-1" placeholder="Xác nhận mật khẩu"
                required ref={userRef} onChange={(e) => setOTP(e.target.value)}
                value={otp}
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

export default ChangePassword;