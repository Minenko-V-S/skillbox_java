package main.model;



import lombok.Data;
import lombok.EqualsAndHashCode;
import main.enums.Settings;
import javax.persistence.*;

@Entity
@Table(name = "global_settings")
@Data
@EqualsAndHashCode(callSuper = true)
public class GlobalSettings extends AbstractEntity {
    //Системное имя настройки
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Settings.Code code;

    //Название настройки
    @Column(nullable = false)
    private String name;

    //Значение настройки
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Settings.Value value;

    public GlobalSettings() {
    }

    public GlobalSettings(Settings.Code code, String name, Settings.Value value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }

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