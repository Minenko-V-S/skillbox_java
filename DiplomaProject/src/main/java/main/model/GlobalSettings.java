package main.model;


import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import main.enums.Settings;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;

@Entity
@Table(name = "global_settings")
@Data
@NoArgsConstructor(force = true) @EqualsAndHashCode(callSuper = true)
public class GlobalSettings extends AbstractEntity {
    //Системное имя настройки
    @NaturalId
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Settings.Code code;

    //Название настройки
    @Column(nullable = false)
    private String name;

    //Значение настройки
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Settings.Value value;

    public Settings.Code getCode() {
        return code;
    }

    public void setCode(Settings.Code code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Settings.Value getValue() {
        return value;
    }

    public void setValue(Settings.Value value) {
        this.value = value;
    }
}