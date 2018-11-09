package com.keddok.util.collector;

import com.keddok.util.collector.metric.CompletedStories;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * @author RMakhmutov
 * @since 12.10.2018
 */
interface CompletedStoriesMetricRepository extends CrudRepository<CompletedStories, Integer> {
    @Query("select * from metric.completed_stories v where v.measure_dt = :measureDate")
    List<CompletedStories> findByMeasureDateAndEmployeeIdAndProjectId(@Param("measureDate") LocalDate measureDate);
}
