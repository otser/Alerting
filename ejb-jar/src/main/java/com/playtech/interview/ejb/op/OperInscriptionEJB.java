package com.playtech.interview.ejb.op;

import javax.ejb.Stateful;

/**
 * les EJB session de type stateful peuvent utiliser les callbacks d'�v�nements marqu�s avec les annotations suivantes :
 * @PostConstruct
 * @PostActivate
 * @PreDestroy
 * @PrePassivate
 * @Remove
 * @author Administrateur
 *
 */
@Stateful
public class OperInscriptionEJB implements OperInscriptionLocal, OperInscriptionRemote{

}
