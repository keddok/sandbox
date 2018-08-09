package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RMakhmutov
 * @since 09.08.2018
 */
@RestController
public class RestEndpoint {
    @Autowired
    private DemoApplication.TempConverter converter;

    @GetMapping(path = "/version")
    public String version() {
        return converter.fahrenheitToCelcius(60f);
    }
}
