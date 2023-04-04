package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DeveloperCountryProjectionTableModel extends AbstractTableModel {
    private DeveloperCountryModel[] developerCountryModels;
    private List<String> columnNames;

    public DeveloperCountryProjectionTableModel(DeveloperCountryModel[] developerCountryModels, List<String> columnNames) {
        this.developerCountryModels = developerCountryModels;
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
        switch (columnNames.get(columnIndex)) {
            case "Name":
                return "Name";
            case "COUNTRY":
                return "Country";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "Name":
                return developerCountryModels[rowIndex].getLeadDeveloper();
            case "Country":
                return developerCountryModels[rowIndex].getCountry();
        }
        return null;
    }
}