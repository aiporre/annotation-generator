
package com.deeplearning.app;


import java.io.File; 
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;


// import java.io.BufferedReader;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;

import java.util.Map;
import java.io.IOException;
// import java.util.ArrayList;

// import java.util.Map.Entry;
// import java.util.AbstractMap.SimpleEntry;

import java.lang.Integer;


import org.jopendocument.dom.OOUtils; 
import org.jopendocument.dom.spreadsheet.Sheet; 
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class AnnotationGen {
	private final File file;

	public AnnotationGen(){
		// ++dev: hard code name of walk folder object
		this.file = new File("annotations.ods");
	}
	// +dev: change to proper name it opens resutl file: 
	// +dev: handle error when empty.		
	public void displayFile(){
		try{
			OOUtils.open(file);
		} catch(Exception e){ 
			e.printStackTrace();		
		}
	}

	public void generateAnnotationFile(){
		try{
			// WalkFolder walkFolder = new WalkFolder("template");
			// TableModel model =  walkFolder.readAsSheetModel();
		    // Save the data to an ODS file and open it.
//			TableModel model ;
			System.out.println("----> identifying the walk folders...");
			WalkFolderIdentificator identificator = new WalkFolderIdentificator("./");
            Map<String, WalkFolder> walkFoldersMap = identificator.identify();


            SpreadSheet spreadSheet = generateEmptySpreadsheet();

            for(String walkFolderId: walkFoldersMap.keySet()){
            	System.out.println("---> k: folder " + walkFolderId);
				// for each walkfolder get coordinates vs values (example {'A10','ON_THE_GROUND';...}
            	WalkFolder walkFolder = walkFoldersMap.get(walkFolderId);
            	Map<String, String> coodinatesMap = walkFolder.readAsCoordinatesMap();

            	// add new sheet with the walk folder identificator.
            	final Sheet sheet = spreadSheet.addSheet(walkFolderId);

            	// allocate fields in the sheet
            	sheet.setRowCount(walkFolder.annotationsNumber()+1);
            	sheet.setColumnCount(3);

            	for(String coordinate: coodinatesMap.keySet()){
            		System.out.println("============>>>>>>>>>>>>  coordinate:" + coordinate);
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
            		sheet.setValueAt(coodinatesMap.get(coordinate),x,y);
            	}
            	// save and finish with the given walking folder.
            	sheet.getSpreadSheet().saveAs(this.file);
            }
            
		} catch(Exception e){ 
			e.printStackTrace();		
		}
	}

	private SpreadSheet generateEmptySpreadsheet() throws IOException {
		final Object[][] data = new Object[1][3];
     	data[0] = new Object[] { "","",""};
        String[] columns = new String[] {"ON_GROUND", "IN_THE_AIR", "NOT_IN_FRAME"};
     	TableModel model = new DefaultTableModel(data, columns);  
       
     	// Save the data to an ODS file and open it.
     	SpreadSheet emptySpreadSheet =  SpreadSheet.createEmpty(model);
     	return emptySpreadSheet;
	}
	

}