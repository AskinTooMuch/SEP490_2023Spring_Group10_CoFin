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
    const USER_UPDATE_GET = '/api/user/update/get';
    const USER_UPDATE_SAVE = '/api/user/update/save';

    //show-hide password 
    const [passwordShown, setPasswordShown] = useState(false);
    const [passwordShown2, setPasswordShown2] = useState(false);
    const [passwordShown3, setPasswordShown3] = useState(false);
    const togglePasswordVisiblity = () => {
        setPasswordShown(passwordShown ? false : true);
    };
    const togglePasswordVisiblity2 = () => {
        setPasswordShown2(passwordShown2 ? false : true);
    };
    const togglePasswordVisiblity3 = () => {
        setPasswordShown3(passwordShown3 ? false : true);
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

    // DTO for displaying user's information
    const [updateUserDTO, setUpdateUserDTO] = useState({
        userId: "",
        username: "",
        dob: "",
        email: "",
        address: ""
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

    //address
    const [userAddress, setUserAddress] = useState(
        {
            street: "",
            ward: "",
            district: "",
            city: ""
        }
    );
    const [faciAddress, setFaciAddress] = useState(
        {
            street: "",
            ward: "",
            district: "",
            city: ""
        }
    );

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

        setUserAddress(JSON.parse(responseJson.userAddress))
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
        setFaciAddress(JSON.parse(responseJson.facilityAddress))
        console.log(facilityInformation.subscriptionId === '');
    }


    // Get user's information to update
    const handleUpdateGet = async () => {
        setShow2(true);
        try {
            const response = await axios.get(USER_UPDATE_GET,
                { params: { userId: sessionStorage.getItem("curUserId") } },
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: false
                }
            );
            const responseJson = response.data;
            console.log(responseJson);
            //Set User information
            setUpdateUserDTO({
                userId: sessionStorage.getItem("curUserId"),
                username: responseJson.username,
                dob: responseJson.dob,
                email: responseJson.email,
                address: responseJson.address
            })
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else if (err.response?.status === 400) {
                toast.error('Yêu cầu không đúng định dạng');
            } else if (err.response?.status === 401) {
                toast.error('Không có quyền thực hiện hành động này');
            } else {
                toast.error('Yêu cầu không đúng định dạng');
            }
        }
    }

    // Update user's information
    const handleUpdateSave = async (event) => {
        setShow2(false);
        console.log("userId===" + sessionStorage.getItem("curUserId"));
        console.log(updateUserDTO);
        event.preventDefault();
        try {
            const response = await axios.put(USER_UPDATE_SAVE,
                updateUserDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: false
                }
            );
            const responseJson = response.data;
            console.log(responseJson);
            setUpdateUserDTO('');
            loadUserDetails();
            toast.success("Cập nhật thông tin thành công")
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else if (err.response?.status === 400) {
                toast.error('Yêu cầu không đúng định dạng');
            } else if (err.response?.status === 401) {
                toast.error('Không có quyền thực hiện hành động này');
            } else {
                toast.error('Yêu cầu không đúng định dạng');
            }
        }
    }

    // Handle input update information
    const handleUpdateUser = (event, field) => {
        let actualValue = event.target.value
        setUpdateUserDTO({
            ...updateUserDTO,
            [field]: actualValue
        })
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
            <h2>Thông tin cá nhân</h2>
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
                                        <p>Địa chỉ</p>
                                    </div>
                                    <div className="col-md-6">
                                        <p id="userAddress">{userAddress.street + ", " + userAddress.ward + ", " + userAddress.district + ", " + userAddress.city}</p>
                                    </div>
                                </div>
                                {/*Start: Change account details*/}
                                <div className="row">
                                    <div className="col-md-6">
                                        <Button onClick={handleShow} style={{ width: "100%" }} className="btn btn-light" id="startChangePassword">Đổi mật khẩu</Button >

                                        <Modal show={show} onHide={handleClose}
                                            size="lg"
                                            aria-labelledby="contained-modal-title-vcenter"
                                            centered >
                                            <Modal.Header closeButton onClick={handleClose}>
                                                <Modal.Title>Thay đổi mật khẩu <p style={{ color: "grey", fontSize: "20px" }} id="pwdnote" data-text=" 8 - 20 kí tự. Bao gồm 1 chữ cái viết hoa, 1 số và 1 kí tự đặc biệt (!,@,#,$,%)"
                                                    className="tip invalid" ><FontAwesomeIcon icon={faInfoCircle} /></p></Modal.Title>
                                            </Modal.Header>
                                            <form onSubmit={handleSubmit} style={{ margin: "10px" }}>
                                                <div className="changepass">
                                                    <div className="row ">
                                                        <div className="col-md-6 ">
                                                            <p>Mật khẩu cũ</p>
                                                        </div>
                                                        <div className="col-md-6 pass-wrapper ">
                                                            <input ref={userRef} id="oldPassword" onChange={e => handleChange(e, "password")}
                                                                value={changePasswordDTO.password} required
                                                                type={passwordShown ? "text" : "password"}
                                                                minLength="8" maxLength="20"
                                                                className="form-control " />
                                                            <i onClick={togglePasswordVisiblity}>{eye}</i>
                                                        </div>
                                                    </div>
                                                    <div className="row">
                                                        <div className="col-md-6">
                                                            <p>Mật khẩu mới <FontAwesomeIcon className="star" icon={faStarOfLife} />
                                                                <FontAwesomeIcon icon={faCheck} className={validPwd ? "valid" : "hide"} />
                                                                <FontAwesomeIcon icon={faTimes} className={validPwd || !changePasswordDTO.newPassword ? "hide" : "invalid"} />
                                                            </p>
                                                        </div>
                                                        <div className="col-md-6 pass-wrapper">

                                                            <input ref={userRef} onChange={e => handleChange(e, "newPassword")}
                                                                value={changePasswordDTO.newPassword} required
                                                                id="newPassword"
                                                                aria-invalid={validPwd ? "false" : "true"}
                                                                aria-describedby="pwdnote"
                                                                type={passwordShown2 ? "text" : "password"}
                                                                className="form-control "
                                                                minLength="8" maxLength="20" onFocus={() => setPassFocus(true)}
                                                                onBlur={() => setPassFocus(false)}
                                                            />
                                                            <i onClick={togglePasswordVisiblity2}>{eye}</i>
                                                        </div>
                                                    </div>
                                                    <div className="row">
                                                        <div className="col-md-6">
                                                            <p>Xác nhận lại
                                                                <FontAwesomeIcon className="star" icon={faStarOfLife} />
                                                                <FontAwesomeIcon icon={faCheck} className={validMatch ? "valid" : "hide"} />
                                                                <FontAwesomeIcon icon={faTimes} className={validMatch || changePasswordDTO.newPassword ? "hide" : "invalid"} /></p>
                                                        </div>
                                                        <div className="col-md-6 pass-wrapper">
                                                            <input ref={userRef}
                                                                id="confirm_pwd"
                                                                onChange={(e) => setMatchPwd(e.target.value)}
                                                                value={matchPwd}
                                                                required
                                                                aria-invalid={validMatch ? "false" : "true"}
                                                                aria-describedby="confirmnote"
                                                                type={passwordShown3 ? "text" : "password"}
                                                                className="form-control "
                                                                minLength="8" maxLength="20" />
                                                            <i onClick={togglePasswordVisiblity3}>{eye}</i>
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
                                                <div className='model-footer'>
                                                    <button className="btn btn-light" style={{ width: "30%" }} id = "confirmChangePassword">
                                                        Đổi mật khẩu
                                                    </button>
                                                    <button className="btn btn-light" style={{ width: "20%" }} onClick={handleClose} id = "cancelChangePassword">
                                                        Huỷ
                                                    </button>
                                                </div>
                                            </form>
                                        </Modal>
                                    </div>
                                    <div className="col-md-6">
                                        <Button onClick={handleUpdateGet} style={{ width: "100%" }} className="btn btn-light" id="startChangeUserInformation">Cập nhật</Button >
                                        <Modal show={show2} onHide={handleClose2}
                                            size="lg"
                                            aria-labelledby="contained-modal-title-vcenter"
                                            centered >
                                            <form onSubmit={handleUpdateSave}>
                                                <Modal.Header><h4>Chỉnh sửa thông tin cá nhân</h4></Modal.Header>
                                                <Modal.Body>
                                                    <div className="mb-3">
                                                        <label className="form-label">Họ và Tên</label>
                                                        <input type="text" className="form-control" name="username" id = "updateUsername"
                                                            ref={userRef} onChange={e => handleUpdateUser(e, "username")}
                                                            value={updateUserDTO.username} required />
                                                    </div>
                                                    <div className="mb-3">
                                                        <label className="form-label">Ngày sinh</label>
                                                        <input type="date" className="form-control" name="dob" id = "updateDob"
                                                            ref={userRef} onChange={e => handleUpdateUser(e, "dob")}
                                                            value={updateUserDTO.dob} required />
                                                    </div>
                                                    <div className="mb-3">
                                                        <label className="form-label">Email</label>
                                                        <input type="email" className="form-control" name="email" id="updateEmail"
                                                            ref={userRef} onChange={e => handleUpdateUser(e, "email")}
                                                            value={updateUserDTO.email} required />
                                                    </div>
                                                    <div className="mb-3">
                                                        <label className="form-label">Địa chỉ</label>
                                                        <input type="text" style={{ minHeight: "50px" }} className="form-control" name="address" id = "updateAddress"
                                                            ref={userRef} onChange={e => handleUpdateUser(e, "address")}
                                                            value={updateUserDTO.address} required />
                                                    </div>
                                                </Modal.Body>
                                                <div className='model-footer'>
                                                    <button style={{ width: "30%" }} className="col-md-6 btn-light" type='submit' id = "submitUserUpdate">
                                                        Xác nhận
                                                    </button>
                                                    <button  style={{ width: "20%" }} className="btn btn-light" onClick={handleClose2} id = "cancelUserUpdate">
                                                        Huỷ
                                                    </button>
                                                </div>
                                            </form>
                                        </Modal>

                                    </div>
                                </div>
                                {/*End: Chnage account details*/}
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
                                <div className="card-header"><p id="facilityName" >Thông tin cơ sở: {facilityInformation.facilityName}</p></div>
                                <div className="card-body">
                                    <form>
                                        <div className="tab-pane " >
                                        <div className="row">
                                                <div className="col-md-6">
                                                    <p>Ngày thành lập</p>
                                                </div>
                                                <div className="col-md-6">
                                                    <p id="facilityFoundDate">{facilityInformation.facilityFoundDate}</p>
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6">
                                                    <p>Mã số kinh doanh</p>
                                                </div>
                                                <div className="col-md-6">
                                                    <p id="licenseNumber">Fuck tuan</p>
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6">
                                                    <p>Số nhà</p>
                                                </div>
                                                <div className="col-md-6">
                                                    <p id="street">{faciAddress.street}</p>
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6">
                                                    <p>Xã</p>
                                                </div>
                                                <div className="col-md-6">
                                                    <p id="ward">{faciAddress.ward}</p>
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6">
                                                    <p>Quận/Huyện</p>
                                                </div>
                                                <div className="col-md-6">
                                                    <p id="state">{faciAddress.district}</p>
                                                </div>
                                            </div>
                                            <div className="row">
                                                <div className="col-md-6">
                                                    <p>Thành phố</p>
                                                </div>
                                                <div className="col-md-6">
                                                    <p id="city">{faciAddress.city}</p>
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
                                                    {facilityInformation.subscriptionId === ''
                                                        ? <p id="subscription">Chưa đăng ký gói</p>
                                                        : <p id="subscription">Gói {facilityInformation.subscriptionId}</p>
                                                    }
                                                </div>
                                            </div>
                                            <div style={{ textAlign: "center" }}>
                                                <button className="btn btn-light" style={{ width: "50%" }} id="startChangeFacilityInformation">Cập nhật</button>
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