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
        String result = columnNames.get(columnIndex);
        return result;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "company_name":
                return sponsorsModels[rowIndex].getCompanyName();
            case "money":
                return sponsorsModels[rowIndex].getMoney();
            case "awardceremony_date":
                return sponsorsModels[rowIndex].getAwardCeremonyDate();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
