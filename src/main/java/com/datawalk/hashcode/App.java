package com.datawalk.hashcode;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

import com.datawalk.hashcode.algorithm.Algorithm;

public class App {

	private static final String INPUTS_LOCATION = "";
	private static final Algorithm algorithm = new Algorithm();

	public static void main(String[] args) throws Exception {
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		DataSet<String> input = env.readTextFile(
				ParameterTool.fromArgs(args).get("input", "src/main/resources/myInput.txt"));
		input.print();
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

}
