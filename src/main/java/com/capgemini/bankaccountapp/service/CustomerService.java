package com.capgemini.bankaccountapp.service;

import java.sql.SQLException;

import com.capgemini.bankaccountapp.model.Customer;

public interface CustomerService {

	public Customer authenticate(Customer customer) throws SQLException;
	public Customer updateProfile(Customer customer);
	public boolean updatePassword(Customer customer, String oldPassword, String newPassword);
	public Customer updateSession(long customerId); 

}
