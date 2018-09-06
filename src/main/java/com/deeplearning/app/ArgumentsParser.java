package com.deeplearning.app;

import org.apache.log4j.Logger;

import java.io.File;

public class ArgumentsParser {
    final static Logger logger = Logger.getLogger(ArgumentsParser.class);

    private static final String ANNOTATIONS_PATH = "--annotations-path";
    private static final String ANNOTATIONS_PATH_ABV = "-m";

    public String outputFileName;
    public String manualAnnotationPath;
    public boolean verbose;


    public ArgumentsParser(){
        // set default values
        this.manualAnnotationPath = "./";
        this.outputFileName = "annotations.ods";
        this.verbose = false;
    }

    public void parseArgs(String[] args){
        int N = args.length;
        for (int i = 0; i < N; i++) {
            String arg = args[i];
            if(arg.equals("--help") || arg.equals("-h")){
                System.out.println("usage: java -cp [path to jar] [options]");
                System.out.println("  -h,--help               print command help");
                System.out.println("  " + ANNOTATIONS_PATH_ABV +"," + ANNOTATIONS_PATH + "   path to look for the " +
                        "annotations (Default ./)");
                System.out.println("  -v,--verbose            open ODS files at the end");
                System.exit(0);
            }
            if(arg.equals(ANNOTATIONS_PATH) || arg.equals(ANNOTATIONS_PATH_ABV)){
                String annotationsPath = args[i + 1];
                if (annotationsPath.startsWith("~" + File.separator)) {
                    annotationsPath = System.getProperty("user.home") + annotationsPath.substring(1);
                }
                this.manualAnnotationPath = annotationsPath;
            }

            if(arg.equals("--verbose") || arg.equals("--v")){
                this.verbose = true;
            }
        }
    }
}

