package com.useragent_parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.useragent_parser.model.TestDeviceCase;
import com.useragent_parser.model.UserAgentInfo;
import com.useragent_parser.util.UserAgentParser;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DeviceDetectionTest {
    private static final Logger logger = LoggerFactory.getLogger(DeviceDetectionTest.class);

    @Autowired
    private UserAgentParser userAgentParser;

    @Autowired
    private ObjectMapper objectMapper;

@Test
void testDeviceDetectionForAllFiles() throws Exception {
    // Path to the folder with JSON files
    File folder = new File("src/test/data/device/");
    File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

    if (files == null || files.length == 0) {
        throw new IllegalArgumentException("No JSON files found in the directory: " + folder.getAbsolutePath());
    }

    // Going through each JSON file
    for (File file : files) {
        logger.info("Processing file: {}", file.getName());

        // Going through each test case
        List<TestDeviceCase> testDeviceCases = objectMapper.readValue(
                file, new TypeReference<List<TestDeviceCase>>() {}
        );

        // Проходим по каждому тестовому кейсу
        for (TestDeviceCase testDeviceCase : testDeviceCases) {
            logger.debug("Testing: {} (File: {})", testDeviceCase.getDesc(), file.getName());

            // Call the function to process User-Agent
            UserAgentInfo result = userAgentParser.parseUserAgent(testDeviceCase.getUa()).block();;

            // Check: device vendor
            String expectedDeviceVendor = testDeviceCase.getExpect().getVendor();
            assert result != null;
            String actualDeviceVendor = result.getDevice() != null ? result.getDevice().getVendor() : null;
            assertEquals(expectedDeviceVendor, actualDeviceVendor,
                    String.format("Device vendor mismatch in file %s for UA: %s (Desc: %s). Expected: %s, but got: %s",
                            file.getName(), testDeviceCase.getUa(), testDeviceCase.getDesc(),
                            expectedDeviceVendor, actualDeviceVendor));

            // Check: device model
            String expectedDeviceModel = testDeviceCase.getExpect().getModel();
            String actualDeviceModel = result.getDevice() != null ? result.getDevice().getModel() : null;
            assertEquals(expectedDeviceModel, actualDeviceModel,
                    String.format("Device model mismatch in file %s for UA: %s (Desc: %s). Expected: %s, but got: %s",
                            file.getName(), testDeviceCase.getUa(), testDeviceCase.getDesc(),
                            expectedDeviceModel, actualDeviceModel));

            // Check: device type
            String expectedDeviceType = testDeviceCase.getExpect().getType();
            String actualDeviceType = result.getDevice() != null ? result.getDevice().getType() : null;
            assertEquals(expectedDeviceType, actualDeviceType,
                    String.format("Device type mismatch in file %s for UA: %s (Desc: %s). Expected: %s, but got: %s",
                            file.getName(), testDeviceCase.getUa(), testDeviceCase.getDesc(),
                            expectedDeviceType, actualDeviceType));
        }
    }
}
}
