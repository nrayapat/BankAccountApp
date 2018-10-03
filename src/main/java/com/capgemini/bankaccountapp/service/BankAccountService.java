package com.capgemini.bankaccountapp.service;

import java.sql.SQLException;

import com.capgemini.bankaccountapp.exceptions.InsufficientAccountBalanceException;
import com.capgemini.bankaccountapp.exceptions.NegativeAmountException;

public interface BankAccountService {

	public double getBalance(long accountId) throws SQLException;
	public double withdraw(long accountId, double amount) throws SQLException;
	public double deposit(long accountId, double amount) throws SQLException;
	public boolean fundTransfer(long fromAcc, long toAcc, double amount) throws InsufficientAccountBalanceException, NegativeAmountException, SQLException;
}
