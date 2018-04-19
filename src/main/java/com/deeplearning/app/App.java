package com.deeplearning.app;

import com.deeplearning.app.AnnotationGen;
import com.deeplearning.app.LegFile;
import com.deeplearning.app.WalkFolderIdentificator;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Creating annotations!" );
        
 		AnnotationGen annotationGen = new AnnotationGen();       
 		annotationGen.generateFile();
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
