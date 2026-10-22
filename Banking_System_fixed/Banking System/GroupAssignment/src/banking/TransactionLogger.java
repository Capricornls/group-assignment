package banking;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TransactionLogger 
{
    private static final String FILE_NAME = "transactions.txt";

    private TransactionLogger() 
    {
        
    }

    public static void log(int accountNumber, String type,double amount, double balance, String message) 
    {

        String date = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String record = date
            + " | Account: " + accountNumber
            + " | Type: " + type
            + " | Amount: M" + amount
            + " | Balance: M" + balance
            + " | Result: " + message;

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) 
        {
            writer.write(record + System.lineSeparator());
        } 
        catch (IOException e) 
        {
            System.out.println("Could not write transaction log: "
                + e.getMessage());
        }
    }

    public static void logTransfer(int source, int destination,double amount, double balance,String message) 
    {

        String date = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String record = date
            + " | Source: " + source
            + " | Destination: " + destination
            + " | Type: TRANSFER"
            + " | Amount: M" + amount
            + " | Source balance: M" + balance
            + " | Result: " + message;

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) 
        {
            writer.write(record + System.lineSeparator());
        } 
        catch (IOException e) 
        {
            System.out.println("Could not write transaction log: "
                + e.getMessage());
        }
    }
}
