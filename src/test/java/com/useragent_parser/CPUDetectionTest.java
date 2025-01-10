package com.useragent_parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.useragent_parser.model.TestCPUCase;
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
public class CPUDetectionTest {
    private static final Logger logger = LoggerFactory.getLogger(CPUDetectionTest.class);

    @Autowired
    private UserAgentParser userAgentParser;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCPUDetection() throws Exception {
        // Uploading JSON
        List<TestCPUCase> testCPUCases = objectMapper.readValue(
                new File("src/test/data/cpu/cpu-all.json"), new TypeReference<List<TestCPUCase>>() {}
        );

        // Going through the test cases
        for (TestCPUCase testCPUCase:testCPUCases) {
            logger.debug("Testing: {}", testCPUCase.getDesc());

            // Call the function for User-Agent

            UserAgentInfo result = userAgentParser.parseUserAgent(testCPUCase.getUa()).block();;

            // Check: architecture
            String expectedCPUArchitecture = testCPUCase.getExpect().getArchitecture();
            assert result != null;
            String actualCPUrArchitecture = result.getCpu() != null ? result.getCpu().getArchitecture() : null;
            assertEquals(expectedCPUArchitecture, actualCPUrArchitecture,
                    String.format("CPU architecture mismatch for UA: %s (Desc: %s). Expected: %s, but got: %s",
                            testCPUCase.getUa(), testCPUCase.getDesc(), expectedCPUArchitecture, actualCPUrArchitecture));

        }
    }
}
