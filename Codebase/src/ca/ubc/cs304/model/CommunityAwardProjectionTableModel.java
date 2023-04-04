package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CommunityAwardProjectionTableModel extends AbstractTableModel {
    private String tableName = "CommunityAward";
    private CommunityAwardModel[] communityAwardModels;
    private List<String> columnNames;

    public CommunityAwardProjectionTableModel(Model[] communityAwardModels, List<String> columnNames) {
        this.communityAwardModels = (CommunityAwardModel[]) communityAwardModels;
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
        String result = columnNames.get(columnIndex);
        return result;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "votes":
                return communityAwardModels[rowIndex].getVotes();
            case "deadline":
                return communityAwardModels[rowIndex].getDeadline();
            case "award_name":
                return communityAwardModels[rowIndex].getAwardName();
            case "award_date":
                return communityAwardModels[rowIndex].getAwardDate();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}