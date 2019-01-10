package com.cg.app.account.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cg.app.account.SavingsAccount;
import com.cg.app.account.dao.SavingsAccountDAO;
import com.cg.app.account.factory.AccountFactory;
//import com.cg.app.account.util.DBUtil;
import com.cg.app.exception.AccountNotFoundException;
import com.cg.app.exception.InsufficientFundsException;
import com.cg.app.exception.InvalidInputException;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {

	private AccountFactory factory;
	@Autowired
	private SavingsAccountDAO savingsAccountDAO;

	@Autowired
	public SavingsAccountServiceImpl(SavingsAccountDAO savingsAccountDAO) {
		factory = AccountFactory.getInstance();
		this.savingsAccountDAO = savingsAccountDAO;
	}

	public SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary) {
		SavingsAccount account = factory.createNewSavingsAccount(accountHolderName, accountBalance, salary);
		savingsAccountDAO.createNewAccount(account);
		return null;
	}

	public SavingsAccount deleteAccount(int accountNumber)
			throws AccountNotFoundException {

		return savingsAccountDAO.deleteAccount(accountNumber);
	}

	public int updateAccount(int accountNumber, String accountHolderName) {
		return savingsAccountDAO.updateAccount(accountNumber, accountHolderName);
	}

	public List<SavingsAccount> getAllSavingsAccount() {
		return savingsAccountDAO.getAllSavingsAccount();
	}

	public void withdraw(SavingsAccount account, double amount) {
		double currentBalance = account.getBankAccount().getAccountBalance();
		// if (amount > 0 && currentBalance >= amount) {
		currentBalance -= amount;
		savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
		// savingsAccountDAO.commit();
		// } else {
		// throw new InsufficientFundsException("Invalid Input or Insufficient Funds!");

	}

	public void deposit(SavingsAccount account, double amount) {
		// if (amount > 0) {
		double currentBalance = account.getBankAccount().getAccountBalance();
		currentBalance += amount;
		savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
		// savingsAccountDAO.commit();
		// } else {
		// throw new InvalidInputException("Invalid Input Amount!");
		// }
	}

	@Transactional(rollbackFor = Throwable.class)
	public void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount) {
		withdraw(sender, amount);
		deposit(receiver, amount);

	}

	public SavingsAccount getAccountById(int accountNumber)
			throws AccountNotFoundException {
		return savingsAccountDAO.getAccountById(accountNumber);
	}

	public SavingsAccount getAccountByName(String accountHolderName)
			throws AccountNotFoundException {

		return savingsAccountDAO.getAccountByName(accountHolderName);
	}

	public double checkAccountBalance(int accountNumber)
			throws AccountNotFoundException {
		return savingsAccountDAO.checkAccountBalance(accountNumber);
	}

	public List<SavingsAccount> getAccountByBalance(double minmumBalance, double maximumBalance)
			throws AccountNotFoundException {

		return savingsAccountDAO.getAccountByBalance(minmumBalance, maximumBalance);
	}

	public List<SavingsAccount> sortAllSavingsAccount(int choice) {
		return savingsAccountDAO.sortAllSavingsAccount(choice);
	}

}