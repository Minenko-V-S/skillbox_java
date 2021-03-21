package main.repositories;

import main.enums.Settings;


import main.model.GlobalSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Long> {

        GlobalSettings findByCodeIs(Settings.Code code);
        }
