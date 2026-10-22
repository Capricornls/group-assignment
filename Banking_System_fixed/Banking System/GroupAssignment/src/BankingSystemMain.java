import banking.BankAccount;
import banking.CurrentAccount;
import banking.Loanable;
import banking.SavingsAccount;
import banking.TransactionLogger;

import java.util.ArrayList;
import java.util.Scanner;

public class BankingSystemMain {
    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<BankAccount> accounts = new ArrayList<>();

    public static void main(String[] args) {
        addExampleAccounts();

        int menuChoice = 0;

        // Keep showing the menu until the user chooses Exit.
        do {
            showMenu();

            try {
                menuChoice = Integer.parseInt(input.nextLine());
                handleMenuChoice(menuChoice);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                TransactionLogger.log(
                    0, "INPUT", 0, 0, "FAILED - Invalid menu input"
                );
            }
        } while (menuChoice != 8);

        input.close();
    }

    // Add a couple of accounts so the program has data to work with.
    private static void addExampleAccounts() {
        accounts.add(new SavingsAccount("Thabo", 1001, 1500));
        accounts.add(new CurrentAccount("Lerato", 1002, 3000));
    }

    // Print the choices available to the user.
    private static void showMenu() {
        System.out.println("\n===== BANKING SYSTEM =====");
        System.out.println("1. Display All Accounts");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Calculate Interest");
        System.out.println("6. Request Loan");
        System.out.println("7. Account Details");
        System.out.println("8. Exit");
        System.out.print("Choose option: ");
    }

    // Run the action that matches the menu choice.
    private static void handleMenuChoice(int menuChoice) {
        switch (menuChoice) {
            case 1:
                displayAllAccounts();
                break;

            case 2:
                handleTransaction("DEPOSIT");
                break;

            case 3:
                handleTransaction("WITHDRAW");
                break;

            case 4:
                transferMoney();
                break;

            case 5:
                calculateInterest();
                break;

            case 6:
                requestLoan();
                break;

            case 7:
                displayAccountDetails();
                break;

            case 8:
                System.out.println("Thank you for using the bank.");
                break;

            default:
                System.out.println("Invalid menu choice.");
                TransactionLogger.log(
                    0, "MENU", 0, 0, "FAILED - Invalid choice"
                );
        }
    }

    private static void displayAllAccounts() {
        for (BankAccount account : accounts) {
            account.displayDetails();
        }
    }

    private static void calculateInterest() {
        BankAccount account = findAccountByNumber();

        if (account != null) {
            double interest = account.calculateInterest();
            System.out.println("Interest: M" + interest);
            TransactionLogger.log(
                account.getAccountNumber(), "INTEREST",
                interest, account.getBalance(), "SUCCESS"
            );
        }
    }

    private static void displayAccountDetails() {
        BankAccount account = findAccountByNumber();

        if (account != null) {
            account.displayDetails();
        }
    }

    // Ask for an account number and return the matching account.
    private static BankAccount findAccountByNumber() {
        System.out.print("Enter account number: ");

        try {
            int accountNumber = Integer.parseInt(input.nextLine());

            for (BankAccount account : accounts) {
                if (account.getAccountNumber() == accountNumber) {
                    return account;
                }
            }

            System.out.println("Account not found.");
            TransactionLogger.log(
                accountNumber, "ACCOUNT SEARCH", 0, 0, "FAILED - Not found"
            );
        } catch (NumberFormatException e) {
            System.out.println("Invalid account number.");
            TransactionLogger.log(
                0, "ACCOUNT SEARCH", 0, 0, "FAILED - Invalid number"
            );
        }

        return null;
    }

    // Ask the user for a transaction amount.
    private static double readAmount() {
        System.out.print("Enter amount: M");
        return Double.parseDouble(input.nextLine());
    }

    // Deposit money into or withdraw money from an account.
    private static void handleTransaction(String transactionType) {
        BankAccount account = findAccountByNumber();

        if (account == null) {
            return;
        }

        double amount = 0;

        try {
            amount = readAmount();

            if (transactionType.equals("DEPOSIT")) {
                account.deposit(amount);
            } else {
                account.withdraw(amount);
            }

            System.out.println("Transaction successful.");
            TransactionLogger.log(
                account.getAccountNumber(), transactionType, amount,
                account.getBalance(), "SUCCESS"
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Transaction failed: " + e.getMessage());
            TransactionLogger.log(
                account.getAccountNumber(), transactionType, amount,
                account.getBalance(), "FAILED - " + e.getMessage()
            );
        }
    }

    // Move money from one account to another.
    private static void transferMoney() {
        System.out.println("SOURCE ACCOUNT");
        BankAccount sourceAccount = findAccountByNumber();

        if (sourceAccount == null) {
            return;
        }

        System.out.println("DESTINATION ACCOUNT");
        BankAccount destinationAccount = findAccountByNumber();

        if (destinationAccount == null) {
            return;
        }

        double amount = 0;

        try {
            amount = readAmount();

            if (sourceAccount == destinationAccount) {
                throw new IllegalArgumentException(
                    "Cannot transfer to the same account."
                );
            }

            if (amount <= 0) {
                throw new IllegalArgumentException(
                    "Transfer amount must be positive."
                );
            }

            sourceAccount.withdraw(amount);
            destinationAccount.deposit(amount);

            System.out.println("Transfer successful.");
            TransactionLogger.log(
                sourceAccount.getAccountNumber(), "TRANSFER OUT",
                amount, sourceAccount.getBalance(), "SUCCESS"
            );
            TransactionLogger.log(
                destinationAccount.getAccountNumber(), "TRANSFER IN",
                amount, destinationAccount.getBalance(), "SUCCESS"
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Transfer failed: " + e.getMessage());
            TransactionLogger.log(
                sourceAccount.getAccountNumber(), "TRANSFER",
                amount, sourceAccount.getBalance(),
                "FAILED - " + e.getMessage()
            );
        }
    }

    // Request a loan for the selected account.
    private static void requestLoan() {
        BankAccount account = findAccountByNumber();

        if (account == null) {
            return;
        }

        double amount = 0;

        try {
            amount = readAmount();
            Loanable loanAccount = (Loanable) account;
            loanAccount.requestLoan(amount);

            System.out.println("Loan approved.");
            TransactionLogger.log(
                account.getAccountNumber(), "LOAN",
                amount, account.getBalance(), "SUCCESS"
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Loan rejected: " + e.getMessage());
            TransactionLogger.log(
                account.getAccountNumber(), "LOAN",
                amount, account.getBalance(), "FAILED - " + e.getMessage()
            );
        }
    }
}
