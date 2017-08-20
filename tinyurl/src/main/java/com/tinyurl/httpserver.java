package main.java.com.tinyurl;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class httpserver {

    private static int port = 8080;
    private HttpServer server;
    private static database db = null;

    public httpserver(int port ) throws Exception {
        this.port = port;
    }
    public void start(final database dbObj) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("server started at " + port);
            server.createContext("/", new RootHandler());
            server.createContext("/GetUrl", new urlGetHandler());
            server.createContext("/postUrl", new urlPostHandler());
            server.setExecutor(null);
            this.db = dbObj;
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static database getDB() {
        return db;
    }

    public void stop() {
        server.stop(0);
        System.out.println("server stopped");
    }

    public  static String getUrlBody(HttpExchange he) throws IOException {
        Map<String, String> parameters = new HashMap<String, String>();
        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        String[] entry = query.split(":");
        return entry[1];
    }

    public static class RootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + httpserver.port + "</h1>";
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static class urlGetHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            // parse request
            String shorturl = httpserver.getUrlBody(he);
            database db = httpserver.getDB();
            int id = tinyurl.decodeShort(shorturl);
            String response = db.getter(String.valueOf(id), "longurl");
            if (response == null) {
                response = " The URL does not exist";
                he.sendResponseHeaders(404, response.length());
            }
            else {
                he.sendResponseHeaders(200, response.length());
            }
            OutputStream os = he.getResponseBody();
            os.write(response.toString().getBytes());
            os.close();
        }

    }

    public static class urlPostHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            System.out.println("Served by /echoPost handler...");
            // parse request
            String url = httpserver.getUrlBody(he);
            database db = httpserver.getDB();
            int id = tinyurl.decode(url);

            String shorturl = db.getter(String.valueOf(id), "shorturl");
            if (shorturl == null) {
                shorturl = tinyurl.encode(id);
                db.setter(String.valueOf(id), url, shorturl);
            }
            // send response

            String response = "new_url:http://shortme/" + shorturl;
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
