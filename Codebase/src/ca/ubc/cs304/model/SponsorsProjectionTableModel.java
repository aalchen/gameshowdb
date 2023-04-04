package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SponsorsProjectionTableModel extends AbstractTableModel {
    private String tableName = "Sponsors";
    private SponsorsModel[] sponsorsModels;
    private List<String> columnNames;

    public SponsorsProjectionTableModel(Model[] sponsorsModels, List<String> columnNames) {
        this.sponsorsModels = (SponsorsModel[]) sponsorsModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return sponsorsModels.length;
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnNames.get(columnIndex)) {
            case "COMPANY_NAME":
                return "Company Name";
            case "MONEY":
                return "Money";
            case "AWARD_CEREMONY_DATE":
                return "Award Ceremony Date";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "Company Name":
                return sponsorsModels[rowIndex].getCompanyName();
            case "Money":
                return sponsorsModels[rowIndex].getMoney();
            case "Award Ceremony Date":
                return sponsorsModels[rowIndex].getAwardCeremonyDate();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
