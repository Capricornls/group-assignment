package banking;

public class CurrentAccount extends BankAccount implements Loanable
{
    private final double overdraftLimit = 2000.00;
    private final double loanLimit = 10000.00;

    public CurrentAccount(String accountHolder, int accountNumber, double balance)
    {
        super(accountHolder, accountNumber, balance);
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

        if (getBalance() - amount < -overdraftLimit)
        {
            throw new IllegalArgumentException("Withdrawal exceeds the M2000.00 overdraft limit.");
        }

        setBalance(getBalance() - amount);
    }

    @Override
    public double calculateInterest()
    {
        System.out.println("Current account cannot earn interest.");
        return 0.0;
    }

    @Override
    public double getOverdraftLimit()
    {
        return overdraftLimit;
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
            throw new IllegalArgumentException("Loan must be positive and cannot exceed M10000.00.");
        }

        setBalance(getBalance() + amount);
    }

    @Override
    public void displayDetails()
    {
        super.displayDetails();
        System.out.println("Account type: Current Account");
        System.out.println("Interest rate: 0%");
        System.out.println("Overdraft limit: M" + overdraftLimit);
        System.out.println("Loan limit: M" + loanLimit);
    }
}
