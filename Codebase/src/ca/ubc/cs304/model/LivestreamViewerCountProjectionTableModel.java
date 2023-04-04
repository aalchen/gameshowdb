package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class LivestreamViewerCountProjectionTableModel extends AbstractTableModel {
    private String tableName = "LivestreamViewerCount";
    private LivestreamViewerCountModel[] livestreamViewerCountModels;
    private List<String> columnNames;

    public LivestreamViewerCountProjectionTableModel(Model[] livestreamViewerCountModels, List<String> columnNames) {
        this.livestreamViewerCountModels = (LivestreamViewerCountModel[]) livestreamViewerCountModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return livestreamViewerCountModels.length;
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnNames.get(columnIndex)) {
            case "VIEWER_COUNT":
                return "Viewer Count";
            case "LANGUAGE":
                return "Language";
            case "NAME":
                return "Name";
            case "AWARD_CEREMONY_DATE":
                return "Award Ceremony Date";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "Viewer Count":
                return livestreamViewerCountModels[rowIndex].getViewerCount();
            case "Language":
                return livestreamViewerCountModels[rowIndex].getLanguage();
            case "Name":
                return livestreamViewerCountModels[rowIndex].getName();
            case "Award Ceremony Date":
                return livestreamViewerCountModels[rowIndex].getAwardCeremonyDate();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
