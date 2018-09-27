package com.example.demo;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;


/**
 * @author RMakhmutov
 * @since 09.08.2018
 */
@RestController
public class RestEndpoint {
    @Autowired
    private DemoApplication.TempConverter converter;

    @GetMapping(path = "/siCall")
    public String version() {
        return converter.fahrenheitToCelcius(60f);
    }

    @GetMapping(path = "/springrestCall")
    public String testGet() {
        return "Hello World";
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(path = "/directCall")
    public String callServlet() throws URISyntaxException {
        return restTemplate.getForObject("http://localhost:8888", String.class);
    }
}
