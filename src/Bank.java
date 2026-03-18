import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Bank acts as the manager for all accounts in the simulator.
 * It demonstrates aggregation by keeping a collection of Account objects.
 */
public class Bank {
    // Central collection of all active accounts.
    private final ArrayList<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Opens a new account if the account number does not already exist.
     */
    public boolean openAccount(Account account) {
        if (searchAccount(account.getAccountNumber()) != null) {
            return false;
        }

        accounts.add(account);
        return true;
    }

    /**
     * Closes an account by account number.
     */
    public boolean closeAccount(String accountNumber) {
        Iterator<Account> iterator = accounts.iterator();
        while (iterator.hasNext()) {
            Account account = iterator.next();
            if (account.getAccountNumber().equals(accountNumber)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Searches the list and returns the matching account, if found.
     */
    public Account searchAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Exposes a copy of the account list for read-only style viewing.
     */
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }
}
