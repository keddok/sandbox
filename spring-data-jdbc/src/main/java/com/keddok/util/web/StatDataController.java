package com.keddok.util.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.keddok.util.collector.MetricCollector;

import java.util.List;

/**
 * @author RMakhmutov
 * @since 11.10.2018
 */
@RestController
public class StatDataController {
    @Autowired
    private List<MetricCollector> metricCollectors;

    @GetMapping(path = "/runMetricCollector")
    public String testGet() {
        for (MetricCollector collector : metricCollectors)
        {
            collector.collect();
        }

        return "{\"result\": \"success\"}";
    }
}
