package com.useragent_parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.useragent_parser.model.TestOSCase;
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
public class OSDetectionTest {
    private static final Logger logger = LoggerFactory.getLogger(OSDetectionTest.class);

    @Autowired
    private UserAgentParser userAgentParser;

    @Autowired
    private ObjectMapper objectMapper;

@Test
void testOSDetectionForAllFiles() throws Exception {
    // Path to the folder with JSON files
    File folder = new File("src/test/data/os/");
    File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

    if (files == null || files.length == 0) {
        throw new IllegalArgumentException("No JSON files found in the directory: " + folder.getAbsolutePath());
    }

    // Going through each JSON file
    for (File file : files) {
        logger.info("Processing file: {}", file.getName());

        // Load test cases from the current file
        List<TestOSCase> testOSCases = objectMapper.readValue(
                file, new TypeReference<List<TestOSCase>>() {}
        );

        // Going through each test case
        for (TestOSCase testOSCase : testOSCases) {
            logger.debug("Testing: {} (File: {})", testOSCase.getDesc(), file.getName());

            // Call the function to process User-Agent
            UserAgentInfo result = userAgentParser.parseUserAgent(testOSCase.getUa()).block();;

            // Check: OS name
            String expectedOSName = testOSCase.getExpect().getName();
            assert result != null;
            String actualOSName = result.getOs() != null ? result.getOs().getName() : null;
            assertEquals(expectedOSName, actualOSName,
                    String.format("OS name mismatch in file %s for UA: %s (Desc: %s). Expected: %s, but got: %s",
                            file.getName(), testOSCase.getUa(), testOSCase.getDesc(),
                            expectedOSName, actualOSName));

            // Check: OS version
            String expectedOSVersion = testOSCase.getExpect().getVersion();
            String actualOSVersion = result.getOs() != null ? result.getOs().getVersion() : null;
            assertEquals(expectedOSVersion, actualOSVersion,
                    String.format("OS version mismatch in file %s for UA: %s (Desc: %s). Expected: %s, but got: %s",
                            file.getName(), testOSCase.getUa(), testOSCase.getDesc(),
                            expectedOSVersion, actualOSVersion));
        }
    }
}
}
