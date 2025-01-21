package com.useragent_parser.controller;

import com.useragent_parser.model.UserAgentInfo;
import com.useragent_parser.service.UserAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
@GetMapping("/api/user-agent/parse-header")
public Mono<UserAgentInfo> parseUserAgent(@RequestHeader("User-Agent") String userAgent,
                                          @RequestHeader(value = "Sec-CH-UA-Platform", required = false) String uaPlatform,
                                          @RequestHeader(value = "Sec-CH-UA-Platform-Version", required = false) String uaPlatformVersion,
                                          @RequestHeader(value = "Sec-CH-UA-Arch", required = false) String uaArch,
                                          @RequestHeader(value = "Sec-CH-UA-Mobile", required = false) String uaMobile,
                                          @RequestHeader(value = "Sec-CH-UA-Model", required = false) String uaModel ) {
    return userAgentService.parseUserAgent(userAgent)
            .flatMap(userAgentInfo -> {
                // Update User-Agent information using Client Hints headers
                HttpHeaders headers = new HttpHeaders();
                if (uaPlatform != null) headers.add("Sec-CH-UA-Platform", uaPlatform);
                if (uaPlatformVersion != null) headers.add("Sec-CH-UA-Platform-Version", uaPlatformVersion);
                if (uaArch != null) headers.add("Sec-CH-UA-Arch", uaArch);
                if (uaMobile != null) headers.add("Sec-CH-UA-Mobile", uaMobile);
                if (uaModel != null) headers.add("Sec-CH-UA-Model", uaModel);

                // Apply updateUserAgentInfoWithClientHints to update the information
                return userAgentService.updateUserAgentInfoWithClientHints(userAgentInfo, headers);
            });
}

    @CrossOrigin(origins = "*")
    @GetMapping("/api/user-agent/parse-query")
    public Mono<UserAgentInfo> parseUserAgentFromQuery(@RequestParam String userAgent) {
        return userAgentService.parseUserAgent(userAgent);
    }
}
