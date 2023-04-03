package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;

public class VideoGameTableModel extends AbstractTableModel {
    private VideoGameModel[] videoGameModels;

    public VideoGameTableModel(VideoGameModel[] videoGameModels) {
        this.videoGameModels = videoGameModels;
    }
    @Override
    public int getRowCount() {
        return videoGameModels.length;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Title";
            case 1:
                return "Year";
            case 2:
                return "Genre";
            case 3:
                return"Developer Name";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return videoGameModels[rowIndex].getTitle();
            case 1:
                return videoGameModels[rowIndex].getYear();
            case 2:
                return videoGameModels[rowIndex].getGenre();
            case 3:
                return videoGameModels[rowIndex].getDeveloperName();
        }
        return null;
    }
}
