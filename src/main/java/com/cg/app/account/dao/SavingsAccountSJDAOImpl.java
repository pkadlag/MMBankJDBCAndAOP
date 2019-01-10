package com.cg.app.account.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cg.app.account.SavingsAccount;
import com.cg.app.exception.AccountNotFoundException;

@Repository
@Primary
public class SavingsAccountSJDAOImpl implements SavingsAccountDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public SavingsAccount createNewAccount(SavingsAccount account) {
		/*Logger logger = Logger.getLogger(SavingsAccountSJDAOImpl.class.getName());
		logger.info("SI is working");*/
		jdbcTemplate.update("INSERT into account VALUES(?,?,?,?,?,?)",
				new Object[] { account.getBankAccount().getAccountNumber(),
						account.getBankAccount().getAccountHolderName(), account.getBankAccount().getAccountBalance(),
						account.isSalary(), null, "SA" });
		return null;
	}

	@Override
	public SavingsAccount getAccountById(int accountNumber) throws AccountNotFoundException {
		/*
		 * Logger logger = Logger.getLogger(SavingsAccountSJDAOImpl.class.getName());
		 * logger.info("Enter account ID");
		 */
		return jdbcTemplate.queryForObject("SELECT * FROM account where account_id=?", new Object[] { accountNumber },
				new SavingsAccountMapper());

	}

	@Override
	public SavingsAccount deleteAccount(int accountNumber) throws AccountNotFoundException {
		jdbcTemplate.update("DELETE FROM account WHERE account_id=?", new Object[] { accountNumber });
		return null;
	}

	@Override
	public SavingsAccount getAccountByName(String accountHolderName) throws AccountNotFoundException {

		return jdbcTemplate.queryForObject("SELECT * FROM account where account_hn=?",
				new Object[] { accountHolderName }, new SavingsAccountMapper());
	}

	@Override
	public List<SavingsAccount> getAccountByBalance(double minmumBalance, double maximumBalance)
			throws AccountNotFoundException {
		// return null;

		return jdbcTemplate.query("SELECT * FROM account WHERE account_bal BETWEEN ? AND ?",
				new Object[] { minmumBalance, maximumBalance }, new SavingsAccountMapper());
	}

	@Override
	public double checkAccountBalance(int accountNumber) throws AccountNotFoundException {

		return jdbcTemplate.queryForObject("SELECT account_bal FROM account where account_id=?",
				new Object[] { accountNumber }, Double.class);
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccount()  {

		return jdbcTemplate.query("SELECT * FROM account", new SavingsAccountMapper());
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance) {
		jdbcTemplate.update("UPDATE ACCOUNT SET account_bal=? where account_id=?",
				new Object[] { currentBalance, accountNumber });

	}

	@Override
	public int updateAccount(int accountNumber, String accountHolderName) {

		return jdbcTemplate.update("UPDATE account SET account_hn=? where account_id=?",
				new Object[] { accountHolderName, accountNumber });
	}

	@Override
	public List<SavingsAccount> sortAllSavingsAccount(int choice) {
		String query = null;
		switch (choice) {
		case 1:
			query = "SELECT * FROM account ORDER BY account_id";
			break;

		case 2:
			query = "SELECT * FROM account ORDER BY account_hn";
			break;
		case 3:
			query = "SELECT * FROM account ORDER BY account_bal";
			break;

		}
		return jdbcTemplate.query(query, new SavingsAccountMapper());
	}

	/*
	 * @Override public void commit() throws SQLException { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 */
}
