package com.datawalk.hashcode;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;

import com.datawalk.hashcode.algorithm.Algorithm;
import com.datawalk.hashcode.mappers.InputMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

	private static final String INPUTS_LOCATION = "";
	private static final Algorithm algorithm = new Algorithm();

	public static void main(String[] args) throws Exception {
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		env.setParallelism(ParameterTool.fromArgs(args).getInt("parallelism", 1));
		DataSet<String> input = env.readTextFile(
				ParameterTool.fromArgs(args).get("input", "src/main/resources/myInput.txt"));
		input.map(new InputMapper()).print();
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
