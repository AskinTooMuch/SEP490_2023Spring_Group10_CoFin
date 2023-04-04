import React, {useState } from 'react'
import { firebase, auth } from '../firebase/firebasesdk'
import { RecaptchaVerifier, signInWithPhoneNumber } from "firebase/auth";
import { useNavigate } from 'react-router-dom';
import { Box, Typography } from '@mui/material';
import { ToastContainer, toast } from 'react-toastify';
const TestOTP = () => {
    const [phoneNumber, setPhoneNumber] = useState("");
    const [otp, setOtp] = useState('');
    const [step, setStep] = useState('INPUT_PHONE_NUMBER');
    const [result, setResult] = useState('');
    const [buttonDisabled, setButtonDisabled] = useState(false);
    const navigate = useNavigate();
    const [isVerified, setIsVerified] = useState(true);
    // Create RecaptchaVerifier instance
    function onCaptchVerify() {
        if (!window.recaptchaVerifier) {
            window.recaptchaVerifier = new RecaptchaVerifier(
                "recaptcha-container",
                {
                    size: "invisible",
                    "expired-callback": () => { },
                },
                auth
            );
        }
    }
    const sendOTP = () => {
        onCaptchVerify();
        if (phoneNumber === "") return;
        const appVerifier = window.recaptchaVerifier;
        const replaced = phoneNumber.replace(phoneNumber[0], "+84")
        console.log(phoneNumber)
        console.log(replaced)
        sessionStorage.setItem("currPhone", replaced);
        auth.signInWithPhoneNumber(replaced, appVerifier).then((result) => {
            setResult(result);
            setStep('VERIFY_OTP');
            setButtonDisabled(true); //Disable button after sending OTP
            setTimeout(() => {
                setButtonDisabled(false); //Enable button after one minute
            }, 60000);
            toast.success("Mã OTP đã được gửi đến số điện thoại của bạn")
        })
            .catch((err) => {
                console.log(err);
                console.log("SMS not sent");
            });
    }

    const reSendOTP = () => {
        const appVerifier = window.recaptchaVerifier;
        const phoneNumber = sessionStorage.getItem("curPhone")
        firebase.auth().signInWithPhoneNumber(phoneNumber, appVerifier)
            .then((verificationId) => {
                const verificationCode = window.prompt('Please enter the verification ' +
                    'code that was sent to your mobile device.');
                const credential = firebase.auth.PhoneAuthProvider.credential(
                    verificationId,
                    verificationCode
                );
                return firebase.auth().currentUser.reauthenticateWithCredential(credential);
            })
            .then((result) => {
                console.log('Re-authenticated user:', result.user);
            })
            .catch((error) => {
                console.error(error);
            });
    }

    const [counter, setCounter] = React.useState(64);
    React.useEffect(() => {
        const timer =
            counter > 0 && setInterval(() => setCounter(counter - 1), 1000);
        return () => clearInterval(timer);
    }, [counter]);

    const ValidateOtp = () => {
        if (otp === null) return;

        result.confirm(otp).then((result) => {
            setTimeout(() => {
                navigate("/register") //Enable button after one minute
            }, 1000);
            sessionStorage.setItem("OTP",isVerified);
            toast.success("Mã OTP chính xác")
        })
            .catch((err) => {
                toast.error("Vui lòng nhập đúng mã OTP")
                console.clear();
            })
    }

    return (
        <div style={{ marginTop: 100 }}>
            <center>

                {step === 'INPUT_PHONE_NUMBER' &&
                    <div>
                        <section className="u-clearfix u-white u-section-1" id="sec-3c81" data-animation-name="" data-animation-duration="0" data-animation-delay="0" data-animation-direction="">
                            <div className="u-custom-color-2 u-expanded-width u-shape u-shape-rectangle u-shape-1"></div>
                            <div style={{ minHeight: "0px" }} className="u-align-center u-border-20 u-border-custom-color-1 u-border-no-left u-border-no-right u-border-no-top u-container-style u-custom-border u-group u-radius-46 u-shape-round u-white u-group-1">
                                <div className="u-container-layout u-valign-middle-xs u-container-layout-1">
                                    <h2 className="u-text u-text-custom-color-1 u-text-default u-text-1">Đăng ký</h2>
                                    <div className="u-form u-login-control u-form-1">
                                        <div className="u-form-group u-form-name">
                                            <label htmlFor="username-a30d" className="u-label u-text-grey-25 u-label-1">Số điện thoại </label>
                                            <input type="text" name="account" value={phoneNumber} placeholder="Nhập số điện thoại" onChange={(e) => setPhoneNumber(e.target.value)}
                                                className="u-border-2 u-border-grey-10 u-grey-10 u-input u-input-rectangle u-input-1" />
                                        </div>
                                        <div className="u-align-left u-form-group u-form-submit">
                                            <div id="recaptcha-container"></div>
                                            <button onClick={sendOTP} className="u-btn u-btn-submit u-button-style u-btn-1">Tiếp tục</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>

                }

                {step === 'VERIFY_OTP' &&

                    <div>
                        <section className="u-clearfix u-white u-section-1" id="sec-3c81" data-animation-name="" data-animation-duration="0" data-animation-delay="0" data-animation-direction="">
                            <div className="u-custom-color-2 u-expanded-width u-shape u-shape-rectangle u-shape-1"></div>
                            <div style={{ minHeight: "0px" }} className="u-align-center u-border-20 u-border-custom-color-1 u-border-no-left u-border-no-right u-border-no-top u-container-style u-custom-border u-group u-radius-46 u-shape-round u-white u-group-1">
                                <div className="u-container-layout u-valign-middle-xs u-container-layout-1">
                                    <h2 className="u-text u-text-custom-color-1 u-text-default u-text-1">Đăng ký</h2>
                                    <div className="u-form u-login-control u-form-1">
                                        <div className="u-form-group u-form-name">
                                            <label htmlFor="username-a30d" className="u-label u-text-grey-25 u-label-1">Nhập mã xác thực </label>
                                            <input type="text" name="otp" placeholder="Nhập mã" onChange={(e) => setOtp(e.target.value)}
                                                className="u-border-2 u-border-grey-10 u-grey-10 u-input u-input-rectangle u-input-1" />

                                        </div>
                                        <div className="u-align-left u-form-group u-form-submit">
                                            <button onClick={ValidateOtp} className="u-btn u-btn-submit u-button-style u-btn-1">Tiếp tục</button>
                                        </div>
                                        <Box mt={3} >
                                            <Typography fontWeight={500} align="center" color='textSecondary'>
                                                OTP gửi lại sau <span style={{ color: "#FF6601", fontWeight: "bold" }}>
                                                    00:{counter}</span>
                                            </Typography>
                                            <button className='btn btn-light' style={{ width: "20%" }} disabled={buttonDisabled} onClick={reSendOTP}>
                                                {buttonDisabled ? 'Vui lòng đợi 1 phút' : 'Gửi lại mã'}</button>
                                        </Box>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                }

            </center>

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

    );
}
export default TestOTP