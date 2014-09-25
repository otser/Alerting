package com.playtech.interview.ejb.error;

import javax.ejb.ApplicationException;

//L'attribut rollback permet de pr�ciser si la lev�e de l'exception va d�clencher ou non un rollback de la transaction en cours. La valeur par d�faut est false.
@ApplicationException(rollback=true)
public class ErreurMetierException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1288730139664977384L;

	public ErreurMetierException() {
	}

	public ErreurMetierException(String msg) {
		super(msg);
	}
}
