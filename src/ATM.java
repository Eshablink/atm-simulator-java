import java.util.List;
import java.util.Scanner;

/**
 * Console-based ATM entry point for the BankingSystem application.
 */
public class ATM {
    public static void main(String[] args) {
        // Create the bank and preload demo accounts so the simulator is usable immediately.
        Bank bank = new Bank();
        seedSampleAccounts(bank);

        // Scanner reads user input from the console menu.
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== Welcome to BankingSystem ATM =====");

        // Ask the user to select the account they want to operate.
        Account account = authenticateAccount(bank, scanner);
        if (account == null) {
            System.out.println("Authentication failed. Exiting system.");
            scanner.close();
            return;
        }

        // Repeatedly show the menu until the user chooses to exit.
        boolean running = true;
        while (running) {
            printMenu(account);
            int choice = readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    // Balance inquiry is a read-only operation.
                    System.out.printf("Current balance: %.2f%n", account.getBalance());
                    break;
                case 2:
                    // PIN is validated before every transaction as required.
                    if (validateTransactionPin(account, scanner)) {
                        double depositAmount = readDouble(scanner, "Enter deposit amount: ");
                        if (account.deposit(depositAmount)) {
                            System.out.println("Deposit successful.");
                        } else {
                            System.out.println("Deposit failed. Enter a positive amount.");
                        }
                    }
                    break;
                case 3:
                    // Withdrawal uses polymorphism so each account enforces its own rules.
                    if (validateTransactionPin(account, scanner)) {
                        double withdrawAmount = readDouble(scanner, "Enter withdrawal amount: ");
                        if (account.withdraw(withdrawAmount)) {
                            System.out.println("Withdrawal successful.");
                        } else {
                            System.out.println("Withdrawal failed due to invalid amount or account limits.");
                        }
                    }
                    break;
                case 4:
                    // Show the stored last 5 transactions for quick account review.
                    displayTransactions(account);
                    break;
                case 5:
                    // Gracefully stop the application loop.
                    running = false;
                    System.out.println("Thank you for using BankingSystem ATM.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }

        scanner.close();
    }

    /**
     * Adds demo accounts to make the ATM easy to test without manual account creation.
     */
    private static void seedSampleAccounts(Bank bank) {
        bank.openAccount(new SavingsAccount("SA1001", "Alice Johnson", 2500.0, "1234", 3.5));
        bank.openAccount(new CurrentAccount("CA2001", "Bob Smith", 1200.0, "4321", 1000.0));
    }

    /**
     * Authenticates once at login using account number and PIN.
     */
    private static Account authenticateAccount(Bank bank, Scanner scanner) {
        System.out.println("Available demo accounts:");
        for (Account account : bank.getAccounts()) {
            System.out.printf("- %s (%s)%n", account.getAccountNumber(), account.getAccountType());
        }

        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Enter account number: ");
            String accountNumber = scanner.nextLine().trim();
            Account account = bank.searchAccount(accountNumber);

            if (account == null) {
                System.out.println("Account not found.");
                continue;
            }

            System.out.print("Enter 4-digit PIN: ");
            String pin = scanner.nextLine().trim();
            if (account.validatePin(pin)) {
                System.out.println("Login successful. Welcome, " + account.getHolderName() + "!");
                return account;
            }

            System.out.println("Incorrect PIN.");
        }
        return null;
    }

    /**
     * Validates the PIN again before any deposit or withdrawal.
     */
    private static boolean validateTransactionPin(Account account, Scanner scanner) {
        System.out.print("Re-enter PIN for transaction approval: ");
        String pin = scanner.nextLine().trim();
        if (!account.validatePin(pin)) {
            System.out.println("PIN validation failed. Transaction cancelled.");
            return false;
        }
        return true;
    }

    /**
     * Prints the main ATM options.
     */
    private static void printMenu(Account account) {
        System.out.println("----- Main Menu -----");
        System.out.println("Account: " + account.getAccountNumber() + " | Type: " + account.getAccountType());
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. View Transactions");
        System.out.println("5. Exit");
    }

    /**
     * Displays recent transactions from newest logical set retained by the account.
     */
    private static void displayTransactions(Account account) {
        List<String> transactions = account.getTransactionHistory();
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        System.out.println("Last " + transactions.size() + " transaction(s):");
        for (String transaction : transactions) {
            System.out.println("* " + transaction);
        }
    }

    /**
     * Reads an integer safely so invalid text input does not crash the application.
     */
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a decimal amount safely for deposits and withdrawals.
     */
    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
