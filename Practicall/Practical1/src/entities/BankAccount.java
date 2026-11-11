
package entities;

public class BankAccount {

    private final String accountNumber;
    private Money balance;
    private final Money overdraftLimit;
    private AccountStatus status;

    public BankAccount(String accountNumber, String currency, Money overdraftLimit) {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Invalid account number");
        }

        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Invalid currency");
        }

        if (overdraftLimit == null || overdraftLimit.getAmountInPaise() < 0 || !overdraftLimit.getCurrency().equals(currency)) {

            throw new IllegalArgumentException("Invalid overdraft limit");
        }

        this.accountNumber = accountNumber;
        this.balance = new Money(0, currency);
        this.overdraftLimit = overdraftLimit;
        this.status = AccountStatus.ACTIVE;
    }

    public void deposit(Money amount) {

        if (status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }

        if (amount == null || !amount.isPositive()) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        balance = balance.add(amount);

        System.out.println("Deposited: " + amount);
    }

    public void withdraw(Money amount) {

        if (status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }

        if (amount == null || !amount.isPositive()) {
            throw new IllegalArgumentException("Invalid withdrawal");
        }

        Money newBalance = balance.subtract(amount);

        if (newBalance.getAmountInPaise() < -overdraftLimit.getAmountInPaise()) {
            throw new IllegalArgumentException("Overdraft limit exceeded");
        }

        balance = newBalance;

        System.out.println("Withdrawn: " + amount);
    }

    public void freeze() {

        if (status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }

        status = AccountStatus.FROZEN;
    }

    public void unfreeze() {

        if (status != AccountStatus.FROZEN) {
            throw new IllegalStateException("Account is not frozen");
        }

        status = AccountStatus.ACTIVE;
    }

    public void close() {

        if (status != AccountStatus.ACTIVE || balance.getAmountInPaise() != 0) {
            throw new IllegalStateException("Cannot close account");
        }

        status = AccountStatus.CLOSED;
    }

    public Money getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountStatus getStatus() {
        return status;
    }
}
