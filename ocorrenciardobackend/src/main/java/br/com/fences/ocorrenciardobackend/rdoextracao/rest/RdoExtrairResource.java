package br.com.fences.ocorrenciardobackend.rdoextracao.rest;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.fences.fencesutils.conversor.InputStreamParaJson;
import br.com.fences.fencesutils.conversor.converter.ColecaoJsonAdapter;
import br.com.fences.fencesutils.verificador.Verificador;
import br.com.fences.ocorrenciaentidade.chave.OcorrenciaChave;
import br.com.fences.ocorrenciardobackend.converter.Converter;
import br.com.fences.ocorrenciardobackend.rdoextracao.negocio.ConsultarOcorrencia;
import br.com.fences.ocorrenciardobackend.rdoextracao.negocio.ExecutarSql;
import br.com.fences.ocorrenciardobackend.rdoextracao.negocio.ListarOcorrencia;
import br.com.fences.ocorrenciardobackend.tratamentoerro.exception.RestRuntimeException;


@RequestScoped
@Path("/rdoextrair")
public class RdoExtrairResource {
	
	private Logger logger =  LogManager.getLogger(RdoExtrairResource.class);

	@Inject
	private ListarOcorrencia listarOcorrencia;
	
	@Inject
	private ConsultarOcorrencia consultarOcorrencia;
	
	@Inject
	private Converter<OcorrenciaChave> converter;
	
	@Inject
	private ExecutarSql executarSql;
	
	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private Gson gson = new GsonBuilder()
			.registerTypeHierarchyAdapter(Collection.class, new ColecaoJsonAdapter())
			.create();

	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pesquisarRouboDeCarga/{dataRegistroInicial}/{dataRegistroFinal}")
    public String pesquisarRouboDeCarga(
    		@PathParam("dataRegistroInicial") String dataRegistroInicial,
    		@PathParam("dataRegistroFinal") String dataRegistroFinal) 
    {
    	validarDatas(dataRegistroInicial, dataRegistroFinal);
    	
   		Set<OcorrenciaChave> chaves = listarOcorrencia.pesquisarRouboDeCarga(dataRegistroInicial, dataRegistroFinal, true);
   		String json = converter.paraJson(chaves);
    	return json;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pesquisarPorDataDeRegistro/{dataRegistroInicial}/{dataRegistroFinal}")
    public String pesquisarPorDataDeRegistro(
    		@PathParam("dataRegistroInicial") String dataRegistroInicial,
    		@PathParam("dataRegistroFinal") String dataRegistroFinal) 
    {
    	validarDatas(dataRegistroInicial, dataRegistroFinal);
    	
   		Set<OcorrenciaChave> chaves = listarOcorrencia.pesquisarPorDataDeRegistro(dataRegistroInicial, dataRegistroFinal);
   		String json = converter.paraJson(chaves);
    	return json;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("consultarOcorrencia/{idDelegacia}/{anoBo}/{numBo}")
    public String consultarOcorrencia(
    		@PathParam("idDelegacia") String idDelegacia,
    		@PathParam("anoBo") String anoBo,
    		@PathParam("numBo") String numBo) 
    {
    	validarChave(idDelegacia, anoBo, numBo);
    	
   		String ocorrencia = consultarOcorrencia.consultarOcorrencia(idDelegacia, anoBo, numBo);
    	return ocorrencia;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("executarSql") 
    public String executarSql(InputStream ipFiltros) 
    {
    	String sql = InputStreamParaJson.converter(ipFiltros);
    	String resultado = null;
    	if (Verificador.isValorado(sql))
    	{
	    	//String sql = gson.fromJson(json, String.class);
	
	    	List<List<String>> registros = executarSql.efetuarConsulta(sql);
	 
	    	resultado = gson.toJson(registros);
    	}
    	else
    	{
    		resultado = "A query esta vazia.";
    	}
    	
    	return resultado;
    }
    
    
    private void validarChave(String idDelegacia, String anoBo, String numBo) {
    	try
    	{
    		Integer.parseInt(idDelegacia);
    	}
    	catch(Exception e)
    	{
    		throw new RestRuntimeException(1, "o idDelegacia [" + idDelegacia + "] nao e' um inteiro valido.", e);
    	}
    	try
    	{
    		Integer.parseInt(anoBo);
    	}
    	catch(Exception e)
    	{
    		throw new RestRuntimeException(2, "o anoBo [" + anoBo + "] nao e' um inteiro valido.", e);
    	}
    	try
    	{
    		Integer.parseInt(numBo);
    	}
    	catch(Exception e)
    	{
    		throw new RestRuntimeException(3, "o numBo [" + numBo + "] nao e' um inteiro valido.", e);
    	}
	}

	private void validarDatas(String dataRegistroInicial, String dataRegistroFinal)
    {
    	Date dataInicial = null;
    	Date dataFinal = null;
    	try
    	{
    		if (dataRegistroInicial.length() != 14) throw new Exception();
    		dataInicial = df.parse(dataRegistroInicial);
    	}
    	catch(Exception e)
    	{
    		throw new RestRuntimeException(1, "a data inicial [" + dataRegistroInicial + "] nao foi informada em no formato yyyyMMddHHmmss", e);
    	}
    	try
    	{
    		if (dataRegistroFinal.length() != 14) throw new Exception();
    		dataFinal = df.parse(dataRegistroFinal);
    	}
    	catch(Exception e)
    	{
    		throw new RestRuntimeException(2, "a data final [" + dataRegistroFinal + "] nao foi informada em no formato yyyyMMddHHmmss", e);
    	}
    	if (dataInicial.after(dataFinal))
    	{
    		throw new RestRuntimeException(3, "a data inicial [" + dataRegistroInicial + "] nao pode ser maior que a data final [" + dataRegistroFinal + "]");
    	}
    	Date dataCorrente = Calendar.getInstance().getTime();
    	if (dataFinal.after(dataCorrente))
    	{
    		throw new RestRuntimeException(3, "a data final [" + dataRegistroFinal + "] nao pode ser maior que a corrente [" + df.format(dataCorrente) + "]");
    	}
    }
	
}
