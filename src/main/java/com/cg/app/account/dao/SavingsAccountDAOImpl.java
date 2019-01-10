/*
 * package com.cg.app.account.dao;
 * 
 * import java.sql.Connection; import java.sql.PreparedStatement; import
 * java.sql.ResultSet; import java.sql.SQLException; import java.sql.Statement;
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.springframework.stereotype.Repository;
 * 
 * import com.cg.app.account.SavingsAccount; import
 * com.cg.app.account.util.DBUtil; import
 * com.cg.app.exception.AccountNotFoundException;
 * 
 * @Repository public class SavingsAccountDAOImpl implements SavingsAccountDAO {
 * 
 * public SavingsAccount createNewAccount(SavingsAccount account) { Connection
 * connection = DBUtil.getConnection(); PreparedStatement preparedStatement =
 * connection.prepareStatement("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)");
 * preparedStatement.setInt(1, account.getBankAccount().getAccountNumber());
 * preparedStatement.setString(2,
 * account.getBankAccount().getAccountHolderName());
 * preparedStatement.setDouble(3, account.getBankAccount().getAccountBalance());
 * preparedStatement.setBoolean(4, account.isSalary());
 * preparedStatement.setObject(5, null); preparedStatement.setString(6, "SA");
 * preparedStatement.executeUpdate(); preparedStatement.close(); //
 * DBUtil.commit(); return account; }
 * 
 * public void updateBalance(int accountNumber, double currentBalance) {
 * Connection connection = DBUtil.getConnection();
 * connection.setAutoCommit(false); PreparedStatement preparedStatement =
 * connection
 * .prepareStatement("UPDATE ACCOUNT SET account_bal=? where account_id=?");
 * preparedStatement.setDouble(1, currentBalance); preparedStatement.setInt(2,
 * accountNumber); preparedStatement.executeUpdate(); }
 * 
 * public SavingsAccount deleteAccount(int accountNumber) throws
 * AccountNotFoundException { if (getAccountById(accountNumber) != null) {
 * Connection connection = DBUtil.getConnection(); PreparedStatement
 * preparedStatement =
 * connection.prepareStatement("DELETE FROM account WHERE account_id=?");
 * preparedStatement.setInt(1, accountNumber); preparedStatement.execute(); //
 * DBUtil.commit(); } return null; }
 * 
 * public List<SavingsAccount> getAllSavingsAccount() throws
 * ClassNotFoundException { List<SavingsAccount> savingsAccounts = new
 * ArrayList<>(); Connection connection = DBUtil.getConnection(); Statement
 * statement = connection.createStatement(); ResultSet resultSet =
 * statement.executeQuery("SELECT * FROM ACCOUNT"); while (resultSet.next()) {//
 * Check if row(s) is present in table int accountNumber = resultSet.getInt(1);
 * String accountHolderName = resultSet.getString("account_hn"); double
 * accountBalance = resultSet.getDouble(3); boolean salary =
 * resultSet.getBoolean("salaried"); SavingsAccount savingsAccount = new
 * SavingsAccount(accountNumber, accountHolderName, accountBalance, salary);
 * savingsAccounts.add(savingsAccount); } // DBUtil.commit(); return
 * savingsAccounts; }
 * 
 * public SavingsAccount getAccountById(int accountNumber) throws
 * AccountNotFoundException { Connection connection = DBUtil.getConnection();
 * PreparedStatement preparedStatement =
 * connection.prepareStatement("SELECT * FROM account where account_id=?");
 * preparedStatement.setInt(1, accountNumber); ResultSet resultSet =
 * preparedStatement.executeQuery(); SavingsAccount savingsAccount = null; if
 * (resultSet.next()) { String accountHolderName =
 * resultSet.getString("account_hn"); double accountBalance =
 * resultSet.getDouble(3); boolean salary = resultSet.getBoolean("salaried");
 * savingsAccount = new SavingsAccount(accountNumber, accountHolderName,
 * accountBalance, salary); return savingsAccount; } throw new
 * AccountNotFoundException("Account with account number " + accountNumber +
 * " does not exist."); }
 * 
 * public SavingsAccount getAccountByName(String accountHolderName) throws
 * AccountNotFoundException { Connection connection = DBUtil.getConnection();
 * PreparedStatement preparedStatement =
 * connection.prepareStatement("SELECT * FROM account where account_hn=?");
 * preparedStatement.setString(1, accountHolderName); ResultSet resultSet =
 * preparedStatement.executeQuery(); SavingsAccount savingsAccount = null; if
 * (resultSet.next()) { int accountNumber = resultSet.getInt("account_id");
 * double accountBalance = resultSet.getDouble(3); boolean salary =
 * resultSet.getBoolean("salaried"); savingsAccount = new
 * SavingsAccount(accountNumber, accountHolderName, accountBalance, salary);
 * return savingsAccount; } throw new AccountNotFoundException(
 * "Account with Account Holder Name " + accountHolderName +
 * " does not exist."); }
 * 
 * public List<SavingsAccount> getAccountByBalance(double minmumBalance, double
 * maximumBalance) throws AccountNotFoundException { Connection connection =
 * DBUtil.getConnection(); PreparedStatement preparedStatement = connection
 * .prepareStatement("SELECT * FROM account WHERE account_bal BETWEEN ? AND ?");
 * preparedStatement.setDouble(1, minmumBalance); preparedStatement.setDouble(2,
 * maximumBalance); ResultSet resultSet = preparedStatement.executeQuery();
 * 
 * List<SavingsAccount> list = new ArrayList(); SavingsAccount savingsAccount =
 * null; while (resultSet.next()) { int accountNumber =
 * resultSet.getInt("account_id"); String accountHolderName =
 * resultSet.getString("account_hn"); double accountBalance =
 * resultSet.getDouble(3); boolean salary = resultSet.getBoolean("salaried");
 * savingsAccount = new SavingsAccount(accountNumber, accountHolderName,
 * accountBalance, salary); list.add(savingsAccount);
 * 
 * } return list; }
 * 
 * public List<SavingsAccount> sortAllSavingsAccount(int choice) {
 * List<SavingsAccount> savingsAccounts = new ArrayList<>(); Connection
 * connection = DBUtil.getConnection(); PreparedStatement preparedStatement;
 * ResultSet resultSet = null; switch (choice) { case 1: preparedStatement =
 * connection.prepareStatement("SELECT * FROM account ORDER BY account_id");
 * resultSet = preparedStatement.executeQuery(); // DBUtil.commit(); break; case
 * 2: preparedStatement =
 * connection.prepareStatement("SELECT * FROM account ORDER BY account_hn");
 * resultSet = preparedStatement.executeQuery(); // DBUtil.commit(); break; case
 * 3: preparedStatement =
 * connection.prepareStatement("SELECT * FROM account ORDER BY account_bal DESC"
 * ); resultSet = preparedStatement.executeQuery(); // DBUtil.commit(); break; }
 * 
 * while (resultSet.next()) {// Check if row(s) is present in table int
 * accountNumber = resultSet.getInt("account_id"); String accountHolderName =
 * resultSet.getString("account_hn"); double accountBalance =
 * resultSet.getDouble("account_bal"); boolean salary =
 * resultSet.getBoolean("salaried"); SavingsAccount savingsAccount = new
 * SavingsAccount(accountNumber, accountHolderName, accountBalance, salary);
 * savingsAccounts.add(savingsAccount); }
 * 
 * return savingsAccounts; }
 * 
 * public double checkAccountBalance(int accountNumber) throws
 * AccountNotFoundException { Connection connection = DBUtil.getConnection();
 * SavingsAccountDAO savingsAccount = new SavingsAccountDAOImpl(); if
 * (savingsAccount.getAccountById(accountNumber).getBankAccount().
 * getAccountNumber() == accountNumber) { PreparedStatement preparedStatement =
 * connection
 * .prepareStatement("SELECT account_bal FROM account where account_id=?");
 * preparedStatement.setInt(1, accountNumber); ResultSet resultSet =
 * preparedStatement.executeQuery(); if (resultSet.next()) { double
 * accountBalance = resultSet.getDouble("account_bal"); return accountBalance; }
 * }
 * 
 * throw new AccountNotFoundException("Account with Account Number" +
 * accountNumber + " does not exist."); }
 * 
 * public SavingsAccount updateAccount(SavingsAccount account) { return null; }
 * 
 * public SavingsAccount updateAccount(int accountNumber, int choice, String
 * nameORsalaried) throws ClassNotFoundException, SQLException {
 * 
 * if (choice == 1) { Connection connection = DBUtil.getConnection();
 * PreparedStatement preparedStatement = connection
 * .prepareStatement("UPDATE account SET account_hn=? where account_id=?");
 * preparedStatement.setString(1, nameORsalaried); preparedStatement.setInt(2,
 * accountNumber); preparedStatement.executeUpdate(); // DBUtil.commit(); } else
 * if (choice == 2) { boolean salaryType = Boolean.parseBoolean(nameORsalaried);
 * Connection connection = DBUtil.getConnection(); PreparedStatement
 * preparedStatement = connection
 * .prepareStatement("UPDATE account SET salaried=? where account_id=?");
 * preparedStatement.setBoolean(1, salaryType); preparedStatement.setInt(2,
 * accountNumber); preparedStatement.executeUpdate(); // DBUtil.commit(); }
 * return null; }
 * 
 * public void commit() throws SQLException { DBUtil.commit(); }
 * 
 * @Override public int updateAccount(int accountNumber, String
 * accountHolderName) { //Connection connection = DBUtil.getConnection(); String
 * query = "UPDATE ACCOUNT SET account_hn=? where account_id=?";
 * //PreparedStatement preparedStatement = connection.prepareStatement(query);
 * preparedStatement.setString(1, accountHolderName);
 * preparedStatement.setInt(2, accountNumber); int result =
 * preparedStatement.executeUpdate(); DBUtil.commit(); return result;
 * 
 * }
 * 
 * }
 */