package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SponsoredAwardProjectionTableModel extends AbstractTableModel {
    private String tableName = "SponsoredAward";
    private SponsoredAwardModel[] sponsoredAwardModels;
    private List<String> columnNames;

    public SponsoredAwardProjectionTableModel(Model[] sponsoredAwardModels, List<String> columnNames) {
        this.sponsoredAwardModels = (SponsoredAwardModel[]) sponsoredAwardModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return sponsoredAwardModels.length;
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
            case "sponsor":
                return sponsoredAwardModels[rowIndex].getSponsor();
            case "award_name":
                return sponsoredAwardModels[rowIndex].getAwardName();
            case "award_date":
                return sponsoredAwardModels[rowIndex].getAwardDate();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
