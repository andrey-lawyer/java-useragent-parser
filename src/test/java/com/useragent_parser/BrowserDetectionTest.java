package com.useragent_parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.useragent_parser.model.TestBrowserCase;
import com.useragent_parser.model.UserAgentInfo;
import com.useragent_parser.util.UserAgentParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
public class BrowserDetectionTest {
    private static final Logger logger = LoggerFactory.getLogger(BrowserDetectionTest.class);

    @Autowired
    private UserAgentParser userAgentParser;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testBrowserDetection() throws Exception {
        // Uploading JSON
        List<TestBrowserCase> testBrowserCases = objectMapper.readValue(
                new File("src/test/data/browser/browser-all.json"), new TypeReference<List<TestBrowserCase>>() {}
        );

        // Going through the test cases
        for (TestBrowserCase testBrowserCase : testBrowserCases) {
            logger.debug("Testing: {}", testBrowserCase.getDesc());

            // Call the function for User-Agent

            UserAgentInfo result = userAgentParser.parseUserAgent(testBrowserCase.getUa()).block();;

            // Check: browser name
            String expectedBrowserName = testBrowserCase.getExpect().getName();
            assert result != null;
            String actualBrowserName = result.getBrowser() != null ? result.getBrowser().getName() : null;
            assertEquals(expectedBrowserName, actualBrowserName,
                    String.format("Browser name mismatch for UA: %s (Desc: %s). Expected: %s, but got: %s",
                            testBrowserCase.getUa(), testBrowserCase.getDesc(), expectedBrowserName, actualBrowserName));

            // Check: browser version
            String expectedBrowserVersion = testBrowserCase.getExpect().getVersion();
            String actualBrowserVersion = result.getBrowser() != null ? result.getBrowser().getVersion() : null;
            assertEquals(expectedBrowserVersion, actualBrowserVersion,
                    String.format("Browser version mismatch for UA: %s (Desc: %s). Expected: %s, but got: %s",
                            testBrowserCase.getUa(), testBrowserCase.getDesc(), expectedBrowserVersion, actualBrowserVersion));

            // Check: main browser version
            String expectedBrowserMajor = testBrowserCase.getExpect().getMajor();
            String actualBrowserMajor = result.getBrowser() != null ? result.getBrowser().getMajor() : null;
            assertEquals(expectedBrowserMajor, actualBrowserMajor,
                    String.format("Browser major version mismatch for UA: %s (Desc: %s). Expected: %s, but got: %s",
                            testBrowserCase.getUa(), testBrowserCase.getDesc(), expectedBrowserMajor, actualBrowserMajor));
        }
    }
}
