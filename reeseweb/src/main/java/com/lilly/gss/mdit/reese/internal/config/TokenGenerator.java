package com.lilly.gss.mdit.reese.internal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by C232018 on 8/8/2017.
 */
@Component
public class TokenGenerator {

    @Bean
    public static String randomToken(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt= new StringBuilder();
        Random rnd= new Random();

        while(salt.length() <18) {
            int index= (int)(rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr=salt.toString();
        return  saltStr;
    }

    @Bean
    public static String tempPassword(){
        String GEN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@$&%";
        StringBuilder salt= new StringBuilder();
        Random rnd= new Random();

        while(salt.length() <8) {
            int index= (int)(rnd.nextFloat() * GEN.length());
            salt.append(GEN.charAt(index));
        }
        String saltStr=salt.toString();
        return  saltStr;
    }


}
