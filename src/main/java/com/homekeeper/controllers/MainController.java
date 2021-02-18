package com.homekeeper.controllers;

import com.homekeeper.config.Money;
import com.homekeeper.exceptions.NotFoundException;
import com.homekeeper.models.Payment;
import com.homekeeper.models.Tariff;
import com.homekeeper.payload.request.AddMonthDataRequest;
import com.homekeeper.payload.request.AddTariffRequest;
import com.homekeeper.payload.response.MessageResponse;
import com.homekeeper.payload.response.PaymentResponse;
import com.homekeeper.repository.PaymentRepository;
import com.homekeeper.repository.RoleRepository;
import com.homekeeper.repository.TariffRepository;
import com.homekeeper.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

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
    TariffRepository tariffRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public MainController (PaymentRepository paymentRepository, TariffRepository tariffRepository) {
        this.paymentRepository = paymentRepository;
        this.tariffRepository = tariffRepository;
    };



    public Payment getCalculation(Payment paymentFromDb) {
        // Принимает payment из метода addMonthData
        // Расчёт и возврат сумм по необходимому месяцу.

        Payment payment = paymentFromDb;
//                paymentRepository.findById(paymentFromDb.getId()).get();
        if (payment.equals(null)) {
            throw new NotFoundException();
        }

        Tariff tariffFromDb = tariffRepository.findFirstByOrderByDateRateChangeDesc().get();

        if (tariffFromDb.equals(null)) {
            throw new NotFoundException();
        }
        Money tariffColdWater = new Money(tariffFromDb.getWaterColdRate());
        payment.setWaterColdSum(tariffColdWater.multiplyByDouble(payment.getWaterColdValueCurrentMonth()).toString());

        Money tariffHotWater = new Money(tariffFromDb.getWaterHotRate());
        payment.setWaterWarmSum(tariffHotWater.multiplyByDouble(payment.getWaterWarmValueCurrentMonth()).toString());

        Money tariffElectricity = new Money(tariffFromDb.getElectricityRate());
        payment.setElectricitySum(tariffElectricity.multiplyByDouble(payment.getElectricityValueCurrentMonth()).toString());

        Money tariffInternet = new Money(tariffFromDb.getInternetRate());
        payment.setInternetSum(tariffInternet.multiplyByDouble(payment.getInternetValueCurrentMonth()).toString());

        Money tariffWaterOut = new Money(tariffFromDb.getWaterOutRate());
        payment.setWaterOutSum(tariffWaterOut.multiplyByDouble(payment.getWaterOutValueCurrentMonth()).toString());


        payment.setRentSum(payment.getRentRateSum());

        return payment;
    }


    @PostMapping("/addMonthData")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addMonthData(@RequestBody AddMonthDataRequest addMonthDataRequest) {
        //        Добавление в базу данных за текущий месяц. Так же вызывает расчёт итоговых сумм за месяц и изменяет баланс пользователей в соответствии с расчётом.
        Payment payment = new Payment(
                addMonthDataRequest.getWaterColdValueCurrentMonth(),
                addMonthDataRequest.getWaterWarmValueCurrentMonth(),
                addMonthDataRequest.getElectricityValueCurrentMonth(),
                addMonthDataRequest.getInternetValueCurrentMonth(),
                addMonthDataRequest.getWaterOutValueCurrentMonth(),
                addMonthDataRequest.getRentRateSum(),
                addMonthDataRequest.getHomeMates()
        );
        payment.setPaymentDate(LocalDateTime.now());
        Payment newPayment = getCalculation(payment);
        paymentRepository.save(newPayment);

        return ResponseEntity.ok(new MessageResponse("Payment added successfully"));
    }

    @PostMapping("/addTariff")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addTariff(@RequestBody AddTariffRequest addTariffRequest){
        // Create new tariff
        Tariff tariff = new Tariff(
                addTariffRequest.getWaterColdRate(),
                addTariffRequest.getWaterHotRate(),
                addTariffRequest.getElectricityRate(),
                addTariffRequest.getInternetRate(),
                addTariffRequest.getWaterOutRate(),
                addTariffRequest.getRentRate()
        );
        tariff.setDateRateChange(LocalDateTime.now());
        tariffRepository.save(tariff);

        return ResponseEntity.ok(new MessageResponse("Tariff added successfully!"));
    };

    @PutMapping("/changeTariffs/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Tariff changeTariffs(@PathVariable("id") Tariff tariffFromDb,
                                @RequestBody Tariff tariff) {
        BeanUtils.copyProperties(tariff, tariffFromDb, "id");
        tariffFromDb.setDateRateChange(LocalDateTime.now());
        //        /changeTariffs
        //        Обновление тарифов
        return tariffRepository.save(tariffFromDb);
    }

    @GetMapping("/makeReport")
    @ResponseBody
    public ResponseEntity <?> makeReport() {
        //        /makeReport
        //        Создание отчёта для отправки арендодателю. Происходит расчёт по аналогии с /getCalculation, но дополнительно возвращаются все данные, которые были использованы при расчёте (показания счётчиков и т.д.)

        try {
            Payment order = paymentRepository.findFirstByOrderByPaymentDateAsc().get();
            String message = "Hello Boss!";
            return ResponseEntity.ok(new PaymentResponse(
                    message,
                    order.getId(),
                    order.getWaterColdValueCurrentMonth(),
                    order.getWaterColdSum(),
                    order.getWaterWarmValueCurrentMonth(),
                    order.getWaterWarmSum(),
                    order.getElectricityValueCurrentMonth(),
                    order.getElectricitySum(),
                    order.getInternetValueCurrentMonth(),
                    order.getInternetSum(),
                    order.getWaterOutValueCurrentMonth(),
                    order.getWaterOutSum(),
                    order.getRentRateSum(),
                    order.getRentSum()
            ));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Database is empty!"));
        }




    }

}
