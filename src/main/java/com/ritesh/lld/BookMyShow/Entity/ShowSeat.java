package com.ritesh.lld.BookMyShow.Entity;

import java.util.List;

import lombok.Data;

@Data
public class ShowSeat {
    private Seat seat;
    private SeatStatus seatStatus;
}
