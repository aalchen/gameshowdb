package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class LivestreamUrlProjectionTableModel extends AbstractTableModel {
    private LivestreamUrlModel[] livestreamUrlModels;
    private List<String> columnNames;

    public LivestreamUrlProjectionTableModel(LivestreamUrlModel[] livestreamUrlModels, List<String> columnNames) {
        this.livestreamUrlModels = livestreamUrlModels;
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
        switch (columnNames.get(columnIndex)) {
            case "URL":
                return "Url";
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
            case "Url":
                return livestreamUrlModels[rowIndex].getUrl();
            case "Language":
                return livestreamUrlModels[rowIndex].getLanguage();
            case "Name":
                return livestreamUrlModels[rowIndex].getName();
            case "Award Ceremony Date":
                return livestreamUrlModels[rowIndex].getAwardCeremonyDate();
        }
        return null;
    }
}
