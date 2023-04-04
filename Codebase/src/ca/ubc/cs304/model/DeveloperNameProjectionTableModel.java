package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DeveloperNameProjectionTableModel extends AbstractTableModel {
    private String tableName = "AwardCeremony";
    private DeveloperNameModel[] developerNameModels;
    private List<String> columnNames;

    public DeveloperNameProjectionTableModel(DeveloperNameModel[] developerNameModels, List<String> columnNames) {
        this.developerNameModels = developerNameModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return developerNameModels.length;
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnNames.get(columnIndex)) {
            case "LEAD_DEVELOPER":
                return "Lead Developer";
            case "WEBSITE":
                return "Website";
            case "NAME":
                return "Name";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "Lead Developer":
                return developerNameModels[rowIndex].getLeadDeveloper();
            case "Website":
                return developerNameModels[rowIndex].getWebsite();
            case "Name":
                return developerNameModels[rowIndex].getName();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
