package com.playtech.interview;

import javax.ejb.Local;

@Local
public interface TimerServiceLocal extends TimerService {

    void scheduleTimer(long milliseconds);

    void initializePersistentData();
}
