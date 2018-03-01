package com.datawalk.hashcode.utils;

import com.datawalk.hashcode.model.Input;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class CLI {

    private String[] args;
    private Options options;

    public CLI(String[] args) {
        this.args = args;
        this.options = new Options();

        //TODO add options list
        //TODO - remove stuff
        options.addOption("f", true, "Pizza's file");
        options.addOption("i", true, "Iterations");
        options.addOption("v", true, "Minimum's value for good solution");
    }

    //TODO - return problem's object
    public Input parse() throws Exception {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (Exception e) {
            System.err.println("Error parsing arguments");
            e.printStackTrace();
            throw e;
        }

        return Input.builder().build();
    }
}
