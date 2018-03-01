package com.datawalk.hashcode;

import com.datawalk.hashcode.algorithm.Algorithm;
import com.datawalk.hashcode.model.Car;
import com.datawalk.hashcode.model.Problem;
import com.datawalk.hashcode.model.Ride;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.shaded.guava18.com.google.common.collect.Lists;

public class App {

	private static final String INPUTS_LOCATION = "";
	private static final Algorithm algorithm = new Algorithm();
	private static Problem problem;

	public static void main(String[] args) throws Exception {
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		env.setParallelism(ParameterTool.fromArgs(args).getInt("parallelism", 1));
		Triple<Problem, Collection<Car>, Collection<Ride>> triple = readFile(env, args);
		DataSet<Car> cars = env.fromCollection(triple.getMiddle());
		DataSet<Ride> rides = env.fromCollection(triple.getRight());
		problem = triple.getLeft();
		System.out.println(problem);
		cars.print();
		rides.print();
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println("********* GOOGLE HASHCODE 2018 *********");
	 * 
	 * CLI cli = new CLI(args); try { Input input = cli.parse(); Output output =
	 * algorithm.runIteration(input); output.printSolutionToFile(); } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * System.out.println("========= Execution finished ========="); }
	 */

	static Triple<Problem, Collection<Car>, Collection<Ride>> readFile(ExecutionEnvironment env, String[] args) {

		Collection<Car> cars = Lists.newArrayList();
		Collection<Ride> rides = Lists.newArrayList();
		Problem problem = null;
		int numLine = -1;
		try (BufferedReader br = new BufferedReader(
				new FileReader(ParameterTool.fromArgs(args).get("input", "src/main/resources/a_example.in")))) {

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

	}

	private static void processRideLines(Collection<Ride> rides, String sCurrentLine, int numLine) {
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
			cars.add(new Car(new Long(i)));
		}
		problem = new Problem(Integer.parseInt(line[5]), Integer.parseInt(line[4]), Integer.parseInt(line[0]),
				Integer.parseInt(line[1]));

		return problem;
	}

}
