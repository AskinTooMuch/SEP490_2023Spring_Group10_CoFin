import { useRef, useState, useEffect } from "react";
import { faCheck, faTimes, faInfoCircle, faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import "../css/register.css";
import { Link, useNavigate } from "react-router-dom";
import { faEye } from "@fortawesome/free-solid-svg-icons";
//import  '../api/provinces.js';
const EMAIL_REGEX = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
const PHONE_REGEX = /(0)(3|5|7|8|9)+([0-9]{8})\b/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,20}$/;
const REGISTER_URL = '/api/auth/signup';
const eye = <FontAwesomeIcon icon={faEye} />;
const Register = () => {
    //show-hide password 
    const [passwordShown, setPasswordShown] = useState(false);
    const [passwordShown2, setPasswordShown2] = useState(false);
    const togglePasswordVisiblity = () => {
        setPasswordShown(passwordShown ? false : true);
    };
    const togglePasswordVisiblity2 = () => {
        setPasswordShown2(passwordShown2 ? false : true);
    };
    const userRef = useRef();
    const [dataLoaded, setDataLoaded] = useState(false);
    //Full Json addresses
    const [fullAddresses, setFullAddresses] = useState('');
    const [city, setCity] = useState([
        { value: '', label: 'Chọn Tỉnh/Thành phố' }
    ]);
    //User Address data
    const [userDistrict, setUserDistrict] = useState(''); //For populate dropdowns
    const [userWard, setUserWard] = useState('');
    const [userCityIndex, setUserCityIndex] = useState(); //Save the index of selected dropdowns
    const [userDistrictIndex, setUserDistrictIndex] = useState();
    const [userWardIndex, setUserWardIndex] = useState();
    const [userStreet, setUserStreet] = useState();
    const [userAddressJson, setUserAddressJson] = useState({
        city: "",
        district: "",
        ward: "",
        street: ""
    });

    //Facility Address data
    const [faciDistrict, setFaciDistrict] = useState(''); //For populate dropdowns
    const [faciWard, setFaciWard] = useState('');
    const [faciCityIndex, setFaciCityIndex] = useState(); //Save the index of selected dropdowns
    const [faciDistrictIndex, setFaciDistrictIndex] = useState();
    const [faciWardIndex, setFaciWardIndex] = useState();
    const [faciStreet, setFaciStreet] = useState();
    const [faciAddressJson, setFaciAddressJson] = useState({
        city: "",
        district: "",
        ward: "",
        street: ""
    });

    //Mật khẩu
    const [password, setPwd] = useState('');
    const [validPwd, setValidPwd] = useState(false);

    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);

    const [validPhone, setValidPhone] = useState(false);

    const [validEmail, setValidEmail] = useState(false);

    const [validHotline, setValidHotline] = useState(false);
    //Register DTO
    const [signUpDTO, setSignUpDTO] = useState({
        username: "",
        userDob: "",
        userPhone: sessionStorage.getItem("curPhone"),
        userEmail: "",
        userAddress: "",
        userPassword: "",
        //Facility
        facilityName: "",
        facilityFoundDate: "",
        facilityHotline: "",
        facilityAddress: "",
        businessLicenseNumber: ""
    });

    const handleSignUpChange = (event, field) => {
        let actualValue = event.target.value
        setSignUpDTO({
            ...signUpDTO,
            [field]: actualValue
        })
    }
    //check register success
    const [success, setSuccess] = useState(false);

    // Set value for address fields
    //User
    useEffect(() => {
        console.log("Load address");
        loadAddress();
        console.log(fullAddresses);
    }, [dataLoaded]);

    const loadAddress = async () => {
        const result = await axios.get("https://raw.githubusercontent.com/kenzouno1/DiaGioiHanhChinhVN/master/data.json",
            {});
        setFullAddresses(result.data);
        // Set inf

        const cityList = fullAddresses.slice();
        for (let i in cityList) {
            cityList[i] = { value: cityList[i].Id, label: cityList[i].Name }
        }
        setCity(cityList);
        setDataLoaded(true);
    }

    function loadUserDistrict(index) {
        console.log("City " + index);
        setUserCityIndex(index);
        const districtOnIndex = fullAddresses[index].Districts;
        const districtList = districtOnIndex.slice();
        for (let i in districtList) {
            districtList[i] = { value: districtList[i].Id, label: districtList[i].Name }
        }
        setUserDistrict(districtList);
    }

    //Load user ward list
    function loadUserWard(index) {
        console.log("District " + index);
        setUserDistrictIndex(index);
        const wardOnIndex = fullAddresses[userCityIndex].Districts[index].Wards;
        const wardList = wardOnIndex.slice();
        for (let i in wardList) {
            wardList[i] = { value: wardList[i].Id, label: wardList[i].Name }
        }
        setUserWard(wardList);
    }

    function saveUserWard(index) {
        console.log("Ward " + index);
        setUserWardIndex(index);
    }

    //Load Address into the fields
    function loadUserAddress(street) {
        setUserStreet(street);
        setFaciStreet(street);
        console.log(fullAddresses[userCityIndex]);
        userAddressJson.city = fullAddresses[userCityIndex].Name;
        userAddressJson.district = fullAddresses[userCityIndex].Districts[userDistrictIndex].Name;
        userAddressJson.ward = fullAddresses[userCityIndex].Districts[userDistrictIndex].Wards[userWardIndex].Name;
        userAddressJson.street = street;
        signUpDTO.userAddress = JSON.stringify(userAddressJson);
        signUpDTO.facilityAddress = JSON.stringify(userAddressJson);
    }

    //Load facility districts
    function loadFaciDistrict(index) {
        console.log("FCity " + index);
        setFaciCityIndex(index);
        const districtOnIndex = fullAddresses[index].Districts;
        const districtList = districtOnIndex.slice();
        for (let i in districtList) {
            districtList[i] = { value: districtList[i].Id, label: districtList[i].Name }
        }
        setFaciDistrict(districtList);
    }

    //Load facility wards
    function loadFaciWard(index) {
        console.log("FDistrict " + index);
        setFaciDistrictIndex(index);
        const wardOnIndex = fullAddresses[faciCityIndex].Districts[index].Wards;
        const wardList = wardOnIndex.slice();
        for (let i in wardList) {
            wardList[i] = { value: wardList[i].Id, label: wardList[i].Name }
        }
        setFaciWard(wardList);
    }

    function saveFaciWard(index) {
        console.log("FWard " + index);
        setFaciWardIndex(index);
    }

    //Load facility address into the field
    function loadFaciAddress(street) {
        setFaciStreet(street);
        faciAddressJson.city = fullAddresses[faciCityIndex].Name;
        faciAddressJson.district = fullAddresses[faciCityIndex].Districts[faciDistrictIndex].Name;
        faciAddressJson.ward = fullAddresses[faciCityIndex].Districts[faciDistrictIndex].Wards[faciWardIndex].Name;
        faciAddressJson.street = street;
        signUpDTO.facilityAddress = JSON.stringify(faciAddressJson);
    }

    useEffect(() => {
        userRef.current.focus();
    }, [])


    useEffect(() => {
        setValidEmail(EMAIL_REGEX.test(signUpDTO.userEmail));
    }, [signUpDTO.userEmail])

    useEffect(() => {
        setValidPhone(PHONE_REGEX.test(signUpDTO.userPhone));
    }, [signUpDTO.userPhone])

    useEffect(() => {
        setValidHotline(PHONE_REGEX.test(signUpDTO.facilityHotline));
    }, [signUpDTO.facilityHotline])

    useEffect(() => {
        setValidPwd(PWD_REGEX.test(password));
        setValidMatch(password === matchPwd);
    }, [password, matchPwd])


    const handleSubmit = async (e) => {
        e.preventDefault();
        const v2 = PWD_REGEX.test(password);
        const v3 = EMAIL_REGEX.test(signUpDTO.userEmail);
        if (!v2 || !v3) {
            toast.error("Sai định dạng yêu cầu");
            return;
        }
        signUpDTO.userPassword = password;
        try {
            const response = await axios.post(REGISTER_URL,
                signUpDTO,
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                }
            );
            console.log(response?.data);
            console.log(JSON.stringify(response));
            toast.success('Đăng ký thành công');
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
                <section className="u-clearfix u-white u-section-1" id="sec-3c81" data-animation-name="" data-animation-duration="0" data-animation-delay="0" data-animation-direction="">
                    <div className="u-custom-color-2 u-expanded-width u-shape u-shape-rectangle u-shape-1"></div>
                    <div style={{ minHeight: "0px" }} className="u-align-center u-border-20 u-border-custom-color-1 u-border-no-left u-border-no-right u-border-no-top u-container-style u-custom-border u-group u-radius-46 u-shape-round u-white u-group-1">
                        <div className="u-container-layout u-valign-middle-xs u-container-layout-1">
                            <h2 className="u-text u-text-custom-color-1 u-text-default u-text-1">Đăng kí thành công</h2>
                            <h3>Vui lòng đợi xác thực</h3>
                            <Link to="/login"><button className="btn btn-light" style={{ width: "30%" }}>Đăng nhập</button></Link>
                        </div>
                    </div>
                </section>
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
                                                    <div className="col-lg-6 first">
                                                        <div className="p-5">
                                                            <h3 className="" style={{ color: " #ff7300" }}>Thông tin chung</h3>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="username">Tên <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="username"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={e => handleSignUpChange(e, "username")}
                                                                        value={signUpDTO.username}
                                                                        required
                                                                        className="form-control " />
                                                                </div>
                                                            </div>
                                                            {/*Date of birth*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="userDob">Ngày sinh (Ngày/Tháng/Năm) <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="date" id="userDob"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => handleSignUpChange(e, "userDob")}
                                                                        value={signUpDTO.userDob}
                                                                        required
                                                                        className="form-control " pattern="\d{4}-\d{2}-\d{2}" />
                                                                </div>
                                                            </div>
                                                            {/*Phone*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="userPhone">Số điện thoại <FontAwesomeIcon className="star" icon={faStarOfLife} />
                                                                        <FontAwesomeIcon icon={faCheck} className={validPhone ? "valid" : "hide"} />
                                                                        <FontAwesomeIcon icon={faTimes} className={validPhone || !signUpDTO.userPhone ? "hide" : "invalid"} />
                                                                    </label>
                                                                    <span id="phonenote" data-text="Số điện thoại Việt Nam, bắt đầu bằng 03|5|7|8|9"
                                                                        className="tip invalid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                                                                    <input type="text" id="userPhone"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => handleSignUpChange(e, "userPhone")}
                                                                        value={signUpDTO.userPhone}
                                                                        required
                                                                        aria-invalid={validPhone ? "false" : "true"}
                                                                        aria-describedby="phonenote"
                                                                        className="form-control " disabled />
                                                                </div>
                                                            </div>
                                                            {/*userEmail*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="userEmail">Email <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <FontAwesomeIcon icon={faCheck} className={validEmail ? "valid" : "hide"} />
                                                                    <FontAwesomeIcon icon={faTimes} className={validEmail || !signUpDTO.userEmail ? "hide" : "invalid"} />
                                                                    <span id="emailnote" data-text="Bắt đầu bằng 1 chữ cái + @example.com"
                                                                        className="tip invalid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                                                                    <input type="text" id="userEmail"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => handleSignUpChange(e, "userEmail")}
                                                                        value={signUpDTO.userEmail}
                                                                        required
                                                                        aria-invalid={validEmail ? "false" : "true"}
                                                                        aria-describedby="emailnote" className="form-control " />
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
                                                                        value={userCityIndex}
                                                                        required>
                                                                        <option value="" disabled selected>Chọn Tỉnh/Thành phố của bạn</option>
                                                                        {city &&
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
                                                                        value={userDistrictIndex}
                                                                        required>
                                                                        <option value="" disabled selected>Chọn Quận/Huyện của bạn</option>
                                                                        {userDistrict &&
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
                                                                        onChange={(e) => saveUserWard(e.target.value)}
                                                                        value={userWardIndex}
                                                                        required>
                                                                        <option value="" disabled selected>Chọn Phường của bạn</option>
                                                                        {userWard &&
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
                                                                        onChange={(e) => loadUserAddress(e.target.value)}
                                                                        required
                                                                        className="form-control " />
                                                                </div>
                                                            </div>
                                                            <div className="mb-4">
                                                                <div className="form-outline login-wrapper">
                                                                    <label htmlFor="password">Mật khẩu <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <FontAwesomeIcon icon={faCheck} className={validPwd ? "valid" : "hide"} />
                                                                    <FontAwesomeIcon icon={faTimes} className={validPwd || !signUpDTO.userPassword ? "hide" : "invalid"} />
                                                                    <span id="pwdnote" data-text=" 8 - 20 kí tự. Bao gồm 1 chữ cái viết hoa, 1 số và 1 kí tự đặc biệt (!,@,#,$,%)"
                                                                        className="tip invalid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                                                                    <input type={passwordShown ? "text" : "password"} id="password"
                                                                        onChange={(e) => setPwd(e.target.value)}
                                                                        value={password}
                                                                        required
                                                                        aria-invalid={validPwd ? "false" : "true"}
                                                                        aria-describedby="pwdnote"
                                                                        className="form-control " />
                                                                    <i onClick={togglePasswordVisiblity}>{eye}</i>
                                                                </div>
                                                            </div>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline login-wrapper">
                                                                    <label htmlFor="confirm_pwd">Xác nhận lại mật khẩu <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <FontAwesomeIcon icon={faCheck} className={validMatch ? "valid" : "hide"} />
                                                                    <FontAwesomeIcon icon={faTimes} className={validMatch || signUpDTO.userPassword ? "hide" : "invalid"} />
                                                                    <span id="confirmnote" data-text="Mật khẩu phải trùng với mẩt khẩu đã nhập ở trên"
                                                                        className="tip invalid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                                                                    <input type={passwordShown2 ? "text" : "password"} id="confirm_pwd"
                                                                        onChange={(e) => setMatchPwd(e.target.value)}
                                                                        required
                                                                        aria-invalid={validMatch ? "false" : "true"}
                                                                        aria-describedby="confirmnote" className="form-control " />
                                                                    <i onClick={togglePasswordVisiblity2}>{eye}</i>
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
                                                                        onChange={(e) => handleSignUpChange(e, "facilityName")}
                                                                        value={signUpDTO.facilityName}
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
                                                                        onChange={(e) => handleSignUpChange(e, "facilityFoundDate")}
                                                                        value={signUpDTO.facilityFoundDate}
                                                                        required
                                                                        className="form-control "  pattern="\d{4}-\d{2}-\d{2}"  />
                                                                </div>
                                                            </div>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline form-white">
                                                                    <label htmlFor="hotline" className="text-white">Hotline <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <FontAwesomeIcon icon={faCheck} className={validHotline ? "valid" : "hide"} />
                                                                    <FontAwesomeIcon icon={faTimes} className={validHotline || !signUpDTO.facilityHotline ? "hide" : "invalid"} />
                                                                    <span id="phonenote" data-text="Số điện thoại Việt Nam, bắt đầu bằng 03|5|7|8|9"
                                                                        className="tip invalid" ><FontAwesomeIcon icon={faInfoCircle} /></span>
                                                                    <input type="text" id="hotline" ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => handleSignUpChange(e, "facilityHotline")}
                                                                        value={signUpDTO.facilityHotline}
                                                                        required
                                                                        className="form-control " />
                                                                </div>
                                                            </div>
                                                            {/*Facility City*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline form-white">
                                                                    <label htmlFor="faciprovince" className="text-white" >Tỉnh/Thành phố<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <select className="form-control mt-1" id="faciprovince"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => loadFaciDistrict(e.target.value)}
                                                                        value={faciCityIndex}
                                                                        required>
                                                                        <option value="" disabled selected>Chọn Tỉnh/Thành phố của bạn</option>
                                                                        {city &&
                                                                            city.map((item, index) => (
                                                                                <option value={index}>{item.label}</option>
                                                                            ))
                                                                        }
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            {/*Facility District*/}
                                                            <div className="mb-4 pb-2">
                                                                <div className="form-outline form-white">
                                                                    <label htmlFor="facidistrict" className="text-white">Quận/Huyện<FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <select className="form-control mt-1" id="facidistrict"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => loadFaciWard(e.target.value)}
                                                                        value={faciDistrictIndex}
                                                                        required>
                                                                        <option value="" disabled selected>Chọn Quận/Huyện của bạn</option>
                                                                        {faciDistrict &&
                                                                            faciDistrict.map((item, index) => (
                                                                                <option value={index}>{item.label}</option>
                                                                            ))
                                                                        }
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            {/*Facility Ward*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="faciward" className="text-white" >Phường/Xã <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <select className="form-control mt-1" id="faciward"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => saveFaciWard(e.target.value)}
                                                                        value={faciWardIndex}
                                                                        required>
                                                                        <option value="" disabled selected>Chọn Phường của bạn</option>
                                                                        {faciWard &&
                                                                            faciWard.map((item, index) => (
                                                                                <option value={index}>{item.label}</option>
                                                                            ))
                                                                        }
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            {/*Facility Street*/}
                                                            <div className="mb-4 ">
                                                                <div className="form-outline">
                                                                    <label htmlFor="facinum" className="text-white">Số nhà <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="facinum"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => loadFaciAddress(e.target.value)}
                                                                        defaultValue={faciStreet}
                                                                        required
                                                                        className="form-control " />
                                                                </div>
                                                            </div>
                                                            <div className="mb-4 ">
                                                                <div className="form-outline form-white">
                                                                    <label htmlFor="brn" className="text-white">Mã số đăng ký kinh doanh <FontAwesomeIcon className="star" icon={faStarOfLife} /></label>
                                                                    <input type="text" id="brn"
                                                                        ref={userRef}
                                                                        autoComplete="off"
                                                                        onChange={(e) => handleSignUpChange(e, "businessLicenseNumber")}
                                                                        value={signUpDTO.businessLicenseNumber}
                                                                        required
                                                                        className="form-control " />
                                                                </div>
                                                            </div>
                                                            <button id="confirmRegister" type="submit" className="btn btn-light" style={{ width: "100%", textAlign: "center" }}
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