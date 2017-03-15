package com.comp680.sunlink.mail;

import android.util.Log;

public class MailSender {

    public MailSender(String signUpEmailText) {
        sendValidationEmail(signUpEmailText);
        Log.w("mail",signUpEmailText);
    }

    private void sendValidationEmail(String signUpEmailText) {
        try {
            GMailSender sender = new GMailSender("csun.sunlink@gmail.com ", "159258357");
            sender.sendMail("This is Subject",
                    "This is Body",
                    "csun.sunlink@gmail.com",
                    signUpEmailText);
            Log.w("mail",signUpEmailText);
        } catch (Exception e) {
            Log.w("SendMail", e.getMessage(), e);
        }

    }
}