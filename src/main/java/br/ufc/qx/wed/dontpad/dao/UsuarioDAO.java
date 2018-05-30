package main.java.br.ufc.qx.wed.dontpad.dao;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import main.java.br.ufc.qx.wed.dontpad.model.Usuario;

public class UsuarioDAO {

	private static final int COL_NOME_DO_USUARIO = 1;
	private static final int COL_EMAIL = 2;
	private static final int COL_SENHA = 3;

	private static final String COL_LABEL_ID = "id";
	private static final String COL_LABEL_EMAIL = "email";
	private static final String COL_LABEL_NOME = "nome";
	
	private Connection conexao;
	private static final String AUTENTICACAO_QUERY = "SELECT * FROM usuario WHERE email = ? and senha = ?";
	
	private static final String BUSCAR_PELO_LOGIN_QUERY = "SELECT * FROM usuario where email = ?";
	
	private static final String SELECT_ALL_QUERY = "SELECT * FROM usuario";
	
	private static final String INSERT_QUERY = "INSERT INTO usuario "
			+ " (nome, email, senha)  values (?, ?, ?)";
	
	private static final String ALTERAR_SENHA_QUERY = "UPDATE usuario SET senha=? WHERE login=? and senha=?";
	private static final String BUSCAR_PELO_ID_QUERY = "SELECT * FROM usuario where id = ?";

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}

	public Usuario adiciona(Usuario usuario) {

		PreparedStatement statement = null;
		try {
			statement = conexao.prepareStatement(INSERT_QUERY);

			statement.setString(COL_NOME_DO_USUARIO, usuario.getNome());
			statement.setString(COL_EMAIL, usuario.getEmail());
			statement.setString(COL_SENHA, cripitografar(usuario.getSenha()));

			statement.execute();
			
			ResultSet keys = statement.getGeneratedKeys();
			if(keys.next()){
				usuario.setId(keys.getLong(1));
			}

			return usuario;

		} catch (MySQLIntegrityConstraintViolationException e) {
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} 

	}

	public List<Usuario> getTodos() {
		try (PreparedStatement statement = conexao.prepareStatement(SELECT_ALL_QUERY);
				ResultSet resultado = statement.executeQuery();) {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			while (resultado.next()) {
				Usuario usuario = getUsuarioApartirDoResultado(resultado);
				usuarios.add(usuario);
			}
			return usuarios;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	static Usuario getUsuarioApartirDoResultado(ResultSet resultado) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(resultado.getLong(COL_LABEL_ID));
		usuario.setEmail(resultado.getString(COL_LABEL_EMAIL));
		usuario.setNome(resultado.getString(COL_LABEL_NOME));
		return usuario;
	}
	
	public boolean autenticar(String email, String senha){
		
		try (PreparedStatement statement = conexao.prepareStatement(AUTENTICACAO_QUERY)) {
			
			statement.setString(1, email);
			statement.setString(2, cripitografar(senha));
			
			try(ResultSet result = statement.executeQuery()){
				return result.first();
			}
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Usuario getUsuario(String login){
		try(PreparedStatement statement = conexao.prepareStatement(BUSCAR_PELO_LOGIN_QUERY)){
			statement.setString(1, login);
			try(ResultSet resultado = statement.executeQuery()){
				if(resultado.first()){
					return getUsuarioApartirDoResultado(resultado);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	public Usuario getUsuario(Long id){
		
		try(PreparedStatement statement = conexao.prepareStatement(BUSCAR_PELO_ID_QUERY)){
			
			statement.setLong(1, id);
			
			try(ResultSet result = statement.executeQuery()){
				if(result.first()){
					return getUsuarioApartirDoResultado(result);
				}
			
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return null;
	}
	
	public boolean alterarSenha(String login, String senhaAtual, String novaSenha){
		
		try(PreparedStatement statement = conexao.prepareStatement(ALTERAR_SENHA_QUERY)){
			statement.setString(1, cripitografar(novaSenha));
			statement.setString(2, login);
			statement.setString(3, cripitografar(senhaAtual));
			return 1 == statement.executeUpdate();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String cripitografar(String texto){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] textoEmBytes = md.digest(texto.getBytes("UTF-8"));
			return new BigInteger(1, textoEmBytes).toString(16);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
