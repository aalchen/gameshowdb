package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;

public class DeveloperNameTableModel extends AbstractTableModel {
    private DeveloperNameModel[] developerNameModels;

    public DeveloperNameTableModel(DeveloperNameModel[] developerNameModels) {
        this.developerNameModels = developerNameModels;
    }
    @Override
    public int getRowCount() {
        return developerNameModels.length;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Name";
            case 1:
                return "Website";
            case 2:
                return "Lead Developer";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return developerNameModels[rowIndex].getName();
            case 1:
                return developerNameModels[rowIndex].getWebsite();
            case 2:
                return developerNameModels[rowIndex].getLeadDeveloper();
        }
        return null;
    }
}
