package vo;

public class ClientVO {
	private int id;
	private String nome;
	private String telefone;
	private String celular;
	private String eMail;
	private String login;
	private String senha;
	private String roteador;

	private String tipodeCabo;
	private EnderecoVO enderecoVO;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getRoteador() {
		return roteador;
	}

	public void setRoteador(String roteador) {
		this.roteador = roteador;
	}

	public String getTipodeCabo() {
		return tipodeCabo;
	}

	public void setTipodeCabo(String tipodeCabo) {
		this.tipodeCabo = tipodeCabo;
	}

	public EnderecoVO getEnderecoVO() {
		return enderecoVO;
	}

	public void setEnderecoVO(EnderecoVO enderecoVO) {
		this.enderecoVO = enderecoVO;
	}

}
