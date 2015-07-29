package br.com.fences.ocorrenciardobackend.tratamentoerro.bind;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.fences.ocorrenciardobackend.tratamentoerro.entity.Erro;
import br.com.fences.ocorrenciardobackend.tratamentoerro.exception.RestException;
import br.com.fences.ocorrenciardobackend.tratamentoerro.exception.RestRuntimeException;

@Provider
public class ExceptionMapperImpl implements ExceptionMapper<Throwable> {
	
	private Logger logger =  LogManager.getLogger(ExceptionMapperImpl.class);
	
	public ExceptionMapperImpl() {}
	
	@Override
	public Response toResponse(Throwable excecao) {
		Erro erro = null;
		if (excecao instanceof RestRuntimeException) 
		{
			RestRuntimeException restException = (RestRuntimeException) excecao;
			erro = new Erro(restException.getCodigoErro(), restException.getMessage());
		} 
		else if (excecao instanceof RestException)
		{
			RestException restException = (RestException) excecao;
			erro = new Erro(restException.getCodigoErro(), restException.getMessage());
		}
		else
		{
			logger.error("Erro inesperado REST", excecao);
			erro = new Erro("Erro inesperado REST [" + excecao.getMessage() + "]");
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(erro).build();
	}
}