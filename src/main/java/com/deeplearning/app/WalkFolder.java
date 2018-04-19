package com.deeplearning.app;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.util.HashMap;
import java.util.Map;

import com.deeplearning.app.LegFile;

public class WalkFolder {
	// private String folderName;
	private LegFile  legFileLeft;
	private LegFile  legFileRight;

	public WalkFolder(String folderName){
		this.legFileLeft = new LegFile(folderName + "/left.csv");
	    this.legFileRight = new LegFile(folderName + "/right.csv");
	}

	public boolean hasLegFiles(){
		return this.legFileLeft.exists() && this.legFileRight.exists();
	}

	public TableModel readAsSheetModel(){

			String[] serieLeft = this.legFileLeft.getColumnSeries();
            String[] serieRight = this.legFileRight.getColumnSeries();

            System.out.println("============+>>> serie: " + serieLeft);
            // +dev: ensure N for right and left are equal
            int N = serieLeft.length;

            for(int i =0; i<N; i++){
        		System.out.println("value ( "+i+") : " + serieLeft[i]);
        	}
   			

   			final Object[][] data = new Object[N][2];
   			// +dev: Hardcoded reading file

			
            for(int i =0; i<N; i++){
            	data[i] = new Object[] { i, serieLeft[i],serieRight[i] };
        	}
			String[] columns = new String[] { "frame_id", "left_foot", "right_foot" };
			TableModel model = new DefaultTableModel(data, columns);  
			return model;
	}

	public int sampleNumber(){
		String[] serieLeft = this.legFileLeft.getColumnSeries();
		return serieLeft.length;
	}

	public Map<String,String> readAsCoordinatesMap(){

			String[] serieLeft = this.legFileLeft.getColumnSeries();
            String[] serieRight = this.legFileRight.getColumnSeries();
			int N = serieLeft.length;

            HashMap<String, String> coodinatesMap = new HashMap<String, String>();
			coodinatesMap.put("A1", "frame_id");
           	coodinatesMap.put("B1", "left_foot");
           	coodinatesMap.put("C1", "right_foot");
            // +dev: ensure N for right and left are equal
            int index;
            for(int i =0; i<N; i++){
            	//data[i] = new Object[] { i, serieLeft[i],serieRight[i] };
            	index = i+2;
            	coodinatesMap.put("A"+index,"" + i);
            	coodinatesMap.put("B"+index,serieLeft[i]);
            	coodinatesMap.put("C"+index,serieRight[i]);
        	}
			
			return coodinatesMap;
	}
}