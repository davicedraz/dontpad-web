package main.java.br.ufc.qx.wed.dontpad.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.br.ufc.qx.wed.dontpad.dao.CadernoDAO;
import main.java.br.ufc.qx.wed.dontpad.model.Caderno;
import main.java.br.ufc.qx.wed.dontpad.model.Usuario;

@WebServlet(urlPatterns="/ajax")
public class CadernoAjaxController extends MyDontpadController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3900676478655565698L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	
	}

}
