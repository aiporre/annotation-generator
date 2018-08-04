
package com.deeplearning.app;


import java.io.File; 
import java.io.FileFilter;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.lang.String;


public class WalkFolderIdentificator {
    private final String walkFoldersDirectory;

	public WalkFolderIdentificator(String walkFoldersDirectory){
		this.walkFoldersDirectory = walkFoldersDirectory;
	}
	// +dev: change to proper name it opens resutl file: 
	// +dev: handle error when empty.		
	public Map<String,WalkFolder> identify(){
		Map<String, WalkFolder> walkFoldersNamePaired = new HashMap<>();
		WalkFolderFactory walkFolderFactory = new WalkFolderFactory();
		System.out.println("--->> walkFolders.size() = " + walkFoldersNamePaired.size());
		for(String folder: findFoldersInDirectory(this.walkFoldersDirectory)) {
			System.out.println("--->> folder: " + folder);
			String walkFolderType = this.getWalkFolderType(folder);
			List<String> legFiles = this.findLegFilesInDirectory(folder);
			List<WalkFolder> walkFolders = walkFolderFactory.getWalkFolder(walkFolderType,legFiles);
			if(walkFolderType != null){
                for (WalkFolder wf: walkFolders) {
                    walkFoldersNamePaired.put(wf.getIdentifier(), wf);
                }

			}
		}

		return walkFoldersNamePaired;
	}
	private String getWalkFolderType(String folder){
	    String walkFolderType = null;
        List<String> legFilesList = this.findLegFilesInDirectory(folder);
        if (legFilesList.size() == 2){
            walkFolderType = WalkFolderFactory.TYPE_SIMPLE_WALK_FOLDER;
        } else if (legFilesList.size() > 2){
            walkFolderType = WalkFolderFactory.TYPE_MANY_WALK_FOLDER;
        } else {
            walkFolderType = null;
        }
	    return walkFolderType;
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
        	foldersInDirectory.add(directoryAsFile.getAbsolutePath());
    	}
    	return foldersInDirectory;
	}

    private List<String> findLegFilesInDirectory(String directoryPath) {
		List<String> legFilesInDirectory = new ArrayList<>();
		File directory = new File(directoryPath);
		FileFilter legFileFilter = new FileFilter() {
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return false;
                } else {
                    return file.getName().endsWith("left.csv") || file.getName().endsWith("right.csv");
                }
            }
        };

		File[] legFiles = directory.listFiles(legFileFilter);

		if(legFiles != null){
			for (File legFile : legFiles) {
				legFilesInDirectory.add(legFile.getAbsolutePath());
			}
		}
        return legFilesInDirectory;
    }
}