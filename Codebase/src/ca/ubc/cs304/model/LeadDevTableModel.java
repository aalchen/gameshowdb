package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;

public class LeadDevTableModel extends AbstractTableModel {
    private VideoGameModel[] videoGameModels;

    public LeadDevTableModel(VideoGameModel[] videoGameModels) {
        this.videoGameModels = videoGameModels;
    }
    @Override
    public int getRowCount() {
        return videoGameModels.length;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return "Developer Name";
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return videoGameModels[rowIndex].getDeveloperName();
    }
}
