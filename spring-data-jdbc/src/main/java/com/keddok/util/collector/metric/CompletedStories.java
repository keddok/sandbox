package com.keddok.util.collector.metric;

import java.time.LocalDate;

/**
 * @author RMakhmutov
 * @since 12.10.2018
 */
public class CompletedStories extends GeneralMetricValue {
    public CompletedStories(LocalDate measureDate, Integer measuredValue) {
        super(measureDate, measuredValue);
    }
}
