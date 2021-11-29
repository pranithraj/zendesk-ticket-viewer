package com.zendesk.app.zendeskticketviewer;

import com.google.gson.Gson;
import com.zendesk.app.zendeskticketviewer.model.TicketViewerResponse;
import com.zendesk.app.zendeskticketviewer.model.Tickets;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@SpringBootApplication
@RestController
public class ZendeskTicketViewerApplication {
    final String pagePart = "?page=";
    final String paginationPart = "&per_page=" + getPropValues("paginationNumber");

    public static void main(String[] args) {
        SpringApplication.run(ZendeskTicketViewerApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/tickets/view")
    public String viewTickets(@RequestParam(value = "page", required = false, defaultValue = "1") String page) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials(Objects.requireNonNull(getPropValues("username")), getPropValues("password"));
        provider.setCredentials(AuthScope.ANY, credentials);

        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
        TicketViewerResponse viewerResponse = null;
        try {
            viewerResponse =
                    executeGetRequest(client, getPropValues("baseUrl") + pagePart + page + paginationPart);
        } catch (IOException e) {
            System.out.println("Unable to access the configuration file!");
        }
        return Tickets.convertToHTML(viewerResponse);
    }

    private TicketViewerResponse executeGetRequest(CloseableHttpClient client, String s) throws IOException {
        HttpResponse response = client.execute(
                new HttpGet(s));
        String json_string = EntityUtils.toString(response.getEntity());

        // JSON to GSON conversion
        Gson gson = new Gson();

        // 1. JSON file to Java object
        return gson.fromJson(json_string, TicketViewerResponse.class);
    }

    private String getPropValues(final String field) {
        final String propFileName = "config.properties";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            final Properties prop = new Properties();
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            return prop.getProperty(field);
        } catch (Exception e) {
            System.out.println("Unable to access the configuration file!");
        }
        return null;
    }
}
