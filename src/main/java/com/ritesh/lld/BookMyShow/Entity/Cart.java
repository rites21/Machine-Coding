package com.ritesh.lld.BookMyShow.Entity;

import java.time.LocalDateTime;
import java.util.List;

public class Cart {
    private User user;
    private Show show;
    private List<ShowSeat> seats;
    private LocalDateTime expiryTime;
}
