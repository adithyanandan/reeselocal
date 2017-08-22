package com.lilly.gss.mdit.reese.internal.service;

import com.lilly.gss.mdit.reese.internal.config.TokenGenerator;
import com.lilly.gss.mdit.reese.internal.model.Greeting;
import com.lilly.gss.mdit.reese.internal.repo.TokenRepository;
import com.lilly.gss.mdit.reese.internal.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;

/**
 * Created by C232018 on 8/8/2017.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Greeting findUserByEmail(String email) {
        System.out.println("Inside the findUserByEmail");
        return userRepository.findByEmail(email);
    }

    @Override
    public Greeting findUserByToken(String token)  {

            System.out.println("Inside the findUserByToken");
             return userRepository.findByToken(token);
    }

    @Override
    public void saveUser(Greeting greeting) {
        userRepository.save(greeting);

    }



}
