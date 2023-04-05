package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class LivestreamUrlProjectionTableModel extends AbstractTableModel {
    private String tableName = "LivestreamUrl";
    private LivestreamUrlModel[] livestreamUrlModels;
    private List<String> columnNames;

    public LivestreamUrlProjectionTableModel(Model[] livestreamUrlModels, List<String> columnNames) {
        this.livestreamUrlModels = (LivestreamUrlModel[]) livestreamUrlModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return livestreamUrlModels.length;
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
            case "url":
                return livestreamUrlModels[rowIndex].getUrl();
            case "language":
                return livestreamUrlModels[rowIndex].getLanguage();
            case "name":
                return livestreamUrlModels[rowIndex].getName();
            case "awardceremony_date":
                return livestreamUrlModels[rowIndex].getAwardCeremonyDate();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
