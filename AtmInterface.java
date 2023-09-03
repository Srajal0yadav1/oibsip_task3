import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

 class AccountInfo
{
	private String name;
	private String accountNo;
	//	private String type;
	private double balance;
	private String pin;
	private List<String> transactionStatement;
	
    public AccountInfo(String accountNumber, String pin,String name) {
        this.accountNo = accountNumber;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionStatement = new ArrayList<>();
		this.name=name;
    }
	public String getName()
	{
		return name;
	}
    public String getAccountNumber() {
        return accountNo;
    }

    public boolean validatePin(String pin) {
        return this.pin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        String transaction = "Credited: +" + amount;
        transactionStatement.add(transaction);
		System.out.println("Transaction Successfull!");
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            String transaction = "Debited: -" + amount;
            transactionStatement.add(transaction);
			System.out.println("Transaction Successfull!");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void transfer(AccountInfo receiver, double amount) {
        if (balance >= amount) {
            balance -= amount;
            receiver.deposit(amount);

            String transaction = "Debited: -" + amount + " (To : " + receiver.getName() + ")";
            transactionStatement.add(transaction);
			System.out.println("Transaction Successfull!");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void printTransactionStatement() {
        System.out.println("\nTransaction History:");
        for (String transaction : transactionStatement) {
            System.out.println(transaction);
        }
        System.out.println("Current Balance: "+balance);
    }
	public void getInfo(){
		
		System.out.println("Account Number: "+accountNo);
		System.out.println("Name: "+name);
		System.out.println("Balance: "+balance);
		//System.out.println("Pin: "+pin);
	}
}

class AtmFunction {
    private Set<AccountInfo> accounts;
    private AccountInfo currentAccount;
    private Scanner sc;

    public AtmFunction() {
        accounts = new HashSet<>();
        sc = new Scanner(System.in);
    }

    public void startAtm() {
        boolean flag = false;
        while (!flag) {
            System.out.println("\n**************************************************");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Take user choice

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    flag = true;
                    System.out.println("\n---------- Thank you ! ----------");
                    sc.close();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void register() {
        System.out.println("\nRegistration Form");
        System.out.print("Enter account number: ");
        String accountNumber = sc.nextLine();
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();
		System.out.println("Enter Name: ");
		String name=sc.nextLine();

        AccountInfo account = new AccountInfo(accountNumber,pin,name);
        accounts.add(account);

        System.out.println("\nRegistration successful. \n");
    }

    private void login() {
        System.out.println("\nLogin Form");
        System.out.print("Enter account number: ");
        String accountNumber = sc.nextLine();
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        AccountInfo account = findAccount(accountNumber);
        if (account != null && account.validatePin(pin)) {
            currentAccount = account;
            loginMenu();
        } else {
            System.out.println("Invalid account number or PIN.");
        }
    }

    private void loginMenu() {
        boolean flag = false;
        while (!flag) {
            System.out.println("\n**********Hello, Mr./Ms." + currentAccount.getName() + "!**********");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Transaction History");
            System.out.println("5. Show Balance");
            System.out.println("6. Account details");
            System.out.println("7. Logout");
            System.out.println("**************************************************");
            System.out.println("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Take user input

            switch (choice) {
                case 1:
                    System.out.println("\n--------------------------------------------------");
                    depositFun();
                    System.out.println("--------------------------------------------------");
                    break;
                case 2:
                    System.out.println("\n--------------------------------------------------");
                    withdrawFun();
                    System.out.println("--------------------------------------------------");
                    break;
                case 3:
                    System.out.println("\n--------------------------------------------------");
                    transferFun();
                    System.out.println("--------------------------------------------------");
                    break;
                case 4:
                    System.out.println("\n--------------------------------------------------");
                    currentAccount.printTransactionStatement();
                    System.out.println("--------------------------------------------------");
                    break;
                case 5:
                    System.out.println("\n--------------------------------------------------");
                    checkBalanceFun();
                    System.out.println("--------------------------------------------------");
                    break;
                case 6:
                    System.out.println("\n--------------------------------------------------");
                    currentAccount.getInfo();
                    System.out.println("--------------------------------------------------");
                    break;
                case 7:
                    flag = true;
                    currentAccount = null;
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }
    private void checkBalanceFun() {
        double balance = currentAccount.getBalance();
        System.out.println("\nYour account balance is: " + balance);
    }

    private void depositFun() {
        System.out.print("\nEnter deposit amount: ");
        double amount = sc.nextDouble();
        currentAccount.deposit(amount);
        //System.out.println("\nDeposit successful of amount "+amount);
    }

    private void withdrawFun() {
        System.out.print("\nEnter withdrawal amount: ");
        double amount = sc.nextDouble();
        currentAccount.withdraw(amount);
       // System.out.println("\nWithdrawal successful of amount "+amount);
    }

    private void transferFun() {
        System.out.print("Enter receiver's account number: ");
        String receiverAccountNumber = sc.nextLine();
        AccountInfo receiver = findAccount(receiverAccountNumber);
        if (receiver != null) {
            System.out.print("Enter transfer amount: ");
            double amount = sc.nextDouble();
            currentAccount.transfer(receiver, amount);
        } 
        else {
            System.out.println("Receiver's account not found.");
        }
    }

    private AccountInfo findAccount(String accountNumber) {
        for (AccountInfo account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}

public class AtmInterface {
    public static void main(String[] args) {
        System.out.println("\n**********Welcome to the AtmInterface.**********");
        AtmFunction atm = new AtmFunction();
        atm.startAtm();
    }
}