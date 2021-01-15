package com.homekeeper.models;

import java.math.BigDecimal;
import java.util.Currency;

public class Money {
    private static final Currency CURRENCY = Currency.getInstance("RUB");
    private BigDecimal value;
    private Currency currency;
    private int precision;

    public Money(String strValue, Currency currency) throws Exception{
        this.currency = currency;
        this.precision = currency.getDefaultFractionDigits();
        if (isNumeric(strValue)) {
            this.value = new BigDecimal(strValue);
        } else {
            throw new Exception("Введенные данные содержат не числовые значения, \n либо разделитель чисел не точка!");
        }
    }

    public Money(String strValue) throws Exception{
        this(strValue, CURRENCY);
    }


    private boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    public Money multiplyByInt(int intValue) throws Exception {
        Money newValue = new Money (String.valueOf(intValue));

        newValue.value = this.value.setScale(this.precision, BigDecimal.ROUND_DOWN).multiply(newValue.value.setScale(this.precision, BigDecimal.ROUND_DOWN));
        newValue.value = newValue.value.setScale(this.precision, BigDecimal.ROUND_DOWN);
        return newValue;
    }

    public Money divideByInt(int intValue) throws Exception {
        Money newValue = new Money (String.valueOf(intValue));
        newValue.value = this.value.setScale(this.precision, BigDecimal.ROUND_DOWN).divide(newValue.value.setScale(this.precision, BigDecimal.ROUND_DOWN));
        newValue.value = newValue.value.setScale(this.precision, BigDecimal.ROUND_DOWN);
        return newValue;
    }

    @Override
    public String toString() {
        return this.value.toString() + " " + this.currency.toString();
    }

//    public static void main(String[] args) throws Exception {
//        Money money = new Money("35.50");
//        System.out.println(money);
//        System.out.println(money.multiplyByInt(5));
//        System.out.println(money.divideByInt(5));
//    }
}
