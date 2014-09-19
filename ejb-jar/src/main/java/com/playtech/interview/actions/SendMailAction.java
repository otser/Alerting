package com.playtech.interview.actions;

import java.util.logging.Logger;

import com.playtech.interview.EventDetails;

public class SendMailAction implements Action {

    private static final Logger log = Logger.getLogger(LogToDbAction.class.getName());

    @Override
    public void execute(EventDetails details) {
        log.info("Sent event to email");
        // send email via commons-email
    }

}
