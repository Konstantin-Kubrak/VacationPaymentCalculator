package ru.neoflex.neostudy.konstantin_kubrak.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.neostudy.konstantin_kubrak.services.PaymentCalculatorService;

@RestController
public class VacationPayment {

    @Autowired
    PaymentCalculatorService paymentCalculatorService;


    @GetMapping(value = "/calculacte", produces = "application/json")
    public String getVacationPayment(
            @RequestParam("salaryMonthAverage") Double salaryMonthAverage,
            @RequestParam(value = "vacationDays") Integer vacationDays,
            @RequestParam(required = false, value = "vacationStart") String vacationStartDate)

     {
        if (vacationStartDate == null || vacationStartDate.isEmpty()) {

            return paymentCalculatorService.getVacationPaymentCalculation(salaryMonthAverage, vacationDays);
        } else
            return paymentCalculatorService.getVacationPaymentCalculation(salaryMonthAverage, vacationDays, vacationStartDate);
    }
}






