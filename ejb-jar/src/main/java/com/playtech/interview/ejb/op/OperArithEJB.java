package com.playtech.interview.ejb.op;

import javax.ejb.Stateless;
/**
 * Les annotations @Local et @Remote peuvent �tre utilis�es directement sur l'EJB mais il est pr�f�rable de d�finir une interface par mode d'acc�s 
 * et d'utiliser l'annotation ad�quate sur chacune des interfaces.
 * les EJB Session de type stateless peuvent utiliser les callbacks d'�v�nements marqu�s avec les annotations suivantes :
 * �	@PostConstruct
 * �	@PreDestroy
 * @author Administrateur
 *
 */
@Stateless
public class OperArithEJB implements OperArithmLocal, OperArithmRemote{
	
	public long additionner(int valeur1, int valeur2) {
		return valeur1 + valeur2;
		}

}
