package com.ritesh.lld.BookMyShow.Entity;

import java.util.List;

import lombok.Data;

@Data
public class Hall {
    private int hallId;
    private List<ShowSeat> seats;
}
