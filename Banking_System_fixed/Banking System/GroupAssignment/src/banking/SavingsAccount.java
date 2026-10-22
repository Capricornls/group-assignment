package banking;

public class SavingsAccount extends BankAccount implements Loanable
{
    private final double interestRate = 0.05;
    private final double minimumBalance = 100.00;
    private final double loanLimit = 5000.00;

    public SavingsAccount(String accountHolder, int accountNumber, double balance)
    {
        super(accountHolder, accountNumber, balance);

        if (balance < minimumBalance)
        {
            throw new IllegalArgumentException("Savings opening balance must be at least M100.00.");
        }
    }

    @Override
    public void deposit(double amount)
    {
        if (amount <= 0)
        {
            throw new IllegalArgumentException("Deposit must be greater than zero.");
        }

        setBalance(getBalance() + amount);
    }

    @Override
    public void withdraw(double amount)
    {
        if (amount <= 0)
        {
            throw new IllegalArgumentException("Withdrawal must be greater than zero.");
        }

        if (getBalance() - amount < minimumBalance)
        {
            throw new IllegalArgumentException("Savings account must maintain M100.00.");
        }

        setBalance(getBalance() - amount);
    }

    @Override
    public double calculateInterest()
    {
        return getBalance() * interestRate;
    }

    @Override
    public double getOverdraftLimit()
    {
        return 0.00;
    }

    @Override
    public double getLoanLimit()
    {
        return loanLimit;
    }

    @Override
    public void requestLoan(double amount)
    {
        if (amount <= 0 || amount > loanLimit)
        {
            throw new IllegalArgumentException("Loan must be positive and cannot exceed M5000.00.");
        }

        setBalance(getBalance() + amount);
    }

    @Override
    public void displayDetails()
    {
        super.displayDetails();
        System.out.println("Account type: Savings Account");
        System.out.println("Interest rate: 5% per year");
        System.out.println("Minimum balance: M" + minimumBalance);
        System.out.println("Loan limit: M" + loanLimit);
    }
}
