package tableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import model.Member;
import model.ProcessPhase;

public class IndicateMemberTableModel extends AbstractTableModel{

	private static final int COL_NAME= 0;
	List<Member> line;
	private String[] column = new String[]{"Name"};
	
	public IndicateMemberTableModel(List<Member> artifact) {
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
		
		Member m = line.get(rowIndex);
		
		if (columnIndex == COL_NAME) {
			return m.getName();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		Member u = line.get(row);
		if (column == COL_NAME) {
			u.setName(aValue.toString());
		}
	}
	
	public Member getMember(int index){
		return line.get(index);
	}

	public void addMember(Member m) {
		line.add(m);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateMember(int lineIndex, Member member) {
		line.set(lineIndex, member);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeMember(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}

}

