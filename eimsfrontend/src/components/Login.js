
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
      //Set user's phone number in session
      sessionStorage.setItem("curUserId", response.data);
      sessionStorage.setItem("curPhone", loginDetail.phone);
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
      <div className="Auth-form-container">

        <form onSubmit={handleSubmit} className="Auth-form">
          <div className="Auth-form-content">
            <h2 className="Auth-form-title">Đăng nhập</h2>
            <div className="form-group mt-3">

              <input type="text" name="phone" className="form-control mt-1" placeholder="Nhập số điện thoại"
                required ref={userRef} onChange={e => handleChange(e, "phone")} value={loginDetail.phone} />
            </div>
            <div className="form-group mt-3 login-wrapper">

              <input type={passwordShown ? "text" : "password"} name="password" className="form-control mt-1" placeholder="Nhập mật khẩu"
                required ref={userRef} onChange={e => handleChange(e, "password")}
                value={loginDetail.password}
              /> 
            <i onClick={togglePasswordVisiblity}>{eye}</i>
            </div>
            <div className="d-grid gap-2 mt-3">
              <button className="btn btn-info" style={{ backgroundColor: "#1877F2", color: "white" }} type="submit">Đăng nhập</button>
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
            
            <p>
              <label>Bạn chưa có tài khoản?</label> <br />
              <span >
                <button className="btn btn-success" style={{ backgroundColor: "#42B72A" }}><Link style={{ color: "white" }} to="/register" >Tạo tài khoản</Link></button>
              </span>

            </p>
            <p className='link'> <label><Link to="/forgotpassword">Quên mật khẩu?</Link></label>

            </p>
          </div>

        </form>




      </div>

    </>
  )
}

export default Login;