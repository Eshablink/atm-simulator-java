/**
 * Current account implementation that supports overdraft up to a limit.
 */
public class CurrentAccount extends Account {
    // Maximum amount the balance is allowed to go below zero.
    private final double overdraftLimit;

    public CurrentAccount(String accountNumber, String holderName, double balance, String pin, double overdraftLimit) {
        super(accountNumber, holderName, balance, pin);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean deposit(double amount) {
        // Deposit must be positive to be meaningful.
        if (amount <= 0) {
            return false;
        }

        balance += amount;
        addTransaction("Deposited: " + formatAmount(amount));
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        // Allow withdrawal as long as overdraft limit is not exceeded.
        if (amount <= 0 || (balance - amount) < -overdraftLimit) {
            return false;
        }

        balance -= amount;
        addTransaction("Withdrew: " + formatAmount(amount));
        return true;
    }

    @Override
    public String getAccountType() {
        return "Current Account";
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
