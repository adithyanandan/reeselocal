package com.lilly.gss.mdit.reese.internal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


/**
 * Created by C232018 on 7/31/2017.
 */
@Configuration
public class MailConfig {


    @Value("${spring.mail.host}")
    private String host;

    @Value("${cluwe.email.sender}")
    private String sender;


    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl mailSender =new JavaMailSenderImpl();
       mailSender.setHost(host);
        mailSender.setUsername(sender);
        return mailSender;
    }







}
