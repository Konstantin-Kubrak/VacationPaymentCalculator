Тестовое задание Neoflex
Осень 2024

Приложение "Калькулятор отпускных".  

Приложение "Калькулятор отпускных".
Микросервис на SpringBoot + Java 11 c одним API:
GET "/calculacte"

Минимальные требования: Приложение принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает 
суммой отпускных, которые придут сотруднику.
Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет отпускных 
с учётом праздников и выходных.

Проверяться будет чистота кода, структура проекта, название полей\классов, правильность использования паттернов. 
Желательно написание юнит-тестов, проверяющих расчет.

# Запросы
Запрос с указанием средней месячной зарплаты и количества дней отпуска:  
http://localhost:9095/calculacte?salaryMonthAverage=45000.00&vacationDays=15
Ответ:
Количество дней отпуска: 15
Оплата: 15614.33

Запрос с указанием средней месячной зарплаты, количества дней отпуска и даты начала отпуска:  
http://localhost:9095/calculacte?salaryMonthAverage=45000.00&vacationDays=15&vacationStart=06-07-2024
Ответ:
Количество дней отпуска: 10
Оплата: 15358.36

# Используемые технологии
Java 11
SpringBoot 
JUnit
