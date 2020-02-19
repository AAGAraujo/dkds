 package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.ProcessPhase;

public class ArtifactsTableModel extends AbstractTableModel{

	private static final int COL_DESCRIPTION = 0;
	List<String> line;
	private String[] column = new String[]{"Description"};
	
	public ArtifactsTableModel(List<String> artifact) {
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
		
		String m = line.get(rowIndex);
		
		if (columnIndex == COL_DESCRIPTION) {
			return m;
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		String u = line.get(row);
		if (column == COL_DESCRIPTION) {
			u = aValue.toString();
		}
	}
	
	public String getArtifact(int index){
		return line.get(index);
	}

	public void addArtifact(String artifact) {
		line.add(artifact);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateArtifact(int lineIndex, String artifact) {
		line.set(lineIndex, artifact);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeArtifact(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}

}

