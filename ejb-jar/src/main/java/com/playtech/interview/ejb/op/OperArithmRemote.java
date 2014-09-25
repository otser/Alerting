package com.playtech.interview.ejb.op;

import javax.ejb.Remote;

/**
 * Cette annotation s'utilise sur une classe qui encapsule un EJB ou l'interface qui d�crit les fonctionnalit�s de l'EJB utilisables � distance. Cette annotation ne peut �tre utilis�e que pour des EJB sessions.
 * @author Administrateur
 *
 */
@Remote
public interface OperArithmRemote {

	public long additionner(int valeur1, int valeur2);
}
