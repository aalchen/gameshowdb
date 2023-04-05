package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AwardProjectionTableModel extends AbstractTableModel {
    private String tableName = "Award";
    private AwardModel[] awardModels;
    private List<String> columnNames;

    public AwardProjectionTableModel(Model[] awardModels, List<String> columnNames) {
        this.awardModels = (AwardModel[]) awardModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return awardModels.length;
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
                return awardModels[rowIndex].getName();
            case "prize":
                return awardModels[rowIndex].getPrize();
            case "award_date":
                return awardModels[rowIndex].getDate();
            case "videogame_title":
                return awardModels[rowIndex].getVideoGameTitle();
            case "videogame_year":
                return awardModels[rowIndex].getVideoGameYear();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}