package com.playtech.interview.ejb.op;

import javax.ejb.Local;

/**
 * L'annotation @javax.ejb.Local permet de préciser que l'EJB pourra être accédé par des clients locaux de la JVM. Elle s'utilise sur une classe qui encapsule 
 * un EJB ou l'interface qui décrit les fonctionnalités de l'EJB utilisables en local dans la JVM. Cette annotation ne peut être utilisée que pour des EJB sessions.
 * @author Administrateur
 *
 */
@Local
public interface OperArithmLocal {
	
	
	public long additionner(int valeur1, int valeur2);

}
