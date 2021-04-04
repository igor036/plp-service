package com.olist.plp.domain.schedule;

import java.util.Date;

import com.olist.plp.application.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractShedule {

    private long initialTimeMillis;
    private long finishTimeMillis;

    protected abstract void run();

    protected void logInit() {
        
        initialTimeMillis = System.currentTimeMillis();
        
        var className     = this.getClass().getSimpleName();
        var startedAt     = DateUtil.format(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT);
        
        log.info("Init run {} at {}", className, startedAt);
    }

    protected void logFinish() {
        
        finishTimeMillis = System.currentTimeMillis();
        
        var executionTime = getExecutionTime();
        var className     = this.getClass().getSimpleName();
        var finishAt      = DateUtil.format(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT);
 
        log.info("Finish run {} at {} executed in {} ", className, finishAt, executionTime);

    }

    protected void logError(Exception ex) {

        finishTimeMillis = System.currentTimeMillis();

        var className     = this.getClass().getSimpleName();
        var executionTime = getExecutionTime();

        log.error("Error when execute: {} execution time: exception: {}", className, executionTime, ex);
    }

    private String getExecutionTime() {

        var executionTime = (finishTimeMillis - initialTimeMillis) / 60d;
        
        initialTimeMillis = 0l;
        finishTimeMillis  = 0l;

        return String.format("%2f seconds", executionTime);
    }
}
