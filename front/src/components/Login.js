
import React, { useState, useRef, useEffect, useContext } from 'react';
import AuthContext from "../context/AuthProvider";
import axios from '../api/axios';

const LOGIN_URL = '/signin';
const Login = () => {
  const { setAuth } = useContext(AuthContext);
  const userRef = useRef();
  const errRef = useRef();
  const [loginDetail, setLoginDetail] = useState({
    email: "",
    password: ""
  })
  const [errMsg, setErrMsg] = useState('');
  const [success, setSuccess] = useState(false);

  useEffect(() => {
    userRef.current.focus();
  }, [])
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
      const respone = await axios.post(LOGIN_URL,
        JSON.stringify({ loginDetail }),
        {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true
        }
      );
      console.log(JSON.stringify(respone?.data));
      const roles = respone?.data?.roles;
      setAuth({loginDetail, roles});
      setLoginDetail('');
      setSuccess(true);
    } catch (err) {
      if(!err?.response){
        setErrMsg('No Server Response');
      } else  if (err.response?.status === 400){
        setErrMsg('Missing Email or Password');
      } else if (err.response?.status === 401){
        setErrMsg('Unauthorized');
      } else {
        setErrMsg('Login Failed!');
      }
      errRef.current.focus();
    }

  }

  return (
    <>
      {success ? (
        <section>
          <h1>You are logged in!</h1>
          <br />
          <p> <a href='#'>Go to Home</a></p>
        </section>
      ) : (
        <div className="login-wrapper">

          <h1>Please Log In</h1>
          <form onSubmit={handleSubmit}>
            <label>
              <p>Username</p>
              <input type="text" name="email"
                ref={userRef} onChange={e => handleChange(e, "email")} value={loginDetail.email} />
            </label>
            <label>
              <p>Password</p>
              <input type="password" name="password"
                ref={userRef} onChange={e => handleChange(e, "password")} value={loginDetail.password} />
            </label>
            <div>
              <button type="submit">Submit</button>
            </div>
          </form>


          <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"}>{errMsg}</p>

        </div>
      )}
    </>
  )
}

export default Login;