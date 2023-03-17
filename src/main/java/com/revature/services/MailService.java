package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    public void sendMail(String recipient, int id){
        MailSender ms = null;
        String sender = "donotreply@goodbank.com";
        String body = "You recently requested a password reset. Please click on this link (or copy and paste into your web browser) to reset your password. \n https://url-to-client-app/" + id;

        //TODO: HASH THE ABOVE USERID!!!!

        SimpleMailMessage simp = new SimpleMailMessage();
        simp.setTo(recipient);
        simp.setFrom(sender);
        simp.setSubject("Your GoodBank password reset");
        simp.setText(body);

        ms.send(simp);
    }
}
