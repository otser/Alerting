package com.playtech.interview.ejb.timer;


/**
 * Le service EJB Timer propose un support de la configuration de la planification d'un timer de deux fa�ons :
 * -Soit de fa�on d�clarative gr�ce � l'annotation @Schedule sur une m�thode d'un EJB Session ou dans le descripteur de d�ploiement.
 * -Soit par programmation
 * Pour d�finir le callback d'un timer par programmation, il y a deux solutions :
 * -Le bean impl�mente l'interface javax.ejb.TimedObject
 * -Utiliser l'annotation @Timeout
 */

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ejb.Timer;

@Singleton(name="timerT1EjbScheduler")
public class TimerT1EJB {

    /**
     * Default constructor. 
     */
    public TimerT1EJB() {
        // TODO Auto-generated constructor stub
    }
	
	@SuppressWarnings("unused")
	//Toutes les 10 secondes des minute pairs de 8H � 23H et du Lundi au Vendredi
	@Schedule(second="*/10", minute="*/2", hour="8-23", dayOfWeek="Mon-Fri", dayOfMonth="*", month="*", year="*", info="MyTimer")
    private void scheduledTimeout(final Timer t) {
        System.out.println("@Schedule called at: " + new java.util.Date());
    }
}