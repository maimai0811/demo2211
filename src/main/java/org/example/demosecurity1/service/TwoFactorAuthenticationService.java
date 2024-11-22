package org.example.demosecurity1.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorAuthenticationService {

    private final GoogleAuthenticator googleAuthenticator;

//    public TwoFactorAuthenticationService() {
//        this.googleAuthenticator = new GoogleAuthenticator();
//    }

    public TwoFactorAuthenticationService() {
        GoogleAuthenticatorConfig config = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder()
                .setTimeStepSizeInMillis(30000)
                .setWindowSize(1)
                .build();

        googleAuthenticator = new GoogleAuthenticator(config);
    }

    // Generate a new secret key for the user
    public String generateSecretKey() {
        GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
        return key.getKey();  // This is the secret key
    }

    // Generate a URL for the QR code that the user will scan
    public String generateQRCodeUrl(String username, String secretKey) {
        return "otpauth://totp/" + username + "?secret=" + secretKey + "&issuer=Demo2211";
    }

    // Verify the OTP entered by the user
    public boolean verifyCode(String secretKey, int otp) {
        return googleAuthenticator.authorize(secretKey, otp);
    }

//    // Xác thực OTP chỉ trong chu kỳ hiện tại (không chấp nhận OTP cũ)
//    public boolean verifyCode(String secretKey, int otp) {
//        // Tính toán thời gian hiện tại theo bước thời gian (30 giây)
//        long timeStepSizeInMillis = 30000; // 30 giây
//        long currentTimeStep = System.currentTimeMillis() / timeStepSizeInMillis;
//
//        // Tính toán OTP cho bước thời gian hiện tại
//        int expectedOtp = getOtpForTimeStep(secretKey, currentTimeStep);
//
//        // So sánh OTP người dùng nhập vào với OTP tính được từ hệ thống
//        return otp == expectedOtp;
//    }
//
//    // Phương thức tính toán OTP cho bước thời gian hiện tại (dựa trên bí mật)
//    private int getOtpForTimeStep(String secretKey, long timeStep) {
//        return googleAuthenticator.getTotpPassword(secretKey, timeStep);  // Tính toán OTP cho bước thời gian hiện tại
//    }
}

