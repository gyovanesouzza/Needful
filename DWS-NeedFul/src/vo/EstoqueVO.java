package vo;

import java.math.BigDecimal;

public class EstoqueVO {

	private int Codigo;
	private int statusAD;
	private String Material;
	private String Qts;
	private BigDecimal Preco;
	private String QtsInicia;
	private String tipo;
	private StatusVO statusVO;
	
	public int getCodigo() {
		return Codigo;
	}
	public void setCodigo(int codigo) {
		Codigo = codigo;
	}
	public String getMaterial() {
		return Material;
	}
	public void setMaterial(String material) {
		Material = material;
	}
	public String getQts() {
		return Qts;
	}
	public void setQts(String qts) {
		Qts = qts;
	}
	public BigDecimal getPreco() {
		return Preco;
	}
	public void setPreco(BigDecimal preco) {
		Preco = preco;
	}
	
	public String getQtsInicia() {
		return QtsInicia;
	}
	public void setQtsInicia(String qtsInicia) {
		QtsInicia = qtsInicia;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public StatusVO getStatusVO() {
		return statusVO;
	}
	public void setStatusVO(StatusVO statusVO) {
		this.statusVO = statusVO;
	}
	public int getStatusAD() {
		return statusAD;
	}
	public void setStatusAD(int statusAD) {
		this.statusAD = statusAD;
	}
	
	
}
