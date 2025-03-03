package com.bechuchi.controller;

import com.bechuchi.service.TestClientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestClientController {

    private final TestClientService testClientService;

    public TestClientController(TestClientService testClientService) {
        this.testClientService = testClientService;
    }

    @GetMapping("/send/{macAddress}")
    public String sendTestMessage(@PathVariable String macAddress) {
        testClientService.sendTestMessage(macAddress);
        return "Test message sent from " + macAddress;
    }
}
