package tableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Challenge;

public class ChallengeTableModel extends AbstractTableModel{
	
	private static final int COL_CHALLENGE = 0;
	List<Challenge> line;
	private String[] column = new String[]{"Challenge"};
	
	public ChallengeTableModel(List<Challenge> challenge) {
		this.line = new ArrayList<>(challenge);
		
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
		
		Challenge m = line.get(rowIndex);
		
		if (columnIndex == COL_CHALLENGE) {
			return m.getDescription();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		Challenge u = line.get(row);
		if (column == COL_CHALLENGE) {
			u.setDescription(aValue.toString());
		}
	}
	
	public Challenge getChallenge(int index){
		return line.get(index);
	}

	public void addChallenge(Challenge challenge) {
		line.add(challenge);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateChallenge(int lineIndex, Challenge challenge) {
		line.set(lineIndex, challenge);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeChallenge(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}

}
