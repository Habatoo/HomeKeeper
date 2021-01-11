package com.homekeeper.controllers;

import com.homekeeper.repository.PaymentRepository;
import com.homekeeper.repository.RoleRepository;
import com.homekeeper.repository.UserBalanceRepository;
import com.homekeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class MainController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserBalanceRepository userBalanceRepository;

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
