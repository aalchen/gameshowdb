package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VideoGameDLCProjectionTableModel extends AbstractTableModel {
    private String tableName = "VideoGameDLC";
    private VideoGameDLCModel[] videoGameDLCModels;
    private List<String> columnNames;

    public VideoGameDLCProjectionTableModel(VideoGameDLCModel[] videoGameDLCModels, List<String> columnNames) {
        this.videoGameDLCModels = videoGameDLCModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return videoGameDLCModels.length;
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnNames.get(columnIndex)) {
            case "TITLE":
                return "Title";
            case "YEAR":
                return "Year";
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
            case "Title":
                return videoGameDLCModels[rowIndex].getTitle();
            case "Year":
                return videoGameDLCModels[rowIndex].getYear();
            case "Video Game Title":
                return videoGameDLCModels[rowIndex].getVideoGameTitle();
            case "Video Game Year":
                return videoGameDLCModels[rowIndex].getVideoGameYear();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
