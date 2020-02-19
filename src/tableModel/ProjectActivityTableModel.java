package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.ProjectActivity;

public class ProjectActivityTableModel extends AbstractTableModel{

	private static final int COL_PROJECTACTIVITY = 0;
	List<ProjectActivity> line;
	private String[] column = new String[]{"ProjectActivity"};
	
	public ProjectActivityTableModel(List<ProjectActivity> projectActivity) {
		this.line = new ArrayList<>(projectActivity);
		
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
		
		ProjectActivity m = line.get(rowIndex);
		
		if (columnIndex == COL_PROJECTACTIVITY) {
			return m.getDescription();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		ProjectActivity u = line.get(row);
		if (column == COL_PROJECTACTIVITY) {
			u.setDescription(aValue.toString());
		}
	}
	
	public ProjectActivity getProjectActivity(int index){
		return line.get(index);
	}

	public void addProjectActivity(ProjectActivity projectActivity) {
		line.add(projectActivity);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateProjectActivity(int lineIndex, ProjectActivity projectActivity) {
		line.set(lineIndex, projectActivity);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeProjectActivity(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}

}

