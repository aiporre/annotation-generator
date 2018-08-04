package com.deeplearning.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WalkFolderFactory {


    public static final String TYPE_MANY_WALK_FOLDER = "MANY_WALK_FOLDER";
    public static final String TYPE_SIMPLE_WALK_FOLDER = "SIMPLE_WALK_FOLDER";

    public List<WalkFolder> getWalkFolder(String walkFolderType, List<String> legFiles){
        if(walkFolderType == null){
            return null;
        }
        List<WalkFolder> walkFolders = new ArrayList<>();
        if(walkFolderType.equalsIgnoreCase(TYPE_MANY_WALK_FOLDER)){
            List<Tuple<String, String>> pairs = findLegFilePairs(legFiles);
            pairs.forEach(pair -> {
                ManyWalkFolder e = new ManyWalkFolder(pair.x, pair.y);
                walkFolders.add(e);
            });
            return walkFolders;

        } else if(walkFolderType.equalsIgnoreCase(TYPE_SIMPLE_WALK_FOLDER)){
            String folderPath = new File(legFiles.get(0)).getParent();
            walkFolders.add(new SimpleWalkFolder(folderPath));
            return walkFolders;
        }
        return null;
    }

    private List<Tuple<String,String>> findLegFilePairs(List<String> legFiles){
        List<Tuple<String,String>> legFilePairs = new ArrayList<> ();
        // sort the files alphabetically
        legFiles.sort(String.CASE_INSENSITIVE_ORDER);
        // instantiate pairs of leg files
        int N = legFiles.size();
        for (int i = 0; i < N; i = i + 2) {
            legFilePairs.add(new Tuple<>(legFiles.get(i),legFiles.get(i+1)));
        }
        return legFilePairs;
    }

    public class Tuple<X, Y> {
        public final X x;
        public final Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

}


