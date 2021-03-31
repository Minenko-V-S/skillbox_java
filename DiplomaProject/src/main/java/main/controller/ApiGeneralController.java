package main.controller;


import main.api.response.InitResponse;
import main.services.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//todo
// для прочих запросов к API
@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    @Autowired
    private final InitResponse initResponse;
    @Autowired
    private final SettingsService settingsService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
    }

    @GetMapping("/settings")
    public ResponseEntity<?> getSettings() {
        return ResponseEntity.ok(settingsService.getSettings());
    }

    @GetMapping("/init")
    private InitResponse init(){
        return initResponse;
    }
}
