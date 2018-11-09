package com.keddok.util.collector;

import org.springframework.stereotype.Component;

/**
 * @author RMakhmutov
 * @since 09.11.2018
 */
@Component
public class MetricSourceIntegerStub implements MetricSource<Integer> {
    @Override
    public Integer count() {
        return 1;
    }
}
