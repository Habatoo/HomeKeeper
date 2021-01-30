package com.homekeeper.controllers;

import com.homekeeper.config.Money;
import com.homekeeper.models.User;
import com.homekeeper.models.UserBalance;
import com.homekeeper.payload.request.UserBalanceRequest;
import com.homekeeper.payload.response.MessageResponse;
import com.homekeeper.payload.response.UserBalanceResponse;
import com.homekeeper.repository.UserBalanceRepository;
import com.homekeeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * Контроллер работы с балансом платежей пользователя.
 * Реализваны методы addFundsToBalance, changeBalance, showBalance
 * @version 0.013
 * @author habatoo
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/balances")
public class UserBalanceController {

    private final UserBalanceRepository userBalanceRepository;

    private final UserRepository userRepository;

    @Autowired
    public UserBalanceController(UserRepository userRepository, UserBalanceRepository userBalanceRepository
    ) {
        this.userRepository = userRepository;
        this.userBalanceRepository = userBalanceRepository;
    }

    /**
     * Занесение данных по внесенным средствам в баланс
     * @param userBalanceRequest - сумма добавляемая к балансу пользователя
     * @param authentication - данные текущего ползователя
     * @return - при отсутствии username в базе - "User not found!"
     * @return - при успешной записи суммы в базу - "Balance added successfully!"
     */
    // TODO добавить обновление данных баланса - суммирование с предыдущим значением
    @PostMapping("/addFundsToBalance")
    ResponseEntity<?> addFundsToBalance(
            @Valid @RequestBody UserBalanceRequest userBalanceRequest,
            Authentication authentication) {

        String userBalanceOldSum;

        if(userBalanceRequest.equals(null)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Empty request!"));
        }

        User user = userRepository.findByUserName(authentication.getName()).get();
        if (user.equals(null)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("User not found!"));
        }

        try {
            UserBalance userBalanceOld = userBalanceRepository.findFirstByUserOrderByBalanceDateDesc(user).get();
            userBalanceOldSum = userBalanceOld.getBalanceSumOfBalance();
        } catch (Exception e) {
            userBalanceOldSum = "0";
        }

        // Create new balance data
        Money userBalanceOldSumMoney = new Money(userBalanceOldSum);
        UserBalance userBalance = new UserBalance(
                userBalanceOldSumMoney.addMoney(userBalanceRequest.getBalanceSumOfBalance()).getValue().toString()
        );

        userBalance.setUser(user);
        userBalance.setBalanceDate(LocalDateTime.now());

        userBalanceRepository.save(userBalance);

        return ResponseEntity.ok(new MessageResponse("Balance added successfully!"
        ));
    }

    /**
     * Редактирование существующего баланса
     * Допускается редактировать только последнее значение баланса
     * @param userBalanceRequest - измененная сумма баланса
     * @param authentication - данные текущего ползователя
     * @return - при пустом запросе - "Error: Empty request!"
     * @return - при отсутствии username в базе - "User not found!"
     * @return - при успешной записи суммы в базу - "Balance data change successful!"
     * @return - при запросе в пучтую бд - "Database is empty!"
     */
    @PutMapping("/changeBalance")
    public ResponseEntity<?> changeBalance(@Valid @RequestBody UserBalanceRequest userBalanceRequest,
                                           Authentication authentication) {
        if(userBalanceRequest.equals(null)) {
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: Empty request!"));
        }

        User user = userRepository.findByUserName(authentication.getName()).get();
        if (user.equals(null)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username not found!"));
        }

        try {
            UserBalance userBalance = userBalanceRepository.findFirstByUserOrderByBalanceDateDesc(user).get();
            userBalance.setBalanceSumOfBalance(userBalanceRequest.getBalanceSumOfBalance());
            userBalanceRepository.save(userBalance);

            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Balance data change successful!"));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Database is empty!"));
        }
    }

    /**
     * Получение баланса текущего пользователя
     * @param authentication
     * @return - при отсутствии username в базе - "User not found!"
     * @return - при успешном запросе выдается json с данными по балансу с самой последней датой
     * @return - при запросе в пучтую бд - "Database is empty!"
     */
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<?> showBalance(Authentication authentication) {
        User user = userRepository.findByUserName(authentication.getName()).get();
        if (user.equals(null)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username not found!"));
        }

        try {
        UserBalance userBalance = userBalanceRepository.findFirstByUserOrderByBalanceDateDesc(user).get();
        return ResponseEntity.ok(new UserBalanceResponse(
                userBalance.getId(),
                userBalance.getBalanceDate(),
                userBalance.getBalanceSumOfBalance(),
                user
        ));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Database is empty!"));
        }
    }
}
