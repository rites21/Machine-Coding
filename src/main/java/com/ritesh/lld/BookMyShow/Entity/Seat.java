package com.ritesh.lld.BookMyShow.Entity;

import lombok.Data;

@Data
public class Seat {
    private String seatId;
    private int row;
    private int col;
    private SeatType type;
}