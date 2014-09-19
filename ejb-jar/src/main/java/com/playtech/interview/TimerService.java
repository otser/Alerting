package com.playtech.interview;

import javax.ejb.Remote;

@Remote
public interface TimerService {

    /**
     * Schedules the messaging timer with the given ms
     * @param milliseconds
     */
    void scheduleTimer(long milliseconds);

    /**
     * Overloaded method which uses the default timeout set in the database
     */
    void scheduleTimer();
}
