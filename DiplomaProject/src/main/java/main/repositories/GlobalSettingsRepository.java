package main.repositories;

import main.enums.SettingsGlobal;
import main.model.GlobalSettings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings, Integer> {
        GlobalSettings findByCodeIs(SettingsGlobal.Code code);
        }
