package tableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Skills;

public class SkillTableModel extends AbstractTableModel{
	
	private static final int COL_DESCRIPTION = 0;
	List<Skills> line;
	private String[] column = new String[]{"Skills"};
	
	public SkillTableModel(List<Skills> skill) {
		this.line = new ArrayList<>(skill);
		
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
		
		Skills m = line.get(rowIndex);
		
		if (columnIndex == COL_DESCRIPTION) {
			return m.getDescription();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		Skills u = line.get(row);
		if (column == COL_DESCRIPTION) {
			u.setDescription(aValue.toString());
		}
	}
	
	public Skills getSkills(int index){
		return line.get(index);
	}

	public void addSkills(Skills skills) {
		line.add(skills);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateSkills(int lineIndex, Skills skills) {
		line.set(lineIndex, skills);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeSkills(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}
	
}
