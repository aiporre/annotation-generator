package com.deeplearning.app;

import javax.swing.table.TableModel;
import java.util.Map;

public interface WalkFolder {

	boolean hasLegFiles();

	TableModel readAsSheetModel();

	int annotationsNumber();

	Map<String,String> readAsCoordinatesMap();

    String getIdentifier();
}
