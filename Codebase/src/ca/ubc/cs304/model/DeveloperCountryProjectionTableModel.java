package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DeveloperCountryProjectionTableModel extends AbstractTableModel {
    private String tableName = "DeveloperCountry";
    private DeveloperCountryModel[] developerCountryModels;
    private List<String> columnNames;

    public DeveloperCountryProjectionTableModel(Model[] developerCountryModels, List<String> columnNames) {
        this.developerCountryModels = (DeveloperCountryModel[]) developerCountryModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return developerCountryModels.length;
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
            case "name":
                return developerCountryModels[rowIndex].getName();
            case "country":
                return developerCountryModels[rowIndex].getCountry();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}