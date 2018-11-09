package com.keddok.util.collector;

import com.keddok.util.collector.metric.CompletedStories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

/**
 * @author RMakhmutov
 * @since 11.10.2018
 */
@Component
public class CompletedStoriesMetricCollector implements MetricCollector {
    @Autowired
    private CompletedStoriesMetricRepository metricRepository;

    @Autowired
    private MetricSource<Integer> metricSource;

    @Override
    public void collect() {
        collectTodayMetricValues();
    }

    private void collectTodayMetricValues() {
        collectMetricValues(LocalDate.now());
    }

    private void collectMetricValues(LocalDate resolutionDate) {
            saveMetricValue(resolutionDate, metricSource.count());
    }

    private CompletedStories saveMetricValue(LocalDate date, Integer count) {
        CompletedStories metricValue;
        List<CompletedStories> values = metricRepository.findByMeasureDateAndEmployeeIdAndProjectId(date);
        if (values.isEmpty())
            metricValue = new CompletedStories(date, count);
        else {
            metricValue = values.get(0);
            metricValue.setMeasuredValue(count);
        }

        return metricRepository.save(metricValue);
    }
}
