package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;

public class VideoGameCountTableModel extends AbstractTableModel {
    private VideoGameCountModel[] videoGameCountModels;

    public VideoGameCountTableModel(VideoGameCountModel[] videoGameCountModels) {
        this.videoGameCountModels = videoGameCountModels;
    }
    @Override
    public int getRowCount() {
        return videoGameCountModels.length;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Developer Name";
            case 1:
                return "Count";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return videoGameCountModels[rowIndex].getDeveloperName();
            case 1:
                return videoGameCountModels[rowIndex].getColumnNum();
        }
        return null;
    }
}
