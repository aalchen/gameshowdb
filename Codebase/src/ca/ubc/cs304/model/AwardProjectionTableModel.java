package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AwardProjectionTableModel extends AbstractTableModel {
    private String tableName = "Award";
    private AwardModel[] awardModels;
    private List<String> columnNames;

    public AwardProjectionTableModel(AwardModel[] awardModels, List<String> columnNames) {
        this.awardModels = awardModels;
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
        switch (columnNames.get(columnIndex)) {
            case "Name":
                return "Name";
            case "PRIZE":
                return "Prize";
            case "AWARD_DATE":
                return "Award Date";
            case "VIDEOGAME_TITLE":
                return "Video Game Title";
            case "VIDEOGAME_YEAR":
                return "Video Game Year";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "Name":
                return awardModels[rowIndex].getName();
            case "Prize":
                return awardModels[rowIndex].getPrize();
            case "Award Date":
                return awardModels[rowIndex].getDate();
            case "Video Game Title":
                return awardModels[rowIndex].getVideoGameTitle();
            case "Video Game Year":
                return awardModels[rowIndex].getVideoGameYear();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}