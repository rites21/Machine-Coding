package com.ritesh.lld.BookMyShow.Entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Show {
    private Hall hall;
    private Movie movie;
    private City city;
    private LocalDateTime startTime;
}
