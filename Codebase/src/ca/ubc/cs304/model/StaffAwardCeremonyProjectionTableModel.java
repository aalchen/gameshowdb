package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StaffAwardCeremonyProjectionTableModel extends AbstractTableModel {
    private String tableName = "StaffAwardCeremony";
    private StaffAwardCeremonyModel[] staffAwardCeremonyModels;
    private List<String> columnNames;

    public StaffAwardCeremonyProjectionTableModel(Model[] staffAwardCeremonyModels, List<String> columnNames) {
        this.staffAwardCeremonyModels = (StaffAwardCeremonyModel[]) staffAwardCeremonyModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return staffAwardCeremonyModels.length;
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
            case "staff_id":
                return staffAwardCeremonyModels[rowIndex].getStaffId();
            case "awardceremony_date":
                return staffAwardCeremonyModels[rowIndex].getAwardCeremonyDate();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
