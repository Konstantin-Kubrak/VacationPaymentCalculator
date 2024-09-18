package ru.neoflex.neostudy.konstantin_kubrak.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.neoflex.neostudy.konstantin_kubrak.services.impl.PaymentCalculatorServiceImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentCalculatorServiceTest {

    @Autowired
    PaymentCalculatorServiceImpl paymentCalculatorService;

    @Test
    @DisplayName("Получение отпускных без даты начала отпуска")
    void getVacationPaymentCalculation() {
        //given
        double averageSalary = 120_000;
        String answerVacationOver1Year = "Количество дней отпуска превышает календарный год: 366";
        String answerVacationLess1Day = "Введено недопустимое количество дней отпуска: -1";
        String correctAnswer = "Количество дней отпуска: 25\nОплата: 102389.08";

        //when
        String resultVacationOver1Year = paymentCalculatorService.getVacationPaymentCalculation(averageSalary,366);
        String resultVacationLess1Day = paymentCalculatorService.getVacationPaymentCalculation(averageSalary,-1);
        String resultCorrectAnswer = paymentCalculatorService.getVacationPaymentCalculation(averageSalary,25);

        //then
        assertThat(resultVacationOver1Year).isEqualTo(answerVacationOver1Year);
        assertThat(resultVacationLess1Day).isEqualTo(answerVacationLess1Day);
        assertThat(resultCorrectAnswer).isEqualTo(correctAnswer);
    }

    @Test
    @DisplayName("Получение отпускных при условии наличия даты начала отпуска")
    void testGetVacationPaymentCalculationWithStartingDate() {

        //given
        double averageSalary = 120_000;
        long amountOfWeekendsAndHolidaysInYearMinusDecember = LocalDate.of(LocalDate.now().getYear(),1,1)
                .datesUntil(LocalDate.of(LocalDate.now().getYear(),11,30).plusDays(1))
                .filter(e -> e.getDayOfWeek() == DayOfWeek.SATURDAY || e.getDayOfWeek() == DayOfWeek.SUNDAY || paymentCalculatorService.getHolidays().containsKey(e))
                .count();
        long amountOfDaysInYearMinusDecember = LocalDate.of(LocalDate.now().getYear(),1,1)
                .datesUntil(LocalDate.of(LocalDate.now().getYear(),11,30).plusDays(1))
                .count();
        String answerVacationOver1Year = "Количество дней отпуска превышает календарный год: 366";
        String answerVacationLess1Day = "Введено недопустимое количество дней отпуска: -1";
        String answerCorrect = "Количество дней отпуска: " + (amountOfDaysInYearMinusDecember-amountOfWeekendsAndHolidaysInYearMinusDecember) + "\nОплата: 933788.4";

        //when
        String resultVacationOver1Year = paymentCalculatorService.getVacationPaymentCalculation(averageSalary,366, "01-01-2024");
        String resultVacationLess1Day = paymentCalculatorService.getVacationPaymentCalculation(averageSalary,-1,"01-01-2024");
        String resultCorrectAnswer = paymentCalculatorService.getVacationPaymentCalculation(averageSalary,((int)amountOfDaysInYearMinusDecember),"01-01-2024");

        //then
        assertThat(resultVacationOver1Year).isEqualTo(answerVacationOver1Year);
        assertThat(resultVacationLess1Day).isEqualTo(answerVacationLess1Day);
        assertThat(resultCorrectAnswer).isEqualTo(answerCorrect);

    }
}