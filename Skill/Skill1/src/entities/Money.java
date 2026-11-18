package entities;

public class Money {
    private final long amountInPaise;

    private final String currency;

    public Money(long amountInPaise, String currency) {
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Invalid Currency");
        }
        this.amountInPaise = amountInPaise;
        this.currency = currency;
    }

    public Money add(Money other) {
        checkCurrency(other);

        return new Money(Math.addExact(amountInPaise, other.amountInPaise), currency);
    }

    public Money subtract(Money other) {
        checkCurrency(other);

        return new Money(Math.subtractExact(this.amountInPaise, other.amountInPaise), currency);
    }

    public boolean isPositive() {
        return this.amountInPaise > 0;
    }

    public void checkCurrency(Money other) {
        if (other == null || !this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }

    public long getAmountInPaise() {
        return amountInPaise;
    }

    public String getCurrency() {
        return currency;
    }

    public String toString() {
        return currency + " " + (amountInPaise / 100.0);
    }

}