package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StaffProjectionTableModel extends AbstractTableModel {
    private String tableName = "Staff";
    private StaffModel[] staffModels;
    private List<String> columnNames;

    public StaffProjectionTableModel(Model[] staffModels, List<String> columnNames) {
        this.staffModels = (StaffModel[]) staffModels;
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
        String result = columnNames.get(columnIndex);
        return result;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "phone_number":
                return staffModels[rowIndex].getPhoneNumber();
            case "name":
                return staffModels[rowIndex].getName();
            case "id":
                return staffModels[rowIndex].getId();
            case "role":
                return staffModels[rowIndex].getRole();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
