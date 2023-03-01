import { useRef, useState, useEffect, Component } from "react";
import { faCheck, faTimes, faInfoCircle, faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import "../css/register.css";
//import  '../api/provinces.js';
const EMAIL_REGEX = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
const PHONE_REGEX = /(84|0[3|5|7|8|9])+([0-9]{8})\b/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const REGISTER_URL = '/api/auth/signup';

const Register = () => {

    const userRef = useRef();
    const [fullAddresses, setFullAddresses] = useState('');
    const options = [
        { value: 'chocolate', label: 'Chocolate' },
        { value: 'strawberry', label: 'Strawberry' },
        { value: 'vanilla', label: 'Vanilla' }
    ]
    const [city, setCity] = useState([
        { value: '', label: 'Chọn Tỉnh/Thành phố' }
    ]);
    //User Address data
    const [citySelected, setCitySelected] = useState(false);
    const [userDistrict, setUserDistrict] = useState('');
    const [userDistrictIndex, setUserDistrictIndex] = useState();
    const [districtSelected, setDistrictSelected] = useState(false);
    const [userWard, setUserWard] = useState('');

    //Personal
    //Name
    const [name, setName] = useState('');

    //Date of birth
    const [dob, setDob] = useState('');

    //Phone Number
    const [phone, setPhone] = useState('');
    const [phoneFocus, setPhoneFocus] = useState(false);
    const [validPhone, setValidPhone] = useState(false);

    //Email
    const [email, setEmail] = useState('');
    const [validEmail, setValidEmail] = useState(false);
    const [emailFocus, setEmailFocus] = useState(false);

    //User address
    const [homenum, setHomeNum] = useState(''); //số nhà
    const [ward, setWard] = useState(''); //phường xã
    const [district, setDistrict] = useState(''); //quận huyện
    const [province, setProvince] = useState(''); //tỉnh thành phố

    //Mật khẩu
    const [password, setPwd] = useState('');
    const [validPwd, setValidPwd] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);

    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);
    const [matchFocus, setMatchFocus] = useState(false);

    //Facility
    //Name
    const [faciName, setFaciName] = useState('');

    //Found Date
    const [date, setDate] = useState('');

    //Hotline
    const [hotline, setHotline] = useState('');

    //Address
    const [faciNum, setFaciNum] = useState(''); //số nhà
    const [faciWard, setFaciWard] = useState(''); //phường xã
    const [faciDistrict, setfaciDistrict] = useState(''); //quận huyện
    const [faciProvince, setFaciProvince] = useState(''); //tỉnh thành phố

    //Business License No.
    const [brn, setBusinessNumber] = useState('');

    const [success, setSuccess] = useState(false);

    // Set value for address fields
    //User
    useEffect(() => {
        console.log("Load address");
        loadAddress();
    }, []);

    const loadAddress = async () => {
        const result = await axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
            {});
        setFullAddresses(result.data);
        console.log("Full address");
        console.log(fullAddresses);
        // Set inf

        const cityList = fullAddresses.slice();
        for (let i in cityList) {
            cityList[i] = { value: cityList[i].Id, label: cityList[i].Name }
        }
        setCity(cityList);
        
    }

    function loadUserDistrict(index) {
        console.log(index);
        setCitySelected(true);
        setUserDistrictIndex(index);
        const districtOnIndex = fullAddresses[index].Districts;
        const districtList = districtOnIndex.slice();
        for (let i in districtList) {
            districtList[i] = { value: districtList[i].Id, label: districtList[i].Name }
        }
        setUserDistrict(districtList);
    }

    function loadUserWard(index) {
        console.log(index);
        setDistrictSelected(true);
        const wardOnIndex = fullAddresses[userDistrictIndex].Districts[index].Wards;
        const wardList = wardOnIndex.slice();
        for (let i in wardList) {
            wardList[i] = { value: wardList[i].Id, label: wardList[i].Name }
        }
        setUserWard(wardList);
        console.log(wardList);
    }

    useEffect(() => {
        userRef.current.focus();
    }, [])


    useEffect(() => {
        setValidEmail(EMAIL_REGEX.test(email));
    }, [email])

    useEffect(() => {
        setValidPhone(PHONE_REGEX.test(phone));
    }, [phone])

    useEffect(() => {
        setValidPwd(PWD_REGEX.test(password));
        setValidMatch(password === matchPwd);
    }, [password, matchPwd])


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
                ({ name, email, password, dob, phone }),
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
                                                    {/*Profile information*/}
                                                    <div className="col-lg-6">
                                                        <div className="p-5">
                                                            <h3 className="" style={{ color: " #4835d4" }}>Thông tin chung</h3>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="name">Tên <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="name"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setName(e.target.value)}
                                                                        value={name}
                                                                        required
                                                                        className="form-control " />
                                                                </div>
                                                            </div>
                                                            {/*Date of bird*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="dob">Ngày sinh <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="date" id="dob"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setDob(e.target.value)}
                                                                        value={dob}
                                                                        required
                                                                        className="form-control " />
                                                                </div>
                                                            </div>
                                                            {/*Phone*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="phone">Số điện thoại <FontAwesomeIcon className="star" icon={faStarOfLife} />
                                                                        <FontAwesomeIcon icon={faCheck} className={validPhone ? "valid" : "hide"} />
                                                                        <FontAwesomeIcon icon={faTimes} className={validPhone || !phone ? "hide" : "invalid"} />
                                                                    </label>
                                                                    <input type="text" id="phone"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setPhone(e.target.value)}
                                                                        value={phone}
                                                                        required
                                                                        aria-invalid={validPhone ? "false" : "true"}
                                                                        aria-describedby="phonenote"
                                                                        onFocus={() => setPhoneFocus(true)}
                                                                        onBlur={() => setPhoneFocus(false)}
                                                                        className="form-control " />
                                                                    <p id="phonenote" className={phoneFocus && phone && !validPhone ? "instructions" : "offscreen"}>
                                                                        <FontAwesomeIcon icon={faInfoCircle} />
                                                                        Hãy điền đúng số điện thoại<br />
                                                                    </p>
                                                                </div>
                                                            </div>
                                                            {/*Email*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="email">Email <FontAwesomeIcon className="star" icon={faStarOfLife} />
                                                                        <FontAwesomeIcon icon={faCheck} className={validEmail ? "valid" : "hide"} />
                                                                        <FontAwesomeIcon icon={faTimes} className={validEmail || !email ? "hide" : "invalid"} /></label>
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
                                                            {/*User city*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="uprovince" >Tỉnh/Thành Phố <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <select className="form-control mt-1" id="uprovince"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => loadUserDistrict(e.target.value)}
                                                                        required>
                                                                        <option value="">Chọn Tỉnh/Thành phố của bạn</option>
                                                                        {   city &&
                                                                            city.map((item, index) => (
                                                                                <option value={index}>{item.label}</option>
                                                                            ))
                                                                        }
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            {/*User district*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="udistrict" >Quận/Huyện <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <select className="form-control mt-1" id="udistrict"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => loadUserWard(e.target.value)}
                                                                        required>
                                                                        <option value="">Chọn Quận/Huyện của bạn</option>
                                                                        { userDistrict &&
                                                                            userDistrict.map((item, index) => (
                                                                                <option value={index}>{item.label}</option>
                                                                            ))
                                                                        }
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            {/*User ward*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="uward" >Phường/Xã <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <select className="form-control mt-1" id="uward"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setWard(e.target.value)}
                                                                        required>
                                                                        <option value="">Chọn Phường của bạn</option>
                                                                        { userWard &&
                                                                            userWard.map((item, index) => (
                                                                                <option value={index}>{item.label}</option>
                                                                            ))
                                                                        }
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            {/*User Street*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="uhomenum">Số nhà <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="uhomenum"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setHomeNum(e.target.value)}
                                                                        value={homenum}
                                                                        required
                                                                        className="form-control " />
                                                                </div>
                                                            </div>
                                                            <div className="mb-4">
                                                                <div className="form-outline">
                                                                    <label htmlFor="password">Mật khẩu <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
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
                                                                    <label htmlFor="confirm_pwd">Xác nhận lại mật khẩu <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
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
                                                    {/*Facility information*/}
                                                    <div className="col-lg-6 bg-indigo text-white">
                                                        <div className="p-5">
                                                            <h3 className="fw-normal ">Cơ sở </h3>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline form-white">
                                                                    <label htmlFor="faciname" className="text-white">Tên cơ sở <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="faciname"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setFaciName(e.target.value)}
                                                                        value={faciName}
                                                                        required
                                                                        className="form-control " />
                                                                </div>
                                                            </div>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline form-white">
                                                                    <label htmlFor="date" className="text-white">Ngày thành lập <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="date" id="date"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setDate(e.target.value)}
                                                                        value={date}
                                                                        required
                                                                        className="form-control " />

                                                                </div>
                                                            </div>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline form-white">
                                                                    <label htmlFor="hotline" className="text-white">Hotline <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="hotline" ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setHotline(e.target.value)}
                                                                        value={hotline}
                                                                        required
                                                                        className="form-control " />
                                                                </div>
                                                            </div>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="facinum" className="text-white">Số nhà <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="facinum"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setFaciNum(e.target.value)}
                                                                        value={faciNum}
                                                                        required
                                                                        className="form-control " />
                                                                </div>
                                                            </div>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="faciward" className="text-white" >Phường/Xã <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <select className="form-control mt-1" id="faciward"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setFaciWard(e.target.value)}
                                                                        value={faciWard}
                                                                        required>
                                                                        <option value="">Chọn Phường của bạn</option>
                                                                        <option value="Kim Liên">Kim Liên</option>
                                                                        <option value="3">Three</option>
                                                                        <option value="4">Four</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div className="mb-4 pb-2">
                                                                <div className="form-outline form-white">
                                                                    <label htmlFor="facidistrict" className="text-white">Quận/Huyện<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <select className="form-control mt-1" id="facidistrict"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setfaciDistrict(e.target.value)}
                                                                        value={faciDistrict}
                                                                        required>
                                                                        <option value="">Chọn Quận/Huyện của bạn</option>
                                                                        <option value="Đống Đa">Đống Đa</option>
                                                                        <option value="3">Three</option>
                                                                        <option value="4">Four</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline form-white">
                                                                    <label htmlFor="faciprovince" className="text-white" >Tỉnh/Thành phố<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <select className="form-control mt-1" id="faciprovince"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setFaciProvince(e.target.value)}
                                                                        value={faciProvince}
                                                                        required>
                                                                        <option value="">Chọn Tỉnh/Thành phố của bạn</option>
                                                                        <option value="Hà Nội">Hà Nội</option>
                                                                        <option value="3">Three</option>
                                                                        <option value="4">Four</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline form-white">
                                                                    <label htmlFor="brn" className="text-white">Mã số đăng ký kinh doanh <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="brn"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => setBusinessNumber(e.target.value)}
                                                                        value={brn}
                                                                        required
                                                                        className="form-control " />

                                                                </div>
                                                            </div>
                                                            <button type="submit" className="btn btn-success" data-mdb-ripple-color="dark"
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