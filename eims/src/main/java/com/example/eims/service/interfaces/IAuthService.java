/*
 * Copyright (C) 2023, FPT University <br>
 * SEP490 - SEP490_G10 <br>
 * EIMS <br>
 * Eggs Incubating Management System <br>
 *
 * Record of change:<br>
 * DATE         Version     Author      DESCRIPTION<br>
 * 02/03/2023   1.0         DuongVV     First Deploy<br>
 */

package com.example.eims.service.interfaces;

import com.example.eims.dto.auth.ChangePasswordDTO;
import com.example.eims.dto.auth.ForgotPasswordDTO;
import com.example.eims.dto.auth.LoginDTO;
import com.example.eims.dto.auth.SignUpDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    /**
     * Sign in .
     *
     * @param loginDTO contains the login phone and password
     * @return
     */
    public ResponseEntity<?> authenticateUser(LoginDTO loginDTO);

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
    public ResponseEntity<?> sendOTP(String phone);

    /**
     * Verify OTP forgot password.
     *
     * @param OTP code to verify phone number
     * @return
     */
    public ResponseEntity<?> verifyOTP(String OTP);

    /**
     * Re-send OTP forgot password.
     *
     * @return
     */
    public ResponseEntity<?> resendOTP(String phone);

    /**
     * Change password after verify OTP.
     *
     * @param forgotPasswordDTO contains the new password, login phone
     * @return
     */
    public ResponseEntity<?> resetPassword(ForgotPasswordDTO forgotPasswordDTO);
}
