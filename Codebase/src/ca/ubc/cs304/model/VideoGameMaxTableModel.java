package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;

public class VideoGameMaxTableModel extends AbstractTableModel {
    private VideoGameCountModel[] videoGameCountModels;

    public VideoGameMaxTableModel(VideoGameCountModel[] videoGameCountModels) {
        this.videoGameCountModels = videoGameCountModels;
    }
    @Override
    public int getRowCount() {
        return videoGameCountModels.length;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Developer Name";
            case 1:
                return "Genre";
            case 2:
                return "Most Recent Release Year";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return videoGameCountModels[rowIndex].getDeveloperName();
            case 1:
                return videoGameCountModels[rowIndex].getGenre();
            case 2:
                return videoGameCountModels[rowIndex].getColumnNum();
        }
        return null;
    }
}
