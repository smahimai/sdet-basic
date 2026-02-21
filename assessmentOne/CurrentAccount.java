public class CurrentAccount extends Account {
    
    private double overdraftLimit;
    
    public CurrentAccount(String accountNumber, double balance, double interest, double overdraftLimit) {
        super(accountNumber, balance, interest);
        this.overdraftLimit = overdraftLimit;
    }
    
    public double getOverdraftLimit() {
        return overdraftLimit;
    }
    
    public void calculateInterest() {
        if (balance > 0) {
            double interestAmount = balance * (interest / 100);
            balance += interestAmount;
            System.out.println("Current Account - Interest calculated: " + interestAmount);
            System.out.println("New balance: " + balance);
        } else {
            System.out.println("Current Account - No interest on negative balance");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0) {
            if (balance - amount >= -overdraftLimit) {
                balance -= amount;
                System.out.println("Withdrawn from Current Account: " + amount);
                if (balance < 0) {
                    System.out.println("Warning: Account is in overdraft. Balance: " + balance);
                }
            } else {
                System.out.println("Withdrawal denied. Overdraft limit of " + overdraftLimit + " exceeded.");
            }
        } else {
            System.out.println("Invalid withdrawal amount");
        }
    }
    
    public void displayAccountInfo() {
        System.out.println("=== Current Account ===");
        super.displayAccountInfo();
        System.out.println("Overdraft Limit: " + overdraftLimit);
        System.out.println("Account Type: Current");
    }
}

