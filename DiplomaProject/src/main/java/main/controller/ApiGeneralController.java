package main.controller;

import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import main.services.SettingsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//todo
// для прочих запросов к API
@RestController
public class ApiGeneralController {

    private final InitResponse initResponse;
    private final SettingsService settingsService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
    }

    @GetMapping("/api/settings")
    private ResponseEntity<SettingsResponse> settings(){
        return new ResponseEntity<>(settingsService.getGlobalSettings(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/init")
    private InitResponse init(){
        return initResponse;
    }
}
