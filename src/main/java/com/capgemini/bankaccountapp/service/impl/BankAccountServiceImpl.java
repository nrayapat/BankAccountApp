package com.capgemini.bankaccountapp.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.bankaccountapp.exceptions.InsufficientAccountBalanceException;
import com.capgemini.bankaccountapp.exceptions.NegativeAmountException;
import com.capgemini.bankaccountapp.repository.impl.BankAccountRepositoryImpl;
import com.capgemini.bankaccountapp.service.BankAccountService;
@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	BankAccountRepositoryImpl bankAccountRepositoryImpl;
	@Autowired
	BankAccountServiceImpl serviceobject;

	@Override
	public double getBalance(long accountId) throws SQLException {
		return bankAccountRepositoryImpl.getBalance(accountId);
	}

	@Override
	public double withdraw(long accountId, double amount) throws SQLException {
		double accountBalance = bankAccountRepositoryImpl.getBalance(accountId);
		bankAccountRepositoryImpl.updateBalance(accountId, accountBalance - amount);
		return accountBalance - amount;
	}

	@Override
	public double deposit(long accountId, double amount) throws SQLException {
		double accountBalance = bankAccountRepositoryImpl.getBalance(accountId);
		bankAccountRepositoryImpl.updateBalance(accountId, accountBalance + amount);
		return accountBalance + amount;
	}

	@Override
	public boolean fundTransfer(long fromAcc, long toAcc, double amount)
			throws InsufficientAccountBalanceException, NegativeAmountException, SQLException {
		double accountBalanceFrom = bankAccountRepositoryImpl.getBalance(fromAcc);
		
		if (accountBalanceFrom < amount) 
			throw new InsufficientAccountBalanceException("There isn't sufficient balance in your account!");
		else if (amount < 0)
			throw new NegativeAmountException("The amount cannot be negative!");
		else {
			serviceobject.deposit(toAcc, amount);
			serviceobject.withdraw(fromAcc, amount);
			return true;
		}
	}

}
