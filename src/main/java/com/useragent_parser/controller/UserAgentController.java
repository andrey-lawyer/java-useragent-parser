package com.useragent_parser.controller;

import com.useragent_parser.model.UserAgentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import com.useragent_parser.service.UserAgentService;

@RestController
public class UserAgentController {

    private final UserAgentService userAgentService;

    @Autowired
    public UserAgentController(UserAgentService userAgentService) {
        this.userAgentService = userAgentService;
    }

    // Endpoint to parse user agent from the "User-Agent" header
    @CrossOrigin(origins = "*")
    @GetMapping("/api/user-agent/parse")
    public Mono<UserAgentInfo> parseUserAgent(@RequestHeader("User-Agent") String userAgent) {
        // Cache the result for a GET request
        return userAgentService.parseUserAgent(userAgent, true);
    }

    // New endpoint to parse user agent from request body without caching
    @CrossOrigin(origins = "*")
    @PostMapping("/api/user-agent/parse")
    public Mono<UserAgentInfo> parseUserAgentFromBody(@RequestBody String userAgent) {
        // Do not cache the result for POST request
        return userAgentService.parseUserAgent(userAgent, false);
    }
}
