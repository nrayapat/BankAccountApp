package com.capgemini.bankaccountapp.repository.impl;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.capgemini.bankaccountapp.model.BankAccount;
import com.capgemini.bankaccountapp.model.Customer;
import com.capgemini.bankaccountapp.repository.CustomerRepository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Customer authenticate(Customer customer) throws SQLException {
		customer = jdbcTemplate.queryForObject("SELECT * FROM customer WHERE customerId = ? AND customerPassword = ?",
				new Object[] { customer.getCustomerId(),customer.getCustomerPassword() }, new CustomerRowMapper());
		BankAccount bankAccount = jdbcTemplate.queryForObject(
				"SELECT * FROM accounts WHERE account_id = (SELECT account_id FROM customer WHERE customerId = ?)",
				new Object[] { customer.getCustomerId() }, new AccountRowMapper());
		customer.setAccount(bankAccount);
		return customer;
	}

	@Override
	public Customer updateProfile(Customer customer) {
		jdbcTemplate.update(
				"UPDATE customer SET customerAddress = ?,customerDateOfBirth = ?,customerEmail=?,customerName=?   WHERE customerId = ?",
				new Object[] { customer.getCustomerAddress(), customer.getCustomerDateOfBirth(),
						customer.getCustomerEmail(), customer.getCustomerName(), customer.getCustomerId() });
		customer = jdbcTemplate.queryForObject("SELECT * FROM customer WHERE customerId=?",
				new Object[] { customer.getCustomerId() }, new CustomerRowMapper());
		return customer;

	}

	@Override
	public boolean updatePassword(Customer customer, String oldPassword, String newPassword) {
		int count = jdbcTemplate.update(
				"UPDATE customer SET customerPassword = ?  WHERE customerId = ? AND customerPassword = ?",
				new Object[] { newPassword, customer.getCustomerId(), oldPassword });
		return (count != 0) ? true : false;
		
	}

	@Override
	public Customer updateSession(long customerId) {
		Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customer WHERE customerId=?",
				new Object[] { customerId }, new CustomerRowMapper());
		BankAccount bankAccount = jdbcTemplate.queryForObject(
				"SELECT * FROM accounts WHERE account_id = (SELECT account_id FROM customer WHERE customerId = ?)",
				new Object[] { customerId }, new AccountRowMapper());
		customer.setAccount(bankAccount);
		return customer;
	}

	private class CustomerRowMapper implements RowMapper<Customer> {

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setCustomerId(rs.getLong(1));
			customer.setCustomerName(rs.getString(2));
			customer.setCustomerPassword(rs.getString(3));
			customer.setCustomerEmail(rs.getString(4));
			customer.setCustomerAddress(rs.getString(5));
			customer.setCustomerDateOfBirth(rs.getDate(6).toLocalDate());
			return customer;

		}
	}

	private class AccountRowMapper implements RowMapper<BankAccount> {

		@Override
		public BankAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
			BankAccount bankAccount = new BankAccount();
			bankAccount.setAccountId(rs.getLong(1));
			bankAccount.setAccountType(rs.getString(2));
			bankAccount.setBalance(rs.getDouble(3));
			return bankAccount;

		}
		}
}

