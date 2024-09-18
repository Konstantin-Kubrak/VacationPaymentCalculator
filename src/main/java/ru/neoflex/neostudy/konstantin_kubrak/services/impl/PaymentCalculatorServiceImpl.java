package ru.neoflex.neostudy.konstantin_kubrak.services.impl;

import org.springframework.stereotype.Service;
import ru.neoflex.neostudy.konstantin_kubrak.services.PaymentCalculatorService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentCalculatorServiceImpl implements PaymentCalculatorService {

    /**
     * Среднее количество дней в месяце без учета федеральных праздников, коеффициент для расчёта отпускных.
     */
    private static final double VACATION_DAYS_COEFFICIENT = 29.3;

    /**
     * Метод для расчёта отпускных без учёта праздников и выходных
     * @param salaryYearAverage - средняя ежемесячная зарплата
     * @param vacationDays - количество дней отпуска
     * @return возвращает сообщение с количеством дней отпуска и суммой отпускных. В случае ошибки возвращает описание проблемы.
     */
    public String getVacationPaymentCalculation(Double salaryYearAverage, Integer vacationDays) {

        if (vacationDays > 365)
            return "Количество дней отпуска превышает календарный год: " + vacationDays;
        if (vacationDays < 1)
            return "Введено недопустимое количество дней отпуска: " + vacationDays;
        return "Количество дней отпуска: " + vacationDays + "\nОплата: " +  Math.round((salaryYearAverage / VACATION_DAYS_COEFFICIENT * vacationDays)*100.0)/100.0;
    }

    /**
     * Метод для расчёта отпускных с учётом праздников и выходных
     * @param salaryYearAverage - средняя ежемесячная зарплата
     * @param vacationDays - количество дней отпуска
     * @param vacationStartDate - дата начала отпуска
     * @return возвращает сообщение с количеством дней отпуска и суммой отпускных. В случае ошибки возвращает описание проблемы.
     */
    public String getVacationPaymentCalculation(Double salaryYearAverage, Integer vacationDays, String vacationStartDate) {

        LocalDate vacationStart;
        if (vacationDays < 1)
            return "Введено недопустимое количество дней отпуска: " + vacationDays;
        if (vacationDays > 365)
            return "Количество дней отпуска превышает календарный год: " + vacationDays;
        try {
            vacationStart = LocalDate.parse(vacationStartDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (Exception ex){
            return "Дата введена в некорректном формате. Пожалуйста, введите дату в формате: dd-MM-yyyy";
        }

        long vacationDaysFiltered = vacationStart.datesUntil(vacationStart.plusDays(vacationDays))
                .filter(e -> e.getDayOfWeek() != DayOfWeek.SATURDAY)
                .filter(e -> e.getDayOfWeek() != DayOfWeek.SUNDAY)
                .filter(e -> !(getHolidays().containsKey(e)))
                .count();

        return "Количество дней отпуска: " + vacationDaysFiltered + "\nОплата: " +  Math.round((salaryYearAverage / VACATION_DAYS_COEFFICIENT * vacationDaysFiltered)*100.0)/100.0;
    }

    /**
     * Метод для получения праздничных дней.
     * @return возвращает коллекцию типа "ключ-значение" с праздничными днями, где ключ - дата праздника LocalDate, значение - описание праздника.
     */
    public Map<LocalDate, String> getHolidays(){

        int currentYear = LocalDate.now().getYear();
        Map<LocalDate, String> holidays = new HashMap<>();
        holidays.put(LocalDate.of(currentYear, 1, 1), "Новый Год");
        holidays.put(LocalDate.of(currentYear, 1, 2), "Новый Год");
        holidays.put(LocalDate.of(currentYear, 1, 3), "Новый Год");
        holidays.put(LocalDate.of(currentYear, 1, 4), "Новый Год");
        holidays.put(LocalDate.of(currentYear, 1, 5), "Новый Год");
        holidays.put(LocalDate.of(currentYear, 1, 6), "Новый Год");
        holidays.put(LocalDate.of(currentYear, 1, 7), "Новый Год");
        holidays.put(LocalDate.of(currentYear, 1, 8), "Новый Год");
        holidays.put(LocalDate.of(currentYear, 2, 23), "День защитника Отечества");
        holidays.put(LocalDate.of(currentYear, 3, 8), "Международный женский день");
        holidays.put(LocalDate.of(currentYear, 5, 1), "День труда");
        holidays.put(LocalDate.of(currentYear, 5, 9), "День Победы");
        holidays.put(LocalDate.of(currentYear, 6, 12), "День России");
        holidays.put(LocalDate.of(currentYear, 11, 4), "День народного единства");

        return holidays;
    }

}
