package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AwardCeremonyProjectionTableModel extends AbstractTableModel {
    private String tableName = "AwardCeremony";
    private AwardCeremonyModel[] awardCeremonyModels;
    private List<String> columnNames;

    public AwardCeremonyProjectionTableModel(Model[] awardCeremonyModels, List<String> columnNames) {
        this.awardCeremonyModels = (AwardCeremonyModel[]) awardCeremonyModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return awardCeremonyModels.length;
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
            case "viewer_count":
                return awardCeremonyModels[rowIndex].getViewerCount();
            case "award_ceremony_date":
                return awardCeremonyModels[rowIndex].getDate();
            case "venue_name":
                return awardCeremonyModels[rowIndex].getVenueName();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}