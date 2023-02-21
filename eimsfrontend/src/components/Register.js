import { useRef, useState, useEffect } from "react";
import { faCheck, faTimes, faInfoCircle, faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from '../api/axios';
import  '../api/provinces.js';
const EMAIL_REGEX = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;

const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const REGISTER_URL = '/api/auth/signup';

const Register = () => {
    
    const userRef = useRef();
    const errRef = useRef();
    const [date, setDate] = useState('');
    const [setDateFocus] = useState(false);

    const [brn, setBusinessNumber] = useState('');
    const [setBNFocus] = useState(false);

    const [hotline, setHotline] = useState('');
    const [setHotlineFocus] = useState(false);

    const [homenum, setHomeNum] = useState('');
    const [homeNumFocus, setHomeNumFocus] = useState(false);

    const [street, setStreet] = useState('');
    const [setStreetFocus] = useState(false);

    const [district, setDistrict] = useState('');
    const [setDistrictFocus] = useState(false);

    const [province, setProvince] = useState('');
    const [setProvinceFocus] = useState(false);

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
        setValidEmail(EMAIL_REGEX.test(email));
    }, [email])

    
    useEffect(() => {
        setValidPwd(PWD_REGEX.test(password));
        setValidMatch(password === matchPwd);
    }, [password, matchPwd])

    useEffect(() => {
        setErrMsg('');
    }, [ email, password, matchPwd])

    const handleSubmit = async (e) => {
        e.preventDefault();
        const v2 = PWD_REGEX.test(password);
        const v3 = EMAIL_REGEX.test(email);
        if ( !v2 || !v3 ) {
            setErrMsg("Invalid Entry");
            return;
        }
        try {
            const response = await axios.post(REGISTER_URL,
                ({password, email }),
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
                                
                            <br/>
                            <label htmlFor="sonha">
                                Địa chỉ cơ sở<FontAwesomeIcon className="star" icon={faStarOfLife} /> <br/>
                                Số nhà <FontAwesomeIcon className="star" icon={faStarOfLife} />
                            </label>
                            <input
                                className="form-control mt-1"
                                type="text"
                                id="sonha"
                                ref={userRef}
                                autoComplete="off"
                                onChange={(e) => setHomeNum(e.target.value)}
                                value={homenum}
                                required
                                aria-describedby="naddress"
                                onFocus={() => setHomeNumFocus(true)}
                                onBlur={() => setHomeNumFocus(false)}
                            />
                            <p id="naddress" className={homeNumFocus && homenum  ? "instructions" : "offscreen"}>
                                <FontAwesomeIcon className="star" icon={faInfoCircle} />
                               Số nhà của bạn<br />
                                
                            </p>

                            <label htmlFor="duong">
                                
                                Đường <FontAwesomeIcon className="star" icon={faStarOfLife} />
                            </label>
                            <input
                                className="form-control mt-1"
                                type="text"
                                id="duong"
                                ref={userRef}
                                autoComplete="off"
                                onChange={(e) => setStreet(e.target.value)}
                                value={street}
                                required
                                onFocus={() => setStreetFocus(true)}
                                onBlur={() => setStreetFocus(false)}
                            />
                            
                            <label htmlFor="quan">
                                
                                Quận/Huyện <FontAwesomeIcon className="star" icon={faStarOfLife} />
                            </label>
                            <select name="calc_shipping_district" 
                                className="form-control mt-1"
                                type="text"
                                id="district"
                                ref={userRef}
                                autoComplete="off"
                                onChange={(e) => setDistrict(e.target.value)}
                                value={district}
                                required
                                onFocus={() => setDistrictFocus(true)}
                                onBlur={() => setDistrictFocus(false)}
                            > <option value="">Chọn Quận/Huyện của bạn</option>
                            <option value="Đống Đa">Đống Đa</option>
                            </select>

                            <label htmlFor="province">
                                
                                Tỉnh/Thành phố <FontAwesomeIcon className="star" icon={faStarOfLife} />
                            </label>
                            <select name="calc_shipping_district" 
                                className="form-control mt-1"
                                type="text"
                                id="province"
                                ref={userRef}
                                autoComplete="off"
                                onChange={(e) => setProvince(e.target.value)}
                                value={province}
                                required
                                onFocus={() => setProvinceFocus(true)}
                                onBlur={() => setProvinceFocus(false)}
                            > <option value="">Chọn Tỉnh/Thành phố của bạn</option>
                            <option value="Hà Nội">Hà Nội</option>
                            </select>
                            <label htmlFor="date">
                                
                                Ngày thành lập <FontAwesomeIcon className="star" icon={faStarOfLife} />
                            </label>
                            <input
                                className="form-control mt-1"
                                type="date"
                                id="date"
                                ref={userRef}
                                autoComplete="off"
                                onChange={(e) => setDate(e.target.value)}
                                value={date}
                                required
                                onFocus={() => setDateFocus(true)}
                                onBlur={() => setDateFocus(false)}
                            />

                            <label htmlFor="brn">
                                
                                Mã số đăng ký kinh doanh <FontAwesomeIcon className="star" icon={faStarOfLife} />
                            </label>
                            <input
                                className="form-control mt-1"
                                type="text"
                                id="brn"
                                ref={userRef}
                                autoComplete="off"
                                onChange={(e) => setBusinessNumber(e.target.value)}
                                value={brn}
                                required
                                onFocus={() => setBNFocus(true)}
                                onBlur={() => setBNFocus(false)}
                            />

                            <label htmlFor="hotline">
                                
                                Hotline <FontAwesomeIcon className="star" icon={faStarOfLife} />
                            </label>
                            <input
                                className="form-control mt-1"
                                type="text"
                                id="hotline"
                                ref={userRef}
                                autoComplete="off"
                                onChange={(e) => setHotline(e.target.value)}
                                value={hotline}
                                required
                                onFocus={() => setHotlineFocus(true)}
                                onBlur={() => setHotlineFocus(false)}
                            />
                            </div>
                            
                            <div className="d-grid gap-2 mt-3">
                                <button className="btn btn-info" disabled={ !validPwd || !validEmail ||  !validMatch ? true : false}>
                                    Đăng ký</button>
                                <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
                                <p className="regislink">
                                    <label> Bạn đã có tài khoản?</label><a style={{ fontSize: "small", fontWeight: "initial", textDecoration: "underline" }} href="login">Đăng nhập</a><br />

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