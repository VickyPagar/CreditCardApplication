package com.firstbank.creditcard.service;

import java.io.Serializable;

/**
 * CardTypeInfo - SOAP response bean for available card types.
 */
public class CardTypeInfo implements Serializable {
    private static final long serialVersionUID = 6789012345678901234L;

    private int    cardTypeId;
    private String cardTypeCode;
    private String cardTypeName;
    private double creditLimitMin;
    private double creditLimitMax;
    private double annualFee;
    private double interestRate;
    private double minIncomeRequired;

    public CardTypeInfo() { }

    public int getCardTypeId() { return cardTypeId; }
    public void setCardTypeId(int cardTypeId) { this.cardTypeId = cardTypeId; }

    public String getCardTypeCode() { return cardTypeCode; }
    public void setCardTypeCode(String cardTypeCode) { this.cardTypeCode = cardTypeCode; }

    public String getCardTypeName() { return cardTypeName; }
    public void setCardTypeName(String cardTypeName) { this.cardTypeName = cardTypeName; }

    public double getCreditLimitMin() { return creditLimitMin; }
    public void setCreditLimitMin(double creditLimitMin) { this.creditLimitMin = creditLimitMin; }

    public double getCreditLimitMax() { return creditLimitMax; }
    public void setCreditLimitMax(double creditLimitMax) { this.creditLimitMax = creditLimitMax; }

    public double getAnnualFee() { return annualFee; }
    public void setAnnualFee(double annualFee) { this.annualFee = annualFee; }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    public double getMinIncomeRequired() { return minIncomeRequired; }
    public void setMinIncomeRequired(double minIncomeRequired) { this.minIncomeRequired = minIncomeRequired; }
}
