package com.eminyilmazz.loanbrokerapp.utility;

public class CreditScoreService {

    private CreditScoreService() {
    }

    public static Integer calculateCreditScore(Long tckn) {
        int creditScore = Math.toIntExact(tckn % 1000);
        return creditScore > 900 ? 1000 : creditScore;
    }
}
