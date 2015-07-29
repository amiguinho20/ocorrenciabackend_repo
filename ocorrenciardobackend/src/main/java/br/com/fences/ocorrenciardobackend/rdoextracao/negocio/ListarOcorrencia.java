package br.com.fences.ocorrenciardobackend.rdoextracao.negocio;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.fences.ocorrenciaentidade.chave.OcorrenciaChave;
import br.com.fences.ocorrenciardobackend.rdoextracao.dao.ListarOcorrenciaDAO;


@RequestScoped
public class ListarOcorrencia implements Serializable{

	private static final long serialVersionUID = 3703047507341298911L;

	private Logger logger =  LogManager.getLogger(ListarOcorrencia.class);
	
	@Inject
	private ListarOcorrenciaDAO listarOcorrenciaDAO;

	public ListarOcorrencia() {
	}

	public Set<OcorrenciaChave> pesquisarRouboDeCarga(
			String dataRegistroInicial, String dataRegistroFinal,
			boolean recuperarComplementares) 
	{
		//-- garante unicidade e ordenacao na ordem de insercao
    	Set<OcorrenciaChave> ocorrenciasChaves = new LinkedHashSet<>(); 
		ocorrenciasChaves = listarOcorrenciaDAO
				.pesquisarRouboDeCarga(dataRegistroInicial,
						dataRegistroFinal, recuperarComplementares);
    	
    	return ocorrenciasChaves;
	}
	
	public Set<OcorrenciaChave> pesquisarPorDataDeRegistro(
			String dataRegistroInicial, String dataRegistroFinal) 
	{
    	Set<OcorrenciaChave> ocorrenciasChaves = new LinkedHashSet<>(); 
		ocorrenciasChaves = listarOcorrenciaDAO
				.pesquisarPorDataDeRegistro(dataRegistroInicial,
						dataRegistroFinal);
    	return ocorrenciasChaves;
	}
	

}
