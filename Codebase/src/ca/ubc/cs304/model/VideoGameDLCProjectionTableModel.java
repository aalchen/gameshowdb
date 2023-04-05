package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VideoGameDLCProjectionTableModel extends AbstractTableModel {
    private String tableName = "VideoGameDLC";
    private VideoGameDLCModel[] videoGameDLCModels;
    private List<String> columnNames;

    public VideoGameDLCProjectionTableModel(Model[] videoGameDLCModels, List<String> columnNames) {
        this.videoGameDLCModels = (VideoGameDLCModel[]) videoGameDLCModels;
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
        String result = columnNames.get(columnIndex);
        return result;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "title":
                return videoGameDLCModels[rowIndex].getTitle();
            case "year":
                return videoGameDLCModels[rowIndex].getYear();
            case "videogame_title":
                return videoGameDLCModels[rowIndex].getVideoGameTitle();
            case "videogame_year":
                return videoGameDLCModels[rowIndex].getVideoGameYear();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}
