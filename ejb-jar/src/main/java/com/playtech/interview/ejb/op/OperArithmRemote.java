package com.playtech.interview.ejb.op;

import javax.ejb.Remote;

/**
 * Cette annotation s'utilise sur une classe qui encapsule un EJB ou l'interface qui décrit les fonctionnalités de l'EJB utilisables à distance. Cette annotation ne peut être utilisée que pour des EJB sessions.
 * @author Administrateur
 *
 */
@Remote
public interface OperArithmRemote {

	public long additionner(int valeur1, int valeur2);
}
