package com.useragent_parser.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "user_agent_info")
public class UserAgentInfo {
    @Id
    private String userAgent;

    @Field("browser")
    private Browser browser;

    @Field("os")
    private Os os;

    @Field("device")
    private Device device;

    @Field("cpu")
    private Cpu cpu;

    @Field("engine")
    private Engine engine;


    @Data
    public static class Browser {
        @Field("name")
        private String name;

        @Field("version")
        private String version;

        @Field("major")
        private String major;

        @Field("type")
        private String type;
    }

    @Data
    public static class Os {
        @Field("name")
        private String name;

        @Field("version")
        private String version;

    }

    @Data
    public static class Device {
        @Field("model")
        private String model;

        @Field("vendor")
        private String vendor;

        @Field("type")
        private String type;
    }

    @Data
    public static class Cpu {
        @Field("architecture")
        private String architecture;
    }

    @Data
    public static class Engine {
        @Field("name")
        private String name;

        @Field("version")
        private String version;
    }
}