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
        switch (columnNames.get(columnIndex)) {
            case "SPONSOR":
                return "Sponsor";
            case "AWARD_NAME":
                return "Award Name";
            case "AWARD_DATE":
                return "Award Date";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "Sponsor":
                return sponsoredAwardModels[rowIndex].getSponsor();
            case "Award Name":
                return sponsoredAwardModels[rowIndex].getAwardName();
            case "Award Date":
                return sponsoredAwardModels[rowIndex].getAwardDate();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
