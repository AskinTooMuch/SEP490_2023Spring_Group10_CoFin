import React, { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { faCheck, faTimes, faInfoCircle, faStarOfLife } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye } from "@fortawesome/free-solid-svg-icons";
const eye = <FontAwesomeIcon icon={faEye} />;
//regex check password
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,20}$/;
const ChangeOldPassword = () => {
    //api url
    const CHANGE_PASS_URL = '/api/auth/changePassword';

    const handleChange = (event, field) => {
        let actualValue = event.target.value
        setChangePasswordDTO({
            ...changePasswordDTO,
            [field]: actualValue
        })
    }

    // DTO for sending change password request
    const [changePasswordDTO, setChangePasswordDTO] = useState({
        userId: sessionStorage.getItem("curUserId"),
        password: "",
        newPassword: "",
        reNewPassword: ""
    });

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

    //show-hide popup change password
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [passFocus, setPassFocus] = useState(false);

    const userRef = useRef();

    const [validPwd, setValidPwd] = useState(false);
    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);

    //Change password through Axios
    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post(CHANGE_PASS_URL,
                changePasswordDTO,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*'
                    },
                    withCredentials: true
                }
            );
            console.log(JSON.stringify(response?.data));
            setChangePasswordDTO({
                userId: sessionStorage.getItem("curUserId"),
                password: "",
                newPassword: "",
                reNewPassword: ""
            });
            toast.success("Đổi mật khẩu thành công");
            setShow(false);
        } catch (err) {
            if (!err?.response) {
                toast.error('Server không phản hồi');
            } else {
                if ((err.response.data === null) || (err.response.data === '')) {
                    toast.error('Có lỗi xảy ra, vui lòng thử lại');
                } else {
                    toast.error(err.response.data);
                }
            }
        }
    }

    const handleChangePassCancel = () => {
        setShow(false);
        setChangePasswordDTO({
            userId: sessionStorage.getItem("curUserId"),
            password: "",
            newPassword: "",
            reNewPassword: ""
        });
    }

    return (
        <div className="profile-info">
            <h2>Thông tin mật khẩu</h2>
            <div className="row outbox">
                <div className='col-md-2' />
                <div className="col-md-8 col-sm-12">
                    <div className="card">
                        <div className="card-body">
                            <div className="tab-pane " >
                                <form onSubmit={handleSubmit} style={{ margin: "10px" }}>
                                    <div className="changepass">
                                        <div className="row ">
                                            <div className="col-md-6 ">
                                                <p>Mật khẩu cũ</p>
                                            </div>
                                            <div className="col-md-6 pass-wrapper ">
                                                <input ref={userRef} id="oldPassword"
                                                    onChange={e => handleChange(e, "password")}
                                                    value={changePasswordDTO.password}
                                                    type={passwordShown ? "text" : "password"}
                                                    className="form-control " />
                                                <i onClick={togglePasswordVisiblity}>{eye}</i>
                                            </div>
                                        </div>
                                        <div className="row " >
                                            <div className="col-md-6">
                                                <p>Mật khẩu mới <FontAwesomeIcon className="star" icon={faStarOfLife} /> </p>
                                            </div>
                                            <div className="col-md-6 pass-wrapper">
                                                <input ref={userRef} onChange={e => handleChange(e, "newPassword")}
                                                    value={changePasswordDTO.newPassword}
                                                    id="newPassword"
                                                    type={passwordShown2 ? "text" : "password"}
                                                    className="form-control"
                                                    onFocus={() => setPassFocus(true)}
                                                    onBlur={() => setPassFocus(false)}
                                                />
                                                <i onClick={togglePasswordVisiblity2}>{eye}</i>
                                            </div>
                                        </div>
                                        <div className="row">
                                            <div className="col-md-6">
                                                <p>Xác nhận lại <FontAwesomeIcon className="star" icon={faStarOfLife} /></p>
                                            </div>
                                            <div className="col-md-6 pass-wrapper">
                                                <input ref={userRef}
                                                    id="confirm_pwd"
                                                    onChange={e => handleChange(e, "reNewPassword")}
                                                    value={changePasswordDTO.reNewPassword}
                                                    type={passwordShown3 ? "text" : "password"}
                                                    className="form-control " />
                                                <i onClick={togglePasswordVisiblity3}>{eye}</i>
                                            </div>

                                        </div>
                                    </div>
                                    <div className='model-footer'>
                                        <button className="btn btn-light" type='submit' style={{ width: "30%" }} id="confirmChangePassword">
                                            Đổi mật khẩu
                                        </button>
                                        <button className="btn btn-light" type='button' style={{ width: "20%" }} onClick={handleChangePassCancel} id="cancelChangePassword">
                                            Huỷ
                                        </button>
                                    </div>
                                </form>

                                {/*End: Change account details*/}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ChangeOldPassword