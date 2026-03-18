# BankingSystem ATM Simulator

## Project overview
BankingSystem is a Java console ATM simulator that demonstrates how a small banking application can be designed using object-oriented programming. The program includes an abstract `Account` base class, specialized `SavingsAccount` and `CurrentAccount` types, a `Bank` manager class, PIN-based authentication, and transaction history tracking for the most recent five transactions per account.

Two demo accounts are preloaded for easy testing:
- Savings account: `SA1001` with PIN `1234`
- Current account: `CA2001` with PIN `4321`

## How to run
1. Compile the source files:
   ```bash
   javac -d out src/*.java
   ```
2. Run the ATM console application:
   ```bash
   java -cp out ATM
   ```

## OOP concepts demonstrated
- **Abstraction:** `Account` is an abstract class that defines shared fields and abstract transaction methods.
- **Inheritance:** `SavingsAccount` and `CurrentAccount` inherit from `Account`.
- **Encapsulation:** account data such as account number, holder name, PIN, and transaction history are protected with private fields and accessor methods.
- **Polymorphism:** the ATM and bank operate on `Account` references, while each subclass applies its own deposit and withdrawal rules.

## Features implemented
- Open, close, and search accounts through the `Bank` class.
- Validate a 4-digit PIN before login and before each deposit/withdrawal.
- View account balance.
- Deposit and withdraw funds with account-specific rules.
- Store and display the last five transactions.
- Simple console-based ATM menu.

## Sample output screenshot description
Because this project is a console application, the “screenshot” would show a terminal window displaying the BankingSystem banner, login prompts for account number and PIN, the numbered ATM menu, and example output for balance checks or transaction history.
