package br.com.fences.ocorrenciardobackend.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Wrapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class DaoUtil {
	
	private static DateFormat dfData = new SimpleDateFormat("yyyyMMdd");
	private static DateFormat dfDataHora = new SimpleDateFormat("yyyyMMddHHmmss");


    /**
     * A passagem de parametros deve estar na ordem de fechamento. 
     * Exemplos: 
     * resultset, preparedstatement ou statement, connection;
     * preparedstatement ou statement, connection;
     * connection;
     * @param Wrapper (ResultSet, Statement, PreparedStatement, Connection) objetos
     */
    public static void fecharRecurso(Wrapper... objetos)
    {
        for (Wrapper objeto : objetos)
        {
            try
            {
                if (objeto instanceof ResultSet)
                {
                    ResultSet rset = (ResultSet) objeto;
                    rset.close();
                }
                if (objeto instanceof Statement)
                {
                    Statement stmt = (Statement) objeto;
                    stmt.close();
                }
                if (objeto instanceof PreparedStatement)
                {
                    PreparedStatement pstm = (PreparedStatement) objeto;
                    pstm.close();
                }
                if (objeto instanceof Connection)
                {
                    Connection conn = (Connection) objeto;
                    conn.close();
                }
            }
            catch(Exception e)
            {
                //-- nao tratar
            }
        }
    }	
    
	public static String converterValorParaString(ResultSet rset, String coluna) throws SQLException
	{
		String valor = "";
		Object obj = rset.getObject(coluna);
		
		if (obj instanceof Date)
		{
			Date data = rset.getDate(coluna);
			valor = dfData.format(data);
		}
		else if (obj instanceof Timestamp)
		{
			Timestamp dataHora = rset.getTimestamp(coluna);
			valor = dfDataHora.format(dataHora);
		}
		else
		{
			valor = rset.getString(coluna);
		}
		return valor;
	}
	
}