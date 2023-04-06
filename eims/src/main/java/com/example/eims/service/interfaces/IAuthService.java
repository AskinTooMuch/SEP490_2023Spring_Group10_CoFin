/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 * 23/03/2023   1.1        ChucNV       Request parameter for authenticateUser<br>
 */

package com.example.eims.service.interfaces;

import com.example.eims.dto.auth.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface IAuthService {
    /**
     * Sign in.
     *
     * @param loginDTO contains the login phone and password
     * @return
     */
    public ResponseEntity<?> authenticateUser(HttpServletRequest request, HttpServletResponse response, LoginDTO loginDTO);

    /**
     * Sign up.
     *
     * @param signUpDTO contains the User's name, email, phone number, date of birth, address.
     * @return
     */
    public ResponseEntity<?> registerUser(SignUpDTO signUpDTO);

    /**
     * Change password.
     *
     * @param changePasswordDTO contains the login phone, old password and new password
     * @return
     */
    public ResponseEntity<?> changePassword(ChangePasswordDTO changePasswordDTO);

    /**
     * Send OTP to reset password.
     *
     * @param phone the phone number of the account
     * @return
     */
    public ResponseEntity<?> sendOTPResetPass(String phone) throws IOException;

    /**
     * Verify OTP forgot password.
     *
     * @param verifyOtpDTO
     * @return
     */
    public ResponseEntity<?> verifyOTPResetPass(VerifyOtpDTO verifyOtpDTO);

    /**
     * Re-send OTP forgot password.
     *
     * @return
     */
    public ResponseEntity<?> resendOTPResetPass(String phone) throws IOException;

    /**
     * Change password after verify OTP.
     *
     * @param forgotPasswordDTO contains the new password, login phone
     * @return
     */
    public ResponseEntity<?> resetPassword(ForgotPasswordDTO forgotPasswordDTO);

    /**
     * Send OTP to register owner.
     *
     * @param phone the phone number of the account
     * @return
     */
    public ResponseEntity<?> sendOTPRegister(String phone) throws IOException;

    /**
     * Verify OTP to register owner.
     *
     * @param verifyOtpDTO
     * @return
     */
    public ResponseEntity<?> verifyOTPRegister(VerifyOtpDTO verifyOtpDTO);

    /**
     * Re-send OTP register owner.
     *
     * @return
     */
    public ResponseEntity<?> resendOTPRegister(String phone) throws IOException;

    /**
     * Check phone number when register account
     *
     * @param phone the phone number
     * @return
     */
    public ResponseEntity<?> checkPhoneRegister(String phone);

    /**
     * Check phone number when reset password
     *
     * @param phone the phone number
     * @return
     */
    public ResponseEntity<?> checkPhoneForgotPassword(String phone);
}
