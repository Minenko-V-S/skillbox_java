package main.services;



import main.enums.Settings;
import main.model.GlobalSettings;
import main.model.dto.SettingsDTO;
import main.repositories.GlobalSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
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

    public SettingsDTO saveSettings(SettingsDTO settings) {
        saveOneSetting(Settings.Code.MULTIUSER_MODE, settings.isMultiuserMode());
        saveOneSetting(Settings.Code.POST_PREMODERATION, settings.isPostPremoderation());
        saveOneSetting(Settings.Code.STATISTICS_IS_PUBLIC, settings.isStatisticsIsPublic());

        return getSettings();
    }

    private GlobalSettings saveOneSetting(Settings.Code code, boolean valueToUpdate) {
        Settings.Value value = valueToUpdate ? Settings.Value.YES : Settings.Value.NO;
        final var option = globalSettingsRepository.findByCodeIs(code);

        if (!value.equals(option.getValue())) {
            option.setValue(value);
            globalSettingsRepository.save(option);
        }

        return option;
    }

    public boolean isStatsPublic() {
        return globalSettingsRepository.findByCodeIs(
                Settings.Code.STATISTICS_IS_PUBLIC
        ).getValue().getValue();
    }
}

