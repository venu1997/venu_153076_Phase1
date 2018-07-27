package com.cg.mypaymentapp.test;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;
public class TestClass {
	static WalletService service;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {}
	@Before
	public void setUp() throws Exception{
		Map<String,Customer> data= new HashMap<String, Customer>();
		Customer cust1=new Customer("Venu", "7036071319",new Wallet(new BigDecimal(9000)));
		Customer cust2=new Customer("Shankar", "8008985352",new Wallet(new BigDecimal(6000)));
		Customer cust3=new Customer("Siva", "8008732530",new Wallet(new BigDecimal(7000)));
		data.put("7036071319", cust1);
		data.put("8008985352", cust2);	
		data.put("8008732530", cust3);	
		service= new WalletServiceImpl(data);
	}
	@After
	public void tearDown() throws Exception {}
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount1() {
		service.createAccount(null, "9177424331", new BigDecimal(1500));
	}
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount2() {
		service.createAccount("", "9908015612", new BigDecimal(1500));
	}
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount3() {
		service.createAccount("teju", "999", new BigDecimal(1500));
	}
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount4() {
		service.createAccount("teju", "", new BigDecimal(1500));
	}
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount5() {
		service.createAccount("", "", new BigDecimal(1500));
	}
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount6() {
		service.createAccount("Venu", "7036071319", new BigDecimal(-100));
	}
	@Test
	public void testCreateAccount7() {
		Customer actual=service.createAccount("Prasad", "8328215652", new BigDecimal(0));
		Customer expected=new Customer("Prasad", "8328215652", new Wallet(new BigDecimal(0)));
		assertEquals(expected, actual);
	}
	@Test
	public void testCreateAccount8() {
		Customer actual=service.createAccount("Prasad", "8328215652", new BigDecimal(5000.75));
		Customer expected=new Customer("Prasad", "8328215652", new Wallet(new BigDecimal(5000.75)));
		assertEquals(expected, actual);
	}
	@Test(expected=InvalidInputException.class)
	public void testShowBalance9() {
		service.showBalance(null);		
	}
	@Test(expected=InvalidInputException.class)
	public void testShowBalance10() {
		service.showBalance("");		
	}
	@Test(expected=InvalidInputException.class)
	public void testShowBalance11() {
		service.showBalance("12345");		
	}
	@Test(expected=InvalidInputException.class)
	public void testShowBalance12() {
		service.showBalance("9123456789");		
	}
	@Test(expected=InvalidInputException.class)
	public void testShowBalance13(){
		service.showBalance("99123456789");		
	}
	@Test
	public void testShowBalance14() {
		Customer customer=service.showBalance("7036071319");
		BigDecimal expectedResult=new BigDecimal(9000);
		BigDecimal obtainedResult=customer.getWallet().getBalance();
		assertEquals(expectedResult, obtainedResult);
	}
	//invalid numbers
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer15() {
		service.fundTransfer("9949493181", "9949491876", new BigDecimal(5000));		
	}
	//source and target numbers same
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer16() {
		service.fundTransfer("9949491876", "9949491876", new BigDecimal(5000));		
	}
	@Test(expected=InsufficientBalanceException.class)
	public void testFundTransfer17() {
		service.fundTransfer("7036071319", "8008985352", new BigDecimal(12000));		
	}
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer18() {
		service.fundTransfer("7036071319", "", new BigDecimal(0));		
	}
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer19() {
		service.fundTransfer("", "8008985352", new BigDecimal(500));		
	}
	@Test
	public void testFundTransfer20() {
		Customer customer=service.fundTransfer("7036071319", "8008985352", new BigDecimal(500));
		BigDecimal expected=customer.getWallet().getBalance();
		BigDecimal actual=new BigDecimal(8500);
		assertEquals(expected, actual);
	}
	@Test
	public void testFundTransfer21(){
		Customer customer=service.fundTransfer("7036071319", "8008985352", new BigDecimal(550.50));
		BigDecimal expected=customer.getWallet().getBalance();
		BigDecimal actual=new BigDecimal(8449.50);
		assertEquals(expected, actual);
	}
	@Test(expected=InsufficientBalanceException.class)
	public void testFundTransfer22() {
		Customer customer=service.fundTransfer("7036071319", "8008985352", new BigDecimal(15000));	
	}
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer26() {
		service.fundTransfer("", "8008985352", new BigDecimal(-100));		
	}
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer23() {
		service.fundTransfer(null, null, new BigDecimal(0));		
	}
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer24() {
		service.fundTransfer("8008732530", null, new BigDecimal(50));		
	}
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer25() {
		service.fundTransfer("8008985352", "7036071319", new BigDecimal(0));		
	}
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount26() {
		service.depositAmount(null, new BigDecimal(500));
	}
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount27() {
		service.depositAmount("", new BigDecimal(500));
	}
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount28() {
		service.depositAmount("9123456789", new BigDecimal(500));
	}
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount29() {
		service.depositAmount("9949491876", new BigDecimal(-1000));
	}
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount30() {
		service.depositAmount("8008732530", new BigDecimal(200000));
	}
	@Test(expected=InsufficientBalanceException.class)
	public void testWithdrawAmount31() {
		service.withdrawAmount("7036071319", new BigDecimal(15000));	
	}
	@Test(expected=InvalidInputException.class)
	public void testWithdrawAmount32() {
		service.withdrawAmount("8328215652", new BigDecimal(5000));	
	}

}
