package ru.neoflex.neostudy.konstantin_kubrak.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.neoflex.neostudy.konstantin_kubrak.services.PaymentCalculatorService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class VacationPaymentTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private PaymentCalculatorService service;

    @Test
    @DisplayName("Тест работы контроллера /calculacte без даты начала отпуска")
    void getVacationPaymentTestNoStartDate() throws Exception {

        when(service.getVacationPaymentCalculation(120_000.0, 20)).thenReturn("Payment: 102389.08");
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/calculacte")
                        .param("salaryMonthAverage", String.valueOf(120_000.00))
                        .param("vacationDays", String.valueOf(20))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Payment: 102389.08"));
    }

    @Test
    @DisplayName("Тест работы контроллера /calculacte с датой начала отпуска")
    void getVacationPaymentTestWithStartDate() throws Exception {

        when(service.getVacationPaymentCalculation(120_000.0, 20, "StartDate")).thenReturn("Payment: 102389.08");
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/calculacte")
                        .param("salaryMonthAverage", String.valueOf(120_000.00))
                        .param("vacationDays", String.valueOf(20))
                        .param("vacationStart", "StartDate")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Payment: 102389.08"));
    }
}