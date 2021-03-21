package main.services;

import main.enums.Settings;
import main.model.dto.SettingsDTO;
import main.repositories.GlobalSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class SettingsService extends Settings {
    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    public SettingsDTO getSettings() {
        SettingsDTO settings = new SettingsDTO();

        globalSettingsRepository.findAll().forEach(setting -> {
            final var MULTIUSER_MODE = globalSettingsRepository.findByCodeIs(Settings.Code.MULTIUSER_MODE);
            final var POST_PREMODERATION = globalSettingsRepository.findByCodeIs(Settings.Code.POST_PREMODERATION);
            final var STATISTICS_IS_PUBLIC = globalSettingsRepository.findByCodeIs(Settings.Code.STATISTICS_IS_PUBLIC);

            settings.setMultiuserMode(MULTIUSER_MODE.getValue().getValue());
            settings.setPostPremoderation(POST_PREMODERATION.getValue().getValue());
            settings.setStatisticsIsPublic(STATISTICS_IS_PUBLIC.getValue().getValue());
        });

        return settings;
    }
}




