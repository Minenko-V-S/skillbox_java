package main.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import main.utils.JsonViews;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.Instant;


@Entity
@Table(name = "captcha_codes")
@Data
@NoArgsConstructor(force = true) @EqualsAndHashCode(callSuper = true)
@ToString(of = {"code", "secretCode", "imageBase64"})
public class CaptchaCodes extends AbstractEntity {
    /** Дата и время генерации кода капчи */
    @NotNull @Column(nullable = false)
    private Instant time;

    /** Код, отображаемый на картинкке капчи */
    @Column(nullable = false)
    private String code;

    /** Код, передаваемый в параметре */
    @Column(name = "secret_code", nullable = false)
    private String secretCode;

    private String imageBase64;


}