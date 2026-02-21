package com.ritesh.lld.BookMyShow.Services;

import java.util.List;

import com.ritesh.lld.BookMyShow.Entity.Cinema;
import com.ritesh.lld.BookMyShow.Entity.Hall;
import com.ritesh.lld.BookMyShow.Entity.Seat;
import com.ritesh.lld.BookMyShow.Entity.SeatStatus;
import com.ritesh.lld.BookMyShow.Entity.Show;
import com.ritesh.lld.BookMyShow.Entity.ShowSeat;
import com.ritesh.lld.BookMyShow.Entity.User;

public class BookingService {
    private final User user;
    private final Show show;
    private final Cinema cinema;
    private final PaymentService paymentService;

    public BookingService(User user, Show show, Cinema cinema, PaymentService paymentService) {
        this.user = user;
        this.show = show;
        this.cinema = cinema;
        this.paymentService = paymentService;
    }

    public void bookShow(int userId, String name, String Show, String city, String seatType) {
        //call cinema db get show from that list
        //from show get hall
        //from hall seat
        //lock seat //book
        //pay and all
        //lets do
        //I am not having db but I will add that with arraylist in interview
        User currUser = new User(userId, name);
        Hall currentHall = new Hall();
//        first call register service for register in production
        List<Show> shows = cinema.getShows();
        for (Show show1 : shows) {
            if (show1.equals(show)) {
                currentHall = show1.getHall();
            }
        }
        SelectSeat(currentHall, seatType);
    }

    //@Transactional
    private void SelectSeat(Hall currentHall, String seatType) {
        List<ShowSeat> showSeats = currentHall.getSeats();
        //WE CAN USE SEAT SELECT STRATEGIES AS WELL BUT WILL ASK INTERVIEWR CURRENTYL ROUND ROBIM
        for (ShowSeat showSeat : showSeats) {
            Seat seat = showSeat.getSeat();
            if (showSeat.getSeatStatus().equals(SeatStatus.AVAILABLE) && seat.getType().equals(seatType)) {
                synchronized (showSeat) {
                    showSeat.setSeatStatus(SeatStatus.LOCKED);
                    //call PaymentSErvice
                    String paymentStatue = paymentService.makePayment();
                    if (paymentStatue.equals("SUCESS")) {
                        showSeat.setSeatStatus(SeatStatus.BOOKED);
                    } else {
                        showSeat.setSeatStatus(SeatStatus.AVAILABLE);
                    }
                }
            }
        }
    }
}
