package main.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import main.enums.SettingsGlobal;

import javax.persistence.*;



@Entity
@Table(name = "global_settings")
@Data
@NoArgsConstructor(force = true) @EqualsAndHashCode(callSuper = true)
public class GlobalSettings extends AbstractEntity {
    

    /** Системное имя настройки */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "code",nullable = false)
    private SettingsGlobal.Code code;

    /** Название настройки */

    @Column(nullable = false)
    private String name;

    /** Значение настройки */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "value",nullable = false)
    private SettingsGlobal.Value value;
}