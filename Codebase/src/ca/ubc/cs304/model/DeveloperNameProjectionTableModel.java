package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DeveloperNameProjectionTableModel extends AbstractTableModel {
    private String tableName = "AwardCeremony";
    private DeveloperNameModel[] developerNameModels;
    private List<String> columnNames;

    public DeveloperNameProjectionTableModel(Model[] developerNameModels, List<String> columnNames) {
        this.developerNameModels = (DeveloperNameModel[]) developerNameModels;
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
        String result = columnNames.get(columnIndex);
        return result;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "lead_developer":
                return developerNameModels[rowIndex].getLeadDeveloper();
            case "website":
                return developerNameModels[rowIndex].getWebsite();
            case "name":
                return developerNameModels[rowIndex].getName();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
