import { useRef, useState, useEffect } from "react";
import { faCheck, faTimes, faInfoCircle, faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import "../css/register.css"
//import  '../api/provinces.js';
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
    }, [email, password, matchPwd])

    const handleSubmit = async (e) => {
        e.preventDefault();
        const v2 = PWD_REGEX.test(password);
        const v3 = EMAIL_REGEX.test(email);
        if (!v2 || !v3) {
            toast.error("Sai định dạng yêu cầu");
            return;
        }
        try {
            const response = await axios.post(REGISTER_URL,
                ({ password, email }),
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
                toast.error('Server không phản hồi');
            } else if (err.response?.status === 409) {
                toast.error('Tài khoản trùng');
            } else {
                toast.error('Đăng ký thất bại')
            }
            errRef.current.focus();
        }
    }

    return (
        <>
            {success ? (
                toast.success("Đăng ký thành công")
            ) : (
                <div className="">

                    <form onSubmit={handleSubmit} className="">
                        <section className="h-100 h-custom gradient-custom-2">
                            <div className="container py-5 h-100">
                                <div className="row d-flex justify-content-center align-items-center h-100">
                                    <div className="col-12">
                                        <div className="card card-registration card-registration-2" style={{ borderRadius: "15px" }}>
                                            <div className="card-body p-0">
                                                <div className="row g-0">
                                                    <div className="col-lg-6">
                                                        <div className="p-5">
                                                            <h3 className="" style={{ color: " #4835d4" }}>Thông tin chung</h3>

                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                <label  htmlFor="email">Email <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="email"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setEmail(e.target.value)}
                                                                        value={email}
                                                                        required
                                                                        aria-invalid={validEmail ? "false" : "true"}
                                                                        aria-describedby="emailnote"
                                                                        onFocus={() => setEmailFocus(true)}
                                                                        onBlur={() => setEmailFocus(false)} className="form-control " />
                                                                   
                                                                    <p id="emailnote" className={emailFocus && email && !validEmail ? "instructions" : "offscreen"}>
                                                                        <FontAwesomeIcon icon={faInfoCircle} />
                                                                        Bắt đầu bằng 1 chữ cái + @example.com<br />
                                                                        Số và các kí tự đặc biệt được sử dụng.
                                                                    </p>

                                                                </div>
                                                            </div>


                                                            <div className="mb-4">

                                                                <div className="form-outline">
                                                                <label  htmlFor="password">Mật khẩu <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="password" id="password"
                                                                        onChange={(e) => setPwd(e.target.value)}
                                                                        value={password}
                                                                        required
                                                                        aria-invalid={validPwd ? "false" : "true"}
                                                                        aria-describedby="pwdnote"
                                                                        onFocus={() => setPwdFocus(true)}
                                                                        onBlur={() => setPwdFocus(false)}
                                                                        className="form-control " />
                                                                    
                                                                    <p id="pwdnote" className={pwdFocus && !validPwd ? "instructions" : "offscreen"}>
                                                                        <FontAwesomeIcon icon={faInfoCircle} />
                                                                        8 - 24 kí tự.<br />
                                                                        Bao gồm 1 chữ cái viết hoa, 1 số và 1 kí tự đặc biệt.<br />
                                                                        Các kí tự đặc biệt cho phép: <span aria-label="exclamation mark">!</span> <span aria-label="at symbol">@</span> <span aria-label="hashtag">#</span> <span aria-label="dollar sign">$</span> <span aria-label="percent">%</span>
                                                                    </p>
                                                                </div>

                                                            </div>
                                                            <div className="mb-4 ">

                                                                <div className="form-outline">
                                                                <label  htmlFor="confirm_pwd">Xác nhận lại mật khẩu <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="password" id="confirm_pwd"
                                                                        onChange={(e) => setMatchPwd(e.target.value)}
                                                                        value={matchPwd}
                                                                        required
                                                                        aria-invalid={validMatch ? "false" : "true"}
                                                                        aria-describedby="confirmnote"
                                                                        onFocus={() => setMatchFocus(true)}
                                                                        onBlur={() => setMatchFocus(false)} className="form-control " />
                                                                    
                                                                    <p id="confirmnote" className={matchFocus && !validMatch ? "instructions" : "offscreen"}>
                                                                        <FontAwesomeIcon icon={faInfoCircle} />
                                                                        Mật khẩu phải trùng với mẩt khẩu đã nhập ở trên
                                                                    </p>

                                                                </div>

                                                            </div>



                                                        </div>
                                                    </div>
                                                    <div className="col-lg-6 bg-indigo text-white">
                                                        <div className="p-5">
                                                            <h3 className="fw-normal ">Địa chỉ cơ sở </h3>

                                                            <div className="mb-4 ">
                                                                <div className="form-outline form-white">
                                                                <label  htmlFor="sonha" className="text-white">Số nhà <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="sonha"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setHomeNum(e.target.value)}
                                                                        value={homenum}
                                                                        required
                                                                        aria-describedby="naddress"
                                                                        onFocus={() => setHomeNumFocus(true)}
                                                                        onBlur={() => setHomeNumFocus(false)} className="form-control " />
                                                                    
                                                                    <p id="naddress" className={homeNumFocus && homenum ? "instructions" : "offscreen"}>
                                                                        <FontAwesomeIcon className="star" icon={faInfoCircle} />
                                                                        Số nhà của bạn<br />

                                                                    </p>

                                                                </div>
                                                            </div>

                                                            <div className="mb-4 pb-2">
                                                                <div className="form-outline form-white">
                                                                <label  htmlFor="duong" className="text-white">Đường <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="duong" ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setStreet(e.target.value)}
                                                                        value={street}
                                                                        required
                                                                        onFocus={() => setStreetFocus(true)}
                                                                        onBlur={() => setStreetFocus(false)} className="form-control " />
                                                                    
                                                                </div>
                                                            </div>


                                                            <div className="mb-4 pb-2">

                                                                <div className="form-outline form-white">
                                                                    <select className="form-control mt-1" id="quan"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setDistrict(e.target.value)}
                                                                        value={district}
                                                                        required
                                                                        onFocus={() => setDistrictFocus(true)}
                                                                        onBlur={() => setDistrictFocus(false)}>
                                                                        <option value="">Chọn Quận/Huyện của bạn</option>
                                                                        <option value="Đống Đa">Đống Đa</option>
                                                                        <option value="3">Three</option>
                                                                        <option value="4">Four</option>
                                                                    </select>
                                                                </div>

                                                            </div>

                                                            <div className="mb-4 ">

                                                                <div className="form-outline form-white">
                                                                    <select className="form-control mt-1" id="province" 
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setProvince(e.target.value)}
                                                                        value={province}
                                                                        required
                                                                        onFocus={() => setProvinceFocus(true)}
                                                                        onBlur={() => setProvinceFocus(false)}>
                                                                        <option value="">Chọn Tỉnh/Thành phố của bạn</option>
                                                                        <option value="Hà Nội">Hà Nội</option>
                                                                        <option value="3">Three</option>
                                                                        <option value="4">Four</option>
                                                                    </select>
                                                                </div>

                                                            </div>


                                                            <div className="mb-4 ">
                                                                <div className="form-outline form-white">
                                                                <label  htmlFor="date" className="text-white">Ngày thành lập <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="date" i id="date"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setDate(e.target.value)}
                                                                        value={date}
                                                                        required
                                                                        onFocus={() => setDateFocus(true)}
                                                                        onBlur={() => setDateFocus(false)} className="form-control " />
                                                                   
                                                                </div>
                                                            </div>


                                                            <div className="mb-4 ">

                                                                <div className="form-outline form-white">
                                                                <label  htmlFor="brn" className="text-white">Mã số đăng ký kinh doanh <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="brn"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setBusinessNumber(e.target.value)}
                                                                        value={brn}
                                                                        required
                                                                        onFocus={() => setBNFocus(true)}
                                                                        onBlur={() => setBNFocus(false)} className="form-control " />
                                                                    
                                                                </div>

                                                            </div>
                                                            <div className="mb-4 ">

                                                                <div className="form-outline form-white">
                                                                <label  htmlFor="hotline" className="text-white">Hotline <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="hotline" ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setHotline(e.target.value)}
                                                                        value={hotline}
                                                                        required
                                                                        onFocus={() => setHotlineFocus(true)}
                                                                        onBlur={() => setHotlineFocus(false)}
                                                                        className="form-control " />
                                                                    
                                                                </div>

                                                            </div>



                                                            <div className="form-check d-flex justify-content-start mb-4 ">
                                                                <input className="form-check-input me-3" type="checkbox" value="" id="form2Example3c" />
                                                                <label className="form-check-label text-white" for="form2Example3">
                                                                    I do accept the <a href="#!" className="text-white"><u>Terms and Conditions</u></a> of your
                                                                    site.
                                                                </label>
                                                            </div>

                                                            <button type="button" className="btn " data-mdb-ripple-color="dark"
                                                                >Đăng ký</button>
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
                                                            <p className="regislink">
                                                                <label> Bạn đã có tài khoản?</label><a style={{ fontSize: "small", fontWeight: "initial", textDecoration: "underline" }} href="login">Đăng nhập</a><br />

                                                            </p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>

                    </form>

                </div>
            )}
        </>

    )
}

export default Register