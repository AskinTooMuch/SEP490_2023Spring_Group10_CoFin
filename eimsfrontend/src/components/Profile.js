import React, { useState, useRef, useEffect, useLayoutEffect } from 'react';
import axios from '../api/axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { faCheck, faTimes, faInfoCircle, faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye } from "@fortawesome/free-solid-svg-icons";
import "../css/profile.css"
import { Modal, Button } from 'react-bootstrap'
const eye = <FontAwesomeIcon icon={faEye} />;
//regex check password
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
const Profile = () => {
    //api url
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
    const inf_fetched_ref = useRef(false);

    const [passFocus, setPassFocus] = useState(false);

    const userRef = useRef();

    // DTO for sending change password request
    const [changePasswordDTO, setChangePasswordDTO] = useState({
        userId: sessionStorage.getItem("curUserId"),
        password: "",
        newPassword: ""
    });

    //Spliting the user's information into 2 objects: user account information and facility information
    //Account information
    const [accountInformation, setAccountInformation] = useState({
        userId: "",
        userRoleName: "",
        username: "",
        userDob: "",
        userEmail: "",
        userSalary: "",
        userAddress: "",
        userStatus: ""
    });
    //Facility information
    const [facilityInformation, setFacilityInformation] = useState({
        facilityId: "",
        facilityName: "",
        facilityAddress: "",
        facilityFoundDate: "",
        hotline: "",
        facilityStatus: "",
        subscriptionId: "",
        subscriptionExpirationDate: ""
    });

    const [validPwd, setValidPwd] = useState(false);
    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);


    //Get user details
    useEffect(() => {
        if (inf_fetched_ref.current) return;
        inf_fetched_ref.current = true;
        loadUserDetails();
    }, []);

    const loadUserDetails = async () => {
        const result = await axios.get(USER_DETAIL_URL,
            { params: { userId: sessionStorage.getItem("curUserId") } },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Access-Control-Allow-Origin': '*'
                },
                withCredentials: false
            });
        const responseJson = result.data;
        console.log(responseJson);
        //Set account information
        setAccountInformation({
            userId: responseJson.userId,
            userRoleName: responseJson.userRoleName, 
            username: responseJson.username, 
            userDob: responseJson.userDob, 
            userEmail: responseJson.userEmail,
            userSalary: responseJson.userSalary,
            userAddress: responseJson.userAddress,
            userStatus: responseJson.userStatus
        })
        // accountInformation.userId = responseJson.userId;
        // accountInformation.userRoleName = responseJson.userRoleName;
        // accountInformation.username = responseJson.username;
        // accountInformation.userDob = responseJson.userDob;
        // accountInformation.userEmail = responseJson.userEmail;
        // accountInformation.userSalary = responseJson.userSalary;
        // accountInformation.userAddress = responseJson.userAddress;
        // accountInformation.userStatus = responseJson.userStatus;
        //console.log(accountInformation);

        //Set facility information
        setFacilityInformation({
            facilityId: responseJson.facilityId,
            facilityName: responseJson.facilityName,
            facilityAddress: responseJson.facilityAddress,
            facilityFoundDate: responseJson.facilityFoundDate,
            hotline: responseJson.hotline,
            facilityStatus: responseJson.facilityStatus,
            subscriptionId: responseJson.subscriptionId,
            subscriptionExpirationDate: responseJson.subscriptionExpirationDate
        })

        // facilityInformation.facilityId = responseJson.facilityId;
        // facilityInformation.facilityName = responseJson.facilityName;
        // facilityInformation.facilityAddress = responseJson.facilityAddress;
        // facilityInformation.facilityFoundDate = responseJson.facilityFoundDate;
        // facilityInformation.hotline = responseJson.hotline;
        // facilityInformation.facilityStatus = responseJson.facilityStatus;
        // facilityInformation.subscriptionId = responseJson.subscriptionId;
        // facilityInformation.subscriptionExpirationDate = responseJson.subscriptionExpirationDate;
        //console.log(facilityInformation);
    }


    //Check Repassword and Regex password
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

    //Change password through Axios
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
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else if (err.response?.status === 400) {
                toast.error('Mật khẩu cũ không đúng');
            } else if (err.response?.status === 401) {
                toast.error('Unauthorized');
            }
            else {
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
                                        <p id="account">{sessionStorage.getItem("curPhone").substring(0, 2) + "*****" + sessionStorage.getItem("curPhone").substring(7)}</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Họ và Tên</p>
                                    </div>
                                    <div className="col-md-6">
                                        <p id="username">{accountInformation.username}</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Ngày sinh</p>
                                    </div>
                                    <div className="col-md-6">
                                        <p id="dob">{accountInformation.userDob}</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <p>Email</p>
                                    </div>
                                    <div className="col-md-6">
                                        <p id="email">{accountInformation.userEmail}</p>
                                    </div>
                                </div>
                                <div className="row">
                                    <div className="col-md-6">
                                        <Button onClick={handleShow} className="btn btn-info">Đổi mật khẩu</Button >

                                        <Modal show={show} onHide={handleClose}
                                            size="lg"
                                            aria-labelledby="contained-modal-title-vcenter"
                                            centered >
                                            <Modal.Header closeButton onClick={handleClose}>
                                                <Modal.Title>Thay đổi mật khẩu</Modal.Title>
                                            </Modal.Header>
                                            <form onSubmit={handleSubmit} style={{ margin: "10px" }}>
                                                <div className="changepass">
                                                    <div className="row ">
                                                        <div className="col-md-6 ">
                                                            <p>Mật khẩu cũ</p>
                                                        </div>
                                                        <div className="col-md-6 pass-wrapper ">
                                                            <input ref={userRef} onChange={e => handleChange(e, "password")}
                                                                value={changePasswordDTO.password} required
                                                                type={passwordShown ? "text" : "password"}
                                                                minLength="8" maxLength="20" />
                                                            <i onClick={togglePasswordVisiblity}>{eye}</i>
                                                        </div>
                                                    </div>
                                                    <div className="row">
                                                        <div className="col-md-6">
                                                            <p>Mật khẩu mới <FontAwesomeIcon className="star" icon={faStarOfLife} />
                                                            </p>
                                                        </div>
                                                        <div className="col-md-6 pass-wrapper">
                                                            <input ref={userRef} onChange={e => handleChange(e, "newPassword")}
                                                                value={changePasswordDTO.newPassword} required
                                                                id="newPassword"
                                                                aria-invalid={validPwd ? "false" : "true"}
                                                                aria-describedby="pwdnote"
                                                                type={passwordShown ? "text" : "password"}
                                                                className={changePasswordDTO.password !== changePasswordDTO.newPassword && validPwd ? "valid" : "invalid"}
                                                                minLength="8" maxLength="20"  onFocus={() => setPassFocus(true)}
                                                                onBlur={() => setPassFocus(false)}
                                                            />
                                                            <i onClick={togglePasswordVisiblity}>{eye}</i>
                                                        </div>
                                                    </div>
                                                    <div className="row">
                                                        <div className="col-md-6">
                                                            <p>Xác nhận lại
                                                                <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                                        </div>
                                                        <div className="col-md-6 pass-wrapper">
                                                            <input ref={userRef}
                                                                id="confirm_pwd"
                                                                onChange={(e) => setMatchPwd(e.target.value)}
                                                                value={matchPwd}
                                                                required
                                                                aria-invalid={validMatch ? "false" : "true"}
                                                                aria-describedby="confirmnote"
                                                                type={passwordShown ? "text" : "password"}
                                                                className={validMatch && matchPwd ? "valid" : "invalid"}
                                                                minLength="8" maxLength="20" />
                                                            <i onClick={togglePasswordVisiblity}>{eye}</i>
                                                        </div>

                                                    </div>
                                                </div>
                                                {/* <h6 id="pwdnote" >
                                                    <FontAwesomeIcon icon={faInfoCircle} />
                                                    &nbsp; 8 - 20 kí tự.<br />
                                                    Bao gồm 1 chữ cái viết hoa, 1 số và 1 kí tự đặc biệt.<br />
                                                    Các kí tự đặc biệt cho phép: <span aria-label="exclamation mark">!</span> <span aria-label="at symbol">@</span> <span aria-label="hashtag">#</span> <span aria-label="dollar sign">$</span> <span aria-label="percent">%</span>
                                                    <br /> Mật khẩu mới không được trùng với mật khẩu cũ
                                                    <br />Mật khẩu xác nhận lại phải trùng với mẩt khẩu đã nhập ở trên

                                                </h6> */}
                                                <button className="btn btn-danger" style={{ width: "20%" }} onClick={handleClose}>
                                                    Huỷ
                                                </button>

                                                <button className="btn btn-success" style={{ width: "30%" }} >
                                                    Đổi mật khẩu
                                                </button>
                                            </form>
                                        </Modal>
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

                        {facilityInformation.facilityId == null
                            ? <div>
                                <div className="card-header">Thông tin cơ sở</div>
                                <div className="card-body"> Người dùng hiện chưa có cơ sở; vui lòng tạo mới hoặc kích hoạt cơ sở. </div>
                            </div>
                            : <div>
                                <div className="card-header">Thông tin cơ sở: {facilityInformation.facilityName}</div>
                                <div className="card-body">
                                    <form>
                                        <div className="tab-pane " >
                                            {/* <div className="row">
                                        <div className="col-md-6">
                                            <p>Số nhà</p>
                                        </div>
                                        <div className="col-md-6">
                                            <p id="houseNumber"></p>
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
                                    </div> */}
                                            <div className="row">
                                                <div className="col-md-6">
                                                    <p>Thành phố</p>
                                                </div>
                                                <div className="col-md-6">
                                                    <p id="city">{facilityInformation.facilityAddress}</p>
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6">
                                                    <p>Hotline</p>
                                                </div>
                                                <div className="col-md-6">
                                                    <p id="hotline">{facilityInformation.hotline}</p>
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6">
                                                    <p>Gói đăng ký</p>
                                                </div>
                                                <div className="col-md-6">
                                                    <p id="subscription">Gói {facilityInformation.subscriptionId}</p>
                                                </div>
                                            </div>
                                            <div style={{ textAlign: "center" }}>

                                                <button className="btn btn-info" style={{ width: "50%" }}>Cập nhật</button>
                                            </div>

                                        </div>
                                    </form>
                                </div>
                            </div>
                        }


                    </div>
                </div>
            </div>
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
    )
}


export default Profile