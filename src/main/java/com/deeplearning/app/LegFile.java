package com.deeplearning.app;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

// import java.util.Map.Entry;
// import java.util.AbstractMap.SimpleEntry;

import java.lang.Integer;

import com.deeplearning.app.AnnotationPair;
import org.apache.log4j.Logger;


public class LegFile {
	final static Logger logger = Logger.getLogger(LegFile.class);

	private String fileName;
	public LegFile(String fileName){
		this.fileName = fileName;
	}

	public String[] getColumnSeries() {
 			CSVParser parser = new CSVParser();
			logger.debug("====>>> parsing..");
			
			List<AnnotationPair> annotationPairs = parser.parseCSVFile(this.fileName);
			logger.debug("---> elements: " + annotationPairs);
			annotationPairs.forEach((AnnotationPair e) -> logger.debug("====> lambda print e : " + e.getLeft()));

			logger.debug("... generating Series");
			List<String> columnSeries = new ArrayList<String>();

			int N = annotationPairs.get(annotationPairs.size() - 1).getRight();
			logger.debug("++++++------> max value: " + N);
			int indexAnnotationPairs = 0;
			AnnotationPair p = annotationPairs.get(indexAnnotationPairs); 
			for(int i=0; i<N+1; i++){
				if(i > p.getRight()){
					indexAnnotationPairs ++;
					p = annotationPairs.get(indexAnnotationPairs);
				}
				String value = "";
				if(p.getLeft().equals("a")){
					value = "IN_THE_AIR";
				} else if(p.getLeft().equals("g")){
					value = "ON_GROUND";
				} else if(p.getLeft().equals("n")){
					value = "NOT_IN_FRAME";
				}
				columnSeries.add(value);
			}

			String[] columnSeriesArr = new String[columnSeries.size()];
			columnSeriesArr = columnSeries.toArray(columnSeriesArr);

			return columnSeriesArr;
	}

	public boolean exists(){
		return new File(this.fileName).exists();
	}

    public String getFileNameParent() {
        return new File(this.fileName).getParent();
    }


    private final class CSVParser {
		private BufferedReader br;
        private String line;
        private String cvsSplitBy;


		public CSVParser(){
			// +dev:  hard coded initial values for this parser
			}
			
	    public List parseCSVFile(String csvFileName)
	    {
            List<AnnotationPair> pairList = new ArrayList<>();
            String line = "";
            BufferedReader br = null;
            String cvsSplitBy = ",";
		
            try {
                br = new BufferedReader(new FileReader(csvFileName));
                logger.debug("======> line " + br);
                while ((line = br.readLine()) != null) {
                    // use comma as separator
                    logger.debug("======> line " + line);
                    String[] country = line.split(cvsSplitBy);
                    logger.debug(" The entry for the csv file is: " + country[0] + "," + country[1]);
                    AnnotationPair pair = new AnnotationPair(country[0],Integer.parseInt(country[1]));
                    pairList.add(pair);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return pairList;
    	    
	    }
	}
}