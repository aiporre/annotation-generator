package com.deeplearning.app;

import org.apache.log4j.Logger;

import java.io.File;

public class ArgumentsParser {
    final static Logger logger = Logger.getLogger(ArgumentsParser.class);

    private static final String ANNOTATIONS_PATH = "--annotations-path";
    private static final String ANNOTATIONS_PATH_ABV = "-m";
    public static final String OUTPUT_ABV = "-o";
    public static final String OUTPUT = "-" + OUTPUT_ABV + "utput";

    public String outputFileName;
    public String manualAnnotationPath;


    public ArgumentsParser(){
        // set default values
        this.manualAnnotationPath = "./";
        this.outputFileName = "annotations.ods";
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
                System.out.println("  " + OUTPUT_ABV + "," + OUTPUT + "             name of the ods output file (Default annotations.ods)");

                System.exit(0);
            }
            if(arg.equals(ANNOTATIONS_PATH) || arg.equals(ANNOTATIONS_PATH_ABV)){
                String annotationsPath = args[i + 1];
                if (annotationsPath.startsWith("~" + File.separator)) {
                    annotationsPath = System.getProperty("user.home") + annotationsPath.substring(1);
                }
                this.manualAnnotationPath = annotationsPath;
            }

            if(arg.equals(OUTPUT) || arg.equals(OUTPUT_ABV)){
                String outputFileName = args[i + 1];
                if (outputFileName.startsWith("~" + File.separator)) {
                    outputFileName = System.getProperty("user.home") + outputFileName.substring(1);
                }
                this.outputFileName = outputFileName;
            }
        }
    }
}

