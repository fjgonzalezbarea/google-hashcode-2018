package com.datawalk.hashcode.model;

import lombok.Data;

@Data
public class Ride {

	Long id;

	int finishTime;

	int startTime;

	int startPositionX;
	
	int startPositionY;

	int finishPositionX;
	
	int finishPositionY;

	int timeTaken;

	public int distance() {
		return Math.abs(finishPositionX - startPositionX) + Math.abs(finishPositionY - startPositionY);
	}

	public int dinstanceFromOrigin(int finishPositionX, int finishPositionY) {
		return Math.abs(this.finishPositionX - finishPositionX) + Math.abs(this.finishPositionY - finishPositionY);
	}
}
