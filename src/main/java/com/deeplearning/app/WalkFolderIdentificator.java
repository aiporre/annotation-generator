
package com.deeplearning.app;


import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.lang.String;


public class WalkFolderIdentificator {
	final static Logger logger = Logger.getLogger(WalkFolderIdentificator.class);

    private final String walkFoldersDirectory;

	public WalkFolderIdentificator(String walkFoldersDirectory){
		this.walkFoldersDirectory = walkFoldersDirectory;
	}
	// +dev: change to proper name it opens resutl file: 
	// +dev: handle error when empty.		
	public Map<String,List<WalkFolder>> identify(){
        logger.info("---> identifying the walk folders at: " +  this.walkFoldersDirectory);
		Map<String, List<WalkFolder>> forlderWalkFoldersMap = new HashMap<>();
		WalkFolderFactory walkFolderFactory = new WalkFolderFactory();
		if (verifyWalkFoldersDirectoryExist()){
			for(String folder: findFoldersInDirectory(this.walkFoldersDirectory)) {
				logger.info("Identifying : " + folder);
				String walkFolderType = this.getWalkFolderType(folder);
				List<String> legFiles = this.findLegFilesInDirectory(folder);
				List<WalkFolder> walkFolders = walkFolderFactory.getWalkFolder(walkFolderType,legFiles);
				if(walkFolderType != null){
					int walkCounter = walkFolders.size();
					forlderWalkFoldersMap.put(folder,walkFolders);
					logger.info("Number of sequences found in folder: " +
							folder.substring(folder.lastIndexOf(File.separator)+1) + " is " + walkCounter + ".");
				}
			}
		} else{
			logger.error("impossible to identify any walkfolder. Folder: " + this.walkFoldersDirectory + " don't exist.");
		}
        logger.info("Total number of walk sequences found: " + forlderWalkFoldersMap.size());
		return forlderWalkFoldersMap;
	}

	private boolean verifyWalkFoldersDirectoryExist() {
		return new File(this.walkFoldersDirectory).exists();
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