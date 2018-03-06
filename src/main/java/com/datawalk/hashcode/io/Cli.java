package com.datawalk.hashcode.io;

import com.datawalk.hashcode.model.Car;
import com.datawalk.hashcode.model.Problem;
import com.datawalk.hashcode.model.Ride;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.shaded.guava18.com.google.common.collect.Lists;

public class Cli {

    /*public static Triple<Problem, Collection<Car>, Collection<Ride>> readFile(
        ExecutionEnvironment env, String[] args) {

        Collection<Car> cars = Lists.newArrayList();
        Collection<Ride> rides = Lists.newArrayList();
        Problem problem = null;
        int numLine = -1;
        try (BufferedReader br = new BufferedReader(
            new FileReader(ParameterTool
                .fromArgs(args).get("input", "src/main/resources/b_should_be_easy.in")))) {

            String sCurrentLine;
            boolean firstLine = true;
            while ((sCurrentLine = br.readLine()) != null) {
                if (firstLine) {
                    problem = processFirstLine(cars, sCurrentLine);
                    firstLine = false;
                } else {
                    processRideLines(rides, sCurrentLine, numLine);
                }
                numLine++;
            }

            return Triple.of(problem, cars, rides);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }*/

    public static Triple<Problem, Collection<Car>, Collection<Ride>> readFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine = br.readLine();
            if (sCurrentLine != null) {
                Problem problem = createProblemFromFile(sCurrentLine);
                Collection<Car> cars = createCarsFromFile(sCurrentLine);
                Collection<Ride> rides = Lists.newArrayList();
                int numLine = 0;
                while ((sCurrentLine = br.readLine()) != null) {
                    rides.add(createRideFromLine(sCurrentLine, numLine));
                    numLine++;
                }
                return Triple.of(problem, cars, rides);
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return null;
    }

    public static void generateOutput(Collection<Car> cars, String outputFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            cars.stream()
                .sorted(Comparator.comparing(Car::getId))
                .forEach(car -> {
                    String rides = car.getRidesTaken().stream().map(r -> r.getId().toString())
                        .collect(Collectors.joining(" "));
                    try {
                        bw.write(car.getRidesTaken().size() + " " + rides);
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public static void generateOutput(Collection<Car> cars, String[] args) {
        try (BufferedWriter bw = new BufferedWriter(
            new FileWriter(ParameterTool.fromArgs(args)
                .get("input", "src/main/resources/output/b_should_be_easy.out")))) {

            cars = cars.stream()
                .sorted((c1, c2) -> c1.getId().compareTo(c2.getId()))
                .collect(Collectors.toList());
            for (Car car : cars) {
                String rides = "";
                rides = car.getRidesTaken().stream().map(r -> r.getId().toString())
                    .collect(Collectors.joining(" "));
                bw.write(car.getRidesTaken().size() + " " + rides);
                bw.newLine();
            }
            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    */

    private static Problem createProblemFromFile(String sCurrentLine) {
        String[] line = sCurrentLine.split(" ");
        return new Problem(Integer.parseInt(line[5]), Integer.parseInt(line[4]),
            Integer.parseInt(line[0]),
            Integer.parseInt(line[1]));
    }

    private static Collection<Car> createCarsFromFile(String sCurrentLine) {
        String[] line = sCurrentLine.split(" ");
        return IntStream.range(0, Integer.parseInt(line[2]))
            .mapToObj(index -> new Car(new Long(index + 1)))
            .collect(Collectors.toList());
    }

    private static Ride createRideFromLine(String sCurrentLine, int numLine) {
        String[] line = sCurrentLine.split(" ");
        Ride ride = new Ride();
        ride.setStartPositionX(Integer.parseInt(line[0]));
        ride.setStartPositionY(Integer.parseInt(line[1]));
        ride.setFinishPositionX(Integer.parseInt(line[2]));
        ride.setFinishPositionY(Integer.parseInt(line[3]));
        ride.setStartTime(Integer.parseInt(line[4]));
        ride.setFinishTime(Integer.parseInt(line[5]));
        ride.setId(new Long(numLine));
        return ride;
    }

    /*private static void processRideLines(Collection<Ride> rides, String sCurrentLine, int numLine) {
        String[] line = sCurrentLine.split(" ");
        Ride ride = new Ride();
        ride.setStartPositionX(Integer.parseInt(line[0]));
        ride.setStartPositionY(Integer.parseInt(line[1]));
        ride.setFinishPositionX(Integer.parseInt(line[2]));
        ride.setFinishPositionY(Integer.parseInt(line[3]));
        ride.setStartTime(Integer.parseInt(line[4]));
        ride.setFinishTime(Integer.parseInt(line[5]));
        ride.setId(new Long(numLine));
        rides.add(ride);
    }

    private static Problem processFirstLine(Collection<Car> cars, String sCurrentLine) {
        Problem problem;
        String[] line = sCurrentLine.split(" ");
        for (int i = 0; i < Integer.parseInt(line[2]); i++) {
            cars.add(new Car(new Long(i + 1)));
        }
        problem = new Problem(Integer.parseInt(line[5]), Integer.parseInt(line[4]),
            Integer.parseInt(line[0]),
            Integer.parseInt(line[1]));

        return problem;
    }*/

}
