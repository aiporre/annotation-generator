package com.deeplearning.app;

import java.io.File;
import java.io.IOException;

import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

import java.util.Map;

import java.lang.Integer;

import org.apache.log4j.Logger;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet; 
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class AnnotationGen {
    final static Logger logger = Logger.getLogger(AnnotationGen.class);

    private final File annotationsOutputFileODS;

	public AnnotationGen(String outputFileName){
		this.annotationsOutputFileODS = new File(outputFileName);
	}
	// +dev: change to proper name it opens resutl annotationsOutputFileODS:
	// +dev: handle error when empty.		
	public void displayFile(){
		try{
			OOUtils.open(this.annotationsOutputFileODS);
		} catch(Exception e){ 
			e.printStackTrace();		
		}
	}

	public void generateAnnotationFile(Map<String,WalkFolder> walkFoldersMap){

		try{

            SpreadSheet spreadSheet = generateEmptySpreadsheet();

            for(String walkFolderId: walkFoldersMap.keySet()){
            	logger.info("---> processing folder: " + walkFolderId);
				// for each walkfolder get coordinates vs values (example {'A10','ON_THE_GROUND';...}
            	WalkFolder walkFolder = walkFoldersMap.get(walkFolderId);
                Map<String, String> coodinatesMap;
            	try {
                     coodinatesMap = walkFolder.readAsCoordinatesMap();
                } catch (ArrayIndexOutOfBoundsException e){
            		e.printStackTrace();
            	    logger.error("The left and right leg files contain errors. Sheet " + walkFolder.getIdentifier()+ " will be skip");
            	    continue;
                }
                logger.info("Coordinates were generated without problems.");

            	// add new sheet with the walk folder identificator.
            	final Sheet sheet = spreadSheet.addSheet(walkFolderId);
            	// allocate fields in the sheet
            	sheet.setRowCount(walkFolder.annotationsNumber()+1);
            	sheet.setColumnCount(3);
                logger.info("Processing coordinates and placing table...");
            	for(String coordinate: coodinatesMap.keySet()){
            		logger.debug("---> coordinate:" + coordinate + ", " + coodinatesMap.get(coordinate));
            		// parse the coordinate into x and y (example A2 ===> x =0 y = 1)
            		int x = 0;
            		int y = 0;
            		if(coordinate.startsWith("A")){
            			x =0;
            		} else if(coordinate.startsWith("B")){
            			x=1;
            		} else if(coordinate.startsWith("C")){
            			x=2;
            		} else {
            			System.err.println("coordinate was incorrect.");
            			break;
            		}
            		y = Integer.parseInt(coordinate.substring(1))-1;

            		// add new vaule to the sheet.
                    logger.debug("placing value at " + x + ", " + y + "." );
            		sheet.setValueAt(coodinatesMap.get(coordinate),x,y);
            	}
            	logger.info("Sheet processed with out problems.");
            	// save and finish with the given walking folder.
            	sheet.getSpreadSheet().saveAs(this.annotationsOutputFileODS);
            }
            logger.info("Annotations saved in the file: " + this.annotationsOutputFileODS);
		} catch(Exception e){ 
			e.printStackTrace();		
		}
	}

	private SpreadSheet generateEmptySpreadsheet() throws IOException {
		final Object[][] data = new Object[1][3];
     	data[0] = new Object[] { "","",""};
        String[] columns = new String[] {"ON_GROUND", "IN_THE_AIR", "NOT_IN_FRAME"};
     	TableModel model = new DefaultTableModel(data, columns);  
       
     	// Save the data to an ODS annotationsOutputFileODS and open it.
     	SpreadSheet emptySpreadSheet =  SpreadSheet.createEmpty(model);
     	return emptySpreadSheet;
	}
	

}