package com.datawalk.hashcode.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Data
@Slf4j
public class Car {
	Long id;
	List<Ride> ridesTaken;
	Integer stepToBeFree;

	public Car(Long id) {
		this.id = id;
		this.ridesTaken = new LinkedList<>();
		this.stepToBeFree = 0;
	}

	public void takeRide(int currentStep, Ride ride) {
	    if(ridesTaken.isEmpty()) {
	        stepToBeFree = currentStep + ride.dinstanceFromOrigin(0,0) + ride.distance() + 1;
        } else {
            Ride lastRide = ridesTaken.get(ridesTaken.size() - 1);
            stepToBeFree = currentStep + ride
                .dinstanceFromOrigin(lastRide.finishPositionX, lastRide.finishPositionY) + ride
                .distance() + 1;
        }
        ridesTaken.add(ride);
	}

	public boolean isAvailable(int currentStep) {
	    return stepToBeFree <= currentStep;
    }

	public Long peso(Ride ride, int currentStep) {
		int lastRideX = getLastRide().map(lastRide -> lastRide.getFinishPositionX()).orElse(0);
		int lastRideY = getLastRide().map(lastRide -> lastRide.getFinishPositionY()).orElse(0);
		int distanceLstRideToRide = ride.dinstanceFromOrigin(lastRideX, lastRideY);

		return Long.valueOf(ride.getFinishTime() - (ride.distance() + distanceLstRideToRide + 1 + currentStep));
	}

    public int distanceToRide(int currentStep, Ride ride) {
        if(ridesTaken.isEmpty()) {
            return currentStep + Math.abs(ride.startPositionX - ride.startPositionY) + ride.distance() + 1;
        }
	    Ride lastRide = ridesTaken.get(ridesTaken.size()-1);
        return currentStep + ride.dinstanceFromOrigin(lastRide.finishPositionX, lastRide.finishPositionY) + ride.distance() + 1;
    }

    public Optional<Ride> getLastRide() {
	    if(ridesTaken.size() == 0) {
	        return Optional.empty();
        }
        return Optional.of(ridesTaken.get(ridesTaken.size()-1));
    }

    public Pair<Integer, Integer> getLastRideFinalCoordinates() {
	    return getLastRide().map(ride -> Pair.of(ride.finishPositionX, ride.finishPositionY)).orElse(Pair.of(0,0));
    }

	public int score(int bonus) {
		return ridesTaken.stream().map(ride -> ride.distance() + bonus(ride, bonus)).reduce((a, b) -> a + b).orElse(0);
	}

	public String toString() {
	    return "Car " + id + " took rides --> " + ridesTaken.stream().map(ride -> ride.id.toString()).collect(Collectors.joining(",")) + "\n";
    }

	private int bonus(Ride ride, int bonus) {
		if (ride.startTime == ride.timeTaken) {
			return bonus;
		}
		return 0;
	}
}
