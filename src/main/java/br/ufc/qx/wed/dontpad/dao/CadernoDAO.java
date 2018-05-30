package main.java.br.ufc.qx.wed.dontpad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import main.java.br.ufc.qx.wed.dontpad.model.Caderno;

public class CadernoDAO {

	private static final String INSERT_QUERY = "INSERT INTO caderno "
			+ "(nome, publico, criacao, atualizacao, id_dono) "
			+ "values (?, ?, ?, ?, ?)";

	
	private static final String INSERT_SEM_DONO_QUERY = "INSERT INTO caderno "
			+ "(nome, publico, criacao, atualizacao) "
			+ "values (?, ?, ?, ?)";

	
	private static final String BUSCAR_PELO_NOME_QUERY = "SELECT * from caderno where nome = ?";
	private static final String BUSCAR_PELO_ID_QUERY = "SELECT * from caderno where id_caderno = ?";
	private static final String ATUALIZAR_CADERNO = "UPDATE caderno " 
			+ " SET conteudo = ?, atualizacao = ?, publico = ?"
			+ " WHERE id_caderno = ?";
	
	
	private Connection conexao;

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}
	
	public Caderno adiciona(Caderno caderno){
		PreparedStatement statement = null;
		try {
			if(caderno.getDono() != null){
				statement = conexao.prepareStatement(INSERT_QUERY);
				statement.setLong(5, caderno.getDono().getId());
			} else {
				statement = conexao.prepareStatement(INSERT_SEM_DONO_QUERY);
			}

			statement.setString(1, caderno.getNome());
			statement.setBoolean(2, caderno.isPublico());
			
			Timestamp dataCriacao = new Timestamp(System.currentTimeMillis());
			
			statement.setTimestamp(3, dataCriacao);
			statement.setTimestamp(4, dataCriacao);
			

			statement.execute();
			
			ResultSet keys = statement.getGeneratedKeys();
			if(keys.next()){
				caderno.setId(keys.getInt(1));
			}
			
			statement.close();
			
			return caderno;

		} catch (MySQLIntegrityConstraintViolationException e) {
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Caderno getCaderno(String nome){
		try(PreparedStatement statement = conexao.prepareStatement(BUSCAR_PELO_NOME_QUERY)){
			statement.setString(1, nome);
			try(ResultSet resultado = statement.executeQuery()){
				if(resultado.first()){
					return cadernoMapper(statement, resultado);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public Caderno getCaderno(Integer id) {
		try(PreparedStatement statement = conexao.prepareStatement(BUSCAR_PELO_ID_QUERY)){
			statement.setInt(1, id);
			try(ResultSet resultado = statement.executeQuery()){
				if(resultado.first()){
					return cadernoMapper(statement, resultado);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	private Caderno cadernoMapper(PreparedStatement statement, ResultSet resultado) throws SQLException {
		Caderno caderno = new Caderno();
		caderno.setId(resultado.getInt(1));
		caderno.setNome(resultado.getString(2));
		caderno.setConteudo(resultado.getString(3));
		caderno.setPublico(resultado.getBoolean(4));
		caderno.setCriacao(resultado.getTimestamp(5));
		caderno.setUltimaAtualizacao(resultado.getTimestamp(6));
		UsuarioDAO dao = new UsuarioDAO();
		dao.setConexao(conexao);
		caderno.setDono(dao.getUsuario(resultado.getLong(7)));
		resultado.close();
		statement.close();
		return caderno;
	}

	public boolean salvar(Caderno caderno) {
		try(PreparedStatement statement = conexao.prepareStatement(ATUALIZAR_CADERNO)){
			statement.setString(1, caderno.getConteudo());
			statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			statement.setBoolean(3, caderno.isPublico());
			statement.setLong(4, caderno.getId());
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
