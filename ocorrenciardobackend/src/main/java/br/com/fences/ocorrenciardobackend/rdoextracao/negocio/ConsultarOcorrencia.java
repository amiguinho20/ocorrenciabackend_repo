package br.com.fences.ocorrenciardobackend.rdoextracao.negocio;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.fences.ocorrenciardobackend.rdoextracao.dao.ConsultarOcorrenciaDAO;


@RequestScoped
public class ConsultarOcorrencia implements Serializable{

	private static final long serialVersionUID = 3703047507341298911L;

	private Logger logger =  LogManager.getLogger(ConsultarOcorrencia.class);
	
	@Inject
	private ConsultarOcorrenciaDAO consultarOcorrenciaDAO;

	public String consultarOcorrencia(String idDelegacia, String anoBo, String numBo)
	{
    	String retorno = consultarOcorrenciaDAO.consultar(idDelegacia, anoBo, numBo);
    	return retorno;
	}
	

}
