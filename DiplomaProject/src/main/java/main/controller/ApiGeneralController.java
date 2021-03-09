package main.controller;

import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//todo
// для прочих запросов к API
@RestController
public class ApiGeneralController {

    InitResponse initResponse;

    public ApiGeneralController(InitResponse initResponse) {
        this.initResponse = initResponse;
    }

    @GetMapping("/api/settings")
    private SettingsResponse settings(){
        return new SettingsResponse();
    }

    @GetMapping("/api/init")
    private InitResponse init(){
        return initResponse;
    }
}
