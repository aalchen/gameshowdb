package ca.ubc.cs304.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CompanyProjectionTableModel extends AbstractTableModel {
    private String tableName = "Company";
    private CompanyModel[] companyModels;
    private List<String> columnNames;

    public CompanyProjectionTableModel(Model[] companyModels, List<String> columnNames) {
        this.companyModels = (CompanyModel[]) companyModels;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return companyModels.length;
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
            case "name":
                return companyModels[rowIndex].getName();
            case "contact_info":
                return companyModels[rowIndex].getContactInfo();
        }
        return null;
    }

    public String getTableName() {
        return this.tableName;
    }
}