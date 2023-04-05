package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VideoGameProjectionTableModel extends AbstractTableModel {
    private String tableName = "VideoGame";
    private VideoGameModel[] videoGameModels;
    private List<String> columnNames;

    public VideoGameProjectionTableModel(Model[] videoGameModels, List<String> columnNames) {
        this.videoGameModels = (VideoGameModel[]) videoGameModels;
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
        return result;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (getColumnName(columnIndex)) {
            case "title":
                return videoGameModels[rowIndex].getTitle();
            case "year":
                return videoGameModels[rowIndex].getYear();
            case "genre":
                return videoGameModels[rowIndex].getGenre();
            case "developer_name":
                return videoGameModels[rowIndex].getDeveloperName();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}