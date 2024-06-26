package com.game.tournament.gametournament.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionalDetails {

    private String orderid;
    private String currency;
    private Integer amount;
    private String key;
    private String key_secret;
    private String username;
    private String mobileno;
    private String emailid;

    public TransactionalDetails(String orderid, Integer amount, String currency, String key, String key_secret, String username, String mobileno, String emailid) {
        this.orderid = orderid;
        this.amount = amount;
        this.currency = currency;
        this.key = key;
        this.key_secret = key_secret;
        this.username = username;
        this.mobileno = mobileno;
        this.emailid = emailid;
    }
}
