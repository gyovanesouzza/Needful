package tabelModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import vo.EstoqueVO;
import vo.StatusVO;

public class EstoqueTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<EstoqueVO> dados = new ArrayList<>();

	private String[] colunas = { "ID", "Produto", "Qts", "Preço", "Status" };

	public EstoqueTableModel() {
		dados = new ArrayList<>();
	}

	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return dados.size();

	}

	@Override
	public Object getValueAt(int linha, int coluna) {

		switch (coluna) {

		case 0:
			return dados.get(linha).getCodigo();
		case 1:
			return dados.get(linha).getMaterial();
		case 2:
			return dados.get(linha).getQts() +" "+ dados.get(linha).getTipo();
		case 3:
			return "R$ "+dados.get(linha).getPreco();
		case 4:
			return dados.get(linha).getStatusVO().getTipo();

		}

		return null;
	}

	@Override
	public void setValueAt(Object valor, int linha, int coluna) {
		switch (coluna) {

		case 0:
			dados.get(linha).setCodigo(Integer.parseInt((String) valor));
			break;
		case 1:
			dados.get(linha).setMaterial((String) valor);
			break;
		case 2:
			dados.get(linha).setQts((String) valor);
			break;
		case 3:
			dados.get(linha).setPreco(new BigDecimal((String) valor));
			break;
		case 4:
			StatusVO statusVO = new StatusVO();
			statusVO.setTipo((String) valor);
			dados.get(linha).setStatusVO(statusVO);
			break;

		}
		this.fireTableRowsUpdated(linha, linha);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		return super.isCellEditable(rowIndex, columnIndex);
	}

	public void addRow(EstoqueVO e) {
		this.dados.add(e);
		this.fireTableDataChanged();

	}

	public void removeRow(int linha) {
		this.dados.remove(linha);
		this.fireTableRowsDeleted(linha, linha);
	}

}
