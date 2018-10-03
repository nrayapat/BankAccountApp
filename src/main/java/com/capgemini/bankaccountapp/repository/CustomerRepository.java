package com.capgemini.bankaccountapp.repository;

import java.sql.SQLException;

import com.capgemini.bankaccountapp.model.Customer;

public interface CustomerRepository {

	public Customer authenticate(Customer customer) throws SQLException;

	public Customer updateProfile(Customer customer) throws SQLException;

	public boolean updatePassword(Customer customer, String oldPassword, String newPassword) throws SQLException;

	public Customer updateSession(long customerId);
}
