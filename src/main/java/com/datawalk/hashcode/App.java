package com.datawalk.hashcode;

import static com.datawalk.hashcode.io.Cli.generateOutput;
import static com.datawalk.hashcode.io.Cli.readFile;

import com.datawalk.hashcode.algorithm.Algorithm;
import com.datawalk.hashcode.exceptions.IllegalInputException;
import com.datawalk.hashcode.model.Car;
import com.datawalk.hashcode.model.Problem;
import com.datawalk.hashcode.model.Ride;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

public class App {

    private static final String INPUTS_LOCATION = "src/main/resources/";
    private static final String OUTPUTS_LOCATION = "src/main/resources/output-extended/";
    private static final String INPUT_A_NAME = "a_example";
    private static final String INPUT_B_NAME = "b_should_be_easy";
    private static final String INPUT_C_NAME = "c_no_hurry";
    private static final String INPUT_D_NAME = "d_metropolis";
    private static final String INPUT_E_NAME = "e_high_bonus";

    private static final String INPUT = ".in";
    private static final String OUTPUT = ".out";

    private static final Algorithm algorithm = new Algorithm();
    private static Problem problem;

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(ParameterTool.fromArgs(args).getInt("parallelism", 1));
        String filePath = ParameterTool.fromArgs(args)
            .get("input", "src/main/resources/a_example.in");
        runProgram(INPUTS_LOCATION + INPUT_A_NAME + INPUT, OUTPUTS_LOCATION + INPUT_A_NAME + OUTPUT);
        runProgram(INPUTS_LOCATION + INPUT_B_NAME + INPUT, OUTPUTS_LOCATION + INPUT_B_NAME + OUTPUT);
        runProgram(INPUTS_LOCATION + INPUT_C_NAME + INPUT, OUTPUTS_LOCATION + INPUT_C_NAME + OUTPUT);
        runProgram(INPUTS_LOCATION + INPUT_D_NAME + INPUT, OUTPUTS_LOCATION + INPUT_D_NAME + OUTPUT);
        runProgram(INPUTS_LOCATION + INPUT_E_NAME + INPUT, OUTPUTS_LOCATION + INPUT_E_NAME + OUTPUT);
    }

    private static void runProgram(String inputFile, String outputFilñe)
        throws IllegalInputException, FileNotFoundException {
        Triple<Problem, Collection<Car>, Collection<Ride>> triple = readFile(inputFile);
        problem = triple.getLeft();
        Collection<Car> cars = triple.getMiddle();
        Collection<Ride> rides = triple.getRight();
        createSolution(cars, rides, problem.getSteps());
        /*for (int step = 0; step < problem.getSteps(); step++) {
			processCars(triple, step);
		}*/
        //System.out.println(cars);
        generateOutput(cars, outputFilñe);
        System.out.println("Total score --> " + score(cars, problem.getBonus()));
    }

    private static void createSolution(Collection<Car> cars, Collection<Ride> rides, Integer numnberOfSteps) {
        IntStream.range(0, numnberOfSteps).forEach(step -> createSolutionForStep(cars, rides, step));
    }

    private static void createSolutionForStep(Collection<Car> cars, Collection<Ride> rides, Integer step) {
        cars.forEach(car -> createSolutionForCar(car, rides, step));
    }

    private static void createSolutionForCar(Car car, Collection<Ride> rides, Integer step) {
        if (car.isAvailable(step)) {
            AtomicInteger atomicStep = new AtomicInteger(step);
            Collections.sort((List<Ride>) rides, createRideComparator(car, atomicStep.get()));
            if (!rides.isEmpty()) {
                setNextRideToCar(car, rides, step);
            }
        }
    }

    private static void setNextRideToCar(Car car, Collection<Ride> rides, Integer step) {
        Ride ride = rides.iterator().next();
        ride.setTimeTaken(step);
        car.takeRide(step, ride);
        rides.remove(ride);
    }

    private static void processCar(Triple<Problem, Collection<Car>, Collection<Ride>> triple,
        int step, Car car) {
        if (car.isAvailable(step)) {
            AtomicInteger step1 = new AtomicInteger(step);
            Collections.sort((List<Ride>) triple.getRight(), (r1, r2) -> {
                Long p1 = car.peso(r1, step1.get());
                Long p2 = car.peso(r2, step1.get());
                return p1.compareTo(p2);
            });
            if (!triple.getRight().isEmpty()) {
                Ride ride = triple.getRight().iterator().next();
                ride.setTimeTaken(step);
                car.takeRide(step, ride);
                triple.getRight().remove(ride);
            }
        }
    }

    public static int score(Collection<Car> cars, int bonus) {
        return cars.stream().mapToInt(car -> car.score(bonus)).sum();
    }

    private static Comparator<Ride> createRideComparator(Car car, Integer step) {
        return (r1, r2) -> {
            Long p1 = car.peso(r1, step);
            Long p2 = car.peso(r2, step);
            return p1.compareTo(p2);
        };
    }

}
