package com.lilly.gss.mdit.reese.internal.service;

import com.lilly.gss.mdit.reese.internal.model.Greeting;

/**
 * Created by C232018 on 8/8/2017.
 */


public interface UserService {
    public Greeting findUserByEmail(String email);
    public Greeting findUserByToken(String Token);
    public void saveUser(Greeting greeting);
}
