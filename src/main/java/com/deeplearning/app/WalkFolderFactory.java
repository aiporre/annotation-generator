package com.deeplearning.app;

public class WalkFolderFactory {


    public static final String TYPE_MANY_WALK_FOLDER = "MANY_WALK_FOLDER";
    public static final String TYPE_SIMPLE_WALK_FOLDER = "SIMPLE_WALK_FOLDER";

    public WalkFolder getWalkFolder(String walkFolderType, String folderPath){
        if(walkFolderType == null){
            return null;
        }
        if(walkFolderType.equalsIgnoreCase(TYPE_MANY_WALK_FOLDER)){
            return new ManyWalkFolder(folderPath);

        } else if(walkFolderType.equalsIgnoreCase(TYPE_SIMPLE_WALK_FOLDER)){
            return new SimpleWalkFolder(folderPath);
        }

        return null;
    }

}


