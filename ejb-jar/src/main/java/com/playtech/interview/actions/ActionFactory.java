package com.playtech.interview.actions;

import javax.persistence.EntityManager;

import com.playtech.interview.ActionType;

public class ActionFactory {

    private EntityManager entityManager;

    public Action createAction(ActionType actionType) {

        switch (actionType) {
        case SEND_MAIL:
            return new SendMailAction();
        case LOG_TO_DB:
            return new LogToDbAction(entityManager);
        case LOG_TO_FILE:
            return new LogToFileAction();
        default:
            throw new IllegalArgumentException(actionType + " is not supported");
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
