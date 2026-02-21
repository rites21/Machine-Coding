package com.ritesh.lld.BookMyShow.Entity;

import java.time.LocalDateTime;
import java.util.Date;

import jdk.jfr.Percentage;
import lombok.Data;

@Data
public class Coupon {
    private String code;
    private double discountPercentage;
    private LocalDateTime expiry;
    private boolean active;
}
