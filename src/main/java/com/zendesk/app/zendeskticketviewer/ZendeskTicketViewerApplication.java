package com.zendesk.app.zendeskticketviewer;

import com.google.gson.Gson;
import com.zendesk.app.zendeskticketviewer.model.Groups;
import com.zendesk.app.zendeskticketviewer.model.TicketViewerResponse;
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
import sun.net.www.http.HttpClient;

import java.io.IOException;

@SpringBootApplication
@RestController
public class ZendeskTicketViewerApplication {


    public static void main(String[] args) {
        SpringApplication.run(ZendeskTicketViewerApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/tickets/view")
    public String viewTickets() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials("pranithraj77@gmail.com", "Pranith@77");
        provider.setCredentials(AuthScope.ANY, credentials);

        CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
        HttpResponse response = null;
        String json_string = "";
        TicketViewerResponse ticketViewerResponse = null;
        try {
            response = client.execute(
                    new HttpGet("https://zccpranith.zendesk.com/api/v2/groups.json"));
            json_string = EntityUtils.toString(response.getEntity());

            // JSON to GSON conversion
            Gson gson = new Gson();

            // 1. JSON file to Java object
            ticketViewerResponse = gson.fromJson(json_string, TicketViewerResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int statusCode = response.getStatusLine()
                .getStatusCode();

        if (ticketViewerResponse != null) {
            for (Groups current: ticketViewerResponse.getGroups()) {
                System.out.println(current.getDefaultField());
                System.out.println(current.getDeleted());
                System.out.println(current.getUpdated_at());
                System.out.println(current.getName());
                System.out.println(current.getDescription());
                System.out.println(current.getCreated_at());
                System.out.println(current.getUrl());
            }
        }

        return Groups.convertToHTML(ticketViewerResponse.getGroups());
    }
}
