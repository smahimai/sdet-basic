public class Account {
    
    protected double balance;
    protected double interest;
    protected String accountNumber;
    
    public Account(String accountNumber, double balance, double interest) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interest = interest;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public double getInterest() {
        return interest;
    }
    
    public void setInterest(double interest) {
        this.interest = interest;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void calculateInterest() {
        double interestAmount = balance * (interest / 100);
        balance += interestAmount;
        System.out.println("Interest calculated: " + interestAmount);
        System.out.println("New balance: " + balance);
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance");
        }
    }
    
    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        System.out.println("Interest Rate: " + interest + "%");
    }
}

