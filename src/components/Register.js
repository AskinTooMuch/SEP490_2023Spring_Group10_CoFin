import { useRef, useState, useEffect } from "react";
import { faCheck, faTimes, faInfoCircle, faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from '../api/axios';

const NAME_REGEX = /^[a-zA-Z]+$/;
const EMAIL_REGEX = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
const USER_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const REGISTER_URL = '/api/auth/signup';

const Register = () => {
    const userRef = useRef();
    const errRef = useRef();

    const [name, setName] = useState('');
    const [validName, setValidName] = useState(false);
    const [nameFocus, setNameFocus] = useState(false);

    const [username, setUser] = useState('');
    const [validUser, setValidUserName] = useState(false);
    const [userFocus, setUserFocus] = useState(false);

    const [email, setEmail] = useState('');
    const [validEmail, setValidEmail] = useState(false);
    const [emailFocus, setEmailFocus] = useState(false);


    const [password, setPwd] = useState('');
    const [validPwd, setValidPwd] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);

    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);
    const [matchFocus, setMatchFocus] = useState(false);

    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        setValidName(NAME_REGEX.test(name));
    }, [name])

    useEffect(() => {
        setValidEmail(EMAIL_REGEX.test(email));
    }, [email])

    useEffect(() => {
        setValidUserName(USER_REGEX.test(username));
    }, [username])

    useEffect(() => {
        setValidPwd(PWD_REGEX.test(password));
        setValidMatch(password === matchPwd);
    }, [password, matchPwd])

    useEffect(() => {
        setErrMsg('');
    }, [name, email, username, password, matchPwd])

    const handleSubmit = async (e) => {
        e.preventDefault();
        // if button enabled with JS hack
        const v1 = USER_REGEX.test(username);
        const v2 = PWD_REGEX.test(password);
        const v3 = EMAIL_REGEX.test(email);
        const v4 = NAME_REGEX.test(name);
        if (!v1 || !v2 || !v3 || !v4) {
            setErrMsg("Invalid Entry");
            return;
        }
        try {
            const response = await axios.post(REGISTER_URL,
                ({ username, password, email, name }),
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: false
                }
            );
            console.log(response?.data);
            console.log(JSON.stringify(response))
            setSuccess(true);
            //clear state and controlled inputs
            //need value attrib on inputs for this
            setUser('');
            setPwd('');
            setMatchPwd('');
        } catch (err) {
            if (!err?.response) {
                setErrMsg('No Server Response');
            } else if (err.response?.status === 409) {
                setErrMsg('Username Taken');
            } else {
                setErrMsg('Đăng ký thất bại')
            }
            errRef.current.focus();
        }
    }

    return (
        <>
            {success ? (
                <section>
                    <h1>Đăng ký thành công</h1>
                    <p>
                        <button><a href="login">Sign In</a></button>
                    </p>
                </section>
            ) : (
                <div className="Auth-form-container">

                    <form onSubmit={handleSubmit} className="Auth-form">
                        <div className="Auth-form-content">
                            <h3 className="Auth-form-title">Đăng ký</h3>
                            <div className="form-group mt-3">
                                <label htmlFor="name">
                                    Tên<FontAwesomeIcon className="star" icon={faStarOfLife} />
                                    <FontAwesomeIcon icon={faCheck} className={validName ? "valid" : "hide"} />
                                    <FontAwesomeIcon icon={faTimes} className={validName || !name ? "hide" : "invalid"} />
                                </label>
                                <input
                                    className="form-control mt-1"
                                    type="text"
                                    id="name"
                                    ref={userRef}
                                    autoComplete="off"
                                    onChange={(e) => setName(e.target.value)}
                                    value={name}
                                    required
                                    aria-invalid={validUser ? "false" : "true"}
                                    aria-describedby="namenote"
                                    onFocus={() => setNameFocus(true)}
                                    onBlur={() => setNameFocus(false)}
                                />
                                <p id="namenote" className={nameFocus && name && !validName ? "instructions" : "offscreen"}>
                                    <FontAwesomeIcon className="star" icon={faInfoCircle} />
                                    Tên thật của bạn<br />
                                    Viết dưới dạng chữ<br />
                                    Số và các kí tự đặc biệt không được sử dụng.
                                </p>
                            </div>
                            <label htmlFor="username">
                                Tên người dùng<FontAwesomeIcon className="star" icon={faStarOfLife} />
                                <FontAwesomeIcon icon={faCheck} className={validUser ? "valid" : "hide"} />
                                <FontAwesomeIcon icon={faTimes} className={validUser || !username ? "hide" : "invalid"} />
                            </label>
                            <input
                                className="form-control mt-1"
                                type="text"
                                id="username"
                                ref={userRef}
                                autoComplete="off"
                                onChange={(e) => setUser(e.target.value)}
                                value={username}
                                required
                                aria-invalid={validUser ? "false" : "true"}
                                aria-describedby="uidnote"
                                onFocus={() => setUserFocus(true)}
                                onBlur={() => setUserFocus(false)}
                            />
                            <p id="uidnote" className={userFocus && username && !validUser ? "instructions" : "offscreen"}>
                                <FontAwesomeIcon className="star" icon={faInfoCircle} />
                                4 - 24 kí tự.<br />
                                Bắt đầu bằng 1 chữ cái<br />
                                Các kí tự đặc biệt không được sử dụng
                            </p>

                            <label htmlFor="email">
                                Email<FontAwesomeIcon className="star" icon={faStarOfLife} />
                                <FontAwesomeIcon icon={faCheck} className={validEmail ? "valid" : "hide"} />
                                <FontAwesomeIcon icon={faTimes} className={validEmail || !email ? "hide" : "invalid"} />
                            </label>
                            <input
                                className="form-control mt-1"
                                type="text"
                                id="email"
                                ref={userRef}
                                autoComplete="off"
                                onChange={(e) => setEmail(e.target.value)}
                                value={email}
                                required
                                aria-invalid={validEmail ? "false" : "true"}
                                aria-describedby="emailnote"
                                onFocus={() => setEmailFocus(true)}
                                onBlur={() => setEmailFocus(false)}
                            />
                            <p id="emailnote" className={emailFocus && email && !validEmail ? "instructions" : "offscreen"}>
                                <FontAwesomeIcon icon={faInfoCircle} />
                                Bắt đầu bằng 1 chữ cái + @example.com<br />
                                Số và các kí tự đặc biệt được sử dụng.
                            </p>

                            <label htmlFor="password">
                                Mật khẩu<FontAwesomeIcon className="star" icon={faStarOfLife} />
                                <FontAwesomeIcon icon={faCheck} className={validPwd ? "valid" : "hide"} />
                                <FontAwesomeIcon icon={faTimes} className={validPwd || !password ? "hide" : "invalid"} />
                            </label>
                            <input
                                className="form-control mt-1"
                                type="password"
                                id="password"
                                onChange={(e) => setPwd(e.target.value)}
                                value={password}
                                required
                                aria-invalid={validPwd ? "false" : "true"}
                                aria-describedby="pwdnote"
                                onFocus={() => setPwdFocus(true)}
                                onBlur={() => setPwdFocus(false)}
                            />
                            <p id="pwdnote" className={pwdFocus && !validPwd ? "instructions" : "offscreen"}>
                                <FontAwesomeIcon icon={faInfoCircle} />
                                8 - 24 kí tự.<br />
                                Bao gồm 1 chữ cái viết hoa, 1 số và 1 kí tự đặc biệt.<br />
                                Các kí tự đặc biệt cho phép: <span aria-label="exclamation mark">!</span> <span aria-label="at symbol">@</span> <span aria-label="hashtag">#</span> <span aria-label="dollar sign">$</span> <span aria-label="percent">%</span>
                            </p>


                            <label htmlFor="confirm_pwd">
                                Xác nhận lại mật khẩu<FontAwesomeIcon className="star" icon={faStarOfLife} />
                                <FontAwesomeIcon icon={faCheck} className={validMatch && matchPwd ? "valid" : "hide"} />
                                <FontAwesomeIcon icon={faTimes} className={validMatch || !matchPwd ? "hide" : "invalid"} />
                            </label>
                            <input
                                className="form-control mt-1"
                                type="password"
                                id="confirm_pwd"
                                onChange={(e) => setMatchPwd(e.target.value)}
                                value={matchPwd}
                                required
                                aria-invalid={validMatch ? "false" : "true"}
                                aria-describedby="confirmnote"
                                onFocus={() => setMatchFocus(true)}
                                onBlur={() => setMatchFocus(false)}
                            />
                            <p id="confirmnote" className={matchFocus && !validMatch ? "instructions" : "offscreen"}>
                                <FontAwesomeIcon icon={faInfoCircle} />
                                Mật khẩu phải trùng với mẩt khẩu đã nhập ở trên
                            </p>
                            <div className="d-grid gap-2 mt-3">
                                <button className="btn btn-info" disabled={!validUser || !validPwd || !validEmail || !validName || !validMatch ? true : false}>
                                    Đăng ký</button>
                                <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
                                <p className="regislink">
                                   <label> Bạn đã có tài khoản?</label><a style={{fontSize:"small",fontWeight:"initial",textDecoration:"underline"}} href="login">Đăng nhập</a><br />
                                
                                </p>
                            </div>
                        </div>

                    </form>

                </div>
            )}
        </>
    )
}

export default Register