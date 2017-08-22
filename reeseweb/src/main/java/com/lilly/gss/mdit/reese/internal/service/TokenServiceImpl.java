package com.lilly.gss.mdit.reese.internal.service;

import com.lilly.gss.mdit.reese.internal.model.TokenGeneration;
import com.lilly.gss.mdit.reese.internal.repo.TokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by C232018 on 8/9/2017.
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public TokenServiceImpl() {
        super();
    }

    @Override
    public TokenGeneration findUserByEmail(String email) {
        System.out.println("Inside the findUserByTokenURL");
        return tokenRepository.findByEmail(email);
    }

    @Override
    public void saveTokenURL(TokenGeneration tokenGeneration) {

        tokenRepository.save(tokenGeneration);

    }
}
