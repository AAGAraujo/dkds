package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.ProcessActivity;

public class ProcessActivityTableModel extends AbstractTableModel{

	private static final int COL_PROCESSACTIVITY = 0;
	List<ProcessActivity> line;
	private String[] column = new String[]{"ProcessActivity"};
	
	public ProcessActivityTableModel(List<ProcessActivity> processActivity) {
		this.line = new ArrayList<>(processActivity);
		
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
		
		ProcessActivity m = line.get(rowIndex);
		
		if (columnIndex == COL_PROCESSACTIVITY) {
			return m.getDescription();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		ProcessActivity u = line.get(row);
		if (column == COL_PROCESSACTIVITY) {
			u.setDescription(aValue.toString());
		}
	}
	
	public ProcessActivity getProcessActivity(int index){
		return line.get(index);
	}

	public void addProcessActivity(ProcessActivity processActivity) {
		line.add(processActivity);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateProcessActivity(int lineIndex, ProcessActivity processActivity) {
		line.set(lineIndex, processActivity);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeProcessActivity(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}

}

