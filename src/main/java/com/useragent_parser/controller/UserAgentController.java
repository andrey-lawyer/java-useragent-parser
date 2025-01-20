package com.useragent_parser.controller;

import com.useragent_parser.model.UserAgentInfo;
import com.useragent_parser.service.UserAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class UserAgentController {

    private final UserAgentService userAgentService;

    @Autowired
    public UserAgentController(UserAgentService userAgentService) {
        this.userAgentService = userAgentService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/api/user-agent/parse")
    public Mono<UserAgentInfo> parseUserAgent(@RequestHeader("User-Agent") String userAgent) {
        return userAgentService.parseUserAgent(userAgent);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/api/user-agent/parse")
    public Mono<UserAgentInfo> parseUserAgentFromBody(@RequestBody String userAgent) {
        return userAgentService.parseUserAgent(userAgent);
    }
}
