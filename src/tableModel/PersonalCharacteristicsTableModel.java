package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.CulturalPersonalCharacteristic;

public class PersonalCharacteristicsTableModel extends AbstractTableModel{
	
	private static final int COL_DESCRIPTION = 0;
	List<CulturalPersonalCharacteristic> line;
	private String[] column = new String[]{"Personal Characteristic"};
	
	public PersonalCharacteristicsTableModel(List<CulturalPersonalCharacteristic> cpc) {
		this.line = new ArrayList<>(cpc);
		
	}
	
	@Override
	public int getColumnCount() {
		return column.length;
	}
	@Override
	public int getRowCount() {
		return line.size();
	}
	@Override
	public String getColumnName(int columnIndex) {
		return column[columnIndex];
	}
	
	public Class getColumnClass() {
		return String.class;
	}
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		CulturalPersonalCharacteristic m = line.get(rowIndex);
		
		if (columnIndex == COL_DESCRIPTION) {
			return m.getDescription();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		CulturalPersonalCharacteristic u = line.get(row);
		if (column == COL_DESCRIPTION) {
			u.setDescription(aValue.toString());
		}
	}
	
	public CulturalPersonalCharacteristic getCPC(int index){
		return line.get(index);
	}

	public void addCPC(CulturalPersonalCharacteristic cpc) {
		line.add(cpc);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateCPC(int lineIndex, CulturalPersonalCharacteristic cpc) {
		line.set(lineIndex, cpc);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeCPC(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}
	
}
