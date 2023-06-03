package pp.projekt.view.fileToPdfConverter;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {
    public final BigDecimal amount;
    public final Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency.toString();
    }
}
