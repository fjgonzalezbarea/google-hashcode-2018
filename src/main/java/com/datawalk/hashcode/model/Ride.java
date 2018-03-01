package com.datawalk.hashcode.model;

import lombok.Data;

@Data
public class Ride {

	Long id;

	int finishTime;

	int startTime;

	int startPosition;

	int finishPosition;

}
