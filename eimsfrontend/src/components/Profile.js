import React, { useState, useRef, useEffect } from 'react';
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { faCheck, faTimes, faInfoCircle, faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye } from "@fortawesome/free-solid-svg-icons";
import "../css/profile.css"
import { Modal, Button } from 'react-bootstrap'
const eye = <FontAwesomeIcon icon={faEye} />;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const Profile = () => {
    //api
    const CHANGE_PASS_URL = '/api/auth/changePassword';
    const USER_DETAIL_URL = '/api/user/details';

    //show-hide password 
    const [passwordShown, setPasswordShown] = useState(false);
    const togglePasswordVisiblity = () => {
        setPasswordShown(passwordShown ? false : true);
    };

    //show-hide popup
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [show2, setShow2] = useState(false);

    const handleClose2 = () => setShow2(false);
    const handleShow2 = () => setShow2(true);

    const userRef = useRef();
    
    

    const [changePasswordDTO, setChangePasswordDTO] = useState({
        phone: sessionStorage.getItem("curPhone"),
        password: "",
        newPassword: ""
    })

    
    const [validPwd, setValidPwd] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);
    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);
    const [matchFocus, setMatchFocus] = useState(false);


    //Get user details
    const [userDetails, setUserDetails] = useState();
    useEffect(() => {
        loadUserDetails();
    }, []);
    const loadUserDetails = async () => {
        const result = await axios.get(USER_DETAIL_URL,
            { params: { phone: sessionStorage.getItem("curPhone") } },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: false
            });
        const responseJson = result.data;
        console.log(responseJson);
        document.getElementById("account").innerText = sessionStorage.getItem("curPhone").substring(0, 2) + "*****" + sessionStorage.getItem("curPhone").substring(7);
        document.getElementById("username").innerText = responseJson.username;
        document.getElementById("dob").innerText = responseJson.userDob;
        document.getElementById("email").innerText = responseJson.userEmail;

        document.getElementById("houseNumber").innerText = responseJson.houseNumber;
        document.getElementById("street").innerText = responseJson.street;
        document.getElementById("state").innerText = responseJson.state;
        document.getElementById("city").innerText = responseJson.city;
        document.getElementById("hotline").innerText = responseJson.hotline;
        document.getElementById("subscripton").innerText = "Gói " + responseJson.subscriptionId;
    }



    useEffect(() => {
        setValidPwd(PWD_REGEX.test(changePasswordDTO.newPassword));
        setValidMatch(changePasswordDTO.newPassword === matchPwd);
    }, [changePasswordDTO.newPassword, matchPwd])

    const handleChange = (event, field) => {
        let actualValue = event.target.value
        setChangePasswordDTO({
            ...changePasswordDTO,
            [field]: actualValue
        })
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        const v2 = PWD_REGEX.test(changePasswordDTO.newPassword);
        if (!v2) {
            toast.error("Mật khẩu sai định dạng");
            return;
        }
        try {
            const response = await axios.post(CHANGE_PASS_URL,
                changePasswordDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: false
                }
            );
            console.log(JSON.stringify(response?.data));
            setChangePasswordDTO('');
            toast.success("Đổi mật khẩu thành công")
            setShow(false)
            window.location.reload(false);
        } catch (err) {
            toast.success("Đổi mật khẩu thành công");
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else if (err.response?.status === 400) {
                toast.error('Mật khẩu cũ không đúng');
            } else if (err.response?.status === 401) {
                toast.error('Unauthorized');
            } else {
                toast.error('Sai mật khẩu');
            }
        }

    }

    return (
        <div className="profile-info">
            <div className="head">
                <h3>Thông tin cá nhân</h3>
            </div>
            <div className="row outbox">
                <div className="col-sm-6 col-xs-12">
                    <div className="card">
                        <div className="card-header">Thông tin tài khoản</div>
                        <div className="card-body">


                            <div className="tab-pane " >
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Tài khoản</p>
                                    </div>
                                    <div className="col-md-6">
                                        <p id="account">0*********0</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Họ và Tên</p>
                                    </div>
                                    <div className="col-md-6">
                                        <p id="username">Phạm Anh Tùng</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Ngày sinh</p>
                                    </div>
                                    <div className="col-md-6">
                                        <p id="dob">12/05/2001</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Email</p>
                                    </div>
                                    <div className="col-md-6">
                                        <p id="email">@gmail.com</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <Button onClick={handleShow} className="btn btn-info">Đổi mật khẩu</Button >
                                        <form onSubmit={handleSubmit} >
                                            <Modal show={show} onHide={handleClose}
                                                size="lg"
                                                aria-labelledby="contained-modal-title-vcenter"
                                                centered >
                                                <Modal.Header closeButton onClick={handleClose}>
                                                    <Modal.Title>Thay đổi mật khẩu</Modal.Title>
                                                </Modal.Header>
                                                <Modal.Body>

                                                    <div className="changepass">
                                                        <div className="row">
                                                            <div className="col-md-6 ">
                                                                <p>Mật khẩu cũ</p>
                                                            </div>
                                                            <div className="col-md-6 pass-wrapper">
                                                                <input ref={userRef} onChange={e => handleChange(e, "password")}
                                                                    value={changePasswordDTO.password} required 
                                                                    type={passwordShown ? "text" : "password"}/>
                                                                    <i onClick={togglePasswordVisiblity}>{eye}</i>
                                                            </div>
                                                        </div>
                                                        <div className="row">
                                                            <div className="col-md-6">
                                                                <p>Mật khẩu mới <FontAwesomeIcon className="star" icon={faStarOfLife} />
                                                                    <FontAwesomeIcon icon={faCheck} className={validPwd ? "valid" : "hide"} />
                                                                    <FontAwesomeIcon icon={faTimes} className={validPwd || !changePasswordDTO.password ? "hide" : "invalid"} /></p>
                                                            </div>
                                                            <div className="col-md-6 pass-wrapper">
                                                                <input ref={userRef} onChange={e => handleChange(e, "newPassword")}
                                                                    value={changePasswordDTO.newPassword} required
                                                                    id="newPassword"
                                                                    aria-invalid={validPwd ? "false" : "true"}
                                                                    aria-describedby="pwdnote"
                                                                    onFocus={() => setPwdFocus(true)}
                                                                    onBlur={() => setPwdFocus(false)}
                                                                    type={passwordShown ? "text" : "password"} />
                                                                    <i onClick={togglePasswordVisiblity}>{eye}</i>
                                                            </div>
                                                            <p id="pwdnote" className={pwdFocus && !validPwd ? "instructions" : "offscreen"}>
                                                                <FontAwesomeIcon icon={faInfoCircle} />
                                                                8 - 24 kí tự.<br />
                                                                Bao gồm 1 chữ cái viết hoa, 1 số và 1 kí tự đặc biệt.<br />
                                                                Các kí tự đặc biệt cho phép: <span aria-label="exclamation mark">!</span> <span aria-label="at symbol">@</span> <span aria-label="hashtag">#</span> <span aria-label="dollar sign">$</span> <span aria-label="percent">%</span>
                                                            </p>
                                                        </div>
                                                        <div className="row">
                                                            <div className="col-md-6">
                                                                <p>Xác nhận lại
                                                                    <FontAwesomeIcon className="star" icon={faStarOfLife} />
                                                                    <FontAwesomeIcon icon={faCheck} className={validMatch && matchPwd ? "valid" : "hide"} />
                                                                    <FontAwesomeIcon icon={faTimes} className={validMatch || !matchPwd ? "hide" : "invalid"} /></p>
                                                            </div>
                                                            <div className="col-md-6 pass-wrapper">
                                                                <input ref={userRef}
                                                                    id="confirm_pwd"
                                                                    onChange={(e) => setMatchPwd(e.target.value)}
                                                                    value={matchPwd}
                                                                    required
                                                                    aria-invalid={validMatch ? "false" : "true"}
                                                                    aria-describedby="confirmnote"
                                                                    onFocus={() => setMatchFocus(true)}
                                                                    onBlur={() => setMatchFocus(false)} 
                                                                    type={passwordShown ? "text" : "password"}/>
                                                                    <i onClick={togglePasswordVisiblity}>{eye}</i>
                                                            </div>

                                                            <p id="confirmnote" className={matchFocus && !validMatch ? "instructions" : "offscreen"}>
                                                                <FontAwesomeIcon icon={faInfoCircle} />
                                                                Mật khẩu phải trùng với mẩt khẩu đã nhập ở trên
                                                            </p>
                                                        </div>

                                                    </div>
                                                </Modal.Body>

                                                <Modal.Footer>
                                                    <Button variant="danger" style={{ width: "20%" }} onClick={handleClose}>
                                                        Huỷ
                                                    </Button>

                                                    <Button variant="dark" style={{ width: "30%" }} className="col-md-6" onClick={handleSubmit}>
                                                        Đổi mật khẩu
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
                                                    </Button>

                                                </Modal.Footer>
                                            </Modal>
                                        </form>
                                    </div>
                                    <div className="col-md-6">
                                        <Button onClick={handleShow2} className="btn btn-info">Cập nhật</Button >
                                        <form><Modal show={show2} onHide={handleClose2}
                                            size="lg"
                                            aria-labelledby="contained-modal-title-vcenter"
                                            centered >
                                            <h3>Chỉnh sửa thông tin cá nhân</h3>
                                            <Modal.Body>
                                                <div className="mb-3">
                                                    <label className="form-label">Họ và Tên</label>
                                                    <input type="text" className="form-control" />

                                                </div>
                                                <div className="mb-3">
                                                    <label className="form-label">Ngày sinh</label>
                                                    <input type="date" className="form-control" />
                                                </div>
                                                <div className="mb-3">
                                                    <label className="form-label">Email</label>
                                                    <input type="email" className="form-control" />
                                                </div>
                                                <div className="mb-3">
                                                    <label className="form-label">Địa chỉ</label>
                                                    <input type="text" style={{ minHeight: "50px" }} className="form-control" />
                                                </div>
                                            </Modal.Body>

                                            <Modal.Footer>
                                                <Button variant="danger" style={{ width: "20%" }} onClick={handleClose2}>
                                                    Huỷ
                                                </Button>

                                                <Button variant="dark" style={{ width: "30%" }} className="col-md-6" onClick={handleClose2}>
                                                    Xác nhận
                                                </Button>

                                            </Modal.Footer>
                                        </Modal>
                                        </form>
                                    </div>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
                <div className="col-md-6 col-xs-12">
                    <div className="card">
                        <div className="card-header">Thông tin cơ sở</div>
                        <div className="card-body">
                            <form>
                                <div className="tab-pane " >
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Số nhà</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p id="houseNumber">69</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Đường</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p id="street">...</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Quận/Huyện</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p id="state">Thạch Thất</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Thành phố</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p id="city">Hà Nội</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Hotline</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p id="hotline">096969696</p>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col-md-6">
                                            <p>Gói đăng ký</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p id="subscription">Gói 1</p>
                                        </div>
                                    </div>
                                    <div style={{ textAlign: "center" }}>

                                        <button className="btn btn-info" style={{ width: "50%" }}>Cập nhật</button>
                                    </div>

                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    )
}

export default Profile