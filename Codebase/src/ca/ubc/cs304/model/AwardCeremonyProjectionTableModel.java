package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AwardCeremonyProjectionTableModel extends AbstractTableModel {
    private AwardCeremonyModel[] awardCeremonyModels;
    private List<String> columnNames;

    public AwardCeremonyProjectionTableModel(AwardCeremonyModel[] awardCeremonyModels, List<String> columnNames) {
        this.awardCeremonyModels = awardCeremonyModels;
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
        switch (columnNames.get(columnIndex)) {
            case "VIEWER_COUNT":
                return "Viewer Count";
            case "AWARD_CEREMONY_DATE":
                return "Award Ceremony Date";
            case "VENUE_NAME":
                return "Venue Name";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "Viewer Count":
                return awardCeremonyModels[rowIndex].getViewerCount();
            case "Award Ceremony Date":
                return awardCeremonyModels[rowIndex].getDate();
            case "Venue Name":
                return awardCeremonyModels[rowIndex].getVenueName();
        }
        return null;
    }
}