package com.eminyilmazz.loanbrokerapp.utility;

import com.eminyilmazz.loanbrokerapp.model.Customer;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanApplicationDto;
import com.eminyilmazz.loanbrokerapp.model.dto.LoanResponseDto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class LoanUtility {
    private static final Double CREDIT_LIMIT_MULTIPLIER = 4D;

    private LoanUtility() {}

    public static LoanResponseDto processApplication(Customer customer, LoanApplicationDto application) {
        LoanResponseDto loan = new LoanResponseDto();
        double amount = 0.0;
        double assurance = application.getAssurance();
        double salary = customer.getMonthlySalary();
        Integer creditScore = customer.getCreditScore();

        if (creditScore < 500) {
            loan.setAmount(amount);
            loan.setApproved(false);
            return loan;
        }

        double bonus = getAssuranceBonus(salary, assurance, creditScore);
        loan.setApproved(true);
        if (creditScore < 1000) {
            if (salary < 5000) amount = 10000.0 + bonus;
            else if (salary < 10000) amount = 20000 + bonus;
            else amount = salary * CREDIT_LIMIT_MULTIPLIER / 2 + bonus;
        } else {
            amount = salary * CREDIT_LIMIT_MULTIPLIER + bonus;
        }
        loan.setAmount(amount);
        return loan;
    }

    private static double getAssuranceBonus(double salary, double assurance, int creditScore) {
        if (assurance <= 0.0) return 0.0; //No assurance

        if (creditScore == 1000) return salary * 1.5; //Credit score -> 1000

        if (salary < 5000) return assurance * 1.1; //Credit score -> 500 < x < 1000
        else if (salary < 10000) return assurance * 1.2; //Credit score -> 500 < x < 1000
        else return assurance * 1.25; //Credit score -> 500 < x < 1000
    }

    public static String formatCurrency(double d){
        String amount = String.valueOf(d);
        Locale locale = new Locale("en", "US");
        Currency currency = Currency.getInstance(locale);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setCurrency(currency);
        BigDecimal bigDecimal = new BigDecimal(amount);
        return numberFormat.format(bigDecimal);
    }
}
