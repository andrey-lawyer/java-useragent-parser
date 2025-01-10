package com.useragent_parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.useragent_parser.model.TestEngineCase;
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
public class EngineDetectionTest {
    private static final Logger logger = LoggerFactory.getLogger(EngineDetectionTest.class);

    @Autowired
    private UserAgentParser userAgentParser;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testEngineDetection() throws Exception {
        // Uploading JSON
        List<TestEngineCase> testEngineCases = objectMapper.readValue(
                new File("src/test/data/engine/engine-all.json"), new TypeReference<List<TestEngineCase>>() {}
        );

        // Going through the test cases
        for (TestEngineCase testEngineCase : testEngineCases) {
            logger.debug("Testing: {}", testEngineCase.getDesc());

            // Call the function for User-Agent
            UserAgentInfo result = userAgentParser.parseUserAgent(testEngineCase.getUa()).block();;

            // Check: engine name
            String expectedEngineName = testEngineCase.getExpect().getName();
            assert result != null;
            String actualEngineName = result.getEngine() != null ? result.getEngine().getName() : null;
            assertEquals(expectedEngineName, actualEngineName,
                    String.format("Engine name mismatch for UA: %s (Desc: %s). Expected: %s, but got: %s",
                            testEngineCase.getUa(), testEngineCase.getDesc(), expectedEngineName, actualEngineName));

            // Check: engine version
            String expectedEngineVersion = testEngineCase.getExpect().getVersion();
            String actualEngineVersion = result.getEngine() != null ? result.getEngine().getVersion() : null;
            assertEquals(expectedEngineVersion, actualEngineVersion,
                    String.format("Engine version mismatch for UA: %s (Desc: %s). Expected: %s, but got: %s",
                            testEngineCase.getUa(), testEngineCase.getDesc(), expectedEngineVersion, actualEngineVersion));

        }
    }
}
