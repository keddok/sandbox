package com.keddok.util.collector.metric;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

/**
 * @author RMakhmutov
 * @since 12.10.2018
 */
public class GeneralMetricValue {
    @Id
    Integer id;
    @Column("measure_dt")
    LocalDate measureDate;
    Integer measuredValue;

    public GeneralMetricValue(LocalDate measureDate, Integer measuredValue) {
        this.measureDate = measureDate;
        this.measuredValue = measuredValue;
    }

    public void setMeasuredValue(Integer measuredValue) {
        this.measuredValue = measuredValue;
    }
}
