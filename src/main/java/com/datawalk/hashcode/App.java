package com.datawalk.hashcode;

import com.datawalk.hashcode.algorithm.Algorithm;
import com.datawalk.hashcode.model.Input;
import com.datawalk.hashcode.model.Output;
import com.datawalk.hashcode.utils.CLI;

public class App {

    private static final String INPUTS_LOCATION = "";
    private static final Algorithm algorithm = new Algorithm();

    public static void main(String[] args) {
        System.out.println("********* GOOGLE HASHCODE 2018 *********");

        CLI cli = new CLI(args);
        try {
            Input input = cli.parse();
            Output output = algorithm.runIteration(input);
            output.printSolutionToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("========= Execution finished =========");
    }

}
