package com.capgemini.bankaccountapp.repository;

import java.sql.SQLException;

public interface BankAccountRepository {

	public double getBalance(long accountId) throws SQLException;
	public boolean updateBalance(long accountId, double newBalance) throws SQLException;
}
