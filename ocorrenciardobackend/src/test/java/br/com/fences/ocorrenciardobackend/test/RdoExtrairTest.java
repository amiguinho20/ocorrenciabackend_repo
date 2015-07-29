package br.com.fences.ocorrenciardobackend.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

public class RdoExtrairTest {

	private Client client;
	private WebTarget selecionarChavesTarget;
	private WebTarget selecionarOcorrenciaTarget;

	@Before
	public void setUp() {
		client = ClientBuilder.newClient();
		selecionarChavesTarget = client
				.target("http://localhost:8080/ocorrenciardobackend/rest/rdoextrair/pesquisarRouboDeCarga/{dataRegistroInicial}/{dataRegistroFinal}");

		selecionarOcorrenciaTarget = client
				.target("http://localhost:8080/ocorrenciardobackend/rest/rdoextrair/consultarOcorrencia/{idDelegacia}/{anoBo}/{numBo}");

	}

	@Test
	public void selecionarOcorrencia() {

		Response response = selecionarOcorrenciaTarget
			.resolveTemplate("idDelegacia","1A")
			.resolveTemplate("anoBo","2015")
			.resolveTemplate("numBo","3")
			.request(MediaType.APPLICATION_JSON)
			.get();
		
		String json = response.readEntity(String.class);
		System.out.println("retorno: "+ json);
	}
	
	
	@Test
	public void selecionarChaves() {
	
		Response response = selecionarChavesTarget
				.resolveTemplate("dataRegistroInicial","20150101000000A")
				.resolveTemplate("dataRegistroFinal","20150101235959")
				.request(MediaType.APPLICATION_JSON)
				.get();		
		
		String json = response.readEntity(String.class);
		System.out.println("retorno: "+ json);
	}
	
}
