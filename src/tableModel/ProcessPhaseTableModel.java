package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.ProcessPhase;

public class ProcessPhaseTableModel extends AbstractTableModel{

	private static final int COL_PROJECTPHASE = 0;
	List<ProcessPhase> line;
	private String[] column = new String[]{"ProcessPhase"};
	
	public ProcessPhaseTableModel(List<ProcessPhase> processPhase) {
		this.line = new ArrayList<>(processPhase);
		
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
		
		ProcessPhase m = line.get(rowIndex);
		
		if (columnIndex == COL_PROJECTPHASE) {
			return m.getDescription();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		ProcessPhase u = line.get(row);
		if (column == COL_PROJECTPHASE) {
			u.setDescription(aValue.toString());
		}
	}
	
	public ProcessPhase getProcessPhase(int index){
		return line.get(index);
	}

	public void addProcessPhase(ProcessPhase processPhase) {
		line.add(processPhase);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateProcessPhase(int lineIndex, ProcessPhase processPhase) {
		line.set(lineIndex, processPhase);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeProcessPhase(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}

}

