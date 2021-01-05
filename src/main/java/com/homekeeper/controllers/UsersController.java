package com.homekeeper.controllers;

import com.homekeeper.models.User;
import com.homekeeper.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/users")
public class UsersController {
    private final UserRepository userRepository;

    @Autowired
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> userList() {
        return userRepository.findAll();
    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    public Object getUserInfo(Authentication authentication) {
        // return authentication.getName();
        return authentication.getPrincipal();
    }

//    @GetMapping("{id}")
//    public User getUserInfo(@PathVariable("id") User user) {
//        // /getUserInfo
//        //        Получение данных авторизованного пользователя из таблицы users, а именно:
//        //        ·         Users.fullname
//        //        ·         Users.balance
//        //        ·         Users.superuser
//        return user;
//    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        //        /addUser
        //        Добавление нового пользователя
        user.setCreationDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @PutMapping("{id}")
    public User changeUser(
            @PathVariable("id") User userFromDb,
            @RequestBody User user
    ) {
        //        /changeUser
        //        Изменение пользователя (пароль, баланс)
        BeanUtils.copyProperties(user, userFromDb, "id");

        return userRepository.save(userFromDb);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") User user) {
        //        /deleteUser
        //        Удаление пользователя
        userRepository.delete(user);
    }

}
