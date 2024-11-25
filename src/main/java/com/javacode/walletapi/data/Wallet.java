package com.javacode.walletapi.data;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Wallet model.
 */

@Data
@Entity
@Table(name="wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // UUID should be in hexadecimal digits, meaning it uses the numbers 0 through 9
    // and letters A through F. The hexadecimal digits are grouped as 32 hexadecimal characters with
    // four hyphens: XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX.
    private String uuid;
    private long amount;
}
