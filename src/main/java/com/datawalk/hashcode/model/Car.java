package com.datawalk.hashcode.model;

import java.util.List;
import lombok.Value;

@Value
public class Car {
    Long id;
    List<Ride> ridesTaken;

    public int score() {
        ridesTaken.stream()
            .map(ride -> ride.distance() + bonus(ride))
            .reduce((a, b) -> a+b);
        return 0;
    }

    private int bonus(Ride ride, int iteration) {
        if(ride.startTime >= iteration) {
            return ride.finishTime - ride.distance();
        }
        return 0;
    }
}
