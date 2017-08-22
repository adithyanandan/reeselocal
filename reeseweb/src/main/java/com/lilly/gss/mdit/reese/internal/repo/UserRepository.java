package com.lilly.gss.mdit.reese.internal.repo;

import com.lilly.gss.mdit.reese.internal.model.Greeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by C232018 on 8/8/2017.
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<Greeting, Long> {
    Greeting findByEmail(String email);
    Greeting findByToken(String token);
}
