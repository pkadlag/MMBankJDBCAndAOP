package com.cg.app.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.cg.app.account.*;
import com.cg.app.exception.AccountNotFoundException;

public interface SavingsAccountDAO {

	SavingsAccount createNewAccount(SavingsAccount account);

	int updateAccount(int accountNumber, String accountHolderName);

	SavingsAccount getAccountById(int accountNumber) throws AccountNotFoundException;

	SavingsAccount deleteAccount(int accountNumber) throws AccountNotFoundException;

	SavingsAccount getAccountByName(String accountHolderName) throws AccountNotFoundException;

	List<SavingsAccount> getAccountByBalance(double minmumBalance, double maximumBalance)
			throws AccountNotFoundException;

	double checkAccountBalance(int accountNumber) throws AccountNotFoundException;

	List<SavingsAccount> getAllSavingsAccount() ;

	void updateBalance(int accountNumber, double currentBalance);

	List<SavingsAccount> sortAllSavingsAccount(int choice);

	/* void commit() ; */

}
