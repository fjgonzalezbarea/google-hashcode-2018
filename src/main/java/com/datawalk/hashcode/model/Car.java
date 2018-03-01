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

    public int score() {
        return ridesTaken.stream()
            .map(ride -> ride.distance() + bonus(ride))
            .reduce((a, b) -> a+b)
            .orElse(0);
    }

    private int bonus(Ride ride) {
        if(ride.startTime == ride.timeTaken) {
            return ride.finishTime - (ride.startTime + ride.distance() + 1);
        } else if (ride.startTime > ride.timeTaken) {
            throw new IllegalStateException("Car should wait for start time of the ride");
        }
        return 0;
    }
}
