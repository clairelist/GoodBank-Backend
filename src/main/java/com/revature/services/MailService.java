package com.revature.services;

import java.util.Properties;

public class MailService<JavaMailSender> { //NOTE: I SHOULD _NOT_ BE A GENERIC, I NEED TO BE SOMETHING ELSE.
    @Bean
    public JavaMailSender getJavaMailSender() { //TODO:: FIX ME UP!
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("my.gmail@gmail.com");
        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
