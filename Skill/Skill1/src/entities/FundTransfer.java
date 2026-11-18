package entities;
import java.time.LocalDateTime;
import java.util.UUID;

public class FundTransfer {
    private final String transferId;
    private final BankAccount source;
    private final BankAccount destination;
    private final Money amount;

    public FundTransfer(BankAccount source, BankAccount destination, Money amount) {
        this.transferId = UUID.randomUUID().toString();
        this.source = source;
        this.destination = destination;
        this.amount = amount;
    }

    public void transfer() {
        if (source == null || destination == null || amount == null)
            throw new IllegalArgumentException("Invalid transfer details");

        if (source == destination)
            throw new IllegalArgumentException("Cannot transfer to same account");

        if (!amount.isPositive())
            throw new IllegalArgumentException("Invalid transfer amount");

        if (source.getStatus() != AccountStatus.ACTIVE ||
                destination.getStatus() != AccountStatus.ACTIVE)
            throw new IllegalStateException("Both accounts must be active");

        if (!source.getBalance().getCurrency().equals(amount.getCurrency()) ||
                !destination.getBalance().getCurrency().equals(amount.getCurrency()))
            throw new IllegalArgumentException("Currency mismatch");

        source.withdraw(amount);
        destination.deposit(amount);

        System.out.println("Transfer successful!");
        System.out.println("Transfer ID: " + transferId);
    }
}
