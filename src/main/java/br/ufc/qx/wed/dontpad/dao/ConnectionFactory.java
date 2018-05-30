package main.java.br.ufc.qx.wed.dontpad.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static final String BANCO_DE_DADOS_SENHA = "root";
	private static final String BANCO_DE_DADOS_USUARIO = "root";
	private static final String BANCO_DE_DADOS_URL = "jdbc:mysql://localhost/mydontpad";

	public Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(BANCO_DE_DADOS_URL,
					BANCO_DE_DADOS_USUARIO, BANCO_DE_DADOS_SENHA);
		} catch (SQLException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
