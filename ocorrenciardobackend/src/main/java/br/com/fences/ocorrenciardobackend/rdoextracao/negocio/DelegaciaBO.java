package br.com.fences.ocorrenciardobackend.rdoextracao.negocio;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.fences.ocorrenciardobackend.rdoextracao.dao.DelegaciaDAO;

@RequestScoped
public class DelegaciaBO {
	
	@Inject
	private DelegaciaDAO delegaciaDAO;
	
	
	public String consultar(String idDelegacia)
	{
		return delegaciaDAO.consultar(idDelegacia);
	}
	
	public String listar()
	{
		return delegaciaDAO.listar();
	}
	

}
