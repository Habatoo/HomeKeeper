package com.homekeeper;

import com.homekeeper.config.Money;
import com.homekeeper.exceptions.IllegalMoneyFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyModuleTests {
    @Test
    @DisplayName("Проверяет формат введенных данных - два знака после запятой, значение валюты")
    void testMoneyContent() {
        // RUB test
        String strValueRUB = "35.50";
        Currency currencyRUB = Currency.getInstance("RUB");
        Money moneyRUB = new Money(strValueRUB); // по умолчанию валюта рубль
        BigDecimal newStrValueRUB = new BigDecimal(strValueRUB);
        newStrValueRUB.setScale(currencyRUB.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN);
        assertEquals(newStrValueRUB, moneyRUB.getValue());
        assertEquals(Currency.getInstance("RUB"), moneyRUB.getCurrency());

        // USD test
        Currency currencyUSD = Currency.getInstance("USD");
        String strValueUSD = "35.50";
        Money moneyUSD = new Money(strValueUSD, currencyUSD);
        BigDecimal newStrValueUSD = new BigDecimal(strValueUSD);
        newStrValueUSD.setScale(currencyUSD.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN);
        assertEquals(newStrValueUSD, moneyUSD.getValue());
        assertEquals(currencyUSD, moneyUSD.getCurrency());
    }

    @Test
    @DisplayName("Проверяет данные - введены только числа, разделитель точка")
    void shouldThrowIllegalMoneyFormatException() throws IllegalMoneyFormatException{
        // RUB test
        Currency currencyRUB = Currency.getInstance("RUB");
        // Буква в числовых данных
        String strValueRUBLetter = "k5.50";
        IllegalMoneyFormatException thrownRUBLetter = assertThrows(
                IllegalMoneyFormatException.class,
                () -> new Money(strValueRUBLetter),
                "Данные содержат не числовые значения, \n либо разделитель чисел не точка!"
        );
        assertTrue(thrownRUBLetter.getMessage().contains("Данные "));
        // Разделитель запятая
        String strValueRUBComma = "35,50";
        IllegalMoneyFormatException thrownRUBComma = assertThrows(
                IllegalMoneyFormatException.class,
                () -> new Money(strValueRUBComma),
                "Данные содержат не числовые значения, \n либо разделитель чисел не точка!"
        );
        assertTrue(thrownRUBComma.getMessage().contains("Данные "));

        // USD test
        Currency currencyUSD = Currency.getInstance("USD");
        // Буква в числовых данных
        String strValueUSDLetter = "k5.50";
        IllegalMoneyFormatException thrownUSDLetter = assertThrows(
                IllegalMoneyFormatException.class,
                () -> new Money(strValueRUBLetter, currencyUSD),
                "Данные содержат не числовые значения, \n либо разделитель чисел не точка!"
        );
        assertTrue(thrownUSDLetter.getMessage().contains("Данные "));
        // Разделитель запятая
        String strValueUSDComma = "35,50";
        IllegalMoneyFormatException thrownUSDComma = assertThrows(
                IllegalMoneyFormatException.class,
                () -> new Money(strValueUSDComma, currencyUSD),
                "Данные содержат не числовые значения, \n либо разделитель чисел не точка!"
        );
        assertTrue(thrownUSDComma.getMessage().contains("Данные "));
    }

    @Test
    @DisplayName("Проверяет умножение и деление денежных единиц на целые числа - два знака после запятой, значение валюты")
    void testMoneyOperations() {
        Object object;

        // RUB test
        String strValueRUB = "35.05";
        int valueIntRUB = 5;
        Currency currencyRUB = Currency.getInstance("RUB");
        Money moneyRUB = new Money(strValueRUB); // по умолчанию валюта рубль

        BigDecimal newStrValueRUB = new BigDecimal(strValueRUB);
        newStrValueRUB.setScale(currencyRUB.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN);

        BigDecimal newValueIntRUB = new BigDecimal(valueIntRUB);
        newValueIntRUB.setScale(currencyRUB.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN);
        BigDecimal resultTestRUBMultiply = newStrValueRUB.multiply(newValueIntRUB);
        BigDecimal resultTestRUBDivision = newStrValueRUB.divide(newValueIntRUB);

        assertEquals(resultTestRUBMultiply.setScale(currencyRUB.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN), moneyRUB.multiplyByInt(valueIntRUB).getValue());
        assertEquals(resultTestRUBDivision.setScale(currencyRUB.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN), moneyRUB.divideByInt(valueIntRUB).getValue());
        assertEquals(currencyRUB, moneyRUB.getCurrency());

        // USD test
        Currency currencyUSD = Currency.getInstance("USD");
        String strValueUSD = "56.70";
        int valueIntUSD = 7;
        Money moneyUSD = new Money(strValueUSD, currencyUSD);

        BigDecimal newStrValueUSD = new BigDecimal(strValueUSD);
        newStrValueUSD.setScale(currencyUSD.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN);

        BigDecimal newValueIntUSD = new BigDecimal(valueIntUSD);
        newValueIntUSD.setScale(currencyUSD.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN);
        BigDecimal resultTestUSDMultiply = newStrValueUSD.multiply(newValueIntUSD);
        BigDecimal resultTestUSDDivision = newStrValueUSD.divide(newValueIntUSD);

        assertEquals(resultTestUSDMultiply, moneyUSD.multiplyByInt(valueIntUSD).getValue());
        assertEquals(resultTestUSDDivision, moneyUSD.divideByInt(valueIntUSD).getValue());
        assertEquals(currencyUSD, moneyUSD.getCurrency());
    }

}