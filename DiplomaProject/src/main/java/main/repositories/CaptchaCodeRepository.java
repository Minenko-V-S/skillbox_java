package main.repositories;

import main.model.CaptchaCodes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.Instant;

@Repository
@Transactional
public interface CaptchaCodeRepository extends CrudRepository<CaptchaCodes, Long> {
    @Modifying
    void deleteByTimeBefore(Instant time);

    CaptchaCodes findBySecretCode(String secretCode);
}