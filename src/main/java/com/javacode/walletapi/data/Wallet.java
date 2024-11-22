package com.javacode.walletapi.data;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String uuid;
    private long amount;
}
