package com.homekeeper.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Модель с данными платежей пользователя и текущим балансом каждого пользователя.
 * Записывается в БД в таблицу с имененм userbalances.
 * @version 0.013
 * @author habatoo
 *
 * @param "id" - primary key таблицы userbalances.
 * @param "balanceDate" - наименовение роли.
 * @param "balanceSumOfBalance" - наименовение роли.
 */
@Entity
@Table(name = "userbalances")
@ToString(of = {"id",
        "balanceDate",
        "balanceSumOfBalance"
})
@EqualsAndHashCode(of = {"id"})
public class UserBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime balanceDate;

    private String balanceSumOfBalance;



}
