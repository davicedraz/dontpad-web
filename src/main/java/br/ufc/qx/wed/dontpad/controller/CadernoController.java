package main.java.br.ufc.qx.wed.dontpad.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.br.ufc.qx.wed.dontpad.dao.CadernoDAO;
import main.java.br.ufc.qx.wed.dontpad.dao.UsuarioDAO;
import main.java.br.ufc.qx.wed.dontpad.model.Caderno;
import main.java.br.ufc.qx.wed.dontpad.model.Usuario;

@WebServlet("/c/*")
public class CadernoController extends MyDontpadController {

	private static final long serialVersionUID = 8405544168385641521L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//TODO: Criar e carregar caderno

		String nomeCadernoURL = getNomeCaderno(req);

		CadernoDAO cadernoDAO = new CadernoDAO();
		cadernoDAO.setConexao((Connection)req.getAttribute("connection"));

		Caderno caderno = cadernoDAO.getCaderno(nomeCadernoURL);

		if(caderno != null){ //caderno já existe
			req.setAttribute("caderno", caderno);
			req.getRequestDispatcher("/WEB-INF/views/caderno.jsp").forward(req, resp);
		}
		else{ //caderno não existe ainda
			HttpSession session = req.getSession(false);
			Usuario usuario = (Usuario) session.getAttribute("usuario");

			if(usuario != null){//existe usuario logado
				Caderno caderno2 = new Caderno(usuario.getNome(), usuario, true);
				cadernoDAO.adiciona(caderno2);
				req.setAttribute("caderno", caderno2);
				req.getRequestDispatcher("/WEB-INF/views/caderno.jsp").forward(req, resp);
			}
			else{//nao existe usuario logado
				Caderno caderno2 = new Caderno(nomeCadernoURL, null, true);
				cadernoDAO.adiciona(caderno2);
				req.setAttribute("caderno", caderno2);
				req.getRequestDispatcher("/WEB-INF/views/caderno.jsp").forward(req, resp);
			}
		}
	}

	public String getNomeCaderno(HttpServletRequest req){
		//TODO: Recuperar nome do caderno
		String uri = req.getRequestURI();
		return uri.substring(uri.lastIndexOf("/"));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//TODO: Atualizar caderno

		CadernoDAO cadernoDAO = new CadernoDAO();
		cadernoDAO.setConexao((Connection)req.getAttribute("connection"));

		String nomeCadernoURL = getNomeCaderno(req);

		HttpSession session = req.getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		String conteudo = req.getParameter("conteudo");
		String visibilidade = req.getParameter("visibilidade");
		int id = Integer.parseInt(req.getParameter("id"));

		System.out.println(visibilidade);
		
		if(usuario != null){//usuario esta logado
			Caderno caderno = new Caderno();
			caderno.setId(id);
			caderno.setConteudo(conteudo);
			caderno.setNome(usuario.getNome());
			caderno.setDono(usuario);

			if(visibilidade != null){
				caderno.setPublico(false);
			}else{
				caderno.setPublico(true);
			}
			
			cadernoDAO.salvar(caderno);

			req.setAttribute("caderno", caderno);
			req.getRequestDispatcher("/WEB-INF/views/caderno.jsp").forward(req, resp);
		}
		
		else{//nao existe usuario logado
			Caderno caderno = new Caderno();
			caderno.setId(id);
			caderno.setConteudo(conteudo);
			caderno.setNome(nomeCadernoURL);
			caderno.setDono(null);
			caderno.setPublico(true);

			cadernoDAO.salvar(caderno);

			req.setAttribute("caderno", caderno);
			req.getRequestDispatcher("/WEB-INF/views/caderno.jsp").forward(req, resp);
		}
	}
}
