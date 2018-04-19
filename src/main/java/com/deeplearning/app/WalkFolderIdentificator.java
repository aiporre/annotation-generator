
package com.deeplearning.app;


import java.io.File; 
import java.io.FileFilter;
// import javax.swing.table.TableModel;

// import java.io.BufferedReader;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

// import java.util.Map.Entry;
// import java.util.AbstractMap.SimpleEntry;

// import java.lang.Integer;
import java.lang.String;

// import org.jopendocument.dom.OOUtils; 
// import org.jopendocument.dom.spreadsheet.Sheet; 
// import org.jopendocument.dom.spreadsheet.SpreadSheet; 

// import com.deeplearning.app.AnnotationPair;
import com.deeplearning.app.WalkFolder;

public class WalkFolderIdentificator {
	private final String currentDirectory;

	public WalkFolderIdentificator(String currentDirectory){
		this.currentDirectory = currentDirectory;
	}
	// +dev: change to proper name it opens resutl file: 
	// +dev: handle error when empty.		
	public Map<String,WalkFolder> identify(){
		Map<String, WalkFolder> walkFolders = new HashMap<>();	
		System.out.println("--->> walkFolders.size() = " + walkFolders.size());
		for(String folder: findFoldersInDirectory(this.currentDirectory)) {
			System.out.println("--->> folder: " + folder);
			WalkFolder walkFolder = new WalkFolder(folder);
			if(walkFolder.hasLegFiles()){
				walkFolders.put(folder, walkFolder);
			}
		}

		return walkFolders;
	}

	private List<String> findFoldersInDirectory(String directoryPath) {
    	File directory = new File(directoryPath);
	
    	FileFilter directoryFileFilter = new FileFilter() {
        	public boolean accept(File file) {
            	return file.isDirectory();
        	}
    	};
		
    	File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
    	List<String> foldersInDirectory = new ArrayList<String>(directoryListAsFile.length);
    	for (File directoryAsFile : directoryListAsFile) {
        	foldersInDirectory.add(directoryAsFile.getName());
    	}
    	return foldersInDirectory;
	}
}