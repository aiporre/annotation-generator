package com.deeplearning.app;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Creating annotations!" );
        ArgumentsParser arguments = new ArgumentsParser();
        arguments.parseArgs(args);
        String annotationsPath = arguments.manualAnnotationPath;
        String outputFileName = arguments.outputFileName;
        AnnotationGen annotationGen = new AnnotationGen(outputFileName,annotationsPath);
 		annotationGen.generateAnnotationFile();
 		annotationGen.displayFile();

        // LegFile  legFile = new LegFile("template/left.csv");
        // String[] serie = legFile.getColumnSeries();
        // System.out.println("============+>>> serie: " + serie);
        // for(int i =0; i<serie.length; i++){
        // 	System.out.println("value ( "+i+") : " + serie[i]);
        // }
 		System.out.println( "End!" );
 		
    }
}
