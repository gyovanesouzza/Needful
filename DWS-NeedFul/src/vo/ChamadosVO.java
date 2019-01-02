package vo;

import java.util.Date;
import java.io.Serializable;
import java.sql.Time;


public class ChamadosVO implements Serializable {
	
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
    private Date data;
    private Time horas;
    private Date agendamento_Data;
    private Time agendamento_horas;
    private String descricao;
    private String tipo;
    private Date confirmacao_Data;
    private Time confirmacao_Horas;
    private Date finalizacao_Data;
    private Time finalizacao_horas;
    private String justificativa;
    private StatusVO statusVO;
    private TecnicoVO tecnicoVO;
    private ClientVO clientVO;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHoras() {
        return horas;
    }

    public void setHoras(Time horas) {
        this.horas = horas;
    }

    public Date getAgendamento_Data() {
        return agendamento_Data;
    }

    public void setAgendamento_Data(Date agendamento_Data) {
        this.agendamento_Data = agendamento_Data;
    }

    public Time getAgendamento_horas() {
        return agendamento_horas;
    }

    public void setAgendamento_horas(Time agendamento_horas) {
        this.agendamento_horas = agendamento_horas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getConfirmacao_Data() {
        return confirmacao_Data;
    }

    public void setConfirmacao_Data(Date confirmacao_Data) {
        this.confirmacao_Data = confirmacao_Data;
    }

    public Time getConfirmacao_Horas() {
        return confirmacao_Horas;
    }

    public void setConfirmacao_Horas(Time confirmacao_Horas) {
        this.confirmacao_Horas = confirmacao_Horas;
    }

    public Date getFinalizacao_Data() {
        return finalizacao_Data;
    }

    public void setFinalizacao_Data(Date finalizacao_Data) {
        this.finalizacao_Data = finalizacao_Data;
    }

    public Time getFinalizacao_horas() {
        return finalizacao_horas;
    }

    public void setFinalizacao_horas(Time finalizacao_horas) {
        this.finalizacao_horas = finalizacao_horas;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public StatusVO getStatusVO() {
        return statusVO;
    }

    public void setStatusVO(StatusVO statusVO) {
        this.statusVO = statusVO;
    }

    public TecnicoVO getTecnicoVO() {
        return tecnicoVO;
    }

    public void setTecnicoVO(TecnicoVO tecnicoVO) {
        this.tecnicoVO = tecnicoVO;
    }

    public ClientVO getClientVO() {
        return clientVO;
    }

    public void setClientVO(ClientVO clientVO) {
        this.clientVO = clientVO;
    }

}