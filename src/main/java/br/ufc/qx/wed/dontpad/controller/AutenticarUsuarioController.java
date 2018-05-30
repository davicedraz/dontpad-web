package main.java.br.ufc.qx.wed.dontpad.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.br.ufc.qx.wed.dontpad.dao.UsuarioDAO;
import main.java.br.ufc.qx.wed.dontpad.model.Usuario;

@WebServlet(urlPatterns="/autenticar")
public class AutenticarUsuarioController extends MyDontpadController{

	private static final String FALHA_NA_AUTENTICACAO = "Usu&aacute;rio ou senha inv&aacute;lidos";
	private static final String FLAG_AUTENTIFICADO = "autenticado";
	private static final long serialVersionUID = 1979208128920557545L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//TODO: Implementar a autenticação do usuario.
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.setConexao((Connection)request.getAttribute("connection"));
		
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		
		if(usuarioDAO.autenticar(email, senha)){
						
			HttpSession session = request.getSession(true);
			session.setAttribute(FLAG_AUTENTIFICADO, true);
			sucessoMensagem(FLAG_AUTENTIFICADO, request);
			
			Usuario usuario = usuarioDAO.getUsuario(email);
			session.setAttribute("usuario", usuario);
			
			response.sendRedirect("c/"+usuario.getNome());
		}
		else{
			request.setAttribute(FLAG_AUTENTIFICADO, false);
			erroMensagem(FALHA_NA_AUTENTICACAO, request);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
				
	}

}
