package main.repositories;

import main.enums.Settings;
import main.model.GlobalSettings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings, Integer> {
        GlobalSettings findByCodeIs(Settings.Code code);
        }
