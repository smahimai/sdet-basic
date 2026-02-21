public class AccountDemo {
    public static void main(String[] args) {
       
        
        // Demonstrate polymorphism
        System.out.println("5. Demonstrating Polymorphism:");
        Account account1 = new SavingsAccount("SA002", 5000.0, 5.0, 500.0);
        Account account2 = new CurrentAccount("CA002", 3000.0, 1.5, 1500.0);
        
        System.out.println("\nProcessing Savings Account (polymorphic):");
        account1.calculateInterest();
        account1.displayAccountInfo();
        
        System.out.println("\nProcessing Current Account (polymorphic):");
        account2.calculateInterest();
        account2.displayAccountInfo();
    }
}

