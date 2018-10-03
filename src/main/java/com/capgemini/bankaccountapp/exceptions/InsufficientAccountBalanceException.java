package com.capgemini.bankaccountapp.exceptions;

public class InsufficientAccountBalanceException extends Exception {

	public InsufficientAccountBalanceException(String message)
	{
		super(message);
	}
	
}
