/**
 * Savings account implementation that enforces a minimum balance rule
 * and stores an interest rate as an example of account-specific state.
 */
public class SavingsAccount extends Account {
    // Extra field unique to savings accounts.
    private final double interestRate;

    // The account cannot go below this amount after a withdrawal.
    private static final double MINIMUM_BALANCE = 500.0;

    public SavingsAccount(String accountNumber, String holderName, double balance, String pin, double interestRate) {
        super(accountNumber, holderName, balance, pin);
        this.interestRate = interestRate;
    }

    @Override
    public boolean deposit(double amount) {
        // Reject invalid deposit values to keep account state correct.
        if (amount <= 0) {
            return false;
        }

        balance += amount;
        addTransaction("Deposited: " + formatAmount(amount));
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        // Prevent negative or zero withdrawals and enforce minimum balance.
        if (amount <= 0 || (balance - amount) < MINIMUM_BALANCE) {
            return false;
        }

        balance -= amount;
        addTransaction("Withdrew: " + formatAmount(amount));
        return true;
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }

    public double getInterestRate() {
        return interestRate;
    }
}
