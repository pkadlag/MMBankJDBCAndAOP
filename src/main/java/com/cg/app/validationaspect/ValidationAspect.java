package com.cg.app.validationaspect;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.cg.app.account.SavingsAccount;

@Aspect
@Component
public class ValidationAspect {
	private Logger logger = Logger.getLogger(ValidationAspect.class.getName());

	@Around("execution(* com.cg.app.account.service.SavingsAccountServiceImpl.withdraw(..))")
	public void withdraw(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("In withdraw method");
		/*
		 * logger.info("Function Name is :" + pjp.getSignature());
		 * logger.info("Parameters are :");
		 */
		Object[] params = pjp.getArgs();
		Double amount = (Double) params[1];
		SavingsAccount account = (SavingsAccount) params[0];
		Double currentBalance = account.getBankAccount().getAccountBalance();

		if (amount > 0 && currentBalance >= amount) {
			logger.info("Withdrawal amount is successful");
			pjp.proceed();
		} else {
			logger.info("Invalid Input Amount");
		}
	}

	@Around("execution(* com.cg.app.account.service.SavingsAccountServiceImpl.deposit(..))")
	public void deposit(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("In Deposit method");
		Object[] params = pjp.getArgs();
		Double amount = (Double) params[1];
		if (amount > 0 && amount != null) {
			pjp.proceed();
			logger.info("deposite is successful");
		} else {
			logger.info("Invalid Input Amount!");
		}
	}

}
