package com.playtech.interview.ejb.op;

import javax.ejb.Local;

/**
 * L'annotation @javax.ejb.Local permet de pr�ciser que l'EJB pourra �tre acc�d� par des clients locaux de la JVM. Elle s'utilise sur une classe qui encapsule 
 * un EJB ou l'interface qui d�crit les fonctionnalit�s de l'EJB utilisables en local dans la JVM. Cette annotation ne peut �tre utilis�e que pour des EJB sessions.
 * @author Administrateur
 *
 */
@Local
public interface OperArithmLocal {
	
	
	public long additionner(int valeur1, int valeur2);

}
