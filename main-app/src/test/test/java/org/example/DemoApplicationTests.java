package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Add explicit reference to your main application class
@SpringBootTest(classes = com.example.DemoApplication.class)
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }
}