package main.java.br.ufc.qx.wed.dontpad.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import main.java.br.ufc.qx.wed.dontpad.dao.ConnectionFactory;

@WebFilter("/*")
public class InjectConnectionFilter implements Filter{
	
	private ConnectionFactory factory;
	
	public InjectConnectionFilter() {
		setFactory(new ConnectionFactory());
	}

	public ConnectionFactory getFactory() {
		return factory;
	}

	public void setFactory(ConnectionFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		Connection connection = getFactory().getConnection();
		request.setAttribute("connection", connection);
		chain.doFilter(request, response);
		try {connection.close();} catch (SQLException e) {}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
