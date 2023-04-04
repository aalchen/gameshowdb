package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VideoGameProjectionTableModel extends AbstractTableModel {
    private String tableName = "VideoGame";
    private VideoGameModel[] videoGameModels;
    private List<String> columnNames;

    public VideoGameProjectionTableModel(VideoGameModel[] videoGameModels, List<String> columnNames) {
        this.videoGameModels = videoGameModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return videoGameModels.length;
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        String result = columnNames.get(columnIndex);
        if (result == "Developer_Name") {
            return "Developer Name";
        }
        return result;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "Title":
                return videoGameModels[rowIndex].getTitle();
            case "Year":
                return videoGameModels[rowIndex].getYear();
            case "Genre":
                return videoGameModels[rowIndex].getGenre();
            case "Developer Name":
                return videoGameModels[rowIndex].getDeveloperName();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}