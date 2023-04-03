package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;

public class DeveloperNameVideoGameTableModel extends AbstractTableModel {
    private DeveloperNameVideoGameModel[] videoGameModels;

    public DeveloperNameVideoGameTableModel(DeveloperNameVideoGameModel[] videoGameModels) {
        this.videoGameModels = videoGameModels;
    }
    @Override
    public int getRowCount() {
        return videoGameModels.length;
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Game Title";
            case 1:
                return "Year Released";
            case 2:
                return "VideoGame Genre";
            case 3:
                return "Developer Name";
            case 4:
                return "Lead Developer";
            case 5:
                return "Developer Website";
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
            case 4:
                return videoGameModels[rowIndex].getLeadDeveloper();
            case 5:
                return videoGameModels[rowIndex].getWebsite();
        }
        return null;
    }
}
