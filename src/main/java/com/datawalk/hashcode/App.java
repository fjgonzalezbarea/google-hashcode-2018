package com.datawalk.hashcode;

import com.datawalk.hashcode.io.Transformer;
import com.datawalk.hashcode.model.SolutionGlobalObject;

public class App {

    private static final String INPUTS_LOCATION = "";
    private static final Transformer transformer = new Transformer();

    public static void main(String[] args) {
        System.out.println("********* GOOGLE HASHCODE 2018 *********");

        SolutionGlobalObject solution = transformer.createSolutionObject(INPUTS_LOCATION);
        solution.solveProblem();
        solution.printSolutionToFile();

        System.out.println("========= Execution finished =========");
    }

}
