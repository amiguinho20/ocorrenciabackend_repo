package br.com.fences.ocorrenciardobackend.rdoextracao.negocio;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.fences.ocorrenciardobackend.rdoextracao.dao.UsuarioDAO;

@RequestScoped
public class UsuarioBO {

	@Inject
	private UsuarioDAO usuarioDAO;
	
	public String consultar(String rgUsuario)
	{
		return usuarioDAO.consultar(rgUsuario);
	}
	
	public String listar()
	{
		return usuarioDAO.listar();
	}
	
}
