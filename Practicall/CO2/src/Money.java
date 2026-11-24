import java.util.Locale;

public record Money(long paise, String currency) {
    public Money {
        if (paise < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be empty");
        }
        currency = currency.toUpperCase(Locale.ROOT);
    }

    public Money times(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        return new Money(Math.multiplyExact(paise, quantity), currency);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s %d.%02d",
                currency, paise / 100, paise % 100);
    }
}