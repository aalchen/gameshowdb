package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VenueProjectionTableModel extends AbstractTableModel {
    private String tableName = "Venue";
    private VenueModel[] venueModels;
    private List<String> columnNames;

    public VenueProjectionTableModel(Model[] venueModels, List<String> columnNames) {
        this.venueModels = (VenueModel[]) venueModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return venueModels.length;
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
                return venueModels[rowIndex].getName();
            case "address":
                return venueModels[rowIndex].getAddress();
            case "capacity":
                return venueModels[rowIndex].getCapacity();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
