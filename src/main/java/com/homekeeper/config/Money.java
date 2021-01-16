package com.homekeeper.config;

import com.homekeeper.exceptions.IllegalMoneyFormatException;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {
    private static final Currency CURRENCY = Currency.getInstance("RUB");
    private BigDecimal value;
    private Currency currency;
    private int precision;

    public Money(String strValue, Currency currency) throws IllegalMoneyFormatException{
        this.currency = currency;
        this.precision = currency.getDefaultFractionDigits();
        if (isNumeric(strValue)) {
            this.value = new BigDecimal(strValue);
        } else {
            throw new IllegalMoneyFormatException("Данные '" + strValue + "' содержат не числовые значения, \n либо разделитель чисел не точка!", strValue);
        }
    }

    public Money(String strValue) throws IllegalMoneyFormatException{
        this(strValue, CURRENCY);
    }

    public BigDecimal getValue() {
        return value.setScale(this.precision, BigDecimal.ROUND_DOWN);
    }

    public Currency getCurrency() {
        return currency;
    }

    private boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    public Money multiplyByInt(int intValue) throws IllegalMoneyFormatException {
        Money newValue = new Money (String.valueOf(intValue));

        newValue.value = this.value.setScale(this.precision, BigDecimal.ROUND_DOWN).multiply(newValue.value.setScale(this.precision, BigDecimal.ROUND_DOWN));
        newValue.value = newValue.value.setScale(this.precision, BigDecimal.ROUND_DOWN);
        return newValue;
    }

    public Money divideByInt(int intValue) throws IllegalMoneyFormatException {
        Money newValue = new Money (String.valueOf(intValue));
        newValue.value = this.value.setScale(this.precision, BigDecimal.ROUND_DOWN).divide(newValue.value.setScale(this.precision, BigDecimal.ROUND_DOWN));
        newValue.value = newValue.value.setScale(this.precision, BigDecimal.ROUND_DOWN);
        return newValue;
    }

    @Override
    public String toString() {
        return this.value.toString() + " " + this.currency.toString();
    }

}