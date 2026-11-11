package entities;
import java.time.LocalDateTime;
import java.util.UUID;

public class LedgerEntry {
    private final String transactionId;
    private final String transactionType;
    private final Money amount;
    private final String timestamp;

    public LedgerEntry(String type, Money amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.transactionType = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Money getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void display() {
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Type: " + transactionType);
        System.out.println("Amount: " + amount);
        System.out.println("Timestamp: " + timestamp);
    }
}
