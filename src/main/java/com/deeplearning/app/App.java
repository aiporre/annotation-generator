package com.deeplearning.app;
import org.apache.log4j.Logger;/**
 * Hello world!
 *
 */
public class App
{
    final static Logger logger = Logger.getLogger(App.class);

    public static void main( String[] args )
    {
        ArgumentsParser arguments = new ArgumentsParser();
        arguments.parseArgs(args);
        logger.info("==================================================================");
        logger.info("==================================================================");
        logger.info("");
        logger.info("                       ANNOTATION GENERATOR                       ");
        logger.info("");
        logger.info("==================================================================");
        logger.info("==================================================================");
        String annotationsPath = arguments.manualAnnotationPath;
        String outputFileName = arguments.outputFileName;
        AnnotationGen annotationGen = new AnnotationGen(outputFileName,annotationsPath);
 		annotationGen.generateAnnotationFile();
 		annotationGen.displayFile();
 		logger.info( "End!" );
 		
    }
}
