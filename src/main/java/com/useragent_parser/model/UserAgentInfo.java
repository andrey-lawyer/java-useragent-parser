package com.useragent_parser.model;

import lombok.Data;

@Data
public class UserAgentInfo {
    private String userAgent;

    private Browser browser;
    private Os os;
    private Device device;
    private Cpu cpu;
    private Engine engine;

    @Data
    public static class Browser {
        private String name;
        private String version;
        private String major;
        private String type;
    }

    @Data
    public static class Os {
        private String name;
        private String version;
    }

    @Data
    public static class Device {
        private String model;
        private String vendor;
        private String type;
    }

    @Data
    public static class Cpu {
        private String architecture;
    }

    @Data
    public static class Engine {
        private String name;
        private String version;
    }
}