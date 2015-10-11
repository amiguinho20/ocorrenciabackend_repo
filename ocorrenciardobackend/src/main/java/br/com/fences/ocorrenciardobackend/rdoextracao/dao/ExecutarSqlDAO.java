package br.com.fences.ocorrenciardobackend.rdoextracao.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

import br.com.fences.ocorrenciardobackend.util.DaoUtil;

@RequestScoped
public class ExecutarSqlDAO {
	
	@Resource(mappedName = "ds/Db2Rdo")
	private DataSource dataSource;
	
    public List<List<String>> efetuarConsulta(String query)
    {
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;

        List<List<String>> linhas = new ArrayList<List<String>>();
        List<String> colunas = null;
        int qtdColuna = 0;

        try
        {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            stmt.setMaxRows(1000);
            rset = stmt.executeQuery(query);

            while (rset.next())
            {

                // -- cabecalho
                if (linhas.isEmpty())
                {
                    ResultSetMetaData rsmd = rset.getMetaData();
                    qtdColuna = rsmd.getColumnCount();
                    colunas = new ArrayList<String>();

                    for (int i = 1; i <= qtdColuna; i++)
                    {
                        colunas.add(rsmd.getColumnName(i));
                    }
                    linhas.add(colunas);
                }

                // -- conteudo
                colunas = new ArrayList<String>();
                for (int i = 1; i <= qtdColuna; i++)
                {
                    colunas.add(rset.getString(i));
                }
                linhas.add(colunas);

            }

            if (linhas.isEmpty())
            {
                colunas = new ArrayList<String>();
                colunas.add("Nao retornou resultado.");
                linhas.add(colunas);
            }

        }
        catch (Exception e)
        {
        	throw new RuntimeException(e);
        }
        finally
        {
        	DaoUtil.fecharRecurso(rset, stmt, conn);
        }

        return linhas;

    }

}
