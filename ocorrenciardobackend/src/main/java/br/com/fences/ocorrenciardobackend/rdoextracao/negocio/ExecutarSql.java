package br.com.fences.ocorrenciardobackend.rdoextracao.negocio;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.fences.ocorrenciardobackend.rdoextracao.dao.ExecutarSqlDAO;

@RequestScoped
public class ExecutarSql {

	@Inject
	private ExecutarSqlDAO executarSqlDAO;

	public List<List<String>> efetuarConsulta(String query) {
		List<List<String>> contagem = null;
		contagem = executarSqlDAO.efetuarConsulta(query);
		return contagem;
	}

}
