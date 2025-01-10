package com.useragent_parser.repositoty;

import com.useragent_parser.model.UserAgentInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserAgentRepository extends ReactiveMongoRepository<UserAgentInfo, String> {
    Mono<UserAgentInfo> findByUserAgent(String userAgent);

}
