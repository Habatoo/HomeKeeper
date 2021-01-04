package com.homekeeper.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class MainController {

    @GetMapping("/")
    public String authUser() {
        //        /auth
        //        Метод авторизации пользователя
        return "/auth";
    }

    @GetMapping("/getCalculation")
    public String getCalculation() {
        // Расчёт и возврат сумм по необходимому месяцу.
        // Принимает – месяц и год, на который необходим расчёт.
        return "/getCalculation";
    }

    @GetMapping("/addMonthData")
    public String addMonthData() {
        //        /addMonthData
        //        Добавление в базу данных за текущий месяц. Так же вызывает расчёт итоговых сумм за месяц и изменяет баланс пользователей в соответствии с расчётом.
        return "/addMonthData";
    }

    @GetMapping("/changeTariffs")
    public String changeTariffs() {
        //        /changeTariffs
        //        Обновление тарифов
        return "/changeTariffs";
    }

    @GetMapping("/makeReport")
    public String makeReport() {
        //        /makeReport
        //        Создание отчёта для отправки арендодателю. Происходит расчёт по аналогии с /getCalculation, но дополнительно возвращаются все данные, которые были использованы при расчёте (показания счётчиков и т.д.)
        return "/makeReport";
    }

}