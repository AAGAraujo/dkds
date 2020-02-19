package tableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Language;

public class LanguageTableModel extends AbstractTableModel{
	
	private static final int COL_DESCRIPTION = 0;
	List<Language> line;
	private String[] column = new String[]{"Language"};
	
	public LanguageTableModel(List<Language> language) {
		this.line = new ArrayList<>(language);
		
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
		
		Language m = line.get(rowIndex);
		
		if (columnIndex == COL_DESCRIPTION) {
			return m.getDescription();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		Language u = line.get(row);
		if (column == COL_DESCRIPTION) {
			u.setDescription(aValue.toString());
		}
	}
	
	public Language getLanguage(int index){
		return line.get(index);
	}

	public void addLanguage(Language language) {
		line.add(language);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateLanguage(int lineIndex, Language language) {
		line.set(lineIndex, language);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeLanguage(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}
	
}
