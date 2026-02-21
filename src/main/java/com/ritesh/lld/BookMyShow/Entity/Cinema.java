package com.ritesh.lld.BookMyShow.Entity;

import java.util.List;

import lombok.Data;

@Data
public class Cinema {
    private int id;
    private City city;
    private List<Show> shows;
}
