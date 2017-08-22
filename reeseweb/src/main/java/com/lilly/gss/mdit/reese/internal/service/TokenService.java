package com.lilly.gss.mdit.reese.internal.service;

import com.lilly.gss.mdit.reese.internal.model.TokenGeneration;

/**
 * Created by C232018 on 8/8/2017.
 */
public interface TokenService {

    public TokenGeneration findUserByEmail(String email);

    public void saveTokenURL(TokenGeneration tokenGeneration);
}
