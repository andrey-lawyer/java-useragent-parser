package com.useragent_parser.util;

import com.useragent_parser.model.UserAgentInfo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class UserAgentParser {

    private static final Map<String, Pattern> cachedPatterns = new ConcurrentHashMap<>();

    private static Pattern getPattern(String regex) {
        return cachedPatterns.computeIfAbsent(regex, r -> Pattern.compile(r, Pattern.CASE_INSENSITIVE));
    }

    public Mono<UserAgentInfo> parseUserAgent(String userAgent) {

        UserAgentInfo info = new UserAgentInfo();
        info.setUserAgent(userAgent);

        return Flux.concat(
                        parseBrowser(userAgent).doOnNext(info::setBrowser),
                        parseCpu(userAgent).doOnNext(info::setCpu),
                        parseEngine(userAgent).doOnNext(info::setEngine),
                        parseOs(userAgent).doOnNext(info::setOs),
                        parseDevice(userAgent).doOnNext(info::setDevice)
                )
                .then(Mono.just(info)); // Collect the results into one UserAgentInfo object
    }

    private Mono<UserAgentInfo.Browser> parseBrowser(String userAgent) {
        return Flux.fromArray(Patterns.BROWSER_PATTERNS)
                .mapNotNull(pattern -> {
                    Pattern compiledPattern = getPattern(pattern[0]);
                    Matcher matcher = compiledPattern.matcher(userAgent);

                    if (matcher.find()) {
                        UserAgentInfo.Browser browser = new UserAgentInfo.Browser();
                        browser.setName(getBrowserName(pattern[1], pattern[2], matcher, pattern[5]));
                        browser.setVersion(getBrowserVersion(pattern[3], matcher, pattern[5]));
                        browser.setMajor(getMajorVersion(browser.getVersion()));
                        browser.setType(pattern[4]);
                        return browser;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .next(); // We take the first result we find
    }

    private Mono<UserAgentInfo.Cpu> parseCpu(String userAgent) {
        return Flux.fromArray(Patterns.CPU_PATTERNS)
                .mapNotNull(pattern -> {
                    Pattern compiledPattern = getPattern(pattern[0]);
                    Matcher matcher = compiledPattern.matcher(userAgent);

                    if (matcher.find()) {
                        UserAgentInfo.Cpu cpu = new UserAgentInfo.Cpu();
                        cpu.setArchitecture(getCpuArchitecture(pattern[1], matcher, pattern[2]));
                        return cpu;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .next();
    }

    private Mono<UserAgentInfo.Engine> parseEngine(String userAgent) {
        return Flux.fromArray(Patterns.ENGINE_PATTERNS)
                .mapNotNull(pattern -> {
                    Pattern compiledPattern = getPattern(pattern[0]);
                    Matcher matcher = compiledPattern.matcher(userAgent);

                    if (matcher.find()) {
                        UserAgentInfo.Engine engine = new UserAgentInfo.Engine();
                        engine.setName(getEngineName(pattern[1], pattern[2], matcher));
                        engine.setVersion(getEngineVersion(pattern[3], matcher));
                        return engine;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .next();
    }

    private Mono<UserAgentInfo.Os> parseOs(String userAgent) {
        Map<String, Object> windowsVersionMap = new HashMap<>();
        windowsVersionMap.put("ME", "4.90");
        windowsVersionMap.put("NT 3.11", "NT3.51");
        windowsVersionMap.put("NT 4.0", "NT4.0");
        windowsVersionMap.put("2000", "NT 5.0");
        windowsVersionMap.put("XP", List.of("NT 5.1", "NT 5.2"));
        windowsVersionMap.put("Vista", "NT 6.0");
        windowsVersionMap.put("7", "NT 6.1");
        windowsVersionMap.put("8", "NT 6.2");
        windowsVersionMap.put("8.1", "NT 6.3");
        windowsVersionMap.put("10", List.of("NT 6.4", "NT 10.0"));
        windowsVersionMap.put("RT", "ARM");
        return Flux.fromArray(Patterns.OS_PATTERNS)
                .mapNotNull(pattern -> {
                    Pattern compiledPattern = getPattern(pattern[0]);
                    Matcher matcher = compiledPattern.matcher(userAgent);

                    if (matcher.find()) {
                        UserAgentInfo.Os os = new UserAgentInfo.Os();
                        os.setName(getOsName(pattern[1], pattern[2], matcher));
                        os.setVersion(getOsVersion(pattern[3], matcher, pattern[4], windowsVersionMap));
                        return os;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .next();
    }

    private Mono<UserAgentInfo.Device> parseDevice(String userAgent) {
        Map<String, Object> vendorMapOPPO = Map.of(
                "OnePlus", List.of("304", "403", "203"),
                "*", "OPPO"
        );

        Map<String, Object> typeMapITEL = Map.of(
                "tablet", List.of("p10001l", "w7001"),
                "*", "mobile"
        );
        return Flux.fromArray(Patterns.DEVICE_PATTERNS)
                .mapNotNull(pattern -> {
                    Pattern compiledPattern = getPattern(pattern[0]);
                    Matcher matcher = compiledPattern.matcher(userAgent);

                    if (matcher.find()) {
                        UserAgentInfo.Device device = new UserAgentInfo.Device();

                        // get the device model
                        String deviceModel = getDeviceModel(pattern[1], pattern[2], matcher, pattern[7]);
                        device.setModel(deviceModel);

                        device.setVendor(getDeviceVendor(pattern[3], pattern[4], matcher, pattern[7], deviceModel, vendorMapOPPO));
                        device.setType(getDeviceType(pattern[5], pattern[6], matcher, pattern[7], deviceModel, typeMapITEL));

                        return device;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .next();
    }

     private String getBrowserName(String defaultBrowserName, String browserNameGroupIndex, Matcher browserMatcher, String modifier) {
        String name;

        // Determine browser name
        if (defaultBrowserName != null) {
            name = defaultBrowserName; // If the name is specified explicitly
        } else {
            name = browserMatcher.group(Integer.parseInt(browserNameGroupIndex)); // If the name is retrieved from a group
        }

        // Apply modifier if necessary
        if (modifier != null &&
                !Objects.equals(modifier, "replaceAll") &&
                !Objects.equals(modifier, "versionDefault") &&
                !Objects.equals(modifier, "cleanedVersion")) {
            name = postfix(name, modifier);
        }

        return name;
    }


    private String getBrowserVersion(String versionGroupIndex, Matcher browserMatcher, String modifier) {
        String version = null;
        if (versionGroupIndex != null) {
            version = browserMatcher.group(Integer.parseInt(versionGroupIndex));
            if (Objects.equals(modifier, "replaceAll") && version != null) {
                version = version.replaceAll("_", ".");
            }
            if (Objects.equals(modifier, "cleanedVersion") && version != null){
                version = version.replaceAll("[^\\d.]+.", ""); ;
            }
            if (Objects.equals(version, "")) {
                version = null;
            }
        }
        if (Objects.equals(modifier, "versionDefault")){
            version = "1";
        }
        return version;
    }

    private String getCpuArchitecture(String defaultCpuArchitecture, Matcher cpuMatcher, String replaceWord) {
        String architecture;

        if (defaultCpuArchitecture != null) {
            architecture = defaultCpuArchitecture;
        } else {
            architecture = lowerize(cpuMatcher.group(1));

            if (replaceWord != null) {
                architecture = architecture.replaceAll(replaceWord, "");
            }
        }

        return architecture;
    }

    private  String getEngineName(String defaultEngineName, String engineNameGroupIndex, Matcher engineMatcher) {
        String name = null;

        if (defaultEngineName != null) {
            name = defaultEngineName;
        } else if (engineNameGroupIndex != null) {
            name = engineMatcher.group(Integer.parseInt(engineNameGroupIndex));
        }

        return name;
    }

    private String getEngineVersion(String versionGroupIndex, Matcher engineMatcher) {
        String version = null;

        if (versionGroupIndex != null) {
            version = engineMatcher.group(Integer.parseInt(versionGroupIndex));
        }

        return version;
    }

    private String getOsName(String defaultOSName, String osNameGroupIndex, Matcher osMatcher) {
        String name;

        if (defaultOSName != null) {
            name = defaultOSName;
        } else {
            name = osMatcher.group(Integer.parseInt(osNameGroupIndex));
        }

        return name;
    }

    private String getOsVersion(String versionGroupIndex, Matcher osMatcher, String modifier, Map<String, Object> windowsVersionMap) {
        String version = null;

        if (versionGroupIndex != null) {
            version = osMatcher.group(Integer.parseInt(versionGroupIndex));

            if (Objects.equals(modifier, "windowsVersionMap")) {
                version = strMapper(version, windowsVersionMap);
            }
            if (Objects.equals(modifier, "replaceAll") && version != null) {
                version = version.replaceAll("_", ".");
            }
            if (Objects.equals(version, "")) {
                version = null;
            }
        }

        return version;
    }

    private String getDeviceModel(String defaultModel, String modelGroupIndex, Matcher deviceMatcher, String modifier) {
        String model = null;

        if (defaultModel != null) {
            model = defaultModel;
        } else if (modelGroupIndex != null) {
            model = deviceMatcher.group(Integer.parseInt(modelGroupIndex));

            if (Objects.equals(modifier, "replaceAll")) {
                model = model.replaceAll("_", " ");
            }
            if (Objects.equals(modifier, "Fire Phone ") || Objects.equals(modifier, "Chromecast ") || Objects.equals(modifier, "SmartTV")) {
                model = prefix(model, modifier);
            }
            if (Objects.equals(modifier, "trim")) {
                model = trim(model, null);
            }
            if (Objects.equals(model, "")) {
                model = null;
            }
        }

        return model;
    }

    private  String getDeviceVendor(String defaultVendor, String vendorGroupIndex, Matcher deviceMatcher, String modifier, String model, Map<String, Object> vendorMapOPPO) {
        String vendor = null;

        if (defaultVendor != null) {
            vendor = defaultVendor;
        } else if (vendorGroupIndex != null) {
            vendor = deviceMatcher.group(Integer.parseInt(vendorGroupIndex));

            if (Objects.equals(modifier, "vendorMapOPPO")) {
                vendor = strMapper(model, vendorMapOPPO);
            }
            if (Objects.equals(modifier, "typeMapITEL")) {
                vendor = lowerize(vendor);
            }
            if (Objects.equals(modifier, "trim")) {
                vendor = trim(vendor, null);
            }
        }

        return vendor;
    }

    private String getDeviceType(String defaultType, String typeGroupIndex, Matcher deviceMatcher, String modifier, String model, Map<String, Object> typeMapITEL) {
        String type = null;

        if (defaultType != null) {
            type = defaultType;
        } else if (typeGroupIndex != null) {
            type = deviceMatcher.group(Integer.parseInt(typeGroupIndex));
        } else if (Objects.equals(modifier, "typeMapITEL")) {
            type = strMapper(model, typeMapITEL);
        }

        return type;
    }


    // Function to get the main version
    private  String getMajorVersion(String version) {
        if (version != null && !version.isEmpty()) {
            // Remove all characters that are not numbers and dots
            String strippedVersion = version.replaceAll("[^\\d.]", "");
            // Split by a point and return the first part
            String[] parts = strippedVersion.split("\\.");
            return parts.length > 0 ? parts[0] : "1"; // Bringing back the first part of the version
        }
        return null;
    }

    private String strMapper(String str, Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof List<?> valueList) {
                for (Object item : valueList) {
                    if (item instanceof String && has((String) item, str)) {
                        return "?".equals(key) ? null : key; // Если элемент списка является частью модели, возвращаем ключ
                    }
                }
            } else if (value instanceof String && has((String) value, str)) {
                return "?".equals(key) ? null : key; // Если строка элемента является частью модели, возвращаем ключ
            }
        }

        return map.containsKey("*") ? (String) map.get("*") : str;
    }

    private  boolean has(String needle, String haystack) {
        if (haystack == null || needle == null) {
            return false;
        }
        // В этой проверке ищем, является ли needle (элемент из списка) частью haystack (строки модели)
        return lowerize(haystack).contains(lowerize(needle));
    }


    private String lowerize(String str) {
        return (str != null && !str.isEmpty()) ? str.toLowerCase() : str;
    }

    private  String trim(String str, Object len) {
        if (str != null && !str.isEmpty()) {
            // Removing the initial spaces
            str = str.replaceAll("^\\s+", "");

            // If len is undefined or equal to null, return the string after removing spaces
            if (len == null || len.toString().equals("undefined")) {
                return str;
            } else {
                // If len is set, cut the string to the length 500
                return str.length() > 500? str.substring(0, 500) : str;
            }
        }
        return "";
    }

    private  String prefix(String str, String pre) {
        if (str != null && pre!=null) {
           return pre + str;
        }
        return "";
    }

    private  String postfix(String str, String post) {
        if (str != null && post!=null) {
            return  str + post;
        }
        return "";
    }
}

