package com.qa.automation.qatoolkit.support;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class LocalStaticServer {
    private static final int PORT = 8080;
    private static final String ROOT = Paths.get("src", "test", "resources", "local-app").toAbsolutePath().toString();

    private static HttpServer server;

    private LocalStaticServer() {
    }

    public static void start() throws IOException {
        if (server != null) {
            return;
        }

        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", LocalStaticServer::serveFile);
        server.start();
    }

    public static void stop() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }

    private static void serveFile(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();
        String fileName = requestPath.equals("/") ? "index.html" : requestPath.substring(1);

        Path root = Paths.get(ROOT).toAbsolutePath().normalize();
        Path requested = root.resolve(fileName).normalize();

        if (!requested.startsWith(root)) {
            exchange.sendResponseHeaders(403, -1);
            return;
        }

        if (!Files.exists(requested) || Files.isDirectory(requested)) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        byte[] bytes = Files.readAllBytes(requested);
        exchange.getResponseHeaders().set("Content-Type", contentType(fileName));
        exchange.sendResponseHeaders(200, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static String contentType(String fileName) {
        if (fileName.endsWith(".html")) {
            return "text/html; charset=UTF-8";
        }
        if (fileName.endsWith(".css")) {
            return "text/css; charset=UTF-8";
        }
        if (fileName.endsWith(".js")) {
            return "application/javascript; charset=UTF-8";
        }
        return "application/octet-stream";
    }
}
