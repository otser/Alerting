package com.playtech.interview.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.playtech.interview.TimerServiceLocal;

public class StartupServlet extends HttpServlet {

    @EJB(name="timerService")
    private TimerServiceLocal timerService;
    
    @EJB(name="timerT1EjbScheduler")
    private TimerServiceLocal timerT1EjbScheduler;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("setup") != null) {
            timerService.initializePersistentData();
        }
        timerService.scheduleTimer();
    }
}
