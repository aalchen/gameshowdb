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
        String result = columnNames.get(columnIndex);
        return result;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "viewer_count":
                return livestreamViewerCountModels[rowIndex].getViewerCount();
            case "language":
                return livestreamViewerCountModels[rowIndex].getLanguage();
            case "name":
                return livestreamViewerCountModels[rowIndex].getName();
            case "awardceremony_date":
                return livestreamViewerCountModels[rowIndex].getAwardCeremonyDate();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
