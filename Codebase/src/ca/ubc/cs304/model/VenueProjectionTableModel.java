package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VenueProjectionTableModel extends AbstractTableModel {
    private String tableName = "Venue";
    private VenueModel[] venueModels;
    private List<String> columnNames;

    public VenueProjectionTableModel(VenueModel[] venueModels, List<String> columnNames) {
        this.venueModels = venueModels;
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
        switch (columnNames.get(columnIndex)) {
            case "NAME":
                return "Name";
            case "ADDRESS":
                return "Address";
            case "CAPACITY":
                return "Capacity";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "Name":
                return venueModels[rowIndex].getName();
            case "Address":
                return venueModels[rowIndex].getAddress();
            case "Capacity":
                return venueModels[rowIndex].getCapacity();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
