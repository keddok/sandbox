package com.keddok.util.collector;

/**
 * @author RMakhmutov
 * @since 09.11.2018
 */
public interface MetricSource<T> {
    T count();
}
