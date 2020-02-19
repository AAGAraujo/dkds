package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.ProjectPhase;

public class ProjectPhaseTableModel extends AbstractTableModel{

	private static final int COL_PROJECTPHASE = 0;
	List<ProjectPhase> line;
	private String[] column = new String[]{"ProjectPhase"};
	
	public ProjectPhaseTableModel(List<ProjectPhase> projectPhase) {
		this.line = new ArrayList<>(projectPhase);
		
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
		
		ProjectPhase m = line.get(rowIndex);
		
		if (columnIndex == COL_PROJECTPHASE) {
			return m.getDescription();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		ProjectPhase u = line.get(row);
		if (column == COL_PROJECTPHASE) {
			u.setDescription(aValue.toString());
		}
	}
	
	public ProjectPhase getProjectPhase(int index){
		return line.get(index);
	}

	public void addProjectPhase(ProjectPhase projectPhase) {
		line.add(projectPhase);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateProjectPhase(int lineIndex, ProjectPhase projectPhase) {
		line.set(lineIndex, projectPhase);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeProjectPhase(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}

}

