package com.ritesh.lld.BookMyShow.Entity;

import java.util.List;

public class Booking {
    private int id;
    private BookingStatus bookingStatus;
    private Show show;
    private List<ShowSeat> seats;
}
