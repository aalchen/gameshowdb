package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StaffProjectionTableModel extends AbstractTableModel {
    private String tableName = "Staff";
    private StaffModel[] staffModels;
    private List<String> columnNames;

    public StaffProjectionTableModel(StaffModel[] staffModels, List<String> columnNames) {
        this.staffModels = staffModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return staffModels.length;
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnNames.get(columnIndex)) {
            case "PHONE_NUMBER":
                return "Phone Number";
            case "NAME":
                return "Name";
            case "ID":
                return "Id";
            case "ROLE":
                return "Role";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "Phone Number":
                return staffModels[rowIndex].getPhoneNumber();
            case "Name":
                return staffModels[rowIndex].getName();
            case "Id":
                return staffModels[rowIndex].getId();
            case "Role":
                return staffModels[rowIndex].getRole();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
