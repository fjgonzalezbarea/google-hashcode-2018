package com.datawalk.hashcode.model;

import java.util.LinkedList;
import java.util.List;
import lombok.Value;

@Value
public class Car {
    Long id;
    List<Ride> ridesTaken;

    public Car(Long id) {
        this.id = id;
        ridesTaken = new LinkedList<>();
    }

    public int score(int bonusValue) {
        return ridesTaken.stream()
            .map(ride -> ride.distance() + bonusValue)
            .reduce((a, b) -> a+b)
            .orElse(0);
    }
}
