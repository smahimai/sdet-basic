public class SavingsAccount extends Account {

    private double minimumBalance;
    
    public SavingsAccount(String accountNumber, double balance, double interest, double minimumBalance) {
        super(accountNumber, balance, interest);
        this.minimumBalance = minimumBalance;
    }
    
    public double getMinimumBalance() {
        return minimumBalance;
    }
    
    public void calculateInterest() {
        // Savings accounts may have compound interest or higher rates
        double interestAmount = balance * (interest / 100);
        balance += interestAmount;
        System.out.println("Savings Account - Interest calculated: " + interestAmount);
        System.out.println("New balance: " + balance);
    }
    
    public void withdraw(double amount) {
        if (amount > 0) {
            if (balance - amount >= minimumBalance) {
                balance -= amount;
                System.out.println("Withdrawn from Savings Account: " + amount);
            } else {
                System.out.println("Withdrawal denied. Minimum balance of " + minimumBalance + " must be maintained.");
            }
        } else {
            System.out.println("Invalid withdrawal amount");
        }
    }
    
    public void displayAccountInfo() {
        System.out.println("=== Savings Account ===");
        super.displayAccountInfo();
        System.out.println("Minimum Balance Required: " + minimumBalance);
        System.out.println("Account Type: Savings");
    }
}
