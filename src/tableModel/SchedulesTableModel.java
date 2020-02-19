package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.ProcessPhase;
import model.Schedule;

public class SchedulesTableModel extends AbstractTableModel{

	private static final int COL_DESCRIPTION = 1;
	private static final int COL_DATETIME = 0;
	List<Schedule> line;
	private String[] column = new String[]{"Datetime","Description"};
	
	public SchedulesTableModel(List<Schedule> artifact) {
		this.line = new ArrayList<>(artifact);
		
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
		
		Schedule m = line.get(rowIndex);
		
		if (columnIndex == COL_DESCRIPTION) {
			return m.getDescription();
		}else if(columnIndex == COL_DATETIME){
			return m.getDateTime();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		Schedule u = line.get(row);
		if (column == COL_DESCRIPTION) {
			u.setDescription(aValue.toString());
		}else if(column == COL_DATETIME)
			u.setDateTime(aValue.toString());
	}
	
	public Schedule getSchedule(int index){
		return line.get(index);
	}

	public void addSchedule(Schedule artifact) {
		line.add(artifact);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateSchedule(int lineIndex, Schedule artifact) {
		line.set(lineIndex, artifact);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeSchedule(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}

}

