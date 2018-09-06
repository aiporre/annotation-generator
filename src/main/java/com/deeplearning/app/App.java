package com.deeplearning.app;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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


        WalkFolderIdentificator identificator = new WalkFolderIdentificator(annotationsPath);
        Map<String, List<WalkFolder>> forlderWalkFoldersMap  = identificator.identify();
        for (String folder: forlderWalkFoldersMap.keySet()) {
            String outputFileName = folder + File.separator +(new File(folder).getName()) + "-manual.ods";

            Map<String, WalkFolder> walkFoldersMap = new HashMap<>();
            for (WalkFolder wf :forlderWalkFoldersMap.get(folder)){
                walkFoldersMap.put(wf.getIdentifier(), wf);
            }
            AnnotationGen annotationGen = new AnnotationGen(outputFileName);
            annotationGen.generateAnnotationFile(walkFoldersMap);
            if(arguments.verbose){
                annotationGen.displayFile();
            }
        }

 		logger.info( "End!" );
 		
    }
}
