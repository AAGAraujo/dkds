package tableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Member;

public class MemberTableModel extends AbstractTableModel{
	
	private static final int COL_NAME = 0;
	private static final int COL_EMAIL = 1;
	private static final int COL_LOCALIZATION = 2;
	List<Member> line;
	private String[] column = new String[]{"Name", "E-mail", "Localization"};
	
	public MemberTableModel(List<Member> members) {
		this.line = new ArrayList<>(members);
		
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
		} else if (columnIndex == COL_LOCALIZATION) {
			return m.getLocalization();
		} else if (columnIndex == COL_EMAIL) {
			return m.getEmail();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		Member u = line.get(row);
		if (column == COL_NAME) {
			u.setName(aValue.toString());
		} else if (column == COL_EMAIL) {
			u.setEmail(aValue.toString());
		} else if (column == COL_LOCALIZATION) {
			u.setLocalization(aValue.toString());
		}
	}
	
	public Member getMember(int index){
		return line.get(index);
	}

	public void addMember(Member member) {
		line.add(member);
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
