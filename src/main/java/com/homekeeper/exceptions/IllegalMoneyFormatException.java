package com.homekeeper.exceptions;

public class IllegalMoneyFormatException extends RuntimeException{
    private String strValue;
    public String getStrValue() { return strValue; }
    public IllegalMoneyFormatException(String message, String strValue) {
        super(message);
        this.strValue = strValue;
    }

}
