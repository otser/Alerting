package com.playtech.interview.ejb.op;

import javax.ejb.Stateless;
/**
 * Les annotations @Local et @Remote peuvent être utilisées directement sur l'EJB mais il est préférable de définir une interface par mode d'accès 
 * et d'utiliser l'annotation adéquate sur chacune des interfaces.
 * les EJB Session de type stateless peuvent utiliser les callbacks d'évènements marqués avec les annotations suivantes :
 * •	@PostConstruct
 * •	@PreDestroy
 * @author Administrateur
 *
 */
@Stateless
public class OperArithEJB implements OperArithmLocal, OperArithmRemote{
	
	public long additionner(int valeur1, int valeur2) {
		return valeur1 + valeur2;
		}

}
