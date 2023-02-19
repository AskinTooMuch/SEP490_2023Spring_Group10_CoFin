
import React, { useState, useRef, useEffect } from 'react';
import axios from '../api/axios';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { Icon } from '@mui/material';
import { VisibilityOffOutlined, VisibilityOutlined } from '@mui/icons-material';

const LOGIN_URL = '/api/auth/signin';
const Login = () => {

  const [type, setType] = useState('password');
  const [icon, setIcon] = useState(VisibilityOffOutlined);

  const handleToggle = () => {
    if (type === 'password') {
      setIcon(VisibilityOutlined);
      setType('text');
    }
    else {
      setIcon(VisibilityOffOutlined);
      setType('password');
    }
  }
  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/";

  const userRef = useRef();
  const errRef = useRef();
  const [loginDetail, setLoginDetail] = useState({
    phone: "",
    password: ""
  })
  const [errMsg, setErrMsg] = useState('');

  useEffect(() => {
    setErrMsg('');
  }, [loginDetail])



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
      navigate("/dashboard");
    } catch (err) {
      if (!err?.response) {
        setErrMsg('No Server Response');
      } else if (err.response?.status === 400) {
        setErrMsg('Missing Email or Password');
      } else if (err.response?.status === 401) {
        setErrMsg('Unauthorized');
      } else {
        setErrMsg('Sai tài khoản/ mật khẩu');
      }
      errRef.current.focus();
    }

  }

  return (
    <>
      <div className="Auth-form-container">

        <form onSubmit={handleSubmit} className="Auth-form">
          <div className="Auth-form-content">
            <h2 className="Auth-form-title">Đăng nhập</h2>
            <div className="form-group mt-3">
              <label>
                Tài khoản</label>
                <input type="text" name="phone" className="form-control mt-1" placeholder="Nhập số điện thoại"
                required ref={userRef} onChange={e => handleChange(e, "phone")} value={loginDetail.phone} />
            </div>
            <div className="form-group mt-3">
              <label>
                Mật khẩu</label>
              <input type={type} name="password" className="form-control mt-1" placeholder="Nhập mật khẩu"
                required ref={userRef} onChange={e => handleChange(e, "password")}
                value={loginDetail.password}
              />

            </div>
            <div className="d-grid gap-2 mt-3">
              <button className="btn btn-info" type="submit">Đăng nhập</button>
            </div>
            
            <hr />
            <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"}><span>{errMsg}</span></p>
            <p>
             <label>Bạn chưa có tài khoản?</label> <br />
              <span >
                <button className="btn btn-success"><Link to="/register">Tạo tài khoản</Link></button>
              </span>
             
            </p>
            <p className='link'> <label><a  href="#">Quên mật khẩu?</a></label>
            
            </p>
          </div>

        </form>




      </div>

    </>
  )
}

export default Login;