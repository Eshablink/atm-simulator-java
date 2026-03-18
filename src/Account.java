import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class that captures the common state and behavior
 * shared by all bank account types in the ATM simulator.
 */
public abstract class Account {
    // Unique number used to identify the account in the bank.
    private final String accountNumber;

    // Human-readable name of the account owner.
    private final String holderName;

    // Four-digit PIN required before any transaction is allowed.
    private final String pin;

    // Current money available in the account.
    protected double balance;

    // Stores only the most recent 5 transactions for quick review.
    private final ArrayList<String> transactionHistory;

    /**
     * Creates a new account with the required common fields.
     */
    public Account(String accountNumber, String holderName, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account opened with balance: " + formatAmount(balance));
    }

    // Abstract deposit behavior is specialized by each account type.
    public abstract boolean deposit(double amount);

    // Abstract withdrawal behavior is specialized by each account type.
    public abstract boolean withdraw(double amount);

    // Polymorphic method to identify the concrete account type.
    public abstract String getAccountType();

    /**
     * Validates that the entered PIN matches the stored 4-digit PIN.
     */
    public boolean validatePin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    /**
     * Adds a transaction and trims the history so only the last 5 remain.
     */
    protected void addTransaction(String transaction) {
        transactionHistory.add(transaction);
        if (transactionHistory.size() > 5) {
            transactionHistory.remove(0);
        }
    }

    /**
     * Returns a defensive copy to preserve encapsulation.
     */
    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Utility helper for consistent currency formatting across the app.
     */
    protected String formatAmount(double amount) {
        return String.format("%.2f", amount);
    }
}
