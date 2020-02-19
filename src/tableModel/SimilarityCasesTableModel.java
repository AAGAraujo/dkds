package tableModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import rbc.business.SimilaridadeCasos;

public class SimilarityCasesTableModel extends AbstractTableModel{
	
	private static final int COL_SIMILARITYCASE = 0;
	private static final int COL_SIMILARITY = 1;
	private static final int COL_EFFECTIVENESS = 2;
	List<SimilaridadeCasos> line;
	private String[] column = new String[]{"Similarity Case","Similarity","Effectiveness"};
	
	public SimilarityCasesTableModel(List<SimilaridadeCasos> similarityCases) {
		this.line = new ArrayList<>(similarityCases);
		
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
		
		SimilaridadeCasos m = line.get(rowIndex);
		
		if (columnIndex == COL_SIMILARITYCASE) {
			return m.getCaso1().getDescricao();
		}else if(columnIndex == COL_SIMILARITY){
			return m.getSimilaridade();
		}else if(columnIndex == COL_EFFECTIVENESS){
			return m.getEffectiveness();
		}
		return "";
	}
	
	
	public void setValueAt(Object aValue, int row, int column) {
		SimilaridadeCasos u = line.get(row);
		if (column == COL_SIMILARITYCASE) {
			u.getCaso1().setDescricao(aValue.toString());
		}else if(column == COL_SIMILARITY){
			u.setSimilaridade(Double.parseDouble(aValue.toString()));
		}else if(column == COL_EFFECTIVENESS){
			u.setEffectiveness(Double.parseDouble(aValue.toString()));
		}
	}
	
	public SimilaridadeCasos getSimilarityCases(int index){
		return line.get(index);
	}

	public void addSimilarityCases(SimilaridadeCasos similarityCases) {
		line.add(similarityCases);
		int lastIndex = getRowCount() - 1;
		fireTableRowsInserted(lastIndex, lastIndex);

	}

	public void updateSimilarityCases(int lineIndex, SimilaridadeCasos similarityCases) {
		line.set(lineIndex, similarityCases);
		fireTableRowsUpdated(lineIndex, lineIndex);

	}

	public void removeSimilarityCases(int lineIndex) {
		line.remove(lineIndex);
		fireTableRowsDeleted(lineIndex, lineIndex);

	}

}
