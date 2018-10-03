package com.capgemini.bankaccountapp.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.bankaccountapp.model.Customer;
import com.capgemini.bankaccountapp.repository.impl.CustomerRepositoryImpl;
import com.capgemini.bankaccountapp.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepositoryImpl customerRepositoryImpl;
	
	@Override
	public Customer authenticate(Customer customer) throws SQLException {
		return customerRepositoryImpl.authenticate(customer);
	}

	@Override
	public Customer updateProfile(Customer customer) {
		return customerRepositoryImpl.updateProfile(customer);
	}

	@Override
	public boolean updatePassword(Customer customer, String oldPassword, String newPassword) {
		return customerRepositoryImpl.updatePassword(customer, oldPassword, newPassword);
	}
	
	@Override
	public Customer updateSession(long customerId) {
		return customerRepositoryImpl.updateSession(customerId);
	}

}
