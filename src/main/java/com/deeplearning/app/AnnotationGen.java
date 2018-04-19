
package com.deeplearning.app;


import java.io.File; 
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;


// import java.io.BufferedReader;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.io.IOException;
// import java.util.ArrayList;

// import java.util.Map.Entry;
// import java.util.AbstractMap.SimpleEntry;

import java.lang.Integer;


import org.jopendocument.dom.OOUtils; 
import org.jopendocument.dom.spreadsheet.Sheet; 
import org.jopendocument.dom.spreadsheet.SpreadSheet; 

import com.deeplearning.app.AnnotationPair;
import com.deeplearning.app.WalkFolder;

public class AnnotationGen {
	private final File file;

	public AnnotationGen(){
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

	public void generateFile(){
		try{
			// ++dev: hard code name of walk folder object	
			// WalkFolder walkFolder = new WalkFolder("template");
			// TableModel model =  walkFolder.readAsSheetModel();
		    // Save the data to an ODS file and open it.
//			TableModel model ;
			System.out.println("----> identifying the walk folders...");
			WalkFolderIdentificator identificator = new WalkFolderIdentificator("./");
            Map<String, WalkFolder> walkFoldersMap = identificator.identify();


            SpreadSheet spreadSheet = generateEmptySpreadsheet();

            for(String k: walkFoldersMap.keySet()){
            	System.out.println("---> k: folder " + k);

            	WalkFolder walkFolder = walkFoldersMap.get(k);
            	Map<String, String> coodinatesMap = walkFolder.readAsCoordinatesMap();
            	final Sheet sheet = spreadSheet.addSheet(k);
            	sheet.setRowCount(walkFolder.sampleNumber()+1);
            	sheet.setColumnCount(3);
            	for(String coordinate: coodinatesMap.keySet()){
            		System.out.println("============>>>>>>>>>>>>  coordinate:" + coordinate);
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

            		sheet.setValueAt(coodinatesMap.get(coordinate),x,y);
            	}
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