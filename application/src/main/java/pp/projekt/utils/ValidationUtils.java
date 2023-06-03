package pp.projekt.utils;

import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    public static boolean validateIban(String enteredRecipientIban) {
        try {
            IbanUtil.validate(enteredRecipientIban);
            return true;
        } catch (IbanFormatException | InvalidCheckDigitException | UnsupportedCountryException e) {
            return false;
        }
    }

    public static String getCurrency(String enteredAmount) {
        Matcher matcher = Pattern.compile("(?<amount>\\d+(\\.\\d{2})?) (?<currencyCode>[A-Z]{3})").matcher(enteredAmount);
        if (!matcher.matches()) {
            return null;
        }
        return matcher.group("currencyCode");
    }

    public static BigDecimal validateAmount(String enteredAmount) {
        Matcher matcher = Pattern.compile("(?<amount>\\d+(\\.\\d{2})?) (?<currencyCode>[A-Z]{3})").matcher(enteredAmount);
        if (!matcher.matches()) {
            return null;
        }
        return new BigDecimal(matcher.group("amount"));
    }
}

