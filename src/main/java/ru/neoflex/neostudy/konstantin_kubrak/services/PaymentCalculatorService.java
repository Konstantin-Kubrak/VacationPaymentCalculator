package ru.neoflex.neostudy.konstantin_kubrak.services;

import org.springframework.stereotype.Component;

@Component
public interface PaymentCalculatorService {


    String getVacationPaymentCalculation(Double salaryYearAverage, Integer vacationDays);
    String getVacationPaymentCalculation(Double salaryYearAverage, Integer vacationDays, String vacationStartDate);
}
