package com.lilly.gss.mdit.reese.internal.service;


import com.lilly.gss.mdit.reese.internal.model.TokenGeneration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;



/**
 * Created by C232018 on 7/31/2017.
 */

@Service
public class EmailService {

    //private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "email-simple";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailContentBuilder mailContentBuilder;







    @Value("${mail.enable}")
    private Boolean enable;


    private void send(MimeMessagePreparator preparator){

        if(enable){
            mailSender.send(preparator);
        }

    }

    public void sendVerificationEmail(TokenGeneration tokenGeneration){

        StringBuilder sb =new StringBuilder();
        sb.append("<HTML>");
        sb.append("<p> Welcome to  <strong>REESE</strong> </p> ");
        sb.append("</HTML>");




        MimeMessagePreparator preparator =new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper message =new MimeMessageHelper(mimeMessage);
                message.setTo(tokenGeneration.getEmail());
                message.setFrom(new InternetAddress("no-reply@lilly.com"));
                message.setSubject("Invitation to Login to Reese Application");
                message.setSentDate(new Date());
               // message.setText("Welcome to Lilly Reese Application");sb.toString()
                String content = mailContentBuilder.build(tokenGeneration);
                message.setText(content,true);
            }
        };
        send(preparator);
    }





    public void sendTempPassword(TokenGeneration tokenGeneration){

        StringBuilder sb =new StringBuilder();
        sb.append("<HTML>");
        sb.append("<p> Welcome to  <strong>REESE</strong> </p> ");
        sb.append("</HTML>");




        MimeMessagePreparator preparator =new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper message =new MimeMessageHelper(mimeMessage);
                message.setTo(tokenGeneration.getEmail());
                message.setFrom(new InternetAddress("no-reply@lilly.com"));
                message.setSubject("Temporary Security password for Reese Application");
                message.setSentDate(new Date());
                // message.setText("Welcome to Lilly Reese Application");sb.toString()
                String content = mailContentBuilder.tempPassGenerator(tokenGeneration);
                message.setText(content,true);
            }
        };
        send(preparator);
    }



}
