package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CommunityAwardProjectionTableModel extends AbstractTableModel {
    private CommunityAwardModel[] communityAwardModels;
    private List<String> columnNames;

    public CommunityAwardProjectionTableModel(CommunityAwardModel[] communityAwardModels, List<String> columnNames) {
        this.communityAwardModels = communityAwardModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return communityAwardModels.length;
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnNames.get(columnIndex)) {
            case "VOTES":
                return "Votes";
            case "DEADLINE":
                return "Deadline";
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
            case "Votes":
                return communityAwardModels[rowIndex].getVotes();
            case "Deadline":
                return communityAwardModels[rowIndex].getDeadline();
            case "Award Name":
                return communityAwardModels[rowIndex].getAwardName();
            case "Award Date":
                return communityAwardModels[rowIndex].getAwardDate();
        }
        return null;
    }
}