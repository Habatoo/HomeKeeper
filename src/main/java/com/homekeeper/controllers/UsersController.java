package com.homekeeper.controllers;

import com.homekeeper.models.User;
import com.homekeeper.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("users")
public class UsersController {
    private final UserRepo userRepo;

    @Autowired
    public UsersController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<User> userList() {
        return userRepo.findAll();
    }

    @GetMapping("{id}")
    public User getUserInfo(@PathVariable("id") User user) {
        // /getUserInfo
        //        Получение данных авторизованного пользователя из таблицы users, а именно:
        //        ·         Users.fullname
        //        ·         Users.balance
        //        ·         Users.superuser
        return user;
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        //        /addUser
        //        Добавление нового пользователя
        user.setCreationDate(LocalDateTime.now());
        return userRepo.save(user);
    }

    @PutMapping("{id}")
    public User changeUser(
            @PathVariable("id") User userFromDb,
            @RequestBody User user
    ) {
        //        /changeUser
        //        Изменение пользователя (пароль, баланс)
        BeanUtils.copyProperties(user, userFromDb, "id");

        return userRepo.save(userFromDb);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") User user) {
        //        /deleteUser
        //        Удаление пользователя
        userRepo.delete(user);
    }

}
