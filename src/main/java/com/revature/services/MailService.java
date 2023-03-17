package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; //below passwordEncoder is ABSTRACT, need THIS to instantiate
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class MailService {

    private PasswordEncoder encoder;

    @Autowired
    public void sendMail(String recipient, CharSequence id){
        MailSender ms = null;
        encoder = new BCryptPasswordEncoder();
        String sender = "donotreply@goodbank.com";

        String uid = encoder.encode(id);

        String body = "You recently requested a password reset. Please click on this link (or copy and paste into your web browser) to reset your password. \n https://url-to-client-app/" + uid;

        SimpleMailMessage simp = new SimpleMailMessage();
        simp.setTo(recipient);
        simp.setFrom(sender);
        simp.setSubject("Your GoodBank password reset");
        simp.setText(body);

        ms.send(simp);
    }
}
