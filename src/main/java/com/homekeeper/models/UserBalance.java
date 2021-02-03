package com.homekeeper.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Модель с данными платежей пользователя и текущим балансом каждого пользователя.
 * Записывается в БД в таблицу с имененм userBalances.
 * @version 0.013
 * @author habatoo
 *
 * @param "id" - primary key таблицы userBalances.
 * @param "balanceDate" - наименовение роли.
 * @param "balanceSumOfBalance" - наименовение роли.
 */
@Entity
@Table(name = "userBalances")
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private User user;

    public UserBalance() {
    }

    public UserBalance(String balanceSumOfBalance) {
        this.balanceSumOfBalance = balanceSumOfBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(LocalDateTime balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getBalanceSumOfBalance() {
        return balanceSumOfBalance;
    }

    public void setBalanceSumOfBalance(String balanceSumOfBalance) {
        this.balanceSumOfBalance = balanceSumOfBalance;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
