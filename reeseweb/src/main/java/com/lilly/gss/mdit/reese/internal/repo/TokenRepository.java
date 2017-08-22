package com.lilly.gss.mdit.reese.internal.repo;
import com.lilly.gss.mdit.reese.internal.model.TokenGeneration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by C232018 on 8/8/2017.
 */
@Repository("tokenRepository")
public interface TokenRepository extends JpaRepository<TokenGeneration, Long> {

    TokenGeneration findByEmail(String email);
}
