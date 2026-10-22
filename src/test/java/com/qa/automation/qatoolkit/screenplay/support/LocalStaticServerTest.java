package com.qa.automation.qatoolkit.screenplay.support;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalStaticServerTest {

    @BeforeAll
    static void startServer() throws IOException {
        LocalStaticServer.start();
    }

    @AfterAll
    static void stopServer() {
        LocalStaticServer.stop();
    }

    @Test
    void servesStaticIndexPage() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/").openConnection();

        assertEquals(200, connection.getResponseCode());
        String response = new String(connection.getInputStream().readAllBytes());
        assertTrue(response.contains("QA Toolkit Demo Home"));
    }
}
