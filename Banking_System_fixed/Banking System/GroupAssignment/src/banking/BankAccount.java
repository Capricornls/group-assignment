package banking;

public abstract class BankAccount
{
    protected String accountHolder;
    protected int accountNumber;
    protected double balance;

    public BankAccount(String accountHolder, int accountNumber, double balance)
    {
        if (accountHolder == null || accountHolder.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid account holder.");
        }
        if (accountNumber <= 0 || balance < 0) {
            throw new IllegalArgumentException("Invalid account details.");
        }

        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountHolder()
    {
        return accountHolder;
    }

    public int getAccountNumber()
    {
        return accountNumber;
    }

    protected void setBalance(double balance)
    {
        this.balance = balance;
    }

    public double getBalance()
    {
        return balance;
    }

    public void displayDetails()
    {
        System.out.println();
        System.out.println("Account holder: " + accountHolder);
        System.out.println("Account number: " + accountNumber);
        System.out.printf("Balance: M%.2f%n", balance);
    }

    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    public abstract double calculateInterest();

    public abstract double getOverdraftLimit();

    public abstract double getLoanLimit();
}
