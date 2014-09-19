package com.playtech.interview.actions;

import java.util.logging.Logger;

import com.playtech.interview.EventDetails;

public class LogToFileAction implements Action {

    private static final Logger log = Logger.getLogger(LogToFileAction.class.getName());

    @Override
    public void execute(EventDetails details) {
        log.info("Logged event to file");
        // obtain a file logger and log
    }

}
